<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2016/9/17
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="mui-bar mui-bar-tab" style="">
    <a id="homeTab" class="mui-tab-item" href="#">
        <span id="icon-home" class="mui-icon iconfont icon-shouye"></span>
        <span class="mui-tab-label">主页</span>
    </a>
    <a id="orderTab" class="mui-tab-item" href="#">
        <span id="icon-order" class="mui-icon iconfont icon-cshy-orders"></span>
        <span class="mui-tab-label">我发的单</span>
    </a>
    <span class="mui-tab-span" href="#">
        <label for="menu-opener-id" id="icon-submit-order" class="mui-icon iconfont icon-add2"> </label>
    </span>
    <a id="bringerTab" class="mui-tab-item" href="#">
        <span id="icon-bringer" class="mui-icon iconfont icon-cshy-orders"></span>
        <span class="mui-tab-label">我接的单</span>
    </a>
    <a id="mineTab" class="mui-tab-item" href="#">
        <span id="icon-mine" class="mui-icon iconfont icon-account"></span>
        <span class="mui-tab-label">个人</span>
    </a>
</nav>

<div id="mask" class="mask"></div>

<input type="checkbox" id="menu-opener-id" class="menu-opener"/>
<a href="#" id="giveTakeout" class="link-one link-general"></a>
<a href="#" id="giveOther" class="link-two link-general"></a>
<a href="#" id="giveExpress" class="link-four link-general"></a>
<a href="#" id="givePrint" class="link-five link-general"></a>

<script src="<c:url value="/js/global/foot.js"/>"></script>
<script>

    (function ($) {
        jQuery("#menu-opener-id").attr("checked", false);
        var iconSubmitOrder = document.getElementById("icon-submit-order");
//        var mask = document.getElementById("mask");
        iconSubmitOrder.addEventListener("tap", function (event) {
            var list = iconSubmitOrder.classList;
            var is = list.contains("opened");
            if (is) {
                iconSubmitOrder.classList.remove("opened");
                jQuery(mask).hide();
            } else {
                iconSubmitOrder.classList.add("opened");
                jQuery("#mask").css("height", jQuery(document).height());
                jQuery("#mask").css("width", jQuery(document).width());
                jQuery(mask).show();
            }
        });
    })(mui);

    document.getElementById('giveExpress').addEventListener('click', function () {
//        var hour = new Date().getHours();
//        if (hour >= 20 || hour < 6) {
//            mui.toast("晚上快递们都下班啦，明天早上6点上班才能发单哦！");
//            return;
//        }
        mui.openWindow({
            url: basePath + '/WeiXin/subs/enterSubmitOrder?orderType=30',
            id: '111'
        });
    });

    document.getElementById('giveTakeout').addEventListener('click', function () {
//        var hour = new Date().getHours();
//        if (hour >= 22 || hour < 6) {
//            mui.toast("晚上餐厅们都下班啦，明天早上6点上班才能发单哦！");
//            return;
//        }
        mui.openWindow({
            url: basePath + '/WeiXin/subs/enterSubmitOrder?orderType=31',
            id: '112'
        });
    });

    document.getElementById('giveOther').addEventListener('click', function () {
//        var hour = new Date().getHours();
//        if (hour >= 23 || hour < 6) {
//            mui.toast("晚上小带们都下班啦，明天早上7点上班才能发单哦！");
//            return;
//        }
        localStorage.removeItem('taskType');
        mui.openWindow({
            url: basePath + '/WeiXin/subs/enterSubmitOrder?orderType=32',
            id: '113'
        });
    });

    document.getElementById('givePrint').addEventListener('click', function () {
        mui.openWindow({
            url: 'https://wj.qq.com/s/1363052/7e37/',
        });
    });

    document.getElementById('homeTab').addEventListener('tap', function (event) {
        mui.openWindow({
            url: basePath + '/WeiXin/main/home',
            id: '110'
        });
    });

    document.getElementById('orderTab').addEventListener('tap', function (event) {
        mui.openWindow({
            url: basePath + '/WeiXin/main/order',
            id: '210'
        });
    });

    document.getElementById('bringerTab').addEventListener('tap', function (event) {
        mui.openWindow({
            url: basePath + '/WeiXin/bringer/bringerOrder',
            id: '410'
        });
    });

    document.getElementById('mineTab').addEventListener('tap', function (event) {
        mui.openWindow({
            url: basePath + '/WeiXin/main/mine',
            id: '310'
        });
    });
</script>