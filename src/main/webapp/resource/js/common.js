function drop_confirm(msg, url){
    if(confirm(msg)){
        window.location = url;
    }
}

function ajax_confirm(msg, url){
    if(confirm(msg)){
    	ajaxget(url);
    }
}

function go(url){
    window.location = url;
}
/* 格式化金额 */
function price_format(price){
    if(typeof(PRICE_FORMAT) == 'undefined'){
        PRICE_FORMAT = '&yen;%s';
    }
    price = number_format(price, 2);

    return PRICE_FORMAT.replace('%s', price);
}
function number_format(num, ext){
    if(ext < 0){
        return num;
    }
    num = Number(num);
    if(isNaN(num)){
        num = 0;
    }
    var _str = num.toString();
    var _arr = _str.split('.');
    var _int = _arr[0];
    var _flt = _arr[1];
    if(_str.indexOf('.') == -1){
        /* 找不到小数点，则添加 */
        if(ext == 0){
            return _str;
        }
        var _tmp = '';
        for(var i = 0; i < ext; i++){
            _tmp += '0';
        }
        _str = _str + '.' + _tmp;
    }else{
        if(_flt.length == ext){
            return _str;
        }
        /* 找得到小数点，则截取 */
        if(_flt.length > ext){
            _str = _str.substr(0, _str.length - (_flt.length - ext));
            if(ext == 0){
                _str = _int;
            }
        }else{
            for(var i = 0; i < ext - _flt.length; i++){
                _str += '0';
            }
        }
    }

    return _str;
}
/* 火狐下取本地全路径 */
function getFullPath(obj)
{
    if(obj)
    {
        //ie
        if (window.navigator.userAgent.indexOf("MSIE")>=1)
        {
            obj.select();
            if(window.navigator.userAgent.indexOf("MSIE") == 25){
                obj.blur();
            }
            return document.selection.createRange().text;
        }
        //firefox
        else if(window.navigator.userAgent.indexOf("Firefox")>=1)
        {
            if(obj.files)
            {
                //return obj.files.item(0).getAsDataURL();
                return window.URL.createObjectURL(obj.files.item(0));
            }
            return obj.value;
        }
        return obj.value;
    }
}
/* 转化JS跳转中的 ＆ */
function transform_char(str)
{
    if(str.indexOf('&'))
    {
        str = str.replace(/&/g, "%26");
    }
    return str;
}
//图片垂直水平缩放裁切显示
(function($){
    $.fn.VMiddleImg = function(options) {
        var defaults={
            "width":null,
"height":null
        };
        var opts = $.extend({},defaults,options);
        return $(this).each(function() {
            var $this = $(this);
            var objHeight = $this.height(); //图片高度
            var objWidth = $this.width(); //图片宽度
            var parentHeight = opts.height||$this.parent().height(); //图片父容器高度
            var parentWidth = opts.width||$this.parent().width(); //图片父容器宽度
            var ratio = objHeight / objWidth;
            if (objHeight > parentHeight && objWidth > parentWidth) {
                if (objHeight > objWidth) { //赋值宽高
                    $this.width(parentWidth);
                    $this.height(parentWidth * ratio);
                } else {
                    $this.height(parentHeight);
                    $this.width(parentHeight / ratio);
                }
                objHeight = $this.height(); //重新获取宽高
                objWidth = $this.width();
                if (objHeight > objWidth) {
                    $this.css("top", (parentHeight - objHeight) / 2);
                    //定义top属性
                } else {
                    //定义left属性
                    $this.css("left", (parentWidth - objWidth) / 2);
                }
            }
            else {
                if (objWidth > parentWidth) {
                    $this.css("left", (parentWidth - objWidth) / 2);
                }
                $this.css("top", (parentHeight - objHeight) / 2);
            }
        });
    };
})(jQuery);
function DrawImage(ImgD,FitWidth,FitHeight){
    var image=new Image();
    image.src=ImgD.src;
    if(image.width>0 && image.height>0)
    {
        if(image.width/image.height>= FitWidth/FitHeight)
        {
            if(image.width>FitWidth)
            {
                ImgD.width=FitWidth;
                ImgD.height=(image.height*FitWidth)/image.width;
            }
            else
            {
                ImgD.width=image.width;
                ImgD.height=image.height;
            }
        }
        else
        {
            if(image.height>FitHeight)
            {
                ImgD.height=FitHeight;
                ImgD.width=(image.width*FitHeight)/image.height;
            }
            else
            {
                ImgD.width=image.width;
                ImgD.height=image.height;
            }
        }
    }
}

