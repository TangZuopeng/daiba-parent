<%@ page import="com.daiba.user.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/10/25
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../../include/common.jsp" %>
<html>
<head>
    <%
        User user = null;
        int use_id = 0;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            use_id = user.getUserId();
        }
    %>
    <meta charset="utf-8">
    <title>带吧网络</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <style>
        .mui-content,
        .mui-slider,
        .mui-slider-group {
            background-color: #FFF0E5;
            height: 100%;
            padding-bottom: 30px;
        }

        .mui-img {
            position: absolute;
            right: 20%;
            margin-right: -20px;
            opacity: 0.5;
            filter: alpha(opacity=70); /* IE8 及其更早版本 */
        }

        .mui-table-view .mui-media-object {
            line-height: 42px;
            max-width: 42px;
            height: 42px
        }

        .orderType-img {
            position: absolute;
            top: -4px;
            left: -6px;
            margin: 0px;
            padding: 0px;
            z-index: 1;
        }

        .mui-control-content .mui-loading {
            margin-top: 50px;
        }

        /*订单状态字和下划线样式*/
        .mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
            color: #ff8400;
        }

        .mui-segmented-control.mui-segmented-control-inverted ~ .mui-slider-progress-bar {
            background-color: #ff8400
        }

        /*.mui-table-view{*/
        /*background-color: #FFF0E5;*/
        /*}*/

        /*订单之间的 效果*/
        .mui-scroll-wrapper .mui-table-view {
            margin-top: 3px;
            background-color: #FFF0E5;
        }

        .mui-scroll-wrapper .mui-table-view .mui-table-view-cell {
            margin-top: 3px;
            color: #8f8f94;
            font-size: 13px;
            background-color: white;
        }

        .mui-table-view .mui-table-view-cell {
            border-radius: 0px;
        }

        .mui-table-view .mui-table-view-cell div:nth-child(1) {
            float: left;
        }

        .mui-table-view .mui-table-view-cell div:nth-child(1) div,
        .mui-table-view .mui-table-view-cell div:nth-child(3) div {
            float: none;
        }

        .mui-table-view .mui-table-view-cell div:nth-child(2),
        .mui-table-view .mui-table-view-cell div:nth-child(3) {
            margin-right: 0px;
            float: right;
        }

        .mui-table-view .mui-table-view-cell div:nth-child(2) {
            margin-top: 8px;
            display: -webkit-flex;
            display: flex;
            -webkit-align-items: center;
            align-items: center;
            -webkit-justify-content: center;
            justify-content: center;
        }

        /*.mui-table-view-cell{*/
        /*color: #8f8f94;*/
        /*font-size: 13px;*/
        /*}*/
        .acceptName {
            color: #0062CC;
            font-size: 12px;
        }

        .acceptAdd,
        .acceptPhoneNum,
        .finishTime,
        .mui-icon {
            color: #ff8400;
            font-size: 12px;
        }
    </style>
</head>
<body>
<%@include file="../../include/head.jsp" %>
<%@include file="../../include/foot.jsp" %>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl"
             class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted mui-segmented-control-bar">
            <a class="mui-control-item" href="#notAcceptOrder">
                未接单
            </a>
            <a class="mui-control-item" href="#acceptOrder">
                已接单
            </a>
            <a class="mui-control-item" href="#finishOrder">
                已完成
            </a>
            <a class="mui-control-item" href="#cancelOrder">
                已取消
            </a>
            <a class="mui-control-item" href="#nopayOrder">
                未付款
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-3"></div>
        <div class="mui-slider-group">
            <div id="notAcceptOrder" class="mui-slider-item mui-control-content mui-active">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="acceptOrder" class="mui-slider-item mui-control-content">
                <div id="scroll2" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="finishOrder" class="mui-slider-item mui-control-content">
                <div id="scroll3" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="cancelOrder" class="mui-slider-item mui-control-content">
                <div id="scroll4" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="nopayOrder" class="mui-slider-item mui-control-content">
                <div id="scroll5" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript" src="<c:url value="/js/subs/subpagelist.js"/>"></script>
<script type="text/javascript">

    var use_id = "<%=use_id%>";

    $(document).ready(function () {
        //  此处修改对应页面的标题
        $('.mui-title').text('我发的单');
        $('#headLeft')[0].style.display = 'none';
        mui('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
        updateTabTarget($('#orderTab'), "mui-icon iconfont icon-dingdan");
    });
</script>
</body>
</html>
