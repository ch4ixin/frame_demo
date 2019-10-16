<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" id="fkpath" val="${path}" />
<title>食品生产质量追溯</title>
<link rel="stylesheet" href="//cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
<link rel="stylesheet" href="//cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
<link rel="shortcut icon" href="${path }/resource/img/favicon.ico"/>
<script src="//cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
<style>
body{ background-color:#fff;  padding-top:20px;}
</style>
</head>
<body>
    <div class="weui-msg">
      <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg-primary"></i></div>
      <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">批号：${aitc_code }</h2>
        <p class="weui-msg__desc">还未录入该批次的生产信息，还可是用以下方式追溯生产信息。</p>
      </div>
      <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
          <a href="javascript:findById();" class="weui-btn weui-btn_primary">输入批次号查询生产信息</a>
     	  <a href="javascript:findByTel();" class="weui-btn weui-btn_default">输入追溯码查询生产信息</a>
        </p>
      </div>
      <div class="weui-msg__extra-area">
        <div class="weui-footer">
          <p class="weui-footer__text">Copyright © 2018 <a href="http://www.sxxlkj.com" >讯龙科技</a></p>
        </div>
      </div>
    </div>
    <script type="text/javascript">
	
	function findById(){
		$.prompt({
		    text: "请输入产品的批次号",
		    title: "质量追溯",
		    onOK: function(text) {
		    	window.location.href="/traceback/moblie/aitc_code/inquiry_detail.htm?aitc_code="+text; 
		    },
		    onCancel: function() {
	
		    },
		    input: ''
		  });
	}
	
	function findByTel(){
		$.prompt({
		    text: "请输入产品的追溯码",
		    title: "质量追溯",
		    onOK: function(text) {
		    	window.location.href="/traceback/moblie/trace/inquiry_detail.htm?trace_code="+text;
		    },
		    onCancel: function() {
	
		    },
		    input: ''
		  });
	}
</script>
    
</body>
</html>
