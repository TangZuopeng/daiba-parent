<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/17
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    String conmsgId = request.getParameter("conmsgId").toString();
    String type = request.getParameter("type").toString();
%>
<%@include file="../../include/common.jsp" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>

    <script type="text/javascript">
        var conmsgid = "<%=conmsgId%>";//0代表是添加 1代表是修改
        var mytype = "<%=type%>";//收货地址编号 用于修改
    </script>
    <style>
        .mui-content {
            background-color: #FFF0E5;
            height: 100%;
        }

        .mui-input-group .mui-input-row {
            margin-top: 10px;
        }

        .mui-content-padded {
            margin-top: 15px;
        }

        .mui-input-row span {
            margin-left: 13px;
            color: #ff8400;
            width: 25%;
            font-size: 13px;
        }

        .mui-input-row span ~ div,
        .mui-input-row span ~ input {
            width: 75%;
            font-size: 12px;
        }

        .mui-span span {
            float: left;
            width: 50%;
        }

        .mui-span span ~ span {
            width: 20%;
            float: right;
        }
        .mui-icon-arrowright{
            padding-right: 5px;
            margin-top: 3px;
        }
        .mui-table-view-cell{
            padding: 11px 15px;
        }
    </style>
</head>
<body>

<%@include file="../../include/head.jsp" %>

<div class="mui-content">
    <form class="mui-input-group" style="margin-top: 20px;">
        <div class="mui-input-row">
            <span>收件人：</span>
            <input type="text" class="mui-input-clear mui-input" placeholder="请输入真实收件人姓名" id="addName">
        </div>
        <div class="mui-input-row" id="addSexItem">
            <span>性别：</span>
            <div class="mui-span">
                <span id="addSex">男神</span>
                <span class="mui-icon mui-icon-arrowright"></span>
            </div>
        </div>
        <div class="mui-input-row">
            <span>电话：</span>
            <input type="text" class="mui-input" placeholder="请输入手机号码" id="addPhoneNum"/>
        </div>
        <div class="mui-input-row" id="addCampusItem">
            <span>校区：</span>
            <div class="mui-span">
                <span id="addCampus">南湖校区</span>
                <span class="mui-icon"></span>
                <%--<span class="mui-icon mui-icon-arrowright"></span>--%>
            </div>
        </div>
        <div class="mui-input-row">
            <span>楼栋：</span>
            <input type="text" class="mui-input-clear mui-input" placeholder="请输入楼栋" id="addBuild">
        </div>
        <div class="mui-input-row">
            <span>房间号：</span>
            <input type="text" class="mui-input-clear mui-input" placeholder="请输入房间号" id="addRoom">
        </div>
    </form>
    <div class="mui-content-padded">
        <button class="mui-btn mui-btn-block mui-btn-primary" id="saveAddItem">保存</button>
        <button class="mui-btn mui-btn-block mui-btn-primary md-trigger" data-modal="modal-4" id="deleteAddItem">删除
        </button>
    </div>
    <div id="myPopSex" class="mui-popover">
        <ul class="mui-table-view mui-table-view-radio" id="myUlSex">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center; font-size: 15px">男神</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right " style="text-align: center;font-size: 15px">女神</a>
            </li>
        </ul>
    </div>

    <div id="myPopCampus" class="mui-popover">
        <ul class="mui-table-view mui-table-view-radio" id="myUlCampus">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">南湖校区</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">林园校区</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right " style="text-align: center;">北湖校区</a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
<script src="<c:url value="/js/personal/per_address.js"/> "></script>
<script>
    mui.init();
    $(document).ready(function () {
        $('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    });
</script>


