<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2017/2/18
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
</head>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/mui.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/mui.picker.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/mui.picker.min.css"/>"/>

<script src="<c:url value="/js/main/submit_order_plugin.js"/> "></script>
<script src="<c:url value="/js/plugins/jquery-1.7.2.min.js"/> "></script>
<script src="<c:url value="/js/plugins/mui.poppicker.js"/> "></script>
<script src="<c:url value="/js/plugins/mui.picker.js"/> "></script>
<script src="<c:url value="/js/plugins/mui.picker.min.js"/> "></script>
<script src="<c:url value="/js/global/common.js"/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/daiba.style.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/iconfont.css?v=1.0.1"/>"/>

<style>
    .mui-content,
    .mui-slider,
    .mui-slider-group {
        background-color: #FFF0E5;
        height: 100%;
    }

    .mui-input-group {
        margin: 8px 20px;
    }

    .mui-input-row span {
        text-align: left;
        margin-left: 8px;
        color: #ff8400;
        width: 25%;
        font-size: 13px;
    }

    .mui-input-row span ~ div,
    .mui-input-row span ~ input {
        width: 75%;
        font-size: 12px;
    }

    .mui-input-row-div span:nth-child(2) {
        width: 10%;
        float: right;
    }

    .mui-table-view-cell .mui-icon-arrowright {
        margin-top: 7px;
        color: #ff8400;
    }

    .mui-icon.mui-icon-arrowright {
        font-size: 20px;
    }

    .mui-table-view {
        margin-top: 20px;
    }

    .mui-table-view .mui-table-view-cell div:nth-child(1) {
        width: 92%;
        float: left;
    }

    .mui-table-view .mui-table-view-cell div:nth-child(2) {
        width: 8%;
        float: right;
    }

    .mui-table-view .mui-table-view-cell div:nth-child(1) div {
        float: none;
    }

    .acceptPhoneNum {
    }

    .acceptName {
        color: #0062CC;
    }

    .acceptSex {
        color: #C0C0C0;
    }

    .acceptAdd {
        color: #C0C0C0;
    }

    .mui-numbox {
        background-color: #FFF0E5;
    }

    .input-style {
        margin-left: 1px;
        padding-left: 0;
    }

    .mui-numbox [class*=btn-numbox],
    .mui-numbox [class*=numbox-btn] {
        font-size: 25px;
        margin: 3px 0px;
        background-color: #fff;
    }

    .mui-numbox .mui-input-numbox,
    .mui-numbox .mui-numbox-input {
        border-right: solid 0px #ccc !important;
        border-left: solid 0px #ccc !important;
        border-bottom: solid 1px #ccc !important;
    }

    .mui-table-view-cell span{
        font-size: 12px;
    }
