<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/17
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>带吧网络</title>
    <style>
        .menu{
            display: flex;
            margin-bottom: 2px;
        }
        .menu-item{
            width: 25%;
            border-radius: 5px;
            margin: 1px;
        }
        .unchecked{
            color: black;
            background: white;
        }
        .checked{
            color: white;
            background: #00AAEE;
        }
        .order{
            margin-left: 16px;
        }
    </style>
</head>
<body>

<%@include file="../../include/common.jsp" %>
<%@include file="../../include/head.jsp" %>
<%@include file="../../include/foot.jsp" %>


<div class="mui-content">
    <div class="menu">
        <button id="received" class="menu-item unchecked checked" onclick="updateType(0)">已接单</button>
        <button id="pending" class="menu-item unchecked" onclick="updateType(1)">未接单</button>
        <button id="finished" class="menu-item unchecked" onclick="updateType(2)">已完成</button>
        <button id="canceled" class="menu-item unchecked" onclick="updateType(3)">已取消</button>
    </div>

    <div id="list">
    </div>
</div>
</body>
<script src="<c:url value="/js/main/sender_order.js"/> "></script>

</html>
