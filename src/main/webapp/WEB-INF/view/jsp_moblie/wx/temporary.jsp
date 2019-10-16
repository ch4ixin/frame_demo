<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>人脸认证</title>
<link rel="stylesheet" href="${path }/resource/jqweui/css/weui.min.css">
<link rel="stylesheet" href="${path }/resource/jqweui/css/jquery-weui.min.css">
<link rel="shortcut icon" href="${path }/resource/img/favicon.ico"/>
<script type="text/javascript" src="${path }/resource/js/jquery.js"></script>
<script src="${path }/resource/jqweui/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<style type="text/css">
	.footer{
		height: 8%
	}
	.footer a{
		width: 90%
	}
	.weui-popup__modal .toolbar {
    	position: relative;
	}
	.weui-popup__modal {
	    background: #ffffff;
	}
	.weui-popup__modal .modal-content {
    	padding-top: 0rem;
	}
	.weui-swiped-btn {
	    line-height: 2.8;
	}
	.weui-swiped-btn_default {
	    background-color: #6161ec;
	}
</style>
</head>
<body style="background-color: #fff" >
    <div class="weui-cells__title">临时人员</div>
	<div class="weui-cells"> 
       <c:forEach items="${list }" var="l" varStatus="status">
         <div class="weui-cell weui-cell_swiped">
           <div class="weui-cell__bd">
             <div class="weui-cell">
               <div class="weui-cell__hd"><img src="${path}/resource/img/user.png" alt="" style="width:50px;margin-right:10px;display:block"></div>
               <div class="weui-cell__bd">
                 <p>${l.note } / ${l.name }</p>
               </div>
               <div class="weui-cell__ft">向左滑动</div>
             </div>
           </div>
           <div class="weui-cell__ft">
             <a class="weui-swiped-btn weui-swiped-btn_default" href="javascript:edit('${l.name}','${l.tel}','${l.note}','${l.face_img}');">查看</a>
             <a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:del('${l.kid }')">删除</a>
           </div>
         </div>
       </c:forEach>
    </div>
	<div class="footer weui-footer_fixed-bottom">
		<a href="javascript:" class="weui-btn weui-btn_primary open-popup" data-target="#useradd">添加临时人员</a>
	</div>
	<div id="useradd" class='weui-popup__container'>
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
          <div class="toolbar">
	          <div class="toolbar-inner">
	            <a href="javascript:" class="picker-button close-popup" style="color:red;">关闭</a>
	            <h1 class="title">添加临时人员</h1>
	          </div>
	      </div>
          <div class="modal-content">
			<div class="weui-cells weui-cells_form">
		      <div class="weui-cell weui-cell_select weui-cell_select-after">
		        <div class="weui-cell__hd">
		          <label for="" class="weui-label">身份:</label>
		        </div>
		        <div class="weui-cell__bd">
		          <select class="weui-select" name="note">
		            <option value="家属">家属</option>
		            <option value="朋友">朋友</option>
		            <option value="同学">同学</option>
		          </select>
		        </div>
		      </div>
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label class="weui-label">姓名:</label></div>
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="text" id="name" placeholder="请输入姓名">
		        </div>
		      </div>
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label class="weui-label">手机号码:</label></div>
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="tel" id="tel" placeholder="11位手机号码">
		        </div>
		      </div>
		    </div>
		     <div class="weui-btn-area">
		      <a class="weui-btn weui-btn_primary" href="javascript:upImage();" >上传人脸</a>
		    </div>
        </div>
      </div>
    </div>
    <div id="useredit" class='weui-popup__container'>
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
      		<div class="toolbar">
		          <div class="toolbar-inner">
		            <a href="javascript:" class="picker-button close-popup" style="color:red;">关闭</a>
		            <h1 class="title">查看临时人员</h1>
		          </div>
	        </div>
          	<div class="weui-cells__title">身份信息</div>
		    <div class="weui-cells weui-cells_form">
		       <div class="weui-cell">
			    <div class="weui-cell__bd">
			      <div class="weui-uploader">
			        <div class="weui-uploader__hd">
			          <p class="weui-uploader__title"></p>
			        </div>
			        <div class="weui-uploader__bd" style="text-align: center;">
			           	<img id="face_image" src="${path }/resource/img/face.png" width="50%">
			        </div>
			      </div>
			    </div>
			  </div>
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label class="weui-label">身份:</label></div>
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="text" id="note_edit" disabled="disabled">
		        </div>
		      </div>
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label class="weui-label">姓名:</label></div>
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="text" id="name_edit" disabled="disabled">
		        </div>
		      </div>
		      <div class="weui-cell">
		        <div class="weui-cell__hd"><label class="weui-label">手机号码:</label></div>
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="text" id="tel_edit" disabled="disabled">
		        </div>
		      </div>
		    </div>
		    </div>
      </div>
