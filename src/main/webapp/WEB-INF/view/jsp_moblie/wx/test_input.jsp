<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>人脸认证</title>
<link rel="stylesheet" href="${path }/resource/jqweui/css/weui.min.css">
<link rel="stylesheet" href="${path }/resource/jqweui/css/jquery-weui.min.css">
<link rel="shortcut icon" href="${path }/resource/img/favicon.ico"/>
<script type="text/javascript" src="${path }/resource/js/jquery.js"></script>
<script src="${path }/resource/jqweui/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<style type="text/css">
.weui-loadmore_line .weui-loadmore__tips {
    color: red;
}
.weui-loadmore_line {
    border-top: 1px solid red;
}
.weui-loadmore {
    width: 70%;
}
.weui-loadmore_line {
    margin-top: 1em;
}
</style>
</head>
<body style="background-color: #EBEBEB" >
    <div class="weui-cells__title">身份信息</div>
    <div class="weui-cells weui-cells_form">
       <div class="weui-cell">
	    <div class="weui-cell__bd">
	      <div class="weui-uploader">
	        <div class="weui-uploader__hd">
	          <p class="weui-uploader__title"></p>
	        </div>
	        <div class="weui-uploader__bd" style="text-align: center;">
	           	<img id="face_image" onclick="upImage()" src="${path }/resource/img/face.png" width="50%">
                <input type="text"  id="filename" name="filename" hidden="hidden">
                <input type="text"  id="url" name="url" hidden="hidden">
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="weui-loadmore weui-loadmore_line">
			<span class="weui-loadmore__tips">提示 </span>
			<div>
			<c:if test="${units_user.face_img != ''}">
				<span class="weui-loadmore__tips">您已录入，再次提交将会重新录入</span>
			</c:if>
				<span class="weui-loadmore__tips">点击上方区域上传人脸照片</span>
			</div>
	  </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="name" readonly="readonly" placeholder="请输入姓名" value="${units_user.name }">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">工号/学号</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="code" readonly="readonly" placeholder="请输入工号或学号" value="${units_user.note }">
        </div>
      </div>
    </div>
    <div class="weui-btn-area">
	    <%-- <c:if test="${units_user.face_img == ''}"> --%>
		    <a class="weui-btn weui-btn_primary" href="javascript:submit()" id="showTooltips">提交</a>
		<%-- </c:if>
		<c:if test="${units_user.face_img != ''}">
	    	<a class="weui-btn weui-btn_default" href="javascript:submit()" id="showTooltips">修改</a>
		</c:if> --%>
    </div>
</body>
<script type="text/javascript">
	
	$(function(){
		var face_img = "${units_user.face_img}";
		var url = "${url}";
		if(face_img != ""){
			$("#face_image").attr("src",url);
			$("input[name=filename]").val(face_img);
			$("input[name=url]").val(url);
		}
		
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
      	wx.chooseImage({
  		    count: 1, // 默认9
  		    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
  		    sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
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
						        $.ajax({
									type : "GET",
									url : "${path}/wx_upload.htm",
									data : "media_id=" + serverId,
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$("#face_image").attr("src",msg.url);
										$("input[name=filename]").val(msg.filename);
										$("input[name=url]").val(msg.url);
										$.hideLoading();
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
								$.ajax({
									type : "GET",
									url : "http://xlaib.sxxlkj.com/frame_demo/wx_upload_base.htm",
									data : "imgBase64=" + localData,
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$("#face_image").attr("src",msg.url);
										$("input[name=filename]").val(msg.filename);
										$("input[name=url]").val(msg.url);
										$.hideLoading();
									}
								});
		  		    		}
		  		    	});

					}
  		    }
  		});//#chooseImage
    }
	
	function submit() {
		$.modal({
	          title: "提示",
	          text: "请确保面部充实图像，并且照片真实感！否则终端识别不通过",
	          buttons: [
	            { text: "取消", className: "default"},
	            { text: "提交", onClick: function(){ 
	            	$.showLoading("提交中...");
	        		var name = $("#name").val();
	        		var code = $("#code").val();
	        		var url = $("#url").val();
	        		var filename = $("#filename").val();
	        		var kid = "${units_user.kid}";
	        		
	        		if(name == ""){
	        			 $.toptip('请输入姓名', 'error');
	        			 $.hideLoading();
	            		 return;
	        		}
	        		
	        		if(code == ""){
	        			 $.toptip('请输入编号', 'error');
	        			 $.hideLoading();
	            		 return;
	        		}
	        		
	        		if(filename == ""){
	        			 $.toptip('请录入人脸', 'error');
	        			 $.hideLoading();
	           		 	 return;
	        		}
	        		
	        		$.ajax({
	                     url: "http://xlaib.sxxlkj.com/frame_demo/wx/test_save.htm",
	                     data: {name:name,code:code,url:url,filename:filename,kid:kid}, 
	                     dataType: "json",
	                     success: function(data){
	                    	$.hideLoading();
	                    	//alert(data);
	                    	if(data == 1){
	                    		$.toast("认证成功");
	         	           	}
	                    	if(data == 2){
	                    		$.toast("认证失败", "forbidden");
	         	           		$.toptip('😥 格式有误，或面部检测不通过，请重新拍照', 8000, 'error');
	         	           	}
	                    	if(data == 3){
	                    		$.toast("认证成功");
	         	           	}
	                     },
	        			 failure : function(response) {
	        				$.hideLoading();
	        				$.toast("服务器连接不上!", "forbidden");
	        			 }
	                 });
	            } }
	          ]
	        });
    }
</script>
</html>