<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../jsp_cp/common/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <title>食品购销凭证</title>
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
        .main{
            width:1000px;
            border:1px solid black;
        }
        table{
            border-collapse: collapse;
            width:900px;
           /* position:absolute;
            left:50px;*/

        }
        tr td{
            border:1px solid black;
            height:30px;
            text-align: center;
        }

        .kehu{
            text-align:left;
            font-weight:700;
            border:0;
        }
        #header{

            font-weight:700;

        }
        #img1{
            width:100%;
        }
        #p2{
            text-align: right;
            margin-right:130px;
            margin-top: -40px;
        }
        .yue{
            border:0px solid red; text-align:left;
            font-weight:600;
            font-size:16px;
        }
        #heji{
            border:1px solid black;text-align:left;
        }
        #shupai{
            position: absolute;
            right:25px;
            margin-top:10px;
            width:20px;
        }
        .img-l{
            float:left;
            width:120px;
            height:120px;
            margin-left:75px;
        }
        .sp-font{
            float:left;
            margin-left:67px;

        }
        .title{
            margin-top:25px;
            letter-spacing:25px;
            font-size:28px;
        }
        .title1{
            font-size:16px;
            text-align: center;
        }
        .img-r{
            margin-top:10px;
            float:right;
            width:335px;
            margin-right:50px;
        }
        .img2{
            width:340px;
        }
        .table{
            margin-left:50px;
        }
        .main-header{
            font-weight: bold;
            font-size:14px;
        }
        .main-header p{
            line-height: 25px;
        }
        .title2{
    		margin-left: 5px;
    		letter-spacing:19px;
    		font-size: 16px;
		}
    </style>
</head>
<body>
<div class="main">
    <div id="header">
        <div class="img-l">
            <img src="${path }/resource/img/erwei.png" alt="" id="img1"/>
        </div>
        <div class="sp-font">
            <p class="title">食品购销凭证</p>
            <p class="title1">（电子一票通）</p>
        </div>
        <div class="img-r">
            <img src="${path }/resource/img/txm.png" alt="" class="img2"/>
            <p class="title2">${list1.tixone_code }</p>
        </div>
    </div>
    <div class="main-header">
        <p>
            <span></span>
        </p>
    </div>
    <div class="table">
    <table>
        <tr >
            <td colspan="3" class="kehu">购货单位：${list1.distributor_name }</td>
            <td colspan="3" class="kehu">地址：${shop_franchiser.address }</td>
            <td colspan="2" class="kehu">电话：</td>
            <td colspan="1" class="kehu">日期：
            	<jsp:useBean id="created_bill_time" class="java.util.Date" />
				<jsp:setProperty name="created_bill_time" property="time" value="${list1.created_bill_time }"/>
				<fmt:formatDate value="${created_bill_time}" type="date" dateStyle="long"/>
            </td>
        </tr> 
        <tr>
            <td>商品条码</td>
            <td>商品名称</td>
            <td>包装规格</td>
            <td>单位</td>
            <td>数量</td>
            <td>单价（元）</td>
            <td>金额（元）</td>
            <td>生产日期</td>
            <td>保质期（天)</td>
        </tr>
        <c:forEach items="${list }" var="l" varStatus="status">
	        <tr>
	            <td>${l.barcode }</td>
	            <td>${l.name }</td>
	            <td></td>
	            <td>${l.unit}</td>
	            <td>${l.number}</td>
	            <td></td>
	            <td></td>
	            <td>
	            	<jsp:useBean id="production_time" class="java.util.Date" />
					<jsp:setProperty name="production_time" property="time" value="${l.production_time }"/>
					<fmt:formatDate value="${production_time}" type="date" dateStyle="long"/>
	            </td>
	            <td></td>
	        </tr>
        </c:forEach>
        <tr>
            <td colspan="9" id="heji">
                <span style="display:block;float:left">合计金额（大写）： 零元整</span>
                <span style="display:block;float:right;margin-right:10px;">
                    ￥:0
                </span>

            </td>
        </tr>
        <tr >
            <td colspan="3" class="kehu">供货单位：${shop.name }</td>
            <td colspan="4" class="kehu">地址：</td>
            <td colspan="2" class="kehu">电话：${shop.link_mobile }</td>
        </tr>
    </table>
        <div class="main-header">
            <p>
                供货单位承诺：以上商品均已行进货检查验收法定程序，所验票证齐全。
            </p>
            <p>
                注：该购销凭证可作为台账查料，根据《食品安全法》，需保存两年。
            </p>
            <p style="text-align: right;margin-right:100px;">
                甘肃省食品药品监督管理局监制
            </p>
        </div>
    </div>　　　　 　　　　
</div>
</body>
</html>