</style>
<body>
<%@include file="../../include/head.jsp" %>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form class="mui-input-group">
                    <ul id="conmsg" hidden class="mui-table-view">
                        <li style="padding-right: 0px" class="mui-table-view-cell" id="addressItem">
                            <div>
                                <div>
                                    <span style="font-size: 13px;" id="people" class="accept">收货人：</span>
                                    <span class="acceptName" id="shrName"></span>
                                    <span class="acceptSex"></span>
                                    <span class="acceptPhoneNum" id="shrTel"></span>
                                </div>
                                <span class="acceptAdd" id="shrAddress"></span>
                                <span id="shrRoom" style="display: none"></span>
                            </div>
                            <div>
                                <span style="margin-top: 12px" class="mui-icon mui-icon-arrowright"></span>
                            </div>
                        </li>
                    </ul>
                    <div class="mui-input-row">
                        <span>任务类型：</span>
                        <div id="taskTypeItem" class="mui-input-row-div">
                            <span style="color: #000000" id="taskType">请选择</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div class="mui-input-row">
                        <span>任务校区：</span>
                        <div id="campusItem" class="mui-input-row-div">
                            <span style="color: #000000" id="campus">南湖校区</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div id="taskForInput" class="mui-input-row">
                        <span style="vertical-align: top" id="taskForSpan">任务简介：</span>
                        <input style="padding-left: 0" type="text" class="mui-input-clear mui-input input-style"
                               placeholder="请输入任务简介"
                               id="taskFor">
                    </div>
                    <div id="place" class="mui-input-row">
                        <span style="vertical-align: top">任务地点：</span>
                        <input style="padding-left: 0" type="text" class="mui-input-clear mui-input input-style"
                               placeholder="请输入任务地点"
                               id="taskAdd">
                    </div>

                    <div id="taskTime" class="mui-input-row">
                        <span>任务时间：</span>
                        <div class="mui-input-row-div" id="timeItem" data-options='{"type":"time"}'>
                            <span style="color: #000000;vertical-align: middle" id="arriveTime">今日之内</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div id="takeOutTaskTime" style="display: none" class="mui-input-row">
                        <span>任务时间：</span>
                        <div class="mui-input-row-div" id="takeOutTimeItem" data-options='{"type":"time"}'>
                            <span style="color: #000000;vertical-align: middle" id="takeOutArriveTime">尽快送达</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div id="isComePay" style="display: none" class="mui-input-row">
                        <span>是否现付：</span>
                        <div class="mui-input-row-div">
                            <div id="mySwitch" class="mui-switch mui-active">
                                <div class="mui-switch-handle"></div>
                            </div>
                        </div>
                    </div>
                    <div id="goodsMoneyInput" style="display: none" class="mui-input-row">
                        <span id="goodsMoneySpan">消费金额：</span>
                        <div class="mui-input-row-div">
                            <div style="float: right;margin-top: 8px;margin-right: 15px;">元</div>
                            <div id="goodsMoneyBox" class="mui-numbox"
                                 style="float:right;width: 130px; background-color: white; border: 0;">
                                <a class="mui-btn mui-btn-numbox-minus ">
                                    <i style="color: #ACACB4;" class="mui-icon iconfont icon-jian"></i>
                                </a>
                                <input id="goodsMoney" class="mui-input-numbox" type="number" value="5"/>
                                <a class="mui-btn mui-btn-numbox-plus">
                                    <i style="margin-top:2px;" class="mui-icon iconfont icon-jia"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="mui-input-row">
                        <span>接单佣金：</span>
                        <div class="mui-input-row-div">
                            <div style="float: right;margin-top: 8px;margin-right: 15px;">元</div>
                            <div id="orderMoney" class="mui-numbox" data-numbox-min='0.5'
                                 style="float:right;width: 130px; background-color: white; border: 0;" id="moneyItem">
                                <a class="mui-btn mui-btn-numbox-minus ">
                                    <i style="color: #ACACB4;" class="mui-icon iconfont icon-jian"></i>
                                </a>
                                <input id="money" class="mui-input-numbox" type="number" value="5"/>
                                <a class="mui-btn mui-btn-numbox-plus">
                                    <i style="margin-top:2px;" class="mui-icon iconfont icon-jia"></i>
                                </a>

                            </div>
                        </div>
                    </div>
                    <div id="taskContent" class="mui-input-row" style="height: 60px">
                        <span style="vertical-align: top;width:35% ;">任务内容：</span>
                        <textarea style="padding:0 5px;" class="mui-input-clear mui-input input-style"
                                  placeholder="此处内容需带客接单后可见"
                                  id="remark"></textarea>
                    </div>
                </form>
                <div class="mui-content-padded" id="submitOrderDiv">
                    <button class="mui-btn mui-btn-block mui-btn-primary" id="submitOrderBtn">发单</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<c:url value="/js/main/submit_other_order.js?v=1.0.9"/>"></script>
<script type="text/javascript">
    $(function () {
        $('.mui-title').text('其他发单');
        mui('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    })
</script>

</html>
