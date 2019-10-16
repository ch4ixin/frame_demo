<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
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
</style>
</head>
<body style="background-color: #fff" >
<div class="weui-msg">
  <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
  <div class="weui-msg__text-area">
    <h2 class="weui-msg__title">识别成功</h2>
  </div>
  <div class="weui-form-preview">
  <div class="weui-form-preview__hd">
    <label class="weui-form-preview__label">姓名</label>
    <em class="weui-form-preview__value">${units_user.name}</em>
  </div>
  <div class="weui-form-preview__bd">
    <div class="weui-form-preview__item">
      <label class="weui-form-preview__label">学号</label>
      <span class="weui-form-preview__value">${units_user.userid}</span>
    </div>
    <div class="weui-form-preview__item">
      <label class="weui-form-preview__label">考生号</label>
      <span class="weui-form-preview__value">${units_user.note}</span>
    </div>
    <div class="weui-form-preview__item">
      <label class="weui-form-preview__label">身份证号</label>
      <span class="weui-form-preview__value">${units_user.identity_id}</span>
    </div>
    <div class="weui-form-preview__item">
      <label class="weui-form-preview__label">宿舍</label>
      <span class="weui-form-preview__value">${units_user.dormitory}</span>
    </div>
  </div>
</div>
  <div class="weui-msg__opr-area">
    <p class="weui-btn-area">
      <a href="javascript:" class="weui-btn weui-btn_primary open-popup"" data-target="#about">提交检录</a>
      <a href="${path}/wx/index.htm?units_id=EQ8FTaRLJqoDYumxPNPhYY;" class="weui-btn weui-btn_default">重新识别</a>
    </p>
  </div>
</div>

<div id="about" class='weui-popup__container'>
      <div class="weui-popup__overlay"></div>
      <div class="weui-popup__modal">
		<div class="weui-cells__title">请输入设备号</div>
		<div class="weui-cells weui-cells_form">
		  <div class="weui-cell">
		    <div class="weui-cell__hd"><label for="" class="weui-label">设备号</label></div>
		    <div class="weui-cell__bd">
		      <input class="weui-input" id="machinecode_note" name="machinecode_note" type="text" placeholder="请输入设备号后点击确定" value="${machinecode}">
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
function chkek(){
	$.showLoading("检录中");
	var machinecode_note = $("#machinecode_note").val();
	if(machinecode_note == ''){
		$.hideLoading();
		$.toptip('设备号为空！', 'warning');
		$.toast("检录失败", "forbidden");
	}else{
		var record_url = "${path}/wx/sendFaceData.htm?stdentid=${units_user.note}&machinecode="+machinecode_note;
		$.ajax({
			type : "GET",
			url : record_url,
			success : function(msg) {
				$.hideLoading();
	    		$.toptip('三秒后会返回识别页面。', 'success');
				$.toast("检录成功");
			  	setTimeout(function() {
			  		window.location.href="${path}/wx/index.htm?units_id=EQ8FTaRLJqoDYumxPNPhYY";
			  	}, 3000)
			}
		});
	}
}
</script>
</html>