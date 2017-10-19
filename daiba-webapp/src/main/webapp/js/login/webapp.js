/**
 * Created by dolphinzhou on 2016/10/1.
 */


(function ($, user) {

    // /**
    //  * 获取项目绝对路径
    //  * @returns {string}
    //  */
    // var basePath = (function () {
    //     var url = window.location + "";
    //     var h = url.split("//");
    //     var x = h[1].split("/");
    //     return h[0] + "//" + window.location.host + "/" + x[1];
    // })();

    /**
     * 用户登录
     *
     * @param loginInfo
     * @param callback
     * @returns {*}
     */
    user.login = function (loginInfo, callback) {
        callback = callback || $.noop; //$.noop返回一个空函数
        loginInfo = loginInfo || {};
        loginInfo.phoneNum = loginInfo.phoneNum || '';
        loginInfo.password = loginInfo.password || '';
        loginInfo.autoLogin = loginInfo.autoLogin || '';
        if (!checkPhoneNum(loginInfo.phoneNum)) {
            return callback('手机号码不合法！！！');
        }
        if (!checkPassword(loginInfo.password)) {
            return callback('密码是6-16个字符，由数字、字母组成！');
        }
        var url = basePath + '/WeiXin/login.do';
        $.ajax(url, {
            data: { //发给服务器的数据
                phoneNum: loginInfo.phoneNum,
                password: jQuery.md5(loginInfo.password)
            },
            dataType: 'json',//服务器返回json格式数据
            type: 'post',
            timeout: 15000,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            beforeSend: function () {
                //让提交按钮失效，以实现防止按钮重复点击
                jQuery('#login').attr('disabled', true);
                //给用户提供友好状态提示
                jQuery('#login').text('登录中...');
            },
            complete: function () {
                //让登陆按钮重新有效
                jQuery('#login').removeAttr('disabled');
                jQuery('#login').text('登录');
            },
            success: function (data) {
                if (data.loginMessage == "2") {
                    return callback("该手机号码未注册，请先注册！");
                } else if (data.loginMessage == "3") {
                    return callback("手机号码或密码错误！");
                } else if (data.loginMessage == "1") {
                    localStorage.setItem("phoneNum", loginInfo.phoneNum);
                    if (autoLogin) {
                        localStorage.setItem("password", loginInfo.password);
                    }
                    //alert(data.stateMessage);
                    if (data.stateMessage == "410") {
                        $.toast("登录成功,进入我接的单！");
                        location.href = basePath + '/WeiXin/bringer/bringerOrder';
                    } else if (data.stateMessage == "310") {
                        $.toast("登录成功,进入个人信息！");
                        location.href = basePath + '/WeiXin/main/mine';
                    } else if (data.stateMessage == "210") {
                        $.toast("登录成功,进入我发的单！");
                        location.href = basePath + '/WeiXin/main/order';
                    } else if (data.stateMessage == "4101") {
                        $.toast("您不是带客，没有您接的单，将进入首页！");
                        location.href = basePath + '/WeiXin/main/home';
                    } else {
                        $.toast("登录成功,进入首页！");
                        location.href = basePath + '/WeiXin/main/home';
                    }
                    return callback();
                }
            },
            error: function (xhr, type, errorThrown) {
                $.alert("网络异常，请检测网络状态！", function () {
                    $.back();
                });
            }
        })
    };

    /**
     * 用户注册
     *
     * @param regInfo
     * @param callback
     * @returns {*}
     */
    user.reg = function (regInfo, callback) {
        callback = callback || $.noop; //$.noop返回一个空函数
        regInfo = regInfo || {};
        regInfo.phoneNum = regInfo.phoneNum || '';
        regInfo.password = regInfo.password || '';
        regInfo.code = regInfo.code || '';
        regInfo.token = regInfo.token || '';
        if (!checkPhoneNum(regInfo.phoneNum)) {
            return callback('手机号码不合法！！！');
        }
        if (!checkPassword(regInfo.password)) {
            return callback('密码是6-16个字符，由数字、字母组成！');
        }
        if (!checkCode(regInfo.code)) {
            return callback('请输入正确的验证码！！！');
        }

        // RSAUtils.setMaxDigits(200);
        // var key = new RSAUtils.getKeyPair(publicKeyExponent, "", publicKeyModulus);
        // var encrypedPwd = RSAUtils.encryptedString(key,regInfo.password);

        var url = basePath + '/WeiXin/register.do';
        $.ajax(url, {
            data: { //发给服务器的数据
                phoneNum: regInfo.phoneNum,
                password: jQuery.md5(regInfo.password),
                code: regInfo.code,
                token: regInfo.token
            },
            dataType: 'json',//服务器返回json格式数据
            type: 'post',
            timeout: 15000,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            beforeSend: function () {
                //让提交按钮失效，以实现防止按钮重复点击
                jQuery('#reg').attr('disabled', true);
                //给用户提供友好状态提示
                jQuery('#reg').text('注册中...');
            },
            complete: function () {
                //让注册按钮重新有效
                jQuery('#reg').removeAttr('disabled');
                jQuery('#reg').text('注册');
            },
            success: function (data) {
                if (data.regMessage == '2') {
                    return callback('该手机号码已经注册！');
                } else if (data.regMessage == '1') {
                    localStorage.setItem('phoneNum', regInfo.phoneNum);
                    // localStorage.setItem('password', regInfo.password);
                    return callback();
                } else if (data.regMessage == '3') {
                    return callback("验证码错误，请输入正确验证码！");
                } else if (data.forgetMessage == '0') {
                    return callback('请不要着急，网络正在加速中！');
                }
            },
            error: function (xhr, type, errorThrown) {
                $.alert("网络错误，请检查网络状态", function () {
                    $.back();
                });
            }
        });
    };

    /**
     * 用户忘记密码
     *
     * @param findInfo
     * @param callback
     * @returns {*}
     */
    user.forget = function (findInfo, callback) {
        callback = callback || $.noop; //$.noop返回一个空函数
        findInfo = findInfo || {};
        findInfo.phoneNum = findInfo.phoneNum || '';
        findInfo.newPassword = findInfo.newPassword || '';
        findInfo.code = findInfo.code || '';
        findInfo.token = findInfo.token || '';
        if (!checkPhoneNum(findInfo.phoneNum)) {
            return callback('手机号码不合法！！！');
        }
        if (!checkPassword(findInfo.newPassword)) {
            return callback('密码是6-16个字符，由数字、字母组成！');
        }
        if (!checkCode(findInfo.code)) {
            return callback('请输入正确的验证码！！！');
        }
        var url = basePath + '/WeiXin/forgetPassword.do';
        $.ajax(url, {
            data: { //发给服务器的数据
                phoneNum: findInfo.phoneNum,
                newPassword: jQuery.md5(findInfo.newPassword),
                code: findInfo.code,
                token: findInfo.token
            },
            dataType: 'json',//服务器返回json格式数据
            type: 'post',
            timeout: 15000,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            beforeSend: function () {
                //让提交按钮失效，以实现防止按钮重复点击
                jQuery('#find').attr('disabled', true);
                //给用户提供友好状态提示
                jQuery('#find').text('找回中...');
            },
            complete: function () {
                //让注册按钮重新有效
                jQuery('#find').removeAttr('disabled');
                jQuery('#find').text('确认找回');
            },
            success: function (data) {
                if (data.forgetMessage == '3') {
                    return callback('验证码错误，请输入正确验证码！');
                } else if (data.forgetMessage == '2') {
                    return callback('该手机号码未注册，请返回注册！');
                } else if (data.forgetMessage == '1') {
                    localStorage.setItem('phoneNum', findInfo.phoneNum);
                    return callback();
                } else if (data.forgetMessage == '0') {
                    return callback('请不要着急，网络正在加速中！');
                }
            },
            error: function (xhr, type, errorThrown) {
                $.alert("网络异常，请检测网络状态！", function () {
                    $.back();
                });
            }
        });
    };

    /**
     * 用户修改密码
     *
     * @param findInfo
     * @param callback
     * @returns {*}
     */
    user.changePassword = function (changeInfo, callback) {
        callback = callback || $.noop; //$.noop返回一个空函数
        changeInfo = changeInfo || {};
        changeInfo.oldPassword = changeInfo.oldPassword || '';
        changeInfo.newPassword = changeInfo.newPassword || '';
        changeInfo.token = changeInfo.token || '';
        if (!checkPassword(changeInfo.newPassword)) {
            return callback('密码是6-16个字符，由数字、字母组成！');
        }
        var url = basePath + '/WeiXin/personal/changerPasswordPost.do';
        $.ajax(url, {
            data: { //发给服务器的数据
                oldPassword: jQuery.md5(changeInfo.oldPassword),
                newPassword: jQuery.md5(changeInfo.newPassword),
                token: changeInfo.token
            },
            dataType: 'json',//服务器返回json格式数据
            type: 'post',
            timeout: 15000,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            beforeSend: function () {
                //让提交按钮失效，以实现防止按钮重复点击
                jQuery('#find').attr('disabled', true);
                //给用户提供友好状态提示
                jQuery('#find').text('修改中...');
            },
            complete: function () {
                //让注册按钮重新有效
                jQuery('#find').removeAttr('disabled');
                jQuery('#find').text('确认修改');
            },
            success: function (data) {
                if (data.changeMessage == '2') {
                    return callback('该手机号码未注册，请返回注册！');
                } else if (data.changeMessage == '1') {
                    return callback();
                } else if (data.changeMessage == '3') {
                    return callback("原密码错误，请输入正确密码！");
                }
            },
            error: function (xhr, type, errorThrown) {
                $.alert("网络异常，请检测网络状态！", function () {
                    $.back();
                });
            }
        });
    };

    /**
     * 发送验证码
     *
     * @param phoneNum 手机号码(接收验证码)
     * @param urlAddress 区分是哪个功能页面的验证码
     * @param token 防止重复提交令牌
     * @param callback 回调函数
     * @returns {*}
     */
    user.sendCode = function (phoneNum, urlAddress, token, callback) {
        callback = callback || $.noop; //$.noop返回一个空函数
        phoneNum = phoneNum || '';
        token = token || '';
        if (!checkPhoneNum(phoneNum)) {
            return callback('手机号码不合法！！！');
        }
        var url = basePath + '/WeiXin/Code/' + urlAddress;
        $.ajax(url, {
            data: {
                phoneNum: phoneNum,
                token: token
            },
            dataType: 'json',//服务器返回json格式数据
            type: 'post',
            timeout: 15000,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            beforeSend: function () {
                //让提交按钮失效，以实现防止按钮重复点击
                // jQuery('#sendCode').attr('disabled',true);
            },
            complete: function () {
                // jQuery('#sendCode').removeAttr('disabled');
            },
            success: function (data) {
                if (data.codeMessage == '1') {
                    return callback();
                } else if (data.codeMessage == '3') {
                    return callback("该手机号码已经注册！");
                } else if (data.codeMessage == '2') {
                    return callback("验证码罢工了，请稍后！");
                } else if (data.codeMessage == '4') {
                    return callback("手机号码未注册，请前往注册！");
                } else if (data.codeMessage == '0') {
                    return callback("加载页面错误，请重新加载页面！");
                } else if (data.codeMessage == '6') {
                    return callback("您的微信账号已经与另一个手机号码进行绑定，请返回登录！");
                }
            },
            error: function (xhr, type, errorThrown) {
                $.alert("网络异常，请检测网络状态！", function () {
                    $.back();
                });
            }
        });
    };

    /**
     * 验证码倒计时
     *
     * @param t 时间
     * @param obj 组件(可以是input或button)
     * @param waitMessage 改变按钮的内容样式为：(60+waitMessage)
     */
    user.disableWait = function (t, obj, waitMessage) {
        var objTag = obj.tagName.toLowerCase();
        if (objTag !== "input" && objTag != "button") {
            return;
        }
        var v = objTag !== "input" ? obj.innerText : obj.value;
        var i = setInterval(function () {
            if (t > 0) {
                switch (objTag) {
                    case "input":
                        obj.value = (--t) + waitMessage;
                        break;
                    case "button":
                        obj.innerText = (--t) + waitMessage;
                        break;
                    default:
                        break;
                }
                obj.disabled = true;
            } else {
                window.clearInterval(i);
                switch (objTag) {
                    case "input":
                        obj.value = v;
                        break;
                    case "button":
                        obj.innerText = v;
                        break;
                    default:
                        break;
                }
                obj.disabled = false;
                ////60s 之后刷新当前页面
                // location.reload();
                ////60s 之后刷新获取验证码的按钮
                // jQuery( "[type='button']" ).button( "refresh" );
            }
        }, 1000);
    };

    /**
     * 检查手机号码是否合法
     *
     * @param phoneNum
     * @returns {boolean}
     */
    var checkPhoneNum = function (phoneNum) {
        phoneNum = phoneNum || '';
        var myreg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;
        return phoneNum.length == 11 && myreg.test(phoneNum);
    };

    /**
     * 检查密码是否合法
     *
     * @param password
     * @returns {boolean}
     */
    var checkPassword = function (password) {
        password = password || '';
        var myreg = /^([A-Z]|[a-z]|[0-9]){6,16}$/;
        return myreg.test(password);
    };

    /**
     * 检查验证码格式是否正确
     *
     * @param code
     * @returns {*}
     */
    var checkCode = function (code) {
        code = code || '';
        var myreg = /^\d{6}$/;
        return myreg.test(code);
    };

    /**
     * 获取应用本地配置
     *
     * @param settings
     */
    user.setSettings = function (settings) {
        settings = settings || {};
        localStorage.setItem('$settings', JSON.stringify(settings));
    };

    /**
     * 设置应用本地配置，如：存储 settions{ autoLogin:true } 、 格式为：JSON
     */
    user.getSettings = function () {
        var settingsText = localStorage.getItem('$settings') || "{}";
        return JSON.parse(settingsText);
    };

}(mui, window.webapp = {}));
