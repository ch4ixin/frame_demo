<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../jsp_cp/common/init.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>人脸录入</title>
<link rel="stylesheet" href="${path }/resource/jqweui/css/weui.min.css">
<link rel="stylesheet" href="${path }/resource/jqweui/css/jquery-weui.min.css">
<link rel="shortcut icon" href="${path }/resource/img/favicon.ico"/>
<script type="text/javascript" src="${path }/resource/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/resource/webuploader/webuploader.css">
<script type="text/javascript" src="${path }/resource/webuploader/webuploader.min.js"></script>
<script src="${path }/resource/jqweui/js/jquery-weui.min.js"></script>
<script src="${path }/resource/js/upfile_aliyun_oos.js"></script>
<script src="${path }/resource/clmtrackr/js/libs/utils.js"></script>
<script src="${path }/resource/clmtrackr/build/clmtrackr.js"></script>
<script src="${path }/resource/clmtrackr/js/libs/Stats.js"></script>
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
#overlay {
	position: absolute;
	top: 0px;
	left: 0px;
}
#container {
	position : relative;
	width : 100%;
	height : 100%;
	margin : 0px auto;
	text-align: center;
}
</style>
</head>
<body style="background-color: #EBEBEB" >
    <div class="weui-cells__title">身份注册</div>
    <div class="weui-cells weui-cells_form">
       <div class="weui-cell">
	    <div class="weui-cell__bd">
	      <div class="weui-uploader">
	        <div class="weui-uploader__hd">
	          <p class="weui-uploader__title"></p>
	        </div>
	        <div class="weui-uploader__bd" style="text-align: center;">
		           <%-- <div><img id="logo_img0" src="${path }/resource/img/face.png" width="65%"></div> --%>
		           <div id="container" >
						<canvas id="logo_img0" onclick="upImage()" style="text-align: center;"></canvas>
						<canvas id="overlay" style="text-align: center;"></canvas>
					</div>
					<input type="button" class="btn" value="start" onclick="animateClean()"></input>
					<input type="file" class="btn" id="files" name="files[]" />
					<p id="convergence"></p>
				<div id="uploader" class="wu-example">
				<div id="thelist" class="uploader-list">
				</div>
				<div class="btns">
				<div id="picker0">拍照</div>
				</div></div>
                <input type="text"  id="filename" name="filename" hidden="hidden">
                <input type="text"  id="url" name="url" hidden="hidden">
	        </div>
	      </div>
	    </div>
	  </div>
	  <div class="weui-loadmore weui-loadmore_line">
		<span class="weui-loadmore__tips">确保头像正立，非正立请旋转手机重拍</span>
	  </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="name" placeholder="请输入姓名" value="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">工号/学号</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" id="code" placeholder="请输入工号或学号" value="">
        </div>
      </div>
    </div>
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:submit()" id="showTooltips">提交</a>
    </div>
