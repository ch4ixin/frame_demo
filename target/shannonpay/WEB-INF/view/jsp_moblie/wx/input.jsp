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

.weui-cells {
    height: 100%;
    width: 100%;
    position: absolute;
    overflow-y: auto;
}
.weui-tabbar {
    position: fixed;
}
</style>
</head>
<body style="background-color: #fff" >
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
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="weui-loadmore weui-loadmore_line">
			<span class="weui-loadmore__tips">点击上方区域进行人脸认证</span>
			<span class="weui-loadmore__tips" style="color: red">认证后，一分钟生效</span>
			<!-- <span class="weui-loadmore__tips">拍照及过通道时请摘掉眼镜、帽子</span> -->
	  </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="name" disabled="disabled" placeholder="请输入姓名" value="${units_user.name }">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">工号/学号</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="code" disabled="disabled" placeholder="请输入工号或学号" value="${units_user.note }">
        </div>
      </div>
    </div>
    <div class="weui-tabbar">
      <a href="${path }/wx/input.htm" class="weui-tabbar__item  weui-bar__item--on" style="border-right: 1px solid #c0bfc4;">
        <div class="weui-tabbar__icon">
          <img src="${path }/resource/icon/user.png" alt="">
        </div>
        <p class="weui-tabbar__label">我</p>
      </a>
      <a href="${path }/wx/index.htm" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
          <img src="${path }/resource/icon/more.png" alt="">
        </div>
        <p class="weui-tabbar__label">管理</p>
      </a>
    </div>
</body>
<script type="text/javascript">
	$(function(){
		$.showLoading();
		wx.config({
		    beta: false,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${jsConfig.appId}', // 必填，企业微信的corpID
		    timestamp: ${jsConfig.timestamp}, // 必填，生成签名的时间戳
		    nonceStr: '${jsConfig.nonceStr}', // 必填，生成签名的随机串
		    signature: '${jsConfig.signature}',// 必填，签名，见 附录-JS-SDK使用权限签名算法
		    jsApiList: ['chooseImage','uploadImage','getLocalImgData'] // 必填，需要使用的JS接口列表，凡是要调用的接口都需要传进来
		});
		
		var face_img = "${units_user.face_img}";
		if(face_img != ""){
			$("input[name=filename]").val(face_img);
			var ajaxTimeOut = $.ajax({
				type : "GET",
				url : "${path}/aliyun_img.htm",
				timeout : 60000, //超时时间设置，单位毫秒
				data : "filename=" + face_img,
				dateType : 'json',
				success : function(msg) {
					//console.log(msg);
					$("#face_image").attr("src",msg);
					$("input[name=url]").val(msg);
					setTimeout(function() {
						$.hideLoading();
			        }, 1000)
				},
		        error:function(response){
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
		}else{
			setTimeout(function() {
				$.hideLoading();
	        }, 500)
		}
	})
	
   function upImage(){
      	wx.chooseImage({
  		    count: 1, // 默认9
  		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
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
						        var ajaxTimeOut = $.ajax({
									type : "GET",
									url : "${path}/qywx_upload.htm",
									timeout : 60000, //超时时间设置，单位毫秒
									data : "media_id=" + serverId + "&filepath=allFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$("#face_image").attr("src",msg.url);
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
									data : "imgBase64=" + localData +"&filepath=allFace",
									dateType : 'json',
									success : function(msg) {
										msg = $.parseJSON(msg);
										$("#face_image").attr("src",msg.url);
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
		var kid = "${units_user.kid}";
   		$.toptip('🕒  提取人脸特征时间会稍长，请稍等...', 60000, 'warning');
   		var ajaxTimeOut = $.ajax({
		 type : "GET",
         url: "${path}/wx/save.htm", 
		 timeout : 60000, //超时时间设置，单位毫秒
            data: {url:url,filename:filename,kid:kid}, 
            dataType: "json",
            success: function(data){
	           	$.hideLoading();
	           	if(data == false){
	           		$.toast("认证失败", "forbidden");
		           		$.toptip('😥 未找到人脸,请重新调整拍照！', 'error');	
	           	}
	           	if(data == true){
	           		$.toptip('🙂 认证成功，一分钟生效', 10000, 'success');  //设置显示时间
	           		$.toast("认证成功");
	           	}
            },
		 error : function(response) {
			$.hideLoading();
			$.toptip('😣 图像解析错误，请使用后置摄像头重试', 'warning');
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
	};	
</script>
</html>