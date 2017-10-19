<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/20
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../include/common.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>带吧网络</title>
    <style>
        .mui-table-view{
            margin-top: 5px;
            background-color: #FFF0E5;
        }
        .mui-content,
        .mui-slider{
            background-color: #FFF0E5;
            height: 100%;
        }
        .mui-table-view .mui-table-view-cell{
            left:5%;
            width: 90%;
            background-color: white;
            margin-top: 5px;
        }
        .mui-table-view-chevron .mui-table-view-cell span{
            margin: 0 2px;
        }
        .mui-table-view-chevron .mui-table-view-cell span:nth-child(3),
        .mui-table-view-chevron .mui-table-view-cell span:nth-child(4){
            float: right;
        }
        .subTitle{
            margin: 4px;
            text-align: center;
            font-size: 15px;
            color: black;
        }
        .mui-content-padded{
            height: 30px;
            margin-top: 10px;
        }
        .mui-ellipsis{
            font-size: 12px;
        }
        .accept{
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
        .span-color{
            color: #000000;
        }
        .mui-btn{
            font-size: 15px;
            height:100%;
            padding: 0;
        }
        /*.mui-table-view-cell {*/
            /*padding: 11px 15px;*/
        /*}*/
        .mui-slider .mui-slider-group .mui-slider-item{
            font-size: 13px;
        }

        .mui-table-view-cell span {
            font-size: 12px;
        }

        /*.goods-name,*/
        /*.goods-num,*/
        /*.goods-price {*/
            /*width: 35%;*/
        /*}*/

        .goods-name {
            width: 45%;
            float: left;
            color: #000000;
        }

        .goods-num {
            width: 10%;
            text-align: right;
            color: #000000;
        }

        .goods-price {
            width: 10%;
            text-align: right;
            margin-right: 30px;
            float: right;
            color: #FF0000;
        }
    </style>
    <%
        String firmId = null;
        String acceptAddCode = null;
        if (request.getParameter("firmId") != null && request.getParameter("acceptAddCode") != null){
            firmId = request.getParameter("firmId");
            acceptAddCode = request.getParameter("acceptAddCode");
        }
    %>
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
                        <div id="bringerDiv" hidden >
                            <ul class="mui-table-view mui-table-view-chevron">
                                <li class="mui-table-view-cell">
                                    <span style="float: left">接单人：</span>
                                    <span style="float: left;font-size: 12px;" id="bringerName" class="span-color"></span>
                                    <span style="float: left;font-size: 12px;" class="span-color"><a id="bringerPhone"></a></span>
                                </li>
                            </ul>
                        </div>

                        <ul id="orderList" style="margin-top: 15px" class="mui-table-view mui-table-view-chevron">
                            <li style="padding: 5px 15px 5px 15px;"  class="mui-table-view-cell" id="addressItem">
                                <div>
                                    <div>
                                        <span class="accept">收货人：</span>
                                        <span style="font-size: 12px;" class="acceptName" id="shrName"></span>
                                        <span style="margin: 0 1px;float: left;font-size: 12px;" id="shrTel"></span>
                                    </div><br>
                                    <div>
                                        <span style="font-size: 12px;" class="acceptAdd" id="shrAddress"></span>
                                    </div>
                                </div>
                            </li>
                            <li id="taskTypeItem" hidden class="mui-table-view-cell">
                                <span>任务类型：</span>
                                <span style="font-size: 12px;" id="taskType" class="span-color"></span>
                            </li>
                            <li id="taskForItem" hidden class="mui-table-view-cell">
                                <span id="taskForSpan">任务简介：</span>
                                <span style="font-size: 12px;" id="taskFor" class="span-color"></span>
                            </li>
                            <li id="taskPlaceItem" class="mui-table-view-cell">
                                <span id="taskPlaceSpan">任务地点：</span>
                                <span style="font-size: 12px;" id="taskPlace" class="span-color"></span>
                            </li>
                            <li id="takeNumItem" hidden class="mui-table-view-cell">
                                <span>取件号：</span>
                                <span style="font-size: 12px;" id="takeNum" class="span-color"></span>
                            </li>
                            <li id="askTimeItem" class="mui-table-view-cell">
                                <span id="askTimeSpan">送达时间：</span>
                                <span style="font-size: 12px;" id="askTime" class="span-color"></span>
                            </li>
                            <li class="mui-table-view-cell">
                                <span>接单佣金：</span>
                                <span style="font-size: 12px;" id="orderMoney" class="span-color"></span>
                            </li>
                            <li hidden id="goodsMoneyItem" class="mui-table-view-cell">
                                <span>消费金额：</span>
                                <span style="font-size: 12px;" id="goodsMoney" class="span-color"></span>
                            </li>
                            <li id="contentItem" class="mui-table-view-cell">
                                <span id="contentSpan">备&emsp;注：</span>
                                <span style="font-size: 12px;" id="content" class="span-color"></span>
                            </li>
                        </ul>
                        <div style="margin-top: 15%;margin-bottom: 10%" class="mui-slider-group">
                            <%--<div id="two" style="display: none" class="mui-content-padded">--%>
                            <%--<button id="confirm" class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4" >确认收货</button>--%>
                            <%--</div>--%>
                            <div id="one"  style="visibility:hidden" class="mui-content-padded">
                                <button id="cancel" class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4">取消订单</button>
                            </div>
                            <div id="three" disabled="true"  style="visibility:hidden" class="mui-content-padded">
                                <button id="applyCancel" class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4">申请取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<script src="<c:url value="/js/subs/order_info.js?v=1.0.4"/> "></script>
<script>

    var firmId = "<%=firmId%>";
    $(document).ready(function() {
        //  此处修改对应页面的标题
        $('.mui-title').text('订单详情');
        mui('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
        init();
    });
</script>
</html>

