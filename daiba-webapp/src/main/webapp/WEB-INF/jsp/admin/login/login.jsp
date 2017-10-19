<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>后台登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/admin/css/style.css"/>"/>
    <style>
        body {
            height: 100%;
            background: #16a085;
            overflow: hidden;
        }

        canvas {
            z-index: -1;
            position: absolute;
        }
    </style>
</head>
<body>
<dl class="admin_login">
    <dt>
        <strong>带吧后台管理系统</strong>
        <em>Management System</em>
    </dt>
    <dd class="user_icon">
        <input type="text" id="accountInput" placeholder="账号" class="login_txtbx"/>
    </dd>
    <dd class="pwd_icon">
        <input type="password" id="passwordInput" placeholder="密码" class="login_txtbx"/>
    </dd>
    <dd class="val_icon">
        <div class="checkcode">
            <input type="text" id="J_codetext" placeholder="验证码" maxlength="4" class="login_txtbx">
            <canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
        </div>
    </dd>
    <dd>
        <input type="button" value="立即登陆" class="submit_btn"/>
    </dd>
    <dd>
        <p>Copyright © daibawangluo 版权所有</p>
    </dd>
</dl>
<script src="<c:url value="/admin/js/plugins/jquery-1.7.2.min.js"/> "></script>
<script src="<c:url value="/admin/js/plugins/jquery.md5.js"/> "></script>
<script src="<c:url value="/admin/js/plugins/Particleground.js"/>"></script>
<script src="<c:url value="/admin/js/common.js"/>"></script>
<script src="<c:url value="/admin/js/login.js"/>"></script>
<script>
    $(document).ready(function () {
        //粒子背景特效
        $('body').particleground({
            dotColor: '#5cbdaa',
            lineColor: '#5cbdaa'
        });
        //验证码
        createCode();
        $(".submit_btn").click(function () {
            validate();
        });
    });
</script>
</body>
</html>