/**
 * 浮动DIV定时显示提示信息,如操作成功, 失败等
 * @param string tips (提示的内容)
 * @param int height 显示的信息距离浏览器顶部的高度
 * @param int time 显示的时间(按秒算), time > 0
 * @sample <a href="javascript:void(0);" onclick="showTips( '操作成功', 100, 3 );">点击</a>
 * @sample 上面代码表示点击后显示操作成功3秒钟, 距离顶部100px
 * @copyright ZhouHr 2010-08-27
 */
function showTips( tips, height, time ){
    var windowWidth = document.documentElement.clientWidth;
    var tipsDiv = '<div class="tipsClass">' + tips + '</div>';

    $( 'body' ).append( tipsDiv );
    $( 'div.tipsClass' ).css({
        'top' : 200 + 'px',
        'left' : ( windowWidth / 2 ) - ( tips.length * 13 / 2 ) + 'px',
        'position' : 'fixed',
        'padding' : '20px 50px',
        'background': '#EAF2FB',
        'font-size' : 14 + 'px',
        'margin' : '0 auto',
        'text-align': 'center',
        'width' : 'auto',
        'color' : '#333',
        'border' : 'solid 1px #A8CAED',
        'opacity' : '0.90',
        'z-index' : '9999'
    }).show();
    setTimeout( function(){$( 'div.tipsClass' ).fadeOut().remove();}, ( time * 1000 ) );
}

function trim(str) {
    return (str + '').replace(/(\s+)$/g, '').replace(/^\s+/g, '');
}
//弹出框登录
function login_dialog(){
    CUR_DIALOG = ajax_form('login','登录',MEMBER_SITE_URL+'/index.php?act=login&inajax=1',360,1);
}

/* 显示Ajax表单 */
function ajax_form(id, title, url, width, model)
{
    if (!width)	width = 480;
    if (!model) model = 1;
    var d = DialogManager.create(id);
    d.setTitle(title);
    d.setContents('ajax', url);
    d.setWidth(width);
    d.show('center',model);
    return d;
}
//显示一个内容为自定义HTML内容的消息
function html_form(id, title, _html, width, model) {
    if (!width)	width = 480;
    if (!model) model = 0;
    var d = DialogManager.create(id);
    d.setTitle(title);
    d.setContents(_html);
    d.setWidth(width);
    d.show('center',model);
    return d;
}
//收藏店铺js
function collect_store(fav_id,jstype,jsobj){
    $.get('index.php?act=index&op=login', function(result){
        if(result=='0'){
            login_dialog();
        }else{
            var url = 'index.php?act=member_favorite_store&op=favoritestore';
            $.getJSON(url, {'fid':fav_id}, function(data){
                if (data.done){
                showDialog(data.msg, 'succ','','','','','','','','',2);
                if(jstype == 'count'){
                    $('[nctype="'+jsobj+'"]').each(function(){
                        $(this).html(parseInt($(this).text())+1);
                    });
                }
                if(jstype == 'succ'){
                    $('[nctype="'+jsobj+'"]').each(function(){
                        $(this).html("收藏成功");
                    });
                }
                if(jstype == 'store'){
                    $('[nc_store="'+fav_id+'"]').each(function(){
                        $(this).before('<span class="goods-favorite" title="该店铺已收藏"><i class="have">&nbsp;</i></span>');
                        $(this).remove();
                    });
                }
            }
                else
                {
                    showDialog(data.msg, 'notice');
                }
            });
        }
    });
}
//收藏商品js
function collect_goods(fav_id,jstype,jsobj){
    $.get('index.php?act=index&op=login', function(result){
        if(result=='0'){
            login_dialog();
        }else{
            var url = 'index.php?act=member_favorite_goods&op=favoritegoods';
            $.getJSON(url, {'fid':fav_id}, function(data){
                if (data.done)
            {
                showDialog(data.msg, 'succ','','','','','','','','',2);
                if(jstype == 'count'){
                    $('[nctype="'+jsobj+'"]').each(function(){
                        $(this).html(parseInt($(this).text())+1);
                    });
                }
                if(jstype == 'succ'){
                    $('[nctype="'+jsobj+'"]').each(function(){
                        $(this).html("收藏成功");
                    });
                }
            }
                else
            {
                showDialog(data.msg, 'notice');
            }
            });
        }
    });
}
//加载购物车信息
function load_cart_information(){
	$.getJSON(SITEURL+'/index.php?act=cart&op=ajax_load&callback=?', function(result){
	    var obj = $('.mod_minicart');
	    if(result){
	       	var html = '';
	       	if(result.cart_goods_num >0){
	            for (var i = 0; i < result['list'].length; i++){
	                var goods = result['list'][i];
	            	html+='<li ncTpye="cart_item_'+goods['cart_id']+'">';
	            	html+='<a traget="_blank" class="pro_img"  href="'+goods['goods_url']+'" title="'+goods['goods_name']+'"><img src="'+goods['goods_image']+'"></a>';
		          	html+='<a traget="_blank" class="pro_name"  href="'+goods['goods_url']+'">'+goods['goods_name']+'</a>';
		          	html+='<div class="mcartRight"><span class="pro_price"><em>&yen;'+goods['goods_price']+'×'+goods['goods_num']+'</span>';
		          	html+='<a href="javascript:void(0);" onClick="drop_topcart_item('+goods['cart_id']+');">删除</a></div>';
		          	html+="</li>";
		        }
				$('.none_tips').remove();
		        obj.find('.list_detail ul').html(html);
    	        obj.find('.list_detail').perfectScrollbar('destroy');
    	        obj.find('.list_detail').perfectScrollbar({suppressScrollX:true});
	         	html = "共<em class='tNum'>"+result.cart_goods_num+"</em>种商品,总计金额：<em class='tSum'>&yen;"+result.cart_all_price+"</em>";
		        obj.find('.checkout_box .fl').html(html);
		        if (obj.find('.cart_num').size()==0) {
		            obj.append('<em class="cart_num">0</em>');
		        }
		        obj.find('.cart_num').html(result.cart_goods_num);
		        $('#rtoobar_cart_count').html(result.cart_goods_num).show();
	      } else {
	      	html="<div class='list_detail'><div class='none_tips'><i> </i><p>购物车中没有商品，赶紧去选购！</p></div>";
	      	obj.find('.list_detail ul').html(html);
			$('.checkout_box').remove();
	      	$('.list_detail ul').remove();
	      	$('#rtoobar_cart_count').html('').hide();
	      	
	      }
	   }
	});
}

