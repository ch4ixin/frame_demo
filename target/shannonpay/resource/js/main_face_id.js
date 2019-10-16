var files = new Array();
function initUploader(i) {
	if(i==undefined){i=""};
	$list = $('#thelist'+i);
	$btn = $('#ctlBtn'+i);
	var state = 'pending';

	var uploader = WebUploader.create({
		// 选完文件后，是否自动上传。
		auto : true,
		// swf文件路径
		swf : 'http://cdn.staticfile.org/webuploader/0.1.5/Uploader.swf',
		duplicate : true,
		// 文件接收服务端。
		server : '/shannonpay/face_recognition.htm',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : '#picker' + i,
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize : true,
	    type: 'image/jpeg',
        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });

	// 当有文件被添加进队列的时候
	uploader.on('fileQueued', function(file) {
		$.showLoading("认证中...");
		$list.append('<div id="' + file.id + '" class="item">' +
		// '<h4 class="info">' + file.name + '</h4>' +
		// '<p class="state">等待上传...</p>' +
		'</div>');
	});
	// 文件上传过程中创建进度条实时显示。
	uploader
			.on(
					'uploadProgress',
					function(file, percentage) {
						var $li = $('#' + file.id), $percent = $li
								.find('.progress .progress-bar');

						// 避免重复创建
						if (!$percent.length) {
							$percent = $(
									'<div class="progress progress-striped active">'
											+ '<div class="progress-bar" role="progressbar" style="width: 0%">'
											+ '</div>' + '</div>')
									.appendTo($li).find('.progress-bar');
						}
						//
						// $li.find('p.state').text('上传中');

						$percent.css('width', percentage * 100 + '%');
					});
	uploader.on('uploadSuccess', function(file, response) {
		// $( '#'+file.id ).find('p.state').text('已上传');
		//console.log(response)

		var end = $.parseJSON(response._raw);
		if(end.success == "false"){
			$.toptip('😥 认证失败！', 'error');
			$.alert("请调整面部充满屏幕，重新认证！", "😥 认证失败");
		}else{
			$("#logo_img0").attr("src",end.url);
			$.alert({
				  title: '🙂 认证成功！',
				  text: end.name,
				  onOK: function () {
				    //点击确认
					  var url = getQueryString("url");
//					  console.log(url+"?code="+ end.code +"&identity_id="+ end.identity_id);
					  window.location.href="http://"+url+"?code="+ end.code +"&identity_id="+ end.identity_id; 
					  //alert("这里放要跳转的页面 ");
				  }
			});
		}
		$.hideLoading();
	});

	uploader.on('uploadError', function(file) {
		$('#' + file.id).find('p.state').text('上传出错');
	});

	uploader.on('uploadComplete', function(file) {
		$('#' + file.id).find('.progress').fadeOut();
	});

	$('#picker'+i).on('click', function() {
		var u = navigator.userAgent;
		if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机

		} else if (u.indexOf('iPhone') > -1) {//苹果手机
//			$.toast("请将手机向右旋转90°面部充满屏幕自拍！", "text");
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