</body>
<script type="text/javascript">
	var canvas = document.getElementById('logo_img0')
	var cc = canvas.getContext('2d');
	var overlay = document.getElementById('overlay');
	var overlayCC = overlay.getContext('2d');
	
	var img = new Image();
	img.src = '${path}/resource/img/face.png';
	img.onload = function() {
		canvas.setAttribute('width', img.width*0.65);
		canvas.setAttribute('height', img.height*0.65);
		cc.drawImage(img,0,0,img.width*0.65, img.height*0.65);
	};
	
	var ctrack = new clm.tracker({stopOnConvergence : true});
	ctrack.init();
	
	stats = new Stats();
	stats.domElement.style.position = 'absolute';
	stats.domElement.style.top = '0px';
	document.getElementById('container').appendChild( stats.domElement );
	
	var drawRequest;
	
	function animateClean() {
		ctrack.start(document.getElementById('logo_img0'));
		drawLoop();
	}
	
	function drawLoop() {
		drawRequest = requestAnimFrame(drawLoop);
		overlayCC.clearRect(0, 0, img.width, img.height);
		if (ctrack.getCurrentPosition()) {
			ctrack.draw(overlay);
		}
	}
	
	// detect if tracker fails to find a face
	document.addEventListener("clmtrackrNotFound", function(event) {
		ctrack.stop();
		alert("从这张照片中找不到人脸。请重新拍照。")
	}, false);
	
	// detect if tracker loses tracking of face
	document.addEventListener("clmtrackrLost", function(event) {
		ctrack.stop();
		alert("从这张照片中找不到人脸。请重新拍照。")
	}, false);
	
	// detect if tracker has converged
	document.addEventListener("clmtrackrConverged", function(event) {
		document.getElementById('convergence').innerHTML = "CONVERGED";
		document.getElementById('convergence').style.backgroundColor = "#00FF00";
		// stop drawloop
		cancelRequestAnimFrame(drawRequest);
	}, false);
	
	// update stats on iteration
	document.addEventListener("clmtrackrIteration", function(event) {
		stats.update();
	}, false);
	
	// function to start showing images
	function loadImage() {
		if (fileList.indexOf(fileIndex) < 0) {
			var reader = new FileReader();
			reader.onload = (function(theFile) {
				return function(e) {
					// check if positions already exist in storage

					// Render thumbnail.
					var canvas = document.getElementById('logo_img0')
					var cc = canvas.getContext('2d');
					var img = new Image();
					img.onload = function() {
						if (img.height > 500 || img.width > 700) {
							var rel = img.height/img.width;
							var neww = 700;
							var newh = neww*rel;
							if (newh > 500) {
								newh = 500;
								neww = newh/rel;
							}
							canvas.setAttribute('width', neww);
							canvas.setAttribute('height', newh);
							cc.drawImage(img,0,0,neww, newh);
						} else {
							canvas.setAttribute('width', img.width);
							canvas.setAttribute('height', img.height);
							cc.drawImage(img,0,0,img.width, img.height);
						}
					}
					img.src = e.target.result;
				};
			})(fileList[fileIndex]);
			reader.readAsDataURL(fileList[fileIndex]);
			overlayCC.clearRect(0, 0, 720, 576);
			document.getElementById('convergence').innerHTML = "";
			ctrack.reset();
		}

	}

	// set up file selector and variables to hold selections
	var fileList, fileIndex;
	if (window.File && window.FileReader && window.FileList) {
		function handleFileSelect(evt) {
			var files = evt.target.files;
			fileList = [];
			for (var i = 0;i < files.length;i++) {
				if (!files[i].type.match('image.*')) {
					continue;
				}
				fileList.push(files[i]);
			}
			if (files.length > 0) {
				fileIndex = 0;
			}

			loadImage();
		}
		document.getElementById('files').addEventListener('change', handleFileSelect, false);
	} else {
		$('#files').addClass("hide");
		$('#loadimagetext').addClass("hide");
	}

	initUploader(0);
	function submit() {
		$.modal({
	          title: "提示",
	          text: "请再次确保头像正立，非正立请将手机向右旋转90度再次拍照！",
	          buttons: [
	            { text: "重拍", className: "default"},
	            { text: "提交", onClick: function(){ 
	            	$.showLoading("提交中...");
	        		var name = $("#name").val();
	        		var code = $("#code").val();
	        		var filename = $("#filename").val();
	        		var url = $("#url").val();
	        		
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
	                     url: "/shannonpay/moblie/save.htm",
	                     data: {name:name,code:code,filename:filename,url:url}, 
	                     dataType: "json",
	                     success: function(data){
	                    	$.hideLoading();
	                    	if(data == false){
	                    		$.toast("录入失败", "forbidden");
	         	           		$.toptip('😥 未找到人脸,请重新调整拍照！', 'error');
	         	           	}
	                    	if(data == true){
	                    		$.toast("录入成功");
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
	};	
</script>
</html>