//头部删除购物车信息，登录前使用goods_id,登录后使用cart_id
function drop_topcart_item(cart_id){
    $.getJSON(SITEURL+'/index.php?act=cart&op=del&cart_id='+cart_id+'&callback=?', function(result){
        if(result.state){
            var obj = $('.mod_minicart');
            //删除成功
            if(result.quantity == 0){
    	      	html="<div class='list_detail'><div class='none_tips'><i> </i><p>购物车中没有商品，赶紧去选购！</p></div>";
    	      	obj.find('.list_detail ul').html(html);
    	      	obj.find('.checkout_box .fl').html('');
    	      	obj.find('.cart_num').remove();
    	      	$('.cart-list').html('<li><dl><dd style="text-align: center; ">暂无商品</dd></dl></li>');
    	      	$('div[ncType="rtoolbar_total_price"]').html('');
    	      	$('#rtoobar_cart_count').html('').hide();
            }else{
                $('li[ncTpye="cart_item_'+ cart_id+'"]').remove();
                $('li[ncTpye="cart_item_'+ cart_id+'"]').remove();
	         	html="共<em class='tNum'>"+result.quantity+"</em>种商品,总计金额：<em class='tSum'>&yen;"+result.amount+"</em>";
	         	obj.find('.checkout_box .fl').html(html);
		        obj.find('.cart_num').html(result.quantity);
    	        obj.find('.list_detail').perfectScrollbar('destroy');
    	        obj.find('.list_detail').perfectScrollbar({suppressScrollX:true});
    	        $('div[ncType="rtoolbar_total_price"]').html("共计金额：<em class='goods-price'>&yen;"+result.amount+"</em>");
    	        $('#rtoobar_cart_count').html(result.quantity);
    	        if ($('#rtoolbar_cartlist > ul').children().size() != result.quantity) {
    	        	$("#rtoolbar_cartlist").load(SHOP_SITE_URL+ '/index.php?act=cart&op=ajax_load&type=html');
    	        }
            }
        }else{
            alert(result.msg);
        }
    });
}

//加载最近浏览的商品
function load_history_information(){
    $.getJSON(SITEURL+'/index.php?act=index&op=viewed_info', function(result){
        var obj = $('.head-user-menu .my-mall');
        if(result['m_id'] >0){
            if (typeof result['consult'] !== 'undefined') obj.find('#member_consult').html(result['consult']);
            if (typeof result['consult'] !== 'undefined') obj.find('#member_voucher').html(result['voucher']);
        }
        var goods_id = 0;
        var text_append = '';
        var n = 0;
        if (typeof result['viewed_goods'] !== 'undefined') {
            for (goods_id in result['viewed_goods']) {
                var goods = result['viewed_goods'][goods_id];
                text_append += '<li class="goods-thumb"><a href="'+goods['url']+'" title="'+goods['goods_name']+
                '" target="_blank"><img src="'+goods['goods_image']+'" alt="'+goods['goods_name']+'"></a>';
                text_append += '</li>';
                n++;
                if (n > 4) break;
            }
        }
        if (text_append == '') text_append = '<li class="no-goods">暂无商品</li>';
        obj.find('.browse-history ul').html(text_append);
    });
}
/*
 * 登录窗口
 *
 * 事件绑定调用范例
 * $("#btn_login").nc_login({
 *     nchash:'<?php echo getNchash();?>',
 *     formhash:'<?php echo Security::getTokenValue();?>',
 *     anchor:'cms_comment_flag'
 * });
 *
 * 直接调用范例
 * $.show_nc_login({
 *     nchash:'<?php echo getNchash();?>',
 *     formhash:'<?php echo Security::getTokenValue();?>',
 *     anchor:'cms_comment_flag'
 * });

 */

