<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/10/23
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../include/common.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
    <style>
        .mui-table-view {
            background-color: #FFF0E5;
        }

        .mui-content,
        .mui-slider {
            background-color: #FFF0E5;
            height: 100%;
        }

        .mui-table-view .mui-table-view-cell {
            left: 5%;
            width: 90%;
            background-color: white;
            margin-top: 5px;
        }

        .mui-table-view-chevron .mui-table-view-cell span {
            margin: 0 2px;
        }

        .mui-table-view-chevron .mui-table-view-cell span:nth-child(3),
        .mui-table-view-chevron .mui-table-view-cell span:nth-child(4) {
            float: right;
        }

        .subTitle {
            margin: 4px;
            text-align: center;
            font-size: 15px;
            color: black;
        }

        .mui-content-padded {
            height: 30px;
            margin-top: 10px;
        }

        .mui-ellipsis {
            font-size: 12px;
        }

        .accept {
            font-size: 13px;
            float: left;
        }

        .acceptName {
            color: #0062CC;
            float: left;
        }

        .acceptAdd {
            color: #C0C0C0;
        }

        .span-color {
            color: #000000;
        }

        .mui-btn {
            font-size: 15px;
            height: 100%;
            padding: 0;
        }
        /*.mui-table-view-cell {*/
            /*padding: 11px 15px;*/
        /*}*/
        .mui-slider .mui-slider-group .mui-slider-item{
            font-size: 12px;
        }

        .goods-name {
            width: 38%;
            float: left;
            color: #000000;
        }

        .goods-money {
            width: 7%;
            color: #FF0000;
        }

        .goods-num {
            width: 5%;
            float: right;
            color: #000000;
        }

        .goods-place {
            width: 40%;
            text-align: right;
            float: right;
            color: #C0C0C0;
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
                    <div id="contents" class="mui-scroll">
                        <div id="orderStatusDiv">
                            <ul class="mui-table-view mui-table-view-chevron">
                                <li class="mui-table-view-cell">
                                    <span>订单状态：</span>
                                    <span id="order_status" class="span-color"></span>
                                </li>
                            </ul>
                        </div>
                        <div id="giverDiv" hidden style="height: 46px;margin-top: 3px">
                            <ul class="mui-table-view mui-table-view-chevron">
                                <li class="mui-table-view-cell">
                                    <span style="float: left">发单人：</span>
                                    <span style="float: left ; font-size: 12px" id="giverName"
                                          class="span-color"></span>
                                    <span style="float: left ; font-size: 12px" class="span-color"><a
                                            id="giverPhone"></a></span>
                                </li>
                            </ul>
                        </div>

                        <ul id="orderList" style="margin-top: 15px" class="mui-table-view mui-table-view-chevron">
                            <li style="padding: 5px 15px 5px 15px;" class="mui-table-view-cell" id="addressItem">
                                <div>
                                    <div>
                                        <span class="accept">收货人：</span>
                                        <span style="font-size: 12px" class="acceptName" id="shrName"></span>
                                        <span style="margin: 0 1px;float: left;font-size: 12px" id="shrTel"></span>
                                    </div>
                                    <br>
                                    <div>
                                        <span style="font-size: 12px" class="acceptAdd" id="shrAddress"></span>
                                    </div>
                                </div>
                            </li>
                            <li id="taskTypeItem" hidden class="mui-table-view-cell">
                                <span>任务类型：</span>
                                <span id="taskType" class="span-color"></span>
                            </li>
                            <li id="taskForItem" hidden class="mui-table-view-cell">
                                <span id="taskForSpan">任务简介：</span>
                                <span style="font-size: 12px" id="taskFor" class="span-color"></span>
                            </li>
                            <li id="taskPlaceItem" class="mui-table-view-cell">
                                <span id="taskPlaceSpan">任务地点：</span>
                                <span style="font-size: 12px" id="taskPlace" class="span-color"></span>
                            </li>
                            <li id="takeNumItem" hidden class="mui-table-view-cell">
                                <span>取件号：</span>
                                <span style="font-size: 12px" id="takeNum" class="span-color"></span>
                            </li>
                            <li id="askTimeItem" class="mui-table-view-cell">
                                <span id="askTimeSpan">送达时间：</span>
                                <span style="font-size: 12px" id="askTime" class="span-color"></span>
                            </li>
                            <li class="mui-table-view-cell">
                                <span>接单佣金：</span>
                                <span style="font-size: 12px" id="orderMoney" class="span-color"></span>
                            </li>
                            <li hidden id="goodsMoneyItem" class="mui-table-view-cell">
                                <span>本金：</span>
                                <span style="font-size: 12px" id="goodsMoney" class="span-color"></span>
                            </li>
                            <li id="contentItem" class="mui-table-view-cell">
                                <span id="contentSpan">备&emsp;注：</span>
                                <span style="font-size: 12px" id="content" class="span-color"></span>
                            </li>
                        </ul>
                        <div class="mui-slider-group">
                            <div id="one" style="visibility:hidden" class="mui-content-padded">
                                <button id="cancel" class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4">
                                    同意取消
                                </button>
                            </div>
                            <div id="two" style="visibility:hidden" class="mui-content-padded">
                                <button id="confirm" class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4">
                                    确认送达
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="<c:url value="/js/bringerSubs/bringOrder_info.js?v=1.0.5"/> "></script>
<script>

    var firm = ${requestScope.firmDetail};
    var sendUser = ${requestScope.sendUser};
    var cartsList = ${requestScope.cartsList};
    $(document).ready(function () {
        //  此处修改对应页面的标题
        $('.mui-title').text('接单详情');
        mui('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
        init();
    });
</script>
</html>