<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/common.jsp" %>
<html class="ui-page-login">
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
</head>

<%@include file="../../include/head.jsp" %>
<body>
<div class="mui-content">
    <form class="mui-input-group" method="post">
        <%-- 使用隐藏域存储生成的token --%>
        <%--使用EL表达式取出存储在session中的token--%>
        <input type="hidden" id="token" value="${token}"/>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id='oldPassword' type="password" class="mui-input-clear mui-input" placeholder="请输入原密码"/>
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
        <button id='find' class="mui-btn mui-btn-block mui-btn-primary">确认修改</button>
    </div>
</div>
<script src="<c:url value="/js/plugins/jquery.md5.js"/> "></script>
<script src="<c:url value="/js/login/webapp.js"/> "></script>
<script type="text/javascript" charset="utf-8">
    (function ($, doc) {
        $.init({});
        $.ready(function () {

            //此处修改对应页面的标题
            jQuery('.mui-title').text('修改密码');

            var tokenBox = doc.getElementById('token');
            var oldPasswordBox = doc.getElementById('oldPassword');
            var newPasswordBox = doc.getElementById('newPassword');
            var newPasswordConfirmBox = doc.getElementById('newPassword_confirm');
            var findButton = doc.getElementById('find');

            var findtimer = null;
            //修改密码按钮事件
            findButton.addEventListener('tap', function (event) {
                var changeInfo = {
                    oldPassword:oldPasswordBox.value,
                    newPassword: newPasswordBox.value,
                    token: tokenBox.value
                };
                var newPasswordConfirm = newPasswordConfirmBox.value;
                if (newPasswordConfirm != changeInfo.newPassword) {
                    //提示两次密码不一致！！！！
                    $.toast('提示两次密码不一致');
                    return;
                }
                clearTimeout(findtimer);
                findtimer = setTimeout(function () {
                    webapp.changePassword(changeInfo, function (err) {
                        if (err) {
                            //弹出错误提示框！！！！
                            $.toast(err);
                            return;
                        }
                        $.alert('修改成功，请重新返回登录！',function () {
                            $.back();
                        });
                        $.openWindow({
                            url: '${pageContext.request.contextPath}/WeiXin/login'
                        });
                    });
                },300);
            });
//            //发送验证码按钮事件
//            sendCodeButton.addEventListener('tap', function () {
//                var phoneNum = phoneNumBox.value;
//                var token = tokenBox.value;
//                var urlAddress = 'sendForgetCode';
//                webapp.sendCode(phoneNum, urlAddress, token, function (err) {
//                    if (err) {
//                        $.toast(err);
//                        return;
//                    }
//                    $.toast("验证码已飞向您的手机，请查收哦！");
//                    //设置获取验证码的按钮不可用
//                    codeBox.removeAttribute('disabled');
//                    webapp.disableWait(60, sendCodeButton, ' s\'');
//                });
//            });
        });
    }(mui, document));
</script>
</body>

</html>