(function($) {
    $.show_nc_login = function(options) {
        var settings = $.extend({}, {action:'/index.php?act=login&op=login&inajax=1', nchash:'', formhash:'' ,anchor:''}, options);
        var login_dialog_html = $('<div class="quick-login"></div>');
        var ref_url = document.location.href;
        var add_html = '<span class="other">';
        if (connect_qq == '1'){
            add_html += '<a href="'+MEMBER_SITE_URL+'/index.php?act=connect_qq" title="QQ账号登录" class="qq"><i></i></a>';
        }
        if (connect_sn == '1'){
            add_html += '<a href="'+MEMBER_SITE_URL+'/index.php?act=connect_sina" title="新浪微博账号登录" class="sina"><i></i></a>';
        }
        if (connect_wx == '1'){
            add_html += '<a href="javascript:void(0);" onclick="ajax_form(\'weixin_form\', \'微信账号登录\', \''+LOGIN_SITE_URL+'/index.php?act=connect_wx&op=index\', 360);" title="微信账号登录" class="wx"><i></i></a>';
        }
        add_html += '</span>';
        login_dialog_html.append('<form class="bg" method="post" id="ajax_login" action="'+LOGIN_SITE_URL+settings.action+'"></form>').find('form')
        	.append('<input type="hidden" value="ok" name="form_submit">')
        	.append('<input type="hidden" value="'+settings.formhash+'" name="formhash">')
        	.append('<input type="hidden" value="'+settings.nchash+'" name="nchash">')
        	.append('<dl><dt>用户名</dt><dd><input type="text" name="user_name" autocomplete="off" class="text"></dd></dl>')
        	.append('<dl><dt>密&nbsp;&nbsp;&nbsp;码</dt><dd><input type="password" autocomplete="off" name="password" class="text"></dd></dl>')
        	.append('<dl><dt>验证码</dt><dd><input type="text" size="10" maxlength="4" class="text fl w60" name="captcha"><img border="0" onclick="this.src=\'index.php?act=seccode&amp;op=makecode&amp;nchash='+settings.nchash+'&amp;t=\' + Math.random()" name="codeimage" title="看不清，换一张" src="index.php?act=seccode&amp;op=makecode&amp;nchash='+settings.nchash+'" class="fl ml10"><span>不区分大小写</span></dd></dl>')
        	.append('<ul><li>›&nbsp;如果您没有登录账号，请先<a class="register" href="'+LOGIN_SITE_URL+'/index.php?act=login&amp;op=register">注册会员</a>然后登录</li><li>›&nbsp;如果您<a class="forget" href="'+LOGIN_SITE_URL+'/index.php?act=login&amp;op=forget_password">忘记密码</a>？，申请找回密码</li></ul>')
        	.append('<div class="enter"><input type="submit" name="Submit" value="登&#12288;&#12288;录" class="submit">'+add_html+'</div><input type="hidden" name="ref_url" value="'+ref_url+'">');

        login_dialog_html.find('input[type="submit"]').click(function(){
        	ajaxpost('ajax_login', '', '', 'onerror');
        });
        html_form("form_dialog_login", "登录", login_dialog_html, 360);
    };
    $.fn.nc_login = function(options) {
        return this.each(function() {
            $(this).on('click',function(){
                $.show_nc_login(options);
                return false;
            });
        });
    };

})(jQuery);

/*
 * 为低版本IE添加placeholder效果
 *
 * 使用范例：
 * [html]
 * <input id="captcha" name="captcha" type="text" placeholder="验证码" value="" >
 * [javascrpt]
 * $("#captcha").nc_placeholder();
 *
 * 生效后提交表单时，placeholder的内容会被提交到服务器，提交前需要把值清空
 * 范例：
 * $('[data-placeholder="placeholder"]').val("");
 * $("#form").submit();
 *
 */
