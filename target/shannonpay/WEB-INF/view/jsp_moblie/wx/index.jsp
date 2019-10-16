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
.weui-loadmore_line {
    border-top: 0px solid red;
}
.weui-loadmore {
    width: 70%;
}
.weui-loadmore_line {
    margin-top: 1em;
}
.weui-input {
    color: #000;
}
.weui-tabbar {
    background-color: #fff;
    box-shadow:1px 5px 10px #220;
}
.weui-grid__icon {
    width: 50px;
    height: 50px;
}
</style>
</head>
<body style="background-color: #fff" >
    <div class="weui-grids">
      <a href="javascript:upImage();" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="${path }/resource/img/ic_launcher.png" alt="">
        </div>
        <p class="weui-grid__label">
          识别体验
        </p>
      </a>
      <a href="${path }/wx/temporary.htm?father_id=${units_user.kid}" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="${path }/resource/img/users.png" alt="">
        </div>
        <p class="weui-grid__label">
          临时人员
        </p>
      </a>
      <a href="#" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="${path }/resource/img/xlkj.jpg" alt="">
        </div>
        <p class="weui-grid__label">
         更多即将上线..
        </p>
      </a>
    </div>
    <div class="weui-tabbar">
      <a href="${path }/wx/input.htm" class="weui-tabbar__item " style="border-right: 1px solid #c0bfc4;">
        <div class="weui-tabbar__icon">
          <img src="${path }/resource/icon/user.png" alt="">
        </div>
        <p class="weui-tabbar__label">我</p>
      </a>
      <a href="${path }/wx/index.htm" class="weui-tabbar__item weui-bar__item--on">
        <div class="weui-tabbar__icon">
          <img src="${path }/resource/icon/more.png" alt="">
        </div>
        <p class="weui-tabbar__label">管理</p>
      </a>
    </div>
</body>
<script type="text/javascript">
	$(function(){
		wx.config({
		    beta: false,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${jsConfig.appId}', // 必填，企业微信的corpID
		    timestamp: ${jsConfig.timestamp}, // 必填，生成签名的时间戳
		    nonceStr: '${jsConfig.nonceStr}', // 必填，生成签名的随机串
		    signature: '${jsConfig.signature}',// 必填，签名，见 附录-JS-SDK使用权限签名算法
		    jsApiList: ['chooseImage','uploadImage','getLocalImgData'] // 必填，需要使用的JS接口列表，凡是要调用的接口都需要传进来
		});
	})
	
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
						        var serverId = res.serverId; // 返回图片的服务端ID
						        var ajaxTimeOut = $.ajax({
									type : "GET",
									url : "${path}/qywx_upload.htm",
									timeout : 60000, //超时时间设置，单位毫秒
									data : "media_id=" + serverId + "&filepath=allLogFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$.hideLoading();
										submit(msg.url,msg.filename);
									},
									complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
									　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
									 　　　　　	ajaxTimeOut.abort(); //取消请求
											$.hideLoading();
											$.toptip('😣 网络连接超时，请重试', 'warning');
								　　　    	  }else if(status=='error'){
							　　　    		    $.hideLoading();
											$.toptip('😣 服务好像出问题了，请重试', 'warning');
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
									data : "imgBase64=" + localData +"&filepath=allLogFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$.hideLoading();
										submit(msg.url,msg.filename);
									},
									complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
									　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
									 　　　　　	ajaxTimeOut.abort(); //取消请求
											$.hideLoading();
											$.toptip('😣 网络连接超时，请重试', 'warning');
								　　　    	  }else if(status=='error'){
							　　　    		    $.hideLoading();
											$.toptip('😣 服务好像出问题了，请重试', 'warning');
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
		$.showLoading("识别中"); 
   		$.toptip('🕒  提取人脸特征时间会稍长，请稍等...', 'warning');
		var ajaxTimeOut = $.ajax({
			 type : "GET",
             url: "${path}/face_recognition.htm",  
             data: {url:url}, 
			 timeout : 60000, //超时时间设置，单位毫秒
             dataType: "json",
             success: function(response){
            	if(response.success == 'true'){
					$.modal({
   	        		  title: response.name.split("_")[0],
   	        		  text: "相似度："+response.name.split("_")[2],
   	        		  buttons: [
   	        		    { text: "重新识别", onClick: function(){
   	        		    	upImage()
   	        		    } },{ text: "关闭", className: "default", onClick: function(){
   	        		    	console.log(response);
   	        		    } },
   	        		  ]
   	        		});
        			$.ajax({
        				type : "GET",
        				url : "${path}/menu/ai/record/add.htm?user_code="+response.code+"&type=体验&record_img="+filename+"&score="+response.name.split("_")[2],
        				success : function(msg) {
        				}
        			});
        		}else{
        			$.alert("😥   请正对屏幕，使用自然表情自拍重新识别", "未识别");
        		}
           		$.hideLoading();
             },
		  	 complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
			　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
			 　　　　　	ajaxTimeOut.abort(); //取消请求
					$.hideLoading();
					$.toptip('😣 网络连接超时，请重试', 'warning');
		　　　    	  }else if(status=='error'){
	　　　    		    $.hideLoading();
					$.toptip('😣 图像解析错误，请使用后置摄像头重试', 'warning');
		　　　    	  }
			 },
			 failure : function(response) {
					$.hideLoading();
					$.toptip('😣 好像出问题了，请重试', 'warning');
			 }
         });
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