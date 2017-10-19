<%--
  Created by IntelliJ IDEA.
  User: StphenTmac
  Date: 2017/4/5
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>带吧网络</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!--标准mui.css-->
    <link href="<c:url value="/css/mui.picker.css"/>" rel="stylesheet" />
    <link href="<c:url value="/css/mui.poppicker.css"/>" rel="stylesheet" />
</head>
<%@include file="../../include/common.jsp" %>
<style>
    html,
    body {
        height: 100%;
        overflow: hidden;
        background-color: #FFF0E5;
    }

    .mui-content,
    .mui-slider,
    .mui-slider-group {
        background-color: #FFF0E5;
        height: 100%;
        /*padding-bottom: 30px;*/
        padding-bottom: 17px;
    }

    .mui-control-content .mui-loading {
        margin-top: 50px;
    }

    .mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
        color: #ff8400;
    }

    .mui-segmented-control.mui-segmented-control-inverted~.mui-slider-progress-bar {
        background-color: #ff8400
    }

    .mui-table-view {
        background-color: #FFF0E5;
    }

    .mui-fullscreen .mui-col-xs-3,
    .mui-fullscreen .mui-col-xs-9 {
        overflow-y: auto;
        height: 100%;
    }

    .mui-fullscreen .mui-segmented-control .mui-control-item {
        line-height: 50px;
        width: 100%;
    }

    .mui-fullscreen .mui-control-content {
        display: block;
    }

    .mui-fullscreen .mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
        background-color: #fff;
    }

    .mui-table-view .mui-table-view-cell {
        background-color: white;
        padding-right: 10px;
        border-bottom: 0px solid #ff8400;
    }

    .mui-table-view .mui-table-view-cell {
        /*padding: 10px 15px;*/
        -moz-border-radius: 0px;
        -webkit-border-radius: 0px;
        border-radius: 0px;
        /*-webkit-box-shadow: 0 1px 6px #ccc;*/
        /*box-shadow: 0 1px 6px #ccc*/
    }

    .fooditem {
        padding: 10px 10px;
    }

    .food-pic-wrap {
        position: relative;
        float: left;
        width: 60px;
        height: 62px;
        text-align: center;
        line-height: 62px;
        overflow: hidden;
    }

    .food-pic {
        width: 80px;
        height: 60px;
        margin-left: -10px;
        margin-top: 0px;
        visibility: visible;
        vertical-align: middle;
    }

    .food-cont-wrap {
        margin-left: 72px;
        color: #2F2F2F;
    }

    .food-cont {
        position: relative;
    }

    .food-name {
        max-height: 2.5em;
        line-height: 1.25em;
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        margin-right: 10px;
        font-size: 15px;
    }

    .food-content-sub {
        line-height: 15px;
        margin: 3px 10px 0 0;
        font-size: 12px;
        color: #a9a9a9;
    }

    .food-price-region {
        font-size: 13px;
        margin: 0px 10px 0 0;
        color: #FF5053;
        width: 50px;
        float: left;
    }

    .foodop {
        position: absolute;
        bottom: -6px;
        right: 0;
    }

    .clearfix {
        visibility: visible;
        display: block;
        font-size: 0;
        content: ' ';
        clear: both;
        height: 0;
    }

    .add-food {
        text-align: left;
        width: 35px;
    }

    .add-food,
    .remove-food {
        height: 35px;
        float: right;
        color: #FF8400;
    }

    .i-add-food {
        font-size: 20px;
        display: inline-block;
        font-style: normal;
    }

    .foodop-num {
        font-size: 16px;
        height: 20px;
        line-height: 20px;
        min-width: 20px;
        text-align: center;
        float: right;
    }

    .remove-food {
        width: 35px;
        text-align: right;
    }

    .i-remove-food {
        font-size: 20px;
        display: inline-block;
        font-style: normal;
    }

    .cart {
        position: fixed;
        bottom: 0;
        z-index: 10;
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
        right: 0;
        color: #fff;
    }

    .cart-price {
        font-size: 20px;
        margin-left: 4px;
        color: #fb4e44;
        line-height: 35px;
    }

    .cart-shipping {
        display: block;
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
        background-color: #ccc;
        color: #fff;
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
    .cart-dtl {
        position: fixed;
        left: 0;
        bottom: 51px;
        z-index: 10;
        width: 100%;
    }

    .cart-dtl-icon {
        position: relative;
        width: 50px;
        margin: 0 0 10px 10px;
    }

    /*.cart-dtl-head::before {
        content: '';
        position: absolute;
        top: -5px;
        left: 23px;
        width: 7px;
        height: 7px;
        border: 1px solid #ebebeb;
        border-width: 1px 0 0 1px;
        -webkit-transform: rotate(45deg);
        -moz-transform: rotate(45deg);
        transform: rotate(45deg);
        background: #fff;
    }*/

    .cart-dtl-head {
        position: relative;
        height: 30px;
        border-left: 4px solid #fec520;
        border-top: 0px solid #ebebeb;
        background: #fff;
    }

    .cart-dusbin {
        float: right;
        height: 30px;
        line-height: 30px;
        padding-left: 18px;
        margin-right: 15px;
        background: url("data:image/jpg;base64,/9j/4AAQSkZJRgABAgAAZABkAAD/7AARRHVja3kAAQAEAAAAPAAA/+4ADkFkb2JlAGTAAAAAAf/bAIQABgQEBAUEBgUFBgkGBQYJCwgGBggLDAoKCwoKDBAMDAwMDAwQDA4PEA8ODBMTFBQTExwbGxscHx8fHx8fHx8fHwEHBwcNDA0YEBAYGhURFRofHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8f/8AAEQgAGgAaAwERAAIRAQMRAf/EAG0AAAMBAQAAAAAAAAAAAAAAAAQGBwUIAQEAAAAAAAAAAAAAAAAAAAAAEAABAgQCBwYFBQAAAAAAAAACAQMREgQFAAYyYhMzFDQHITFRgRUWQWEiUlVyUzU2GBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8Aqdt6kZtulGNczccr25p4jko7hUPBUtiJqKI6KFCKyx+aYBdu1ru91uL9yrM0ZaK4OEhU1SFzqAKlQe4acRVBFP1IWAaWc5ZzbaBssw5OcUBQVcOremJUSExSkKRX5JgFz/QN+/FU248XN99+lu9Xv1sA3u5dy+91bOmdtdI5TuWNak2TYaICfWslV1RUYKcvZN34DKy5V2W5+z9vlqzB7h9S4ySibSTgptnsoxhGX6po+WACdYs12ynky8FZbdR1Nxv1OxVhS0zbYG0jzzatqioSqJI2kyKuAq3oFh/G0u64fct7n9rR0NXuwC04YN9Y2lcJA21gVtmZYTmlZMoj4rKkcAJlzp/eLZ7P279Oft71LjJCNZ+Nm2eyiCRhN9U0PPAZVbYqnL+Wsi2Crdadr6e/07hIyqqhBtnnFIZkEoCjiR7MBVMBKOv/APE2/lNM97zfw3Op9/lgIVgKF0R/uTXKaB8xvtFeW1/HVjgOisB//9k=") no-repeat 0 7px;
        -webkit-background-size: 13px 13px;
        -moz-background-size: 13px 13px;
        background-size: 13px 13px;
        font-size: 12px;
        color: #444;
    }

    .cart-dtl-list {
        max-height: 225px;
        overflow: hidden;
        padding: 0 10px;
        background: #f5f5f5;
        width: 100%;
    }

    .cart-dtl-item {
        max-height: 65px;
        border-bottom: 1px solid #ddd;
        overflow: hidden;
        padding: 0 0px 10px 0;
    }

    .cart-dtl-item-inner {
        overflow: hidden;
        height: 35px;
    }

    .cart-dtl-dot {
        float: left;
        width: 4px;
        height: 4px;
        margin: 19px 5px 0 0;
        -webkit-border-radius: 2px;
        -moz-border-radius: 2px;
        border-radius: 2px;
        background: #ff8400;
    }

    .cart-goods-name {
        float: left;
        max-width: 170px;
        margin-top: 10px;
        height: 22px;
        line-height: 22px;
        overflow: hidden;
        font-size: 15px;
        color: #444;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .cart-dtl-oprt {
        float: right;
        margin: 8px -5px 0 0;
    }

    .cart-dtl-price {
        float: right;
        max-width: 60px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        margin-right: 12px;
        line-height: 42px;
        color: #ff6900;
    }

    .cart-dtl-price {
        float: right;
        max-width: 60px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        margin-right: 12px;
        line-height: 42px;
        color: #ff6900;
    }

    /*找带客按钮是否可用标记*/
    .submit-dishes{
        background-color: #ff8400;
    }
</style>
<body>
<%@include file="../../include/head.jsp" %>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <%--<div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted mui-segmented-control-bar">--%>
            <%--<a class="mui-control-item" href="#school">--%>
                <%--快捷--%>
            <%--</a>--%>
            <%--<a class="mui-control-item" href="#outsideSchool">--%>
                <%--普通--%>
            <%--</a>--%>
        <%--</div>--%>
        <%--<div id="sliderProgressBar" class="mui-slider-progress-bar"></div>--%>
        <div class="mui-slider-group">
            <%--<div id="school" class="mui-slider-item mui-control-content ">--%>
                <%--<div class="mui-loading">--%>
                    <%--<div class="mui-spinner">--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div id="outsideSchool" class="mui-slider-item mui-control-content mui-active">
                <div class="mui-loading">
                    <div class="mui-spinner">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="cartContents" class="cart-dtl" style="display: none;">
        <%--<div class="cart-dtl-icon">
            <i class="mui-icon iconfont icon-msnui-cart icon-gouwuche ico-cart-active"></i>
            <div class="cart-num" style="right: 10px;">0</div>
        </div>--%>
        <div class="cart-dtl-head">
            <span id="clearCart" class="cart-dusbin">
                <i></i>
                清空购物车
            </span>
        </div>
        <div class="cart-dtl-list">
            <div id="cartList" class="j-cart-dtl-list-inner" style="transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms;">

            </div>
        </div>
    </div>
    <div id="cart" class="cart">
        <div class="cart-tip">
            <div id="cartIcon" class="cart-icon">
                <i class="icon-cart"></i>
                <div hidden class="cart-num">0</div>
            </div>
            <div>
                <span id="prices" class="cart-price">￥0</span><br/>
                <span id="shippingFee" class="cart-shipping">另需配送费 ￥0</span>
            </div>
        </div>
        <div id="submitOrder" class="cart-btns">
            <a class="cart-btn-unavail">
                <span class="inner">
                    找带客
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
</body>
<script src="<c:url value="/js/plugins/mui.picker.js"/>"></script>
<script src="<c:url value="/js/plugins/mui.poppicker.js"/>"></script>
<script src="<c:url value="/js/main/dishes.js?v=1.0.2"/>"></script>
<script type="text/javascript">
    $(function(){
        mui.init({
            swipeBack: false
        });
        mui('.cart-dtl-list').scroll({
            indicators: false //是否显示滚动条
        });
    })
</script>
</html>