(function($) {
    $.fn.nc_placeholder = function() {
        var isPlaceholder = 'placeholder' in document.createElement('input');
        return this.each(function() {
            if(!isPlaceholder) {
                $el = $(this);
                $el.focus(function() {
                    if($el.attr("placeholder") === $el.val()) {
                        $el.val("");
                        $el.attr("data-placeholder", "");
                    }
                }).blur(function() {
                    if($el.val() === "") {
                        $el.val($el.attr("placeholder"));
                        $el.attr("data-placeholder", "placeholder");
                    }
                }).blur();
            }
        });
    };
})(jQuery);

/*
 * 弹出窗口
 */
(function($) {
    $.fn.nc_show_dialog = function(options) {

        var that = $(this);
        var settings = $.extend({}, {width: 480, title: '', close_callback: function() {}}, options);

        var init_dialog = function(title) {
            var _div = that;
            that.addClass("dialog_wrapper");
            that.wrapInner(function(){
                return '<div class="dialog_content">';
            });
            that.wrapInner(function(){
                return '<div class="dialog_body" style="position: relative;">';
            });
            that.find('.dialog_body').prepend('<h3 class="dialog_head" style="cursor: move;"><span class="dialog_title"><span class="dialog_title_icon">'+settings.title+'</span></span><span class="dialog_close_button">X</span></h3>');
            that.append('<div style="clear:both;"></div>');

            $(".dialog_close_button").click(function(){
                settings.close_callback();
                _div.hide();
            });

            that.draggable({handle: ".dialog_head"});
        };

        if(!$(this).hasClass("dialog_wrapper")) {
            init_dialog(settings.title);
        }
        settings.left = $(window).scrollLeft() + ($(window).width() - settings.width) / 2;
        settings.top  = ($(window).height() - $(this).height()) / 2;
        $(this).attr("style","display:none; z-index: 1100; position: fixed; width: "+settings.width+"px; left: "+settings.left+"px; top: "+settings.top+"px;");
        $(this).show();

    };
})(jQuery);

/**
 * Membership card
 *
 *
 * Example:
 *
 * HTML part
 * <a href="javascript" nctype="mcard" data-param="{'id':5}"></a>
 *
 * JAVASCRIPT part
 * <script type="text/javascript" src="<?php echo RESOURCE_SITE_URL;?>/js/qtip/jquery.qtip.min.js"></script>
 * <link href="<?php echo RESOURCE_SITE_URL;?>/js/qtip/jquery.qtip.min.css" rel="stylesheet" type="text/css">
 * $('a[nctype="mcard"]').membershipCard();
 */
