<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../jsp_cp/common/init.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>太原科技大学统一身份认证系统</title>
<link rel="stylesheet" href="${path }/resource/jqweui/css/weui.min.css">
<link rel="stylesheet" href="${path }/resource/jqweui/css/jquery-weui.min.css">
<link rel="stylesheet" type="text/css" href="${path }/resource/webuploader/webuploader.css">
<link rel="shortcut icon" href="${path }/resource/img/favicon.ico"/>
<style type="text/css">
.weui-loadmore_line .weui-loadmore__tips {
    color: white;
}
.weui-loadmore_line { 
    border-top: 0px solid red;
}
.weui-loadmore {
    width: 85%;
}
.myapp-login-logo {
	width: 100%;
	text-align: center;
}
.weui-cellf{
	margin-top: 1.17647059em;
	line-height: 1.47058824;
	font-size: 17px;
	overflow: hidden;
	position: relative;
	margin: 10px 30px;
	background-color: rgba(255, 255, 255, 0.2);
	box-shadow: 1px 1px 10px rgba(255, 255, 255, 0.2);
}
.weui-loadmore__tips{

}
.weui-uploader__hd{
	padding:0.8em 0;
}
.weui-uploader__title{
	font-size: 20px;
	font-weight: bold;
	text-shadow: 1px 1px 3px #333333;
	text-align: center;
	color: #ffffff;
}
.weui-loadmore_line {
	margin-top: 1.4em;
}
.weui-loadmore_line .weui-loadmore__tips{
	background-color: rgba(255, 255, 255, 0);
}
.weui-btn-area {
	margin: 1.17647059em 25px 0.3em;
}
.weui-btn_default {
    color: #fff;
    background-color: #00b7ee;
}
.weui-btn_default:not(.weui-btn_disabled):visited {
    color: #fff;
}
</style>
</head>
<body style="background-color: #002147" >
	<div class="myapp-login-logo" style="padding:1.5em 0 0 0;">
		<i></i>
		<img src="${path }/resource/img/tykdlogo.png" width="85%">
	</div>
    <div class="weui-cellf weui-cells_form">
      <div class="weui-cell">
	    <div class="weui-cell__bd">
	      <div class="weui-uploader">
	        <div class="weui-uploader__hd">
	          <p class="weui-uploader__title">统一身份认证系统</p>
	        </div>
	        <div class="weui-uploader__bd" style="text-align: center;">
	            <img id="face_image" onclick="upImage()" src="${path }/resource/img/face.png" width="60%">
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="weui-loadmore weui-loadmore_line">
		<span class="weui-loadmore__tips">点击上方区域人脸识别登录</span>
		<span class="weui-loadmore__tips">请正对屏幕，使用自然表情自拍</span>
	  </div>
    </div>
   	<div class="weui-btn-area">
      <a class="weui-btn weui-btn_default" href="http://wxcj.tyust.edu.cn/tyust/regedit_index" id="showTooltips">切换账号密码登录</a>
    </div> 
    <div id="about" class='weui-popup__container'>
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
		<div class="weui-cells__title">验证身份证号</div>
		<div class="weui-cells weui-cells_form">
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><label for="" class="weui-label">后六位</label></div>
		    <div class="weui-cell__bd">
		      <input class="weui-input" id="identity_id_note" name="identity_id_note" type="text" placeholder="请输入身份证后六位（加x）">
              <input type="text" id="identity_id" name="identity_id" hidden="hidden">
              <input type="text" id="code" name="code" hidden="hidden">
              <input type="text" id="userid" name="userid" hidden="hidden">
              <input type="text" id="filename" name="filename" hidden="hidden">
		    </div>
		  </div>
		</div>
		<div class="weui-btn-area">
	        <a href="javascript:chkek();" class="weui-btn weui-btn_primary">确定</a>
    	</div>
      </div>
    </div>
