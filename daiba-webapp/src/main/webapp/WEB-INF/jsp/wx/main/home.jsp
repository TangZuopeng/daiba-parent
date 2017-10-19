<%@ page import="com.daiba.user.model.User" %>
<%@ page import="com.daiba.user.model.Address" %>
<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/12
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../include/common.jsp" %>
<!DOCTYPE html>
<html>
<%
    User user = (User) session.getAttribute("user");
    Address address = (Address) session.getAttribute("address");
    int use_id = 0;
    String role = null;
    int bir_id = 0;
    String campus = null;
    if(user != null){
        use_id = user.getUserId();
        role = user.getRole();
        bir_id = user.getBriId();
    }
    if(address != null){
        campus = address.getCampus();
    }
    String error = null;
    if (request.getParameter("accpetFirmsError") != null){
        error = request.getParameter("accpetFirmsError");
    }
%>
<head>
    <meta charset="utf-8">
    <title>带吧网络</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!--标准mui.css-->
    <link href="<c:url value="/css/mui.picker.css"/>" rel="stylesheet" />
    <link href="<c:url value="/css/mui.poppicker.css"/>" rel="stylesheet" />
    <style>
        .mui-content,
        .mui-slider,
        .mui-slider-group{
            background-color: #FFF0E5;
            height: 100%;
        }

        .mui-control-content .mui-loading {
            margin-top: 50px;
        }

        /*订单状态字和下划线样式*/
        .mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
            color: #ff8400;
        }
        .mui-segmented-control.mui-segmented-control-inverted~.mui-slider-progress-bar {
            background-color: #ff8400
        }

        .mui-table-view{
            background-color: #FFF0E5;
            margin-bottom: 10px;
        }
        .mui-table-view .mui-media-object {
            line-height: 42px;
            max-width: 42px;
            height: 42px
        }
        .mui-content .title{
            margin: 0px;
            display: -webkit-flex;
            display: flex;
            -webkit-align-items: center;
            align-items: center;
            -webkit-justify-content: center;
            justify-content: center;
            font-size: 15px;
        }
        .mui-table-view-cell .money-style{

        }
        .mui-media-body .font-name{
            color: #0062CC;
            font-size: 12px
        }
        .title a,
        .mui-media-body .font-time,
        .mui-media-body .font-address{
            font-size: 11px;
            color: #ff8400;
        }
        .mui-table-view-cell a{
            width: 80%;
            float: left;
        }
        .mui-table-view-cell a~div,
        .mui-table-view-cell a~input,
        .mui-table-view-cell a~button{
            width: 20%;
            float: right;
        }
        .mui-table-view .mui-table-view-cell{
            background-color: white;
            padding-right: 10px;
        }
        .mui-scroll-wrapper{
            margin-top: 0px;
        }
        .mui-table-view .mui-table-view-cell{
            /*padding: 10px 15px;*/

            padding-bottom: 3px;
            -moz-border-radius: 50px;
            -webkit-border-radius: 50px;
            border-radius: 0px;

            margin-top: 3px;
            /*-webkit-box-shadow: 0 1px 6px #ccc;*/
            /*box-shadow: 0 1px 6px #ccc*/
        }
        p{
            font-size: 10px;
            line-height: 13px;
        }
        .mui-img{
            width: 80px;
            position: absolute;
            right: 20%;
            margin-right: -20px;
            opacity: 0.5;
            filter:alpha(opacity=70); /* IE8 及其更早版本 */
        }
        .orderType-img{
            position: absolute;
            top: -4px;
            left: -6px;
            margin: 0px;
            padding: 0px;
            z-index: 1;
        }
        .give-img{
            width: 20px;
            margin-left: 1px;
            padding: 0px;
            float: left;
        }
        .mui-table-view-cell>a:not(.mui-btn){
            padding: 0px 0px;
            margin: 0px 0px;
        }

        .portrait{
            border-radius:100px
        }

        /*下拉刷新顶部样式*/
        .mui-pull-top-tips {
            position: absolute;
            top: -20px;
            left: 50%;
            margin-left: -25px;
            width: 40px;
            height: 40px;
            border-radius: 100%;
            z-index: 1;
        }

        /*下拉刷新样式延时*/
        .mui-pull-top-tips.mui-transitioning {
            -webkit-transition-duration: 200ms;
            transition-duration: 200ms;
        }

        /*顶部刷新提示样式*/
        .mui-pull-top-canvas {
            overflow: hidden;
            background-color: #FFF0E5;
            border-radius: 40px;
            box-shadow: 0 4px 10px #bbb;
            width: 40px;
            height: 40px;
            margin: 0 auto;
        }

        .mui-pull-top-canvas canvas {
            width: 40px;
        }
        /*性别字符样式*/
        .sex-boy{
            font-size:14px;
            color: deepskyblue
        }
        .sex-girl{
            font-size:14px;
            color: hotpink
        }
        /*点击加载更多按钮样式*/
        .js-load-more{
            padding:0 15px;
            width:140px;
            height:30px;
            background-color:#ff8400;
            color:#fff;
            line-height:30px;
            text-align:center;
            border-radius:5px;
            margin:55px auto 75px auto;
            border:0 none;
            font-size:15px;
            display:none;/*默认不显示，ajax调用成功后才决定显示与否*/
        }
        /*无更多数据样式*/
        .js-load-nomore{
            padding:0 15px;
            width:160px;
            height:30px;
            background-color:#fff0e5;
            color:#ABABAB;
            line-height:30px;
            text-align:center;
            margin:55px auto 75px auto;
            border:0 none;
            font-size:15px;
        }

        .font-add{
            color: #00AAEE;
        }
        .font-color{
            color: #ec971f;
        }
    </style>