(function($){
	$.fn.membershipCard = function(options){
		var defaults = {
				type:''			// params  shop/circle/cms/micorshop
			};
		options = $.extend(defaults,options);
		return this.each(function(){
			var $this = $(this);
			var data_str = $(this).attr('data-param');eval('data_str = '+data_str);
			var _uri = SITEURL+'/index.php?act=member_card&callback=?&uid='+data_str.id+'&from='+options.type;
			var _dl = '';
			$this.qtip({
	            content: {
	                text: 'Loading...',
	                ajax: {
	                    url: _uri,
		                type: 'GET',
		                dataType: 'jsonp',
		                success: function(data) {
		                	if(data){
		                		_dl = $('<dl></dl>');
		                		// member name
		                		_dttmp = $('<dt class="member-id"></dt>');
		                		_dttmp.append('<i class="sex'+data.sex+'"></i>')
	                			.append('<a href="'+SHOP_SITE_URL+'/index.php?act=member_snshome&mid='+data.id+'" target="_blank">'+data.name+'</a>'+(data.truename != ''?'('+data.truename+')':''));
		                		//show membergrade
		                		if(options.type == 'shop'){
		                			_dttmp.append(((data.level_name)?'&nbsp;<div class="nc-grade-mini">'+data.level_name+'</div>':''));
		                		}
		                		_dttmp.appendTo(_dl);
		                		
		                		// avatar
		                		$('<dd class="avatar"><a href="'+SHOP_SITE_URL+'/index.php?act=member_snshome&mid='+data.id+'" target="_blank"><img src="'+data.avatar+'" /></a><dd>')
		                			.appendTo(_dl);
		                		// info
		                		var _info = '';
		                		if(typeof connect !== 'undefined' && connect === 1 && data.follow != 2){
		                			var class_html = 'chat_offline';
		                			var text_html = '离线';
		                			if (typeof user_list[data.id] !== 'undefined' && user_list[data.id]['online'] > 0 ) {
		                				class_html = 'chat_online';
		                				text_html = '在线';
		                			}
		                			_info += '<a class="chat '+class_html+'" title="点击这里给我发消息" href="JavaScript:chat('+data.id+');">'+text_html+'</a>';
		                		}
		                		if(data.qq != ''){
		                			_info += '<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin='+data.qq+'&site=qq&menu=yes" title="QQ: '+data.qq+'"><img border="0" src="http://wpa.qq.com/pa?p=2:'+data.qq+':52" style=" vertical-align: middle;"/></a>';
		                		}
		                		if(data.ww != ''){
		                			_info += '<a target="_blank" href="http://amos.im.alisoft.com/msg.aw?v=2&amp;uid='+data.ww+'&site=cntaobao&s=1&charset='+_CHARSET+'" ><img border="0" src="http://amos.im.alisoft.com/online.aw?v=2&uid='+data.ww+'&site=cntaobao&s=2&charset='+_CHARSET+'" alt="点击这里给我发消息" style=" vertical-align: middle;"/></a>';
		                		}
		                		if(_info == ''){
		                			_info = '--';
		                		}
		                		var _ul = $('<ul></ul>').append('<li>城市：'+((data.areainfo != null)?data.areainfo:'--')+'</li>')
		                			.append('<li>生日：'+((data.birthday != null)?data.birthday:'--')+'</li>')
		                			.append('<li>联系：'+_info+'</li>').appendTo('<dd class="info"></dd>').parent().appendTo(_dl);
		                		// ajax info
		                		if(data.url != ''){
			                		$.getJSON(data.url+'/index.php?act=member_card&op=mcard_info&uid='+data.id, function(d){
			                			if(d){
			                				eval('var msg = '+options.type+'_function(d);');
			                				msg.appendTo(_dl);
			                			}
			                		});
			                		data.url = '';
			                	}

		                		// bottom
		                		var _bottom;
		                		if(data.follow != 2){
			                		_bottom = $('<div class="bottom"></div>');
		                			var _a;
		                			if(data.follow == 1){
		                				$('<div class="follow-handle" nctype="follow-handle'+data.id+'" data-param="{\'mid\':'+data.id+'}"></div>')
		                					.append('<a href="javascript:void(0);" >已关注</a>')
		                					.append('<a href="javascript:void(0);" nctype="nofollow">取消关注</a>').find('a[nctype="nofollow"]').click(function(){
		                						onfollow($(this));
		                					}).end().appendTo(_bottom);
		                			}else{
		                				$('<div class="follow-handle" nctype="follow-handle'+data.id+'" data-param="{\'mid\':'+data.id+'}"></div>')
		                					.append('<a href="javascript:void(0);" nctype="follow">加关注</a>').find('a[nctype="follow"]').click(function(){
		                						follow($(this));
		                					}).end().appendTo(_bottom);
		                			}
		                			$('<div class="send-msg"> <a href="'+MEMBER_SITE_URL+'/index.php?act=member_message&op=sendmsg&member_id='+data.id+'" target="_blank"><i></i>站内信</a> </div>').appendTo(_bottom);
		                		}

		                		var _content = $('<div class="member-card"></div>').append(_dl).append(_bottom);
			                    this.set('content.text', ' ');this.set('content.text', _content);
		                	}
		                }
	                }
	            },
	            position: {
	                viewport: $(window)
	            },
	            hide: {
					fixed: true,
					delay: 300
				},
	            style: 'qtip-wiki'
	         });
		});
		function follow(o){
			var data_str = o.parent().attr('data-param');
			eval( "data_str = "+data_str);
			$.getJSON(MEMBER_SITE_URL+'/index.php?act=member_snsfriend&op=addfollow&callback=?&mid='+data_str.mid, function(data){
				if(data){
					$('[nctype="follow-handle'+data_str.mid+'"]').html('<a href="javascript:void(0);" >已关注</a> <a href="javascript:void(0);" nctype="nofollow">取消关注</a>').find('a[nctype="nofollow"]').click(function(){
						onfollow($(this));
					});
				}
			});
		}
		function onfollow(o){
			var data_str = o.parent().attr('data-param');
			eval( "data_str = "+data_str);
			$.getJSON(MEMBER_SITE_URL+'/index.php?act=member_snsfriend&op=delfollow&callback=?&mid='+data_str.mid, function(data){
				if(data){
					$('[nctype="follow-handle'+data_str.mid+'"]').html('<a href="javascript:void(0);" nctype="follow">加关注</a>').find('a[nctype="follow"]').click(function(){
						follow($(this));
					});
				}
			});
		}
		function shop_function(d){

		}
		function circle_function(d){
			var rs = $('<dd class="ajax-info"></dd>');
			$.each(d,function(i, n){
				rs.append('<div class="rank-div" title="'+n.circle_name+'圈等级'+n.cm_level+'，经验值'+n.cm_exp+'"><a href="'+CIRCLE_SITE_URL+'/index.php?act=group&c_id='+n.circle_id+'" target="_blank">'+n.circle_name+'</a><i></i><em class="rank-em rank-'+n.cm_level+'">'+n.cm_level+'</em></div>');
			});
			return rs;
		}
		function microshop_function(d){
			var rs = $('<dd class="ajax-info"></dd>');
            rs.append('<span class="ajax-info-microshop">随心看：' + d.goods_count + '</span>');
            rs.append('<span class="ajax-info-microshop">个人秀：' + d.personal_count + '</span>');
			return rs;
		}
	};
})(jQuery);

