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

    .mui-icon.mui-icon-arrowright{
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

    .mui-input-row span:nth-of-type(1),
    .accept {
        font-size: 12px;
    }

    .mui-slider-group .mui-table-view-cell {
        background-color: white;
        margin-top: 5px;
        border-bottom: 1px solid #ff8400;
    }

    .mui-table-view-radio .mui-table-view-cell {
        -moz-border-radius: 0;
        -webkit-border-radius: 0;
        border-radius: 0;
    }
    .input-style{
        margin-left: 1px;
        padding-left: 0;
    }
    .mui-popover {
        border-radius: 0px;
        width: 70%;
        height: 35%;
        background-color: white;
    }
    #popover .mui-scroll{
        width: 100%;
        height: 100%;
        margin: 0 auto;
    }
    #popoverList .mui-table-view-cell{
        text-align: center;
        vertical-align:middle;
        margin-top: 1px;
        background-color: white;
        color: #ff8400;
        width: 100%;
        height: 25%;
    }
    #popoverList{
        overflow-y:hidden;
        border-radius: 0px;
        margin: 0 auto;
        width: 100%;
        height: 100%;
        background-color: white;
    }
    .mui-popover .mui-table-view .mui-table-view-cell:first-child, .mui-popover .mui-table-view .mui-table-view-cell:first-child > a:not(.mui-btn) {
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
    }
    .mui-popover .mui-table-view .mui-table-view-cell:last-child, .mui-popover .mui-table-view .mui-table-view-cell:last-child > a:not(.mui-btn) {
        border-bottom-right-radius: 0px;
        border-bottom-left-radius: 0px;
    }

    .mui-numbox [class*=btn-numbox],
    .mui-numbox [class*=numbox-btn] {
        font-size: 25px;
        margin: 3px 0px;
        background-color: #fff;
    }

    .mui-numbox .mui-input-numbox,
    .mui-numbox .mui-numbox-input {
        border-right: solid 0px #ccc!important;
        border-left: solid 0px #ccc!important;
        border-bottom: solid 1px #ccc!important;
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
                    <ul class="mui-table-view">
                        <li style="padding-right: 0px" class="mui-table-view-cell" id="addressItem">
                            <div>
                                <div>
                                    <span style="font-size: 13px;" class="accept">收货人：</span>
                                    <span class="acceptName" id="shrName"></span>
                                    <span class="acceptSex"></span>
                                    <span class="acceptPhoneNum" id="shrTel"></span>
                                </div>
                                <span class="acceptAdd" id="shrAddress"></span>
                                <span id="shrRoom" style="display: none"></span>
                            </div>
                            <div>
                                <%--<a class="mui-icon mui-icon-arrowright"></a>--%>
                                <span style="margin-top: 12px" class="mui-icon mui-icon-arrowright"></span>
                            </div>
                        </li>
                    </ul>
                    <div class="mui-input-row" id='showPlacePicker'>
                        <span>取件地址：</span>
                        <div class="mui-input-row-div">
                            <span style="color: #000000;padding-left: 0" id="palceResult"></span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>

                    <div class="mui-input-row">
                        <span>取件号：</span>
                        <input type="text" style="padding-left: 0" class="mui-input-clear mui-input input-style" placeholder="请输入取件时所需凭证"
                               id="shrQjh">
                    </div>

                    <div class="mui-input-row">
                        <span>送达时间：</span>
                        <div class="mui-input-row-div" id="timeItem" data-options='{"type":"time"}'>
                            <span style="color: #000000;padding-left: 0" id="arriveTime">今日之内</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div class="mui-input-row" id="weightItem">
                        <span>物件大小：</span>
                        <div class="mui-input-row-div input-style" >
                            <span style="color: #000000;padding-left: 0" id="weightText">请选择</span>
                            <span class="mui-icon mui-icon-arrowright"></span>
                        </div>
                    </div>
                    <div class="mui-input-row">
                        <span>接单佣金：</span>
                        <div class="mui-input-row-div">
                            <div style="float: right;margin-top: 8px;margin-right: 15px;">元</div>
                            <div class="mui-numbox" data-numbox-min='1.5' data-numbox-max='8.5' style="float:right;width: 130px; background-color: white; border: 0" id="moneyItem">
                                <a id="minuxMoneyBtn" class="mui-btn mui-btn-numbox-minus ">
                                    <i style="color: #ACACB4;" class="mui-icon iconfont icon-jian"></i>
                                </a>
                                <input id="money" class="mui-input-numbox" type="number" />
                                <a class="mui-btn mui-btn-numbox-plus">
                                    <i style="margin-top:2px;" class="mui-icon iconfont icon-jia"></i>
                                </a>

                            </div>
                        </div>
                    </div>
                    <div class="mui-input-row" style="height: 60px">
                        <span style="vertical-align: top;width:35% ;">发单留言：</span>
                        <textarea style="padding:0 5px;" class="mui-input-clear mui-input" placeholder="详细地址等补充说明。" id="remark"></textarea>
                    </div>
                </form>
                <div class="mui-content-padded" id="submitOrderDiv">
                    <button class="mui-btn mui-btn-block mui-btn-primary" id="submitOrderBtn">发单</button>
                </div>
            </div>
        </div>
    </div>
    <!--显示物件大小-->
    <div id="popover" class="mui-popover">
        <ul id="popoverList" class="mui-table-view mui-table-view-radio">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">小件(一人可拿多件)</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right " style="text-align: center;">中件(一人可拿3-4件)</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">大件(一人可拿2件)</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right " style="text-align: center;">超大件(一人可拿1件)</a>
            </li>
        </ul>
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<c:url value="/js/main/submit_express_order.js?v=1.0.1"/>"></script>
<script type="text/javascript">
    $(function(){
        $('.mui-title').text('快递发单');
        mui('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    })

</script>

</html>
