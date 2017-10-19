<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/17
  Time: 13:54
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
        .mui-content,
        .mui-slider,
        .mui-slider-group{
            background-color: #FFF0E5;
            height: 100%;
        }

        .mui-slider-group .mui-table-view-cell {
            background-color: white;
            margin-top: 5px;
            /*border-bottom: 1px solid #ff8400;*/
            border-bottom: 0px solid #ff8400;
            padding: 11px 15px;
        }

        .mui-table-view-cell:nth-child(2),
        .mui-table-view-cell:nth-child(5) {
            margin-top: 20px;
        }

        .mui-slider-group .mui-table-view {
            background-color: #FFF0E5;
        }

        /*.mui-table-view-cell:first-child .mui-navigate-right .mui-pull-left {*/
            /*!*margin-top: 13px;*!*/
        /*}*/

        .mui-btn-primary {
            color: #fff;
            border: 0px solid #FF9B2F;
            background-color: #FF9B2F;
        }

        .mui-slider-group {
            margin: 0 20px;
        }
        .mui-navigate-right:after,
        .mui-push-right:after {
            color: #ff8400;
        }
        .mui-pull-right{
            color: #ff8400;
            margin-right: 50px;
        }
        .copyright{
            padding: 0;
            margin: 0;
            font-family: "microsoft yahei" , simsun , helvetica , arial , sans-serif;
            font-size: 12px;
            text-align: center;
            color: #333;
        }
    </style>
</head>

<body>
<%@include file="../../include/head.jsp" %>
<%@include file="../../include/foot.jsp" %>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div class="mui-slider-group">
            <div class="mui-slider-item mui-control-content mui-active">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <ul class="mui-table-view">
                            <li class="mui-table-view-cell mui-media" id="topItem">
                                <a href="javascript:;" class="mui-navigate-right">
                                    <img class="mui-media-object mui-pull-left" id="pHeadIcon">
                                    <div class="mui-media-body">
                                        <div id="pNickname"></div>
                                        <p class='mui-ellipsis'>
                                           <%--<div id="credit">信誉值:<span id="pCreditValue"  ></span></div>--%>
                                        </p>
                                    </div>
                                </a>
                            </li>

                            <li class="mui-table-view-cell" id="putOrderItem">
                                <a>
                                    <div class="mui-pull-left">发单数：</div>
                                    <div id="pOrderNum" class="mui-pull-right"></div>
                                </a>
                            </li>
                            <li class="mui-table-view-cell" id="getOrderItem">
                                <a>
                                    <div class="mui-pull-left">接单数：</div>
                                    <div class="mui-pull-right" id="pGetOrderNum"></div>
                                </a>
                            </li>
                            <li class="mui-table-view-cell" id="isReceiverItem">
                                <a>
                                    <div class="mui-pull-left">新订单推送：</div>
                                    <div id="mySwitch" class="mui-switch">
                                        <div class="mui-switch-handle"></div>
                                    </div>
                                </a>
                            </li>
                            <li class="mui-table-view-cell" id="weekIncomeLi">
                                <a>
                                    <div class="mui-pull-left">本周收入：</div>
                                    <div class="mui-pull-right" id="weekIncome"></div>
                                </a>
                            </li>

                            <li class="mui-table-view-cell" id="addressManageItem">
                                <a class="mui-navigate-right">
                                    收货地址管理
                                </a>
                            </li>
                            <li class="mui-table-view-cell" id="applyBringerItem">
                                <a class="mui-navigate-right">
                                    申请为带客
                                </a>
                            </li>
                            <li class="mui-table-view-cell" id="touchUsItem">
                                <a class="mui-navigate-right">
                                    联系我们
                                </a>
                            </li>

                            <li class="mui-table-view-cell" id="commonQuestionItem">
                                <a class="mui-navigate-right">
                                    常见问题
                                </a>
                            </li>
                        </ul>
                        <div class="mui-content-padded" id="returnLoginBtn">
                            <button class="mui-btn mui-btn-block mui-btn-primary" data-modal="modal-4">退出登录</button>
                        </div>
                        <div>
                            <article class="copyright">
                                <p>长春工业大学带吧网络提供技术支持</p>
                            </article>
                        </div>
                        <div>
                            <article class="copyright">
                                <p>Copyright © 2016-2017 带吧网络 版权所有</p>
                            </article>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="<c:url value="/js/main/mine.js?v=1.0.6"/> "></script>
<script type="text/javascript" charset="utf-8">
    mui.init({});
    (function ($) {
        jQuery('.mui-title').text('个人中心');
        updateTabTarget(jQuery('#mineTab'),"mui-icon iconfont icon-wo");
        jQuery('#headLeft')[0].style.display = 'none';
        $('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
    })(mui);
</script>
</html>
