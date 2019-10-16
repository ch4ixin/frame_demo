<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <title>费用登记表</title>
    <link rel="shortcut icon" href="${path}/resource/img/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style>
        p,div,a,img,table,tr,td{
            padding:0;
            margin:0;
        }
        body{
            font-size:14px;
            font-family: "Microsoft YaHei";
        }
        .sp-font{
            padding:10px;
        }
        .title{
           font-size:16px;
        }
        .main{
            text-align:center;
        }
  	    .table{
     	    width:280px;
     	    margin:auto;
     	}
        table{
            border-collapse: collapse;

        }
        tr td{
            border:1px solid black;
            padding:4px;
            font-size:12px;
            text-align: center;
        }
        .date{
         width:61px;
        }
    </style>
</head>
<body>
<div class="main">
    <div id="header">
        <div class="sp-font">
            <p class="title">费用登记表</p>
        </div>
    </div>
    <div class="table">
    <table align="center">
        <tr>
            <td>司机姓名</td>
            <td>${fk_car.name }</td>
            <td>车牌号码</td>
            <td>${fk_car.code }</td>
        </tr>
	    <tr>
            <td>装货日期</td>
            <td class="date">
            <jsp:useBean id="loading_date" class="java.util.Date" />
			<jsp:setProperty name="loading_date" property="time" value="${fk_car.loading_date }"/>
			<fmt:formatDate value="${loading_date}" type="date" dateStyle="long"/>
            </td>
            <td>实收</td>
            <td>${fk_car.real }</td>
        </tr>
        <tr>
            <td>毛重</td>
            <td>${fk_car.gross_weight }</td>
            <td>起始地址</td>
            <td>${fk_car.start_locale }</td>
        </tr>
        <tr>
            <td>到达地点</td>
            <td>${fk_car.end_locale }</td>
            <td>过路费</td>
            <td>${fk_car.road_toll }</td>
        </tr>
        <tr>
            <td>加油数量</td>
            <td>${fk_car.refuel_toll }</td>
            <td>餐饮住宿</td>
            <td>${fk_car.catering_toll }</td>
        </tr>
        <tr>
            <td>卸车费用</td>
            <td>${fk_car.unload_toll }</td>
            <td>合计</td>
            <td>${fk_car.total }</td>
        </tr>
    </table>
    </div>　　　　 　　　　
</div>
</body>
</html>