</body>
<script type="text/javascript" src="${path }/resource/js/jquery.js"></script>
<script type="text/javascript" src="${path }/resource/webuploader/webuploader.min.js"></script>
<script src="${path }/resource/jqweui/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
	var bucketName = "tykd-xszc-temp";

	$(function(){
		wx.config({
	        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	        appId: '${jsConfig.appId}', // 必填，公众号的唯一标识
	        timestamp: ${jsConfig.timestamp}, // 必填，生成签名的时间戳
	        nonceStr: '${jsConfig.nonceStr}', // 必填，生成签名的随机串
	        signature: '${jsConfig.signature}',// 必填，签名，见附录1
	        jsApiList: ['getLocation','chooseImage','uploadImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	    });
	});

	function upImage(){
		  wx.chooseImage({
	  		    count: 1, // 默认9
	  		    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
	  		    sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
	  		    success: function (res) {
						$.showLoading("上传中");
						var u = navigator.userAgent;
						if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
							wx.uploadImage({
							    localId: res.localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
							    isShowProgressTips: 0, // 默认为1，显示进度提示
							    success: function (res) {
							        var serverId = res.serverId; // 返回图片的服务器端ID
							        $.ajax({
										type : "GET",
										url : "${path}/wx_upload.htm",
										data : "media_id=" + serverId + "&filepath=allLogFace",
										dateType : 'json',
										success : function(msg) {
											msg = $.parseJSON(msg);
											$("#face_image").attr("src",msg.url);
											$.hideLoading();
											submit(msg.url,msg.filename);
										}
									});
							    }
							});
						} else if (u.indexOf('iPhone') > -1) {//苹果手机
							wx.getLocalImgData({
			  		    	    localId: res.localIds[0], // 图片的localID
			  		    	    success: function (res) {
			  		    	        var localData = res.localData.replace('data:image/jgp;base64,', ''); // localData是图片的base64数据，可以用img标签显示
									localData = encodeURIComponent(localData);
									$.ajax({
										type : "GET",
										url : "${path}/wx_upload_base.htm",
										data : "imgBase64=" + localData +"&filepath=allLogFace",
										dateType : 'json',
										success : function(msg) {
											msg = $.parseJSON(msg);
											$("#face_image").attr("src",msg.url);
											$.hideLoading();
											submit(msg.url,msg.filename);
										}
									});
			  		    		}
			  		    	});
						}
	  		    }
	  		});//#chooseImage
    }

	function submit(url,filename) {
		$.showLoading("识别中");
		$.ajax({
             url: "${path}/face_recognition.htm",  
             data: {url:url}, 
             dataType: "json",
             success: function(response){
            	if(response.success == 'true'){
   	            	var identity_id = response.identity_id;
   	            	identity_id = identity_id.substring(identity_id.length-6);
   	            	var code = response.code;
					$("input[name=code]").val(code);
					$("input[name=identity_id]").val(identity_id);
					$("input[name=filename]").val(filename);
					$("input[name=userid]").val(response.userid);
					$("#about").popup();
        		}else{
        			$.alert("😥   若多次未识别，可能准考证照片与现实人脸差异较大，请切换为账号密码登录方式", "未识别");
        		}
        		$.hideLoading();
             },
			 failure : function(response) {
				$.hideLoading();
				$.toast("服务器连接不上!", "forbidden");
			 }
         });
	}
	
	function chkek(){
		var userid = $("#userid").val();
		var code = $("#code").val();
		var identity_id = $("#identity_id").val();
		var identity_id_note = $("#identity_id_note").val();
		var record_img = $("#filename").val();
		
		if(identity_id == identity_id_note){
			var record_url = "${path}/menu/ai/record/add.htm?user_code="+code+"&type=登录&record_img="+record_img;
			$.ajax({
				type : "GET",
				url : record_url,
				success : function(msg) {
					$.toast("识别成功");
				  	setTimeout(function() {
					  	var url = getQueryString("url");
						window.location.href="http://"+url+"?code="+ userid +"&identity_id="+ identity_id;
			        }, 1000)
				}
			});
    	}else{
    		$.toptip('😥   身份证号码与人脸不匹配，请重新操作', 'error');
			$.toast("验证失败", "forbidden");
			//$.closePopup();
    	}
	}
	
	function getQueryString(name) {
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
		return unescape(r[2]);
		}
		return null;
	}
</script>
</html>