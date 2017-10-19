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

<body>

<%@include file="../../include/head.jsp" %>

<div class="mui-content">
    <form class="mui-input-group" method="post">
        <%-- 使用隐藏域存储生成的token --%>
        <%--使用EL表达式取出存储在session中的token--%>
        <input type="hidden" id="token" value="${token}"/>
        <div class="mui-input-row">
            <a class="mui-action-back mui-icon iconfont icon-account"></a>
            <input id="phoneNum" type="text" class="mui-input-clear mui-input" placeholder="请输入手机号"/>
        </div>
        <div class="mui-input-row code">
            <a class="mui-icon iconfont icon-code"></a>
            <input id='code' type="text" disabled class="mui-input" placeholder="验证码"/>
            <button id='sendCode' type="button" class="mui-btn mui-btn-block mui-btn-primary">验证码</button>
        </div>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id="password" type="password" class="mui-input-clear mui-input" placeholder="请输入密码"/>
        </div>
        <div class="mui-input-row">
            <a class="mui-icon iconfont icon-passWord"></a>
            <input id='password_confirm' type="password" class="mui-input-clear mui-input" placeholder="请确认密码"/>
        </div>
    </form>

    <div class="mui-content-padded">
        <button id='reg' type="submit" class="mui-btn mui-btn-block mui-btn-primary">注册</button>
    </div>
    <div class="mui-content-padded">
        <p>*注册即表示同意并接受
            <a id="userAgreement">用户协议</a>
        </p>
    </div>

    <div class="mui-content-padded" hidden>
        <button id='invitation' type="button" class="mui-btn mui-btn-block mui-btn-primary md-trigger"
                data-modal="modal-4"></button>
    </div>
</div>

<%--弹窗框--%>
<%--<%@include file="../../include/dialog.jsp" %>--%>

<script src="<c:url value="/js/plugins/jquery.md5.js"/> "></script>
<script src="<c:url value="/js/login/webapp.js"/> "></script>
<script type="text/javascript" charset="utf-8">

    (function ($, doc) {
        $.init({
            swipeBack: true
        });
        $.ready(function () {

            var toLogin = function () {
                $.openWindow({
                    url: '${pageContext.request.contextPath}/WeiXin/login'
                });
            };

            /*//邀请码弹出框
            window.onload = function () {
                var invitationCodeBox = doc.getElementById("invitationCode");
                var invitationButton = doc.getElementById("invitation");
                var mdConfirmButton = doc.getElementById("md-confirm");
                var mdCancleButton = doc.getElementById("md-cancle");
                invitationButton.click();

                jQuery('#md-title').text('解封码');
                jQuery('#md-content').text('请您正确输入书签上面的六位解封码！');

                jQuery('#md-cancle').text('路过路过');
                jQuery('#md-confirm').text('解开封印');

                jQuery('#md-form').removeAttr("hidden");

                //验证邀请码的位数
                var myreg = /^\d{6}$/;
                //弹出框
                mdConfirmButton.addEventListener("tap", function () {
                    if (!myreg.test(invitationCodeBox.value)) {
                        $.alert('少侠，请您先寻得解封码!', function () {
                            $.back();
                        });
                        return;
                    }
                    var url = basePath + '/WeiXin/invitationTesting.do';
                    $.ajax(url, {
                        data: {
                            invitationCode: invitationCodeBox.value
                        },
                        dataType: 'json',//服务器返回json格式数据
                        type: 'post',
                        timeout: 15000,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        success: function (data) {
                            if (data.invitationMessage == 1) {
                                $.toast("封印解除,请您完成注册！");
                                jQuery('.mui-content').removeAttr("hidden");
                                return;
                            } else if (data.invitationMessage == 2) {
                                $.alert("解除码已被使用，请您继续寻找解除码！", function () {
                                    $.back();
                                });
                            } else if (data.invitationMessage == 3) {
                                $.alert("解除码错误,请您先确认您的解除码！", function () {
                                    $.back();
                                })
                            }
                        },
                        error: function (xhr, type, errorThrown) {
                            $.toast('网络异常，请检查网络状态.');
                        }
                    });
                }, false);
                mdCancleButton.addEventListener("tap", function () {
                    $.back();
                }, false);
            };
            //*/

            //此处修改对应页面的标题
            jQuery('.mui-title').text('注册');

            var tokenBox = doc.getElementById('token');
            var regButton = doc.getElementById('reg');
            var sendCodeButton = doc.getElementById('sendCode');
            var phoneNumBox = doc.getElementById('phoneNum');
            var codeBox = doc.getElementById('code');
            var passwordBox = doc.getElementById('password');
            var passwordConfirmBox = doc.getElementById('password_confirm');
            var userAgreementButton = doc.getElementById("userAgreement");

            var regtimer = null;
            //注册按钮事件
            regButton.addEventListener('tap', function (event) {

                regButton.setAttribute('disabled','disabled');
                jQuery(regButton).text('校验中...');

                var regInfo = {
                    phoneNum: phoneNumBox.value,
                    password: passwordBox.value,
                    code: codeBox.value,
                    token: tokenBox.value
                };
                var passwordConfirm = passwordConfirmBox.value;
                if (passwordConfirm != regInfo.password) {

                    regButton.removeAttribute('disabled');
                    jQuery(regButton).text('注册');

                    //提示两次密码不一致！！！！
                    $.toast('提示两次密码不一致');
                    return;
                }
                clearTimeout(regtimer);
                regtimer = setTimeout(function () {
                    //调用wabapp 里面的 reg 方法
                    webapp.reg(regInfo, function (err) {
                        if (err) {

                            regButton.removeAttribute('disabled');
                            jQuery(regButton).text('注册');

                            //弹出错误提示框！！！！
                            $.toast(err);
                            return;
                        }
                        $.alert('注册成功', function () {
                            $.back();
                            $.openWindow({
                                url: '${pageContext.request.contextPath}/WeiXin/login',
                                id: '520'
                            });
                        });
                    });
                }, 300);
            });
            var codetimer = null;
            //发送验证码按钮事件
            sendCodeButton.addEventListener('tap', function () {
                var phoneNum = phoneNumBox.value;
                var token = tokenBox.value;
                var urlAddress = 'sendRegCode';

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
                        $.toast("验证码已飞向您的手机，请少侠查收哦！");

//                        sendCodeButton.removeAttribute('disabled');
                        jQuery(sendCodeButton).text('验证码');

                        codeBox.removeAttribute('disabled');
                        webapp.disableWait(60, sendCodeButton, ' s ');
                    });
                }, 100);
            });
            userAgreementButton.addEventListener('tap', function (event) {
                $.openWindow({
                    url: '${pageContext.request.contextPath}/WeiXin/userAgreement',
                    id: 'user_agreement.jsp'
                });
            });
        });
    }(mui, document));

</script>
</body>

</html>