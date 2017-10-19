<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/common.jsp" %>
<html class="ui-page-login">
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
    <style>
        .mui-table-view-cell{
            padding: 11px 15px;
        }
    </style>
</head>

<%@include file="../../include/head.jsp" %>
<body>
<div class="mui-content">
    <form class="mui-input-group" method="post">

        <%-- 使用隐藏域存储生成的token --%>
        <%--使用EL表达式取出存储在session中的token--%>
        <input type="hidden" id="token" value="${token}"/>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-account"></a>
            <input id='phoneNum' type="text" class="mui-input-clear mui-input" placeholder="请输入手机号"/>
        </div>
        <div class="mui-input-row code">
            <a class="mui-icon iconfont icon-code"></a>
            <input id='code' type="text" disabled class="mui-input" placeholder="验证码"/>
            <button id='sendCode' type='button' class="mui-btn mui-btn-block mui-btn-primary">验证码</button>
        </div>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id='newPassword' type="password" class="mui-input-clear mui-input" placeholder="请输入新密码"/>
        </div>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id='newPassword_confirm' type="password" class="mui-input-clear mui-input" placeholder="请确认新密码"/>
        </div>
    </form>

    <div class="mui-content-padded">
        <button id='find' class="mui-btn mui-btn-block mui-btn-primary">确认找回</button>
    </div>
</div>
<script src="<c:url value="/js/plugins/jquery.md5.js"/> "></script>
<script src="<c:url value="/js/login/webapp.js"/> "></script>
<script type="text/javascript" charset="utf-8">
    (function ($, doc) {
        $.init({});
        $.ready(function () {

            //此处修改对应页面的标题
            jQuery('.mui-title').text('找回密码');

            var tokenBox = doc.getElementById('token');
            var findButton = doc.getElementById('find');
            var sendCodeButton = doc.getElementById('sendCode');
            var phoneNumBox = doc.getElementById('phoneNum');
            var codeBox = doc.getElementById('code');
            var newPasswordBox = doc.getElementById('newPassword');
            var newPasswordConfirmBox = doc.getElementById('newPassword_confirm');

            var findtimer = null;
            //找回密码按钮事件
            findButton.addEventListener('tap', function (event) {
                var findInfo = {
                    phoneNum: phoneNumBox.value,
                    newPassword: newPasswordBox.value,
                    code: codeBox.value,
                    token: tokenBox.value
                };
                var newPasswordConfirm = newPasswordConfirmBox.value;
                if (newPasswordConfirm != findInfo.newPassword) {
                    //提示两次密码不一致！！！！
                    $.toast('提示两次密码不一致');
                    return;
                }
                clearTimeout(findtimer);
                findtimer = setTimeout(function () {
                    webapp.forget(findInfo, function (err) {
                        if (err) {
                            //弹出错误提示框！！！！
                            $.toast(err);
                            return;
                        }
                        $.alert('找回成功，返回登录！', function () {
                            $.back();
                            $.openWindow({
                                url: '${pageContext.request.contextPath}/WeiXin/login',
                                id:'520'
                            });
                        });
                    });
                },300);
            });
            var codetimer = null;
            //发送验证码按钮事件
            sendCodeButton.addEventListener('tap', function () {
                var phoneNum = phoneNumBox.value;
                var token = tokenBox.value;
                var urlAddress = 'sendForgetCode';

                sendCodeButton.setAttribute('disabled','disabled');
                jQuery(sendCodeButton).text('校验中');

                clearTimeout(codetimer);
                codetimer = setTimeout(function () {
                    webapp.sendCode(phoneNum, urlAddress, token, function (err) {
                        if (err) {
                            sendCodeButton.removeAttribute('disabled');
                            jQuery(sendCodeButton).text('验证码');
                            $.toast(err);
                            return;
                        }

//                        sendCodeButton.removeAttribute('disabled');
                        jQuery(sendCodeButton).text('验证码');

                        $.toast("验证码已飞向您的手机，请查收哦！");
                        //设置验证码输入框可用
                        codeBox.removeAttribute('disabled');
                        webapp.disableWait(60, sendCodeButton, ' s');
                    });
                },100);
            });
        });
    }(mui, document));
</script>
</body>

</html>