<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/17
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../include/common.jsp" %>
<html>
<%
    String pageFrom = request.getParameter("pageFrom").toString();
    String taskType = null;
    if (request.getParameter("taskType") != null){
        taskType = request.getParameter("taskType");
    }
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
    <script type="text/javascript">
        var pageFrom = "<%=pageFrom%>";
        var taskType = "<%=taskType%>";
    </script>
    <style>
        .mui-content,
        .mui-slider,
        .mui-slider-group{
            background-color: #FFF0E5;
            height: 100%;
        }
        /*订单之间的 折叠效果*/
        .mui-scroll-wrapper .mui-table-view .mui-table-view-cell{
            margin-top: 5px;
            border-bottom: 0px solid #ff8400;
            background-color: white;
            color: #8f8f94;
            font-size: 13px;
        }

        .mui-table-view .mui-table-view-cell div:nth-child(1){
            width: 87%;
            float: left;
        }
        .mui-table-view .mui-table-view-cell div:nth-child(2){
            width: 13%;
            float: right;
        }
        .mui-table-view .mui-table-view-cell div:nth-child(1) div{
            float: none;
        }
        .phoneNum,
        .acceptPhoneNum{
            float: right;
        }
        .mui-table-view .mui-table-view-cell {
            margin: 8px 20px;
        }
        .mui-table-view{
            background-color: #FFF0E5;
        }
        .acceptName{
            color: #0062CC;
        }
        .acceptAdd,
        .acceptPhoneNum,
        .finishTime,
        .acceptSex,
        .mui-icon{
            color: #ff8400;
        }
        .mui-icon.mui-icon-left-nav.mui-pull-left{
            color: #FFF0E5;
        }
        .mui-icon-compose{
            font-size: 36px;
        }
    </style>
</head>

<body>
<%@include file="../../include/head.jsp" %>
<div class="mui-content">

    <div id="slider" class="mui-slider">
        <div class="mui-slider-group">
            <div class="mui-slider-item mui-control-content mui-active">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <ul class="mui-table-view" id="myItem">

                        </ul>
                        <div class="mui-content-padded" id="addClick">
                            <button class="mui-btn mui-btn-block mui-btn-primary">添加地址</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="<c:url value="/js/personal/address_main.js?v=1.0.1"/>"></script>
<script>
    mui.init({});
    (function ($) {
        $('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    })(mui);
</script>
</html>
