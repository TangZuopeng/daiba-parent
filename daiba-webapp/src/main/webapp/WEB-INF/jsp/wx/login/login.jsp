<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../../include/common.jsp" %>

<html class="ui-page-login">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
    <style>
        .mui-table-view-cell{
            padding: 11px 15px;
        }
    </style>
</head>
<body>

<%@include file="../../include/head.jsp" %>

<div class="mui-content">

    <form id="login-form" class="mui-input-group" method="post">
        <div class="mui-input-row">
            <a class="mui-action-back mui-icon iconfont icon-account"></a>
            <input id="phoneNum" type="text" class="mui-input-clear mui-input" placeholder="请输入手机号"/>
        </div>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id="password" type="password" class="mui-input-clear mui-input" placeholder="请输入密码"/>
        </div>
    </form>
    <form class="mui-input-group">
        <ul class="mui-table-view mui-table-view-chevron">
            <li class="mui-table-view-cell">
                自动登录
                <div id="autoLogin" class="mui-switch mui-active">
                    <div class="mui-switch-handle"></div>
                </div>
            </li>
        </ul>
    </form>
    <div class="mui-content-padded">
        <button id='login' class="mui-btn mui-btn-block mui-btn-primary">登录</button>
        <div class="link-area">
            <a id='forgetPassword'>忘记密码</a>
            <span class="spliter">|</span>
            <a id='reg'>注册账号</a>
        </div>
    </div>
</div>

<script src="<c:url value="/js/plugins/jquery.md5.js"/> "></script>
<script src="<c:url value="/js/login/webapp.js"/> "></script>

<script type="text/javascript" charset="utf-8">
    (function ($, doc) {
        $.init({});
        $.ready(function () {

            var settings = webapp.getSettings();

            //此处修改对应页面的标题
            jQuery('.mui-title').text('登录');

//            //获取记录的IP
//            var value = localStorage.getItem('ipconfig');
//            if (value) {
//            } else {
//                value = 'http://192.168.1.1';
//            }

            //3s之后进入首页
//            var toMain = function () {
//                location.href = basePath + '/WeiXin/main/home';
//                return;
//            };

            var loginButton = doc.getElementById('login');
            var phoneNumBox = doc.getElementById('phoneNum');
            var passwordBox = doc.getElementById('password');
            var autoLoginButton = doc.getElementById("autoLogin");
            var regButton = doc.getElementById('reg');
            var forgetButton = doc.getElementById('forgetPassword');

            //赋予默认的用户名和密码
            phoneNumBox.value = localStorage.getItem("phoneNum");

            //判断是否自动登录
            if (settings.autoLogin) {
                passwordBox.value = localStorage.getItem("password");
                var loginInfo = {
                    phoneNum: phoneNumBox.value,
                    password: passwordBox.value
                };
                webapp.login(loginInfo, function (err) {
                    if (err) {
                        //弹出错误提示框！！！！
                        $.toast(err);
//                        alert(err);
                        return;
                    }
                });
            }

            var timer = null;
            //登录按钮
            loginButton.addEventListener('tap', function (event) {
                var loginInfo = {
                    phoneNum: phoneNumBox.value,
                    password: passwordBox.value,
                    autoLogin: settings.autoLogin
                };
//                value = localStorage.getItem('ipconfig');
//                if (value) {
//                } else {
//                    value = 'http://192.168.1.1';
//                }
                clearTimeout(timer);
                timer = setTimeout(function () {
                    webapp.login(loginInfo, function (err) {
                        if (err) {
                            //弹出错误提示框！！！！
//                        $.toast(err);
                            $.alert(err);
                            return;
                        }
                    });
                },300);
            });
            autoLoginButton.classList[settings.autoLogin ? 'add' : 'remove']('mui-active');
            autoLoginButton.addEventListener('toggle', function (event) {
                setTimeout(function () {
                    var isActive = event.detail.isActive;
                    settings.autoLogin = isActive;
                    webapp.setSettings(settings);
                }, 50);
            }, false);
            regButton.addEventListener('tap', function (event) {
                $.openWindow({
                    url: '${pageContext.request.contextPath}/WeiXin/register',
                    id:'510'
                });
            }, false);

            forgetButton.addEventListener('tap', function (event) {
                $.openWindow({
                    url: '${pageContext.request.contextPath}/WeiXin/forgetPassword',
                    id:'540'
                });
            }, false);
        });

    }(mui, document));
</script>
</body>
</html>

