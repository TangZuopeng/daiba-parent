var baseTag;
var timer = 300;
(function ($, doc) {
    init();
    baseTag=$;
    addClickEvent($);
}(mui, document));

//为列表项添加点击事件
function addClickEvent(tag){
    //跳转到个人中心页面
    document.getElementById("topItem").addEventListener('tap', function() {
        tag.openWindow({
            url: basePath+'/WeiXin/personal/EnterMineDetail',
            id:'311',
        });
    });

    //跳转到已发单页面
    document.getElementById("putOrderItem").addEventListener('tap', function() {

    });

    //跳转到已接单页面
    document.getElementById("getOrderItem").addEventListener('tap', function() {

    });

    //跳转到收货地址管理
    document.getElementById("addressManageItem").addEventListener('tap', function() {
        tag.openWindow({
            url: basePath+"/WeiXin/personal/EnterManageAdd?pageFrom=mine",
            id:'312'
        });
    });

    //申请为带客
    document.getElementById("applyBringerItem").addEventListener('tap', function () {
        var btnArray = ['取消', '确认'];
        mui.confirm('若申请带客,将会在24小时内给予答复！', '申请为带客？', btnArray, function (e) {
            if (e.index == 1) {
                clearTimeout(timer);
                timer = setTimeout(function () {
                    goApplyBringer();
                },300);
            }
        });
    });

    //联系我们
    document.getElementById("touchUsItem").addEventListener('tap', function () {
        var btnArray = ['取消', '确认'];
        mui.confirm('可拨打13159608118或直接在公众号上回复！', '联系我们？', btnArray, function (e) {
        });
    });

    //常见问题
    document.getElementById("commonQuestionItem").addEventListener('tap', function () {
        var btnArray = ['取消', '确认'];
        mui.confirm('请看公众号‘更多’菜单中操作指南！', '常见问题！', btnArray, function (e) {
        });
    });

    //退出登录
    document.getElementById("returnLoginBtn").addEventListener('tap', function () {
        var btnArray = ['取消', '确认'];
        mui.confirm('若退出登录,你将需要重新登录哟！', '退出登录？', btnArray, function (e) {
            if (e.index == 1) {
                clearTimeout(timer);
                timer = setTimeout(function () {
                    goReturnLogin();
                },300);
            }
        });
    });

    //初始化是否新订单推送按钮监听
    document.getElementById("mySwitch").addEventListener("toggle",function(event){
        if(event.detail.isActive){
            updateIsReceiver(1);
        }else{
            updateIsReceiver(0);
        }
    });

}

function updateIsReceiver(isReceiver) {
    $.ajax({
        url:basePath + "/WeiXin/personal/EditIsReceiver.do",
        dataType:"json",
        type:"POST",
        data: {
            isReceiver: isReceiver
        },
        async:false,
        success:function(result){
            if(result.isReceiver){
                mui.toast('亲已开启抢钱提醒！');
            }else{
                mui.toast('小带不能帮亲抢钱了，嘤嘤嘤！');
            }
        },
        error:function(){
            mui.toast('修改失败！');
        }
    });
}

//退出登录
function goReturnLogin(){
    //退出登录之后，不再记住密码
    var settings = getSettings();
    settings.autoLogin = false;
    setSettings(settings);
    localStorage.removeItem("password");
    baseTag.openWindow({
        url: basePath+"/WeiXin/personal/ReturnLogin",
        id:'110'
    });
}

//申请为带客
function goApplyBringer(){
    $.ajax({
        url:basePath+"/WeiXin/personal/ApplyBringer",
        dataType:"json",
        type:"POST",
        async:false,
        success:function(result){
            if(result.success){
                mui.openWindow({
                    url: 'https://wj.qq.com/s/1381380/2940'
                });
            }else{
                mui.toast(result.message);
                mui.openWindow({
                    url: 'https://wj.qq.com/s/1381380/2940'
                });
            }
        },
        error:function(){

        }
    });
}

//显示或者隐藏带客信息
function isShow(flag){
    if(flag){
        $("#credit").show();
        $("#getOrderItem").show();
    }else{
        $("#credit").hide();
        $("#getOrderItem").hide();
        $("#weekIncomeLi").hide();
    }
}

//初始化页面
function init(){
    $.ajax({
        url: basePath + "/WeiXin/personal/ShowUserInfo",
        dataType: "json",
        type: "POST",
        async:false,
        success: function (result) {
            var success=result.success;
            if(success){
                var personalInfo=result.personalInfo;
                var flag=result.flag;
                if(flag){
                    $("#pNickname").text(personalInfo.name);//昵称
                    $("#pCreditValue").text(personalInfo.creditWorthiness);//信誉值
                    $("#pOrderNum").text(personalInfo.orderNum);//发单数
                    $("#pGetOrderNum").text(personalInfo.acceptCount);//接单数
                    $("#weekIncome").text(personalInfo.weekIncome/100 + '元');
                    $("#pHeadIcon").attr('src',personalInfo.portrait);//头像
                    $("#applyBringerItem").hide();
                    //初始化新订单推送开关
                    if (personalInfo.isReceiver == 1){
                        document.getElementById('mySwitch').className = 'mui-switch mui-active';
                    }
                }else{
                    $('#isReceiverItem').hide();
                    isShow(flag);
                    $("#pNickname").text(personalInfo.name);
                    $("#pOrderNum").text(personalInfo.orderNum);
                    $("#pHeadIcon").attr('src',personalInfo.portrait);
                }
            }else{
                mui.toast(result.msg);
            }
        },
        error: function(){
            mui.alert("你已经退出登录,请重新登录！", function () {
                mui.back();
            });
        }
    });
}

/**
 * 获取应用本地配置
 *
 * @param settings
 */
setSettings = function (settings) {
    settings = settings || {};
    localStorage.setItem('$settings', JSON.stringify(settings));
};

/**
 * 设置应用本地配置，如：存储 settions{ autoLogin:true } 、 格式为：JSON
 */
getSettings = function () {
    var settingsText = localStorage.getItem('$settings') || "{}";
    return JSON.parse(settingsText);
};