</body>
<script type="text/javascript">
	$(function(){
		$('.weui-cell_swiped').swipeout();
		wx.config({
		    beta: false,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${jsConfig.appId}', // 必填，企业微信的corpID
		    timestamp: ${jsConfig.timestamp}, // 必填，生成签名的时间戳
		    nonceStr: '${jsConfig.nonceStr}', // 必填，生成签名的随机串
		    signature: '${jsConfig.signature}',// 必填，签名，见 附录-JS-SDK使用权限签名算法
		    jsApiList: ['chooseImage','uploadImage','getLocalImgData'] // 必填，需要使用的JS接口列表，凡是要调用的接口都需要传进来
		});
	});
	
   function upImage(){
		var phoneNum = /^1[345789]\d{9}$/;
		if($("#name").val() == ""){
			 $.toptip('请输入姓名', 'error');
    		 return;
		}
		
		if($("#tel").val() == ""){
			 $.toptip('请输入手机号码', 'error');
    		 return;
		}
		
		if (!phoneNum.test($("#tel").val())) {
			$.toptip('请正确输入手机号码', 'error');
			return;
		}
		
      	wx.chooseImage({
  		    count: 1, // 默认9
  		    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
  		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
  		    isSaveToAlbum: 0, //整型值，0表示拍照时不保存到系统相册，1表示自动保存，默认值是1
  		    success: function (res) {
					$.showLoading("上传中");
					var u = navigator.userAgent;
					if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
						wx.uploadImage({
						    localId: res.localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
						    isShowProgressTips: 0, // 默认为1，显示进度提示
						    success: function (res) {
						        var serverId = res.serverId; // 返回图片的服务器端ID
						        var ajaxTimeOut = $.ajax({
									type : "GET",
									url : "${path}/qywx_upload.htm",
									timeout : 60000, //超时时间设置，单位毫秒
									data : "media_id=" + serverId + "&filepath=allFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$.hideLoading();
										submit(msg.url,msg.filename);
									},
									error : function(response) {
										$.hideLoading();
										$.toptip('😣 服务好像出问题了，请重试', 'warning');
									},
									complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
									　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
									 　　　　　	ajaxTimeOut.abort(); //取消请求
											$.hideLoading();
											$.toptip('😣 网络好像出问题了，请重试', 'warning');
								　　　    	  }
								　　	},
									failure : function(response) {
										$.hideLoading();
										$.toptip('😣 好像出问题了，请重试', 'warning');
									}
								});
						    }
						});
					} else if (u.indexOf('iPhone') > -1) {//苹果手机
						wx.getLocalImgData({
		  		    	    localId: res.localIds[0], // 图片的localID
		  		    	    success: function (res) {
		  		    	        var localData = res.localData; // localData是图片的base64数据，可以用img标签显示
								localData = encodeURIComponent(localData.replace('data:image/jpg;base64,', ''));
								var ajaxTimeOut = $.ajax({
									type : "GET",
									url : "${path}/wx_upload_base.htm",
									timeout : 60000, //超时时间设置，单位毫秒
									data : "imgBase64=" + localData + "&filepath=allFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$.hideLoading();
										submit(msg.url,msg.filename);
									},
									error : function(response) {
										$.hideLoading();
										$.toptip('😣 服务好像出问题了，请重试', 'warning');
									},
									complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
									　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
									 　　　　　	ajaxTimeOut.abort(); //取消请求
											$.hideLoading();
											$.toptip('😣 网络好像出问题了，请重试', 'warning');
								　　　    	  }
								　　	},
									failure : function(response) {
										$.hideLoading();
										$.toptip('😣 好像出问题了，请重试', 'warning');
									}
								});
		  		    		}
		  		    	});
					}
  		    }
  		});//#chooseImage
    }
	
	function submit(url,filename) {
    	$.showLoading("认证中");
		var name = $("#name").val();
		var tel = $("#tel").val();
		var note = $("select[name='note']").val();
		var father_id = "${units_user.kid}";

    		$.toptip('🕒  提取人脸特征时间会稍长，请稍等...', 60000, 'warning');
    		var ajaxTimeOut = $.ajax({
			 type : "GET",
             url: "${path}/wx/temporary/save.htm",  
			 timeout : 60000, //超时时间设置，单位毫秒
             data: {units_id:"RZpoUMy4iXMnBb65GbBiwq",name:name,tel:tel,note:note,father_id:father_id,url:url,filename:filename}, 
             dataType: "json",
             success: function(data){
            	$.hideLoading();
            	if(data == false){
            		$.toast("认证失败", "forbidden");
 	           		$.toptip('😥 未找到人脸,请重新上传！', 'error');	
            	}
            	if(data == true){
            		$.toptip('🙂 认证成功，一分钟后生效', 10000, 'success');  //设置显示时间
            		$.toast("认证成功");
            		
            		setTimeout(function() {
            			 history.go(0) 
			        }, 500)
 	           	}
             },
			 error:function(response) {
					$.hideLoading();
					$.toptip('😣 图像解析错误，请使用后置摄像头重试', 'warning');
			 },
			 complete:function(XMLHttpRequest,status){ //请求完成后最终执行参数
			　　　if(status=='timeout'){//超时,status还有success,error等值的情况
			　　　　　	ajaxTimeOut.abort(); //取消请求
					$.hideLoading();
					$.toptip('😣 网络好像出问题了，请重试', 'warning');
			　　　    }
			　},
			 failure : function(response) {
			 	$.hideLoading();
				$.toptip('😣 好像出问题了，请重试', 'warning');
			 }
         });

}
    function edit(name,tel,note,face_img){
		$.showLoading();
		var ajaxTimeOut = $.ajax({
			type : "GET",
			url : "${path}/aliyun_img.htm",
		    timeout : 60000, //超时时间设置，单位毫秒
			data : "filename=" + face_img,
			dateType : 'json',
			success : function(msg) {
				$("#face_image").attr("src",msg);
				$("#name_edit").val(name);
        		$("#tel_edit").val(tel);
        		$("#note_edit").val(note);
			},
			error : function(response) {
				$.hideLoading();
				$.toptip('😣 服务好像出问题了，请重试', 'warning');
			},
			complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
			　　　if(status=='timeout'){//超时,status还有success,error等值的情况
			　　　　　	ajaxTimeOut.abort(); //取消请求
					$.hideLoading();
					$.toptip('😣 网络好像出问题了，请重试', 'warning');
			　　　    }
			},
			failure : function(response) {
			 	$.hideLoading();
				$.toptip('😣 好像出问题了，请重试', 'warning');
			}
	 	});
		setTimeout(function() {
			$.hideLoading();
			$("#useredit").popup();
        }, 500)
	}
	
	function del(kid){
		$.showLoading();
		var ajaxTimeOut = $.ajax({
				type : "GET",
				url : "${path}/wx/del.htm?kid="+kid,
			    timeout : 60000, //超时时间设置，单位毫秒
				dateType : 'json',
				success : function(msg) {
					console.log(msg);
					$.toast("删除成功");
					setTimeout(function() {
               			 history.go(0) 
   			        }, 500)
				},
				error : function(response) {
					$.hideLoading();
					$.toptip('😣 服务好像出问题了，请重试', 'warning');
				},
				complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
				　　　if(status=='timeout'){//超时,status还有success,error等值的情况
				　　　　　	ajaxTimeOut.abort(); //取消请求
						$.hideLoading();
						$.toptip('😣 网络好像出问题了，请重试', 'warning');
				　　　    }
				},
				failure : function(response) {
				 	$.hideLoading();
					$.toptip('😣 好像出问题了，请重试', 'warning');
				}
		});
		$.hideLoading();
	}
</script>
</html>