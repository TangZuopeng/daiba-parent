function showCheck(a) {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 1000, 1000);
    ctx.font = "80px 'Microsoft Yahei'";
    ctx.fillText(a, 0, 100);
    ctx.fillStyle = "white";
}
var code;
//  创建验证码
function createCode() {
    code = "";
    var codeLength = 4;
    var selectChar = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    for (var i = 0; i < codeLength; i++) {
        var charIndex = Math.floor(Math.random() * 60);
        code += selectChar[charIndex];
    }
    if (code.length != codeLength) {
        createCode();
    }
    showCheck(code);
}
//  验证码校验
function validate() {
    var inputCode = document.getElementById("J_codetext").value.toUpperCase();
    var codeToUp = code.toUpperCase();
    if (inputCode.length <= 0) {
        document.getElementById("J_codetext").setAttribute("placeholder", "输入验证码");
        createCode();
        return false;
    }
    else if (inputCode != codeToUp) {
        document.getElementById("J_codetext").value = "";
        document.getElementById("J_codetext").setAttribute("placeholder", "验证码错误");
        createCode();
        return false;
    }
    else {
        submit();
        return true;
    }
}

function submit() {
    var account = $("#accountInput").val();
    var password = $("#passwordInput").val();
    $.ajax({
        url: basePath + "/Admin/login.do",
        data: {
            account: account,
            password: jQuery.md5(password)
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (data) {
            if (data.loginMessage == '1') {
                //重定向
                redirect("/home");
            } else if (data.loginMessage == '2') {
                createCode();
                alert("你不是管理员！你是谁？你怎么知道我们后台管理网址的？")
            } else {
                createCode();
                alert("密码错误，请重新输入密码");
            }
        },
        error: function () {
            createCode();
            alert("网络异常！！！")
        }
    });
}