/*
 * 地址联动选择
 * input不为空时出现编辑按钮，点击按钮进行联动选择
 *
 * 使用范例：
 * [html]
 * <input id="region" name="region" type="hidden" value="" >
 * [javascrpt]
 * $("#region").nc_region();
 * 
 * 默认从cache读取地区数据，如果需从数据库读取（如后台地区管理），则需设置定src参数
 * $("#region").nc_region({{src:'db'}});
 * 
 * 如需指定地区下拉显示层级，需指定show_deep参数，默认未限制
 * $("#region").nc_region({{show_deep:2}}); 这样最多只会显示2级联动
 * 
 * 系统分自动将已经选择的地区ID存放到ID依次为_area_1、_area_2、_area_3、_area_4、_area的input框中
 * _area存放选中的最后一级ID
 * 这些input框需要手动在模板中创建
 * 
 * 取得已选值mff="sqde"
 * $('#region').val() ==> 河北 石家庄市 井陉矿区
 * $('#region').fetch('islast')  ==> true; 如果非最后一级，返回false
 * $('#region').fetch('area_id') ==> 1127
 * $('#region').fetch('area_ids') ==> 3 73 1127
 * $('#region').fetch('selected_deep') ==> 3 已选择分类的深度
 * $("#region").fetch('area_id_1') ==> 3
 * $("#region").fetch('area_id_2') ==> 73
 * $("#region").fetch('area_id_3') ==> 1127
 * $("#region").fetch('area_id_4') ==> ''
 */

(function($) {
	$.fn.nc_region = function(options) {
		var $region = $(this);
		var settings = $.extend({}, {
			area_id: 0,
			region_span_class: "_region_value",
			src: "cache",
			show_deep: 0,
			btn_style_html: "",
			tip_type: ""
		}, options);
		settings.islast = false;
		settings.selected_deep = 0;
		settings.last_text = "";
		this.each(function() {
			var $inputArea = $(this);
			if ($inputArea.val() === "") {
				initArea($inputArea)
			} else {
				var $region_span = $('<span id="_area_span" class="' + settings.region_span_class + '">' + $inputArea.val() + "</span>");
				var $region_btn = $('<input type="button" class="input-btn" ' + settings.btn_style_html + ' value="编辑" />');
				$inputArea.after($region_span);
				$region_span.after($region_btn);
				$region_btn.on("click", function() {
					$region_span.remove();
					$region_btn.remove();
					initArea($inputArea)
				});
				settings.islast = true
			}
			this.settings = settings;
			if ($inputArea.val() && /^\d+$/.test($inputArea.val())) {
				$.getJSON(SITEURL + "/index.php?act=index&op=json_area_show&area_id=" + $inputArea.val() + "&callback=?", function(data) {
					$("#_area_span").html(data.text == null ? "无" : data.text)
				})
			}
		});

		function initArea($inputArea) {
			settings.$area = $("<select></select>");
			$inputArea.before(settings.$area);
			loadAreaArray(function() {
				loadArea(settings.$area, settings.area_id)
			})
		}
		function loadArea($area, area_id) {
			if ($area && nc_a[area_id].length > 0) {
				var areas = [];
				areas = nc_a[area_id];
				if (settings.tip_type && settings.last_text != "") {
					$area.append("<option value=''>" + settings.last_text + "(*)</option>")
				} else {
					$area.append("<option value=''>-请选择-</option>")
				}
				for (i = 0; i < areas.length; i++) {
					$area.append("<option value='" + areas[i][0] + "'>" + areas[i][1] + "</option>")
				}
				settings.islast = false
			}
			$area.on("change", function() {
				var region_value = "",
					area_ids = [],
					selected_deep = 1;
				$(this).nextAll("select").remove();
				$region.parent().find("select").each(function() {
					if ($(this).find("option:selected").val() != "") {
						region_value += $(this).find("option:selected").text() + " ";
						area_ids.push($(this).find("option:selected").val())
					}
				});
				settings.selected_deep = area_ids.length;
				settings.area_ids = area_ids.join(" ");
				$region.val(region_value);
				settings.area_id_1 = area_ids[0] ? area_ids[0] : "";
				settings.area_id_2 = area_ids[1] ? area_ids[1] : "";
				settings.area_id_3 = area_ids[2] ? area_ids[2] : "";
				settings.area_id_4 = area_ids[3] ? area_ids[3] : "";
				settings.last_text = $region.prevAll("select").find("option:selected").last().text();
				var area_id = settings.area_id = $(this).val();
				if ($('#_area_1').length > 0) $("#_area_1").val(settings.area_id_1);
				if ($('#_area_2').length > 0) $("#_area_2").val(settings.area_id_2);
				if ($('#_area_3').length > 0) $("#_area_3").val(settings.area_id_3);
				if ($('#_area_4').length > 0) $("#_area_4").val(settings.area_id_4);
				if ($('#_area').length > 0) $("#_area").val(settings.area_id);
				if ($('#_areas').length > 0) $("#_areas").val(settings.area_ids);
				if (settings.show_deep > 0 && $region.prevAll("select").size() == settings.show_deep) {
					settings.islast = true;
					if (typeof settings.last_click == 'function') {
						settings.last_click(area_id);
					}
					return
				}
				if (area_id > 0) {
					if (nc_a[area_id] && nc_a[area_id].length > 0) {
						var $newArea = $("<select></select>");
						$(this).after($newArea);
						loadArea($newArea, area_id);
						settings.islast = false
					} else {
						settings.islast = true;
						if (typeof settings.last_click == 'function') {
							settings.last_click(area_id);
						}
					}
				} else {
					settings.islast = false
				}
				if ($('#islast').length > 0) $("#islast").val("");
			})
		}
		function loadAreaArray(callback) {
			if (typeof nc_a === "undefined") {
				$.getJSON(SITEURL + "/index.php?act=index&op=json_area&src=" + settings.src + "&callback=?", function(data) {
					nc_a = data;
					callback()
				})
			} else {
				callback()
			}
		}
		if (typeof jQuery.validator != 'undefined') {
			jQuery.validator.addMethod("checklast", function(value, element) {
				return $(element).fetch('islast');
			}, "请将地区选择完整");
		}
	};
	$.fn.fetch = function(k) {
		var p;
		this.each(function() {
			if (this.settings) {
				p = eval("this.settings." + k);
				return false
			}
		});
		return p
	}
})(jQuery);