</head>
<body>
<%@include file="../../include/head.jsp" %>
<%@include file="../../include/foot.jsp" %>
<%--<div id="topPopover" class="mui-popover">--%>
    <%--<div class="mui-popover-arrow"></div>--%>
    <%--<div>--%>
        <%--<ul id="topPopoverList" class="mui-table-view">--%>
            <%--<li id="giveExpress" class="mui-table-view-cell">--%>
                <%--<img class="give-img" src="<c:url value="/img/giveexpress.png"/>"/>--%>
                <%--<a style="margin-left: 8px;width: 25%" href="#">快递</a>--%>
            <%--</li>--%>
            <%--<li id="giveTakeout" class="mui-table-view-cell">--%>
                <%--<img class="give-img" src="<c:url value="/img/givetakeout.png"/>"/>--%>
                <%--<a style="margin-left: 8px;width: 25%" href="#">带饭</a>--%>
            <%--</li>--%>
            <%--<li id="giveOther" class="mui-table-view-cell">--%>
                <%--<img class="give-img" src="<c:url value="/img/giveother.png"/>"/>--%>
                <%--<a style="margin-left: 8px;width: 25%" href="#">其他</a>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>
<%--</div>--%>
<div class="mui-content">
    <div id="slider" class="mui-slider mui-fullscreen">
        <div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted mui-segmented-control-bar">
            <a class="mui-control-item" href="#allOrder">
                全部
            </a>
            <a class="mui-control-item" href="#expressOrder">
                快递
            </a>
            <a class="mui-control-item" href="#takeoutOrder">
                食堂
            </a>
            <a class="mui-control-item" href="#otherOrder">
                其他
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-3"></div>
        <div class="mui-slider-group">
            <div id="allOrder" class="mui-slider-item mui-control-content mui-active">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="expressOrder" class="mui-slider-item mui-control-content">
                <div id="scroll2" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="takeoutOrder" class="mui-slider-item mui-control-content">
                <div id="scroll3" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="otherOrder" class="mui-slider-item mui-control-content">
                <div id="scroll4" class="mui-scroll-wrapper">
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
</body>
<script src="<c:url value="/js/plugins/mui.picker.js"/>"></script>
<script src="<c:url value="/js/plugins/mui.poppicker.js"/>"></script>
<script src="<c:url value="/js/plugins/mui.pullToRefresh.js"/>"></script>
<script src="<c:url value="/js/plugins/mui.pullToRefresh.material.js"/>"></script>
<script src="<c:url value="/js/main/home.js?v=1.1.2"/>"></script>
<script type="text/javascript">

    var use_id = "<%=use_id%>";
    var role = "<%=role%>";
    var bir_id = "<%=bir_id%>";
    var error = "<%=error%>";
    var reallyCampus = "<%=campus%>";

    $(function(){
        //阻尼系数
        var deceleration = mui.os.ios?0.003:0.0009;
        mui('.mui-scroll-wrapper').scroll({
            indicators: false //是否显示滚动条
        });
        $('#headRight').html('发单');
        //判断是否是接单被拒后返回的页面
        if(error != 'null'){
            mui.toast("少侠、此单已经被人抢先一步接了！");
        }
        updateTabTarget($('#homeTab'),"mui-icon iconfont icon-ordinaryhome");
//        $('#headRight')[0].style.display = '';
        $('#headLeft')[0].style.display = 'none';
    });

</script>

</html>