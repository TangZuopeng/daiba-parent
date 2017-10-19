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
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
    <style>
        .mui-content,
        .mui-slider,
        .mui-slider-group {
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

        .mui-table-view-radio .mui-table-view-cell {
            -moz-border-radius: 0;
            -webkit-border-radius: 0;
            border-radius: 0;
        }

        .mui-slider-group .mui-table-view-cell:nth-child(2),
        .mui-slider-group .mui-table-view-cell:nth-child(4) {
            margin-top: 20px;
        }

        .mui-slider-group .mui-table-view {
            background-color: #FFF0E5;
        }

        .mui-table-view-cell:first-child .mui-navigate-right .mui-pull-left {
            margin-top: 13px;
        }

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
            color: #FF9B2F;
        }

        .mui-pull-right {
            color: #ff8400;
            margin-right: 50px;
        }
        .mui-table-view-cell{
            padding: 11px 15px;
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
                        <ul class="mui-table-view">
                            <li class="mui-table-view-cell" id="uHeakIconLi">
                                <a>
                                    <div class="mui-pull-left">头像</div>
                                    <img class="mui-media-object mui-pull-right" src="images/cbd.jpg" id="uHeakIconId">
                                </a>
                            </li>

                            <li class="mui-table-view-cell" id="uNickNameLi">
                                <a class="mui-navigate-right">
                                    <div class="mui-pull-left">昵称</div>
                                    <div class="mui-pull-right" id="uNickNameId"></div>
                                </a>
                            </li>

                            <li class="mui-table-view-cell" id="uSexLi">
                                <a class="mui-navigate-right" class="mui-btn mui-btn-primary mui-btn-block">
                                    <div class="mui-pull-left">性别</div>
                                    <div class="mui-pull-right" id="uSexId">男</div>
                                </a>
                            </li>

                            <li class="mui-table-view-cell">
                                <div class="mui-pull-left">手机号</div>
                                <div class="mui-pull-right" id="uPhoneNumId"></div>
                            </li>

                            <li class="mui-table-view-cell">
                                <div class="mui-pull-left">认证</div>
                                <div class="mui-pull-right" id="uBringerId"></div>
                            </li>

                            <li class="mui-table-view-cell" id="uStudentNumLi">
                                <div class="mui-pull-left">学号</div>
                                <div class="mui-pull-right" id="uStudentNumId"></div>
                            </li>

                            <li class="mui-table-view-cell" id="uRealNameLi">
                                <div class="mui-pull-left">真实姓名</div>
                                <div class="mui-pull-right" id="uRealNameId"></div>
                            </li>
                        </ul>
                        <div class="mui-content-padded" id='editPasswordBtn'>
                            <button class="mui-btn mui-btn-block mui-btn-primary">修改密码</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="popover" class="mui-popover">
        <ul class="mui-table-view mui-table-view-radio">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">男</a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right" style="text-align: center;">女</a>
            </li>
        </ul>
    </div>
</div>
</body>
<script src="<c:url value="/js/personal/mine_detail.js"/> "></script>
<script type="text/javascript" charset="utf-8">
    mui.init({});
    (function ($) {
        jQuery('.mui-title').text('详细信息');
        $('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
    })(mui);
</script>


</html>