/* 加入购物车 */
function addcart(goods_id,quantity,callbackfunc) {
    if (!quantity) return false;
    var url = 'index.php?act=cart&op=add';
    quantity = parseInt(quantity);
    $.getJSON(url, {'goods_id':goods_id, 'quantity':quantity}, function(data) {
        if (data != null) {
            if (data.state) {
            	if(callbackfunc){
            		eval(callbackfunc + "(data)");
            	}
                // 头部加载购物车信息mff="sqde"
                load_cart_information();
                $("#rtoolbar_cartlist").load(SHOP_SITE_URL + '/index.php?act=cart&op=ajax_load&type=html');
            } else {
                alert(data.msg);
            }
        }
    });
}

function setCookie(name,value,days){
        var exp=new Date();
        exp.setTime(exp.getTime() + days*24*60*60*1000);
        var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        document.cookie=name+"="+escape(value)+";expires="+exp.toGMTString();
}
function getCookie(name){
        var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr!=null){
                return unescape(arr[2]);
                return null;
        }
}
function delCookie(name){
        var exp=new Date();
        exp.setTime(exp.getTime()-1);
        var cval=getCookie(name);
        if(cval!=null){
                document.cookie=name+"="+cval+";expires="+exp.toGMTString();
        }
}
$(function(){
    var act = "search";
    if (act == "store_list"){
        $('#hdSearchTab ul li span').eq(0).html('店铺');
        $('#hdSearchTab ul li span').eq(1).html('商品');
        $('#hdSearchTab ul li').eq(0).attr('act','store_list');
        $('#search_act').attr("value","store_list");
        }
    $('#hdSearchTab').hover(function(){
        $('#hdSearchTab ul li').eq(1).show();
        $('#hdSearchTab ul li i').addClass('over').removeClass('arrow');
    },function(){
        $('#hdSearchTab ul li').eq(1).hide();
        $('#hdSearchTab ul li i').addClass('arrow').removeClass('over');
    });
    $('#hdSearchTab ul li').eq(1).click(function(){
        $(this).hide();
        if($(this).find('span').html() == '店铺') {
            $('#keyword').attr("placeholder","请输入您要搜索的店铺关键字");
            $('#hdSearchTab ul li span').eq(0).html('店铺');
            $('#hdSearchTab ul li span').eq(1).html('商品');
            $('#search_act').attr("value",'store_list');
        } else {
            $('#keyword').attr('placeholder','请输入您要搜索的商品关键字');
            $('#hdSearchTab ul li span').eq(0).html('商品');
            $('#hdSearchTab ul li span').eq(1).html('店铺');
            $('#search_act').attr("value",'search');
        }
        $("#keyword").focus();
    });
});