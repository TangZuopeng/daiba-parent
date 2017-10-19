<%--
  Created by IntelliJ IDEA.
  User: tangzuopeng
  Date: 2016/9/17
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>-
<%
    String orderType = null;
    if (request.getParameter("orderType") != null){
        orderType = request.getParameter("orderType");
    }
%>
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
        padding-bottom: 80px;
    }

    .mui-input-row span {
        text-align: left;
        margin-left: 8px;
        color: #ff8400;
        width: 25%;
        font-size: 13px;
    }

    .mui-input-row span~div,
    .mui-input-row span~input {
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
        margin-top: 5px;
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

    .acceptPhoneNum {}

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

    .mui-table-view-cell span {
        font-size: 12px;
    }

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

    .cart {
        position: fixed;
        bottom: 0;
        z-index: 9000;
        width: 100%;
        height: 50px;
        background-color: #FFF0E5;
        border-top: 1px solid #FF8400;
    }

    .cart-tip {
        color: #444;
        padding-left: 12px;
    }

    .cart-icon {
        float: left;
        position: relative;
        top: -20px;
        margin-right: 10px;
    }

    .icon-cart.icon-cart-active {
        background-position: 0 -57px;
    }

    .icon-cart {
        /*font-style: normal;
        display: block;
        font-size: 40px;
        color: #FF8400;*/
        display: block;
        width: 50px;
        height: 57px;
        background: url("//xs01.meituan.net/waimai_i/img/cart.4ba0ae5e.png") no-repeat;
        -webkit-background-size: 50px auto;
        -moz-background-size: 50px auto;
        background-size: 50px auto;
    }

    .cart-num {
        border-radius: 50% 50%;
        background-color: #FF0000;
        width: 14px;
        height: 14px;
        line-height: 16px;
        font-size: 12px;
        text-align: center;
        position: absolute;
        top: 3px;
        right: 0px;
        color: #fff;
    }

    .cart-price {
        font-size: 20px;
        margin-left: 4px;
        color: #fb4e44;
        line-height: 35px;
    }

    .cart-shipping {
        font-size: 12px;
        margin-left: 4px;
        margin-top: -7px;
    }

    .cart-btns {
        position: absolute;
        top: 0;
        right: 0;
        height: 100%;
    }

    .cart-btn-confirm,
    .cart-btn-unavail {
        display: block;
        height: 100%;
    }

    .cart-btn-unavail .inner {
        background-color: #FF8400;
        color: #FFF0E5;
    }

    .cart-btn-confirm .inner,
    .cart-btn-unavail .inner {
        display: block;
        width: 110px;
        height: 50px;
        line-height: 50px;
        font-size: 20px;
        text-align: center;
    }
</style>
<body>

<%@include file="../../include/head.jsp" %>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form id="menu" class="mui-input-group">
                    <div id="shippingFeeDiv" class="mui-table-view">
                        <div class="mui-table-view-cell">
                            <span class="goods-name">接单佣金</span>
                            <span class="goods-price">￥0.00</span>
                        </div>
                    </div>

                    <ul id="conmsg" style="margin-top: 20px;" class="mui-table-view">
                        <li style="padding-right: 0px" class="mui-table-view-cell" id="addressItem">
                            <div>
                                <div>
                                    <span style="font-size: 13px;" id="people" class="accept">收餐人：</span>
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
                    <div id="takeOutTaskTime" class="mui-input-row">
                        <span>要求时间：</span>
                        <div class="mui-input-row-div" id="takeOutTimeItem" data-options='{"type":"time"}'>
                            <span style="color: #000000;vertical-align: middle" id="takeOutArriveTime"></span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <!--<div id="isComePay" style="display: none" class="mui-input-row">
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
                            <div id="goodsMoneyBox" class="mui-numbox" style="float:right;width: 130px; background-color: white; border: 0;">
                                <a class="mui-btn mui-btn-numbox-minus ">
                                    <i style="color: #ACACB4;" class="mui-icon iconfont icon-jian"></i>
                                </a>
                                <input id="goodsMoney" class="mui-input-numbox" type="number" value="5" />
                                <a class="mui-btn mui-btn-numbox-plus">
                                    <i style="margin-top:2px;" class="mui-icon iconfont icon-jia"></i>
                                </a>
                            </div>
                        </div>
                    </div>-->
                    <div class="mui-input-row">
                        <span>接单佣金：</span>
                        <div class="mui-input-row-div">
                            <div style="float: right;margin-top: 8px;margin-right: 15px;">元</div>
                            <div id="orderMoney" class="mui-numbox" data-numbox-min='1.5' style="float:right;width: 130px; background-color: white; border: 0;" id="moneyItem">
                                <a id="feeMinus" class="mui-btn mui-btn-numbox-minus ">
                                    <i style="color: #ACACB4;" class="mui-icon iconfont icon-jian"></i>
                                </a>
                                <input readonly id="money" class="mui-input-numbox" type="number" value="1.5" />
                                <a id="feePlus" class="mui-btn mui-btn-numbox-plus">
                                    <i style="margin-top:2px;" class="mui-icon iconfont icon-jia"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="mui-input-row" style="height: 60px">
                        <span style="vertical-align: top;width:35% ;">发单留言：</span>
                        <textarea style="padding:0 5px;" class="mui-input-clear mui-input" placeholder="对菜品口味的要求。(可不填)" id="remark"></textarea>
                    </div>
                </form>
                <!--<div class="m ui-content-padded" id="submitOrderDiv">
                    <button class="mui-btn mui-btn-block mui-btn-primary" id="submitOrderBtn">发单</button>
                </div>-->
            </div>
            <div class="cart">
                <div class="cart-tip">
                    <!--<div class="cart-icon">
                        <i class="icon-cart icon-cart-active"></i>
                        <div class="cart-num">1</div>
                    </div>-->
                    <div>
                        <span id="noFav" hidden class="cart-price">￥：<span id="fee">0</span></span>
                        <span id="fav" hidden class="cart-price">￥：<s id="oldFee">0</s>&nbsp; <span id="newFee">0</span></span><br/>
                        <span id="favMsg" hidden class="cart-shipping">满7减1.5（配送费）</span>
                    </div>
                </div>
                <div id="submitOrderBtn" disabled="true" class="cart-btns">
                    <a class="cart-btn-unavail">
                        <span class="inner">
                            <strong>发布带单</strong>
                        </span>
                    </a>
                    <!--<a class="cart-btn-unavail">
                        <span class="inner">
                            ￥
                            8
                            起送
                        </span>
                    </a>-->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="<c:url value="/js/main/submit_takeout_order.js?v=1.0.5"/>"></script>
<script type="text/javascript">

    var orderType = <%=orderType%>;

    $(function(){
        $('.mui-title').text('食堂发单');
        mui('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    })
</script>

</html>
