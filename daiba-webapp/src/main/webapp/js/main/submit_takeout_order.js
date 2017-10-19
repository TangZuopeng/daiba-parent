/**
 * Created by StphenTmac on 2017/4/5.
 */
//购物车中菜品
var cartJson;
//发单地址码
var placeCode;
//满减前商品总价
var oldPrices = 0;
//满减后商品总价
var newPrices = 0;
//当前时间
var now = new Date();
//当前时间+30分钟
var quick = addMinutes(now, 30);
//最初接单佣金,表示最少雇佣金
var shippingFee;
//最新接单佣金
var newShippingFee;
//满减前总金额
var oldAllFee = 0;
//满减后总金额
var newAllFee = 0;
//初始化组件
var menu = document.getElementById('menu');
var shippingFeeDiv = document.getElementById('shippingFeeDiv');

//计算后n分钟的时间
function addMinutes(date,minutes) {
    minutes=parseInt(minutes);
    var   interTimes=minutes*60*1000;
    interTimes=parseInt(interTimes);
    return new Date(Date.parse(date)+interTimes);
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

function initChangeShipping() {
    //添加减号监听事件
    document.getElementById('feeMinus').addEventListener('tap', function () {
        var money = mui('#orderMoney').numbox().getValue();
        $('#shippingFeeDiv .goods-price').html('￥' + money);
        newShippingFee = money;
        newAllFee = (newPrices + newShippingFee*100)/100;
        $('#newFee').html(newAllFee);
        oldAllFee = (oldPrices + newShippingFee*100)/100;
        $('#oldFee').html(oldAllFee);
    });
    //添加加号监听事件
    document.getElementById('feePlus').addEventListener('tap', function () {
        var money = mui('#orderMoney').numbox().getValue();
        $('#shippingFeeDiv .goods-price').html('￥' + money);
        newShippingFee = money;
        newAllFee = (newPrices + newShippingFee*100)/100;
        $('#newFee').html(newAllFee);
        oldAllFee = (oldPrices + newShippingFee*100)/100;
        $('#oldFee').html(oldAllFee);
    });
}

//将后台提交商品总金额, 用于满减
function submitGoodMoney() {
    $.ajax({
        url: basePath + '/WeiXin/dishes/fullReduction.do',
        data: {
            goodsMoney: oldPrices
        },
        dataType: 'json',
        type: "POST",
        success: function (result) {
            newPrices = result;
            newAllFee = (newPrices + newShippingFee*100)/100;
            if (newPrices == oldPrices){
                $('#fee').html(newAllFee);
                $('#noFav').show();
            } else {
                $('#newFee').html(newAllFee);
                $('#fav').show();
                $('#favMsg').show();
            }
            $('#submitOrderBtn').removeAttr("disabled");
        },
        error: function (err) {
            mui.toast("满减信息拉取失败！");
        }
    });
}

function init() {
    cartJson = window.localStorage.getItem('dishes');
    shippingFee = window.localStorage.getItem('shippingFee');
    var cart = eval(cartJson);
    setMenu(cart);
    submitGoodMoney();
    initAddress();
    cusAddress();
    myArriveTime();
    initChangeShipping();
    //提交订单
    var submitOrderTimer = null;
    var submitOrderBtn = document.getElementById("submitOrderBtn");
    submitOrderBtn.addEventListener('tap', function() {

        submitOrderBtn.setAttribute('disabled','disabled');
        $(submitOrderBtn).find('strong').text('校验中...');

        clearTimeout(submitOrderTimer);
        submitOrderTimer = setTimeout(function () {
            if(checkSubmit()){
                submitOrder();
            }
            submitOrderBtn.removeAttribute('disabled');
            $(submitOrderBtn).find('strong').text('发布带单');
        },300);
    });
}

//检查提交的信息是否符合要求
function checkSubmit(){
    //alert($("#shrRoom").text());
    var tel= $("#shrTel").text();//发单人电话号码
    var remark=$("#remark").val();//留言

    if(tel==''){
        mui.toast('请选择收餐人地址信息');
        return;
    }

    if(remark.length > 50){
        mui.toast('菜品要求应小于50字');
        return;
    }
    return true;
}

function initWxConfig(){
    $.ajax({
        url: basePath + '/WeiXin/JSSDK/getJSSDK.do',
        data: {
            mUrl: basePath + '/WeiXin/dishes/enterSubmitDishesOrder'
        },
        dataType: 'json',
        success: function(result){
            wx.config({
                debug: false,
                appId: result.appId,
                timestamp: result.timestamp,
                nonceStr: result.nonceStr,
                signature: result.signature,
                jsApiList: ['chooseWXPay']
            });
        },
        error: function(){
            mui.alert("请检查你的网络连接状态！", function () {
                mui.back();
            });
        }
    });
}


//触发预支付
function prePay(firmId, fee){
    $.ajax({
        url: basePath + '/WeiXin/pay/pay.do',
        dataType: 'json',
        data: {
            firmId: firmId,
            fee: fee
        },
        success: function(result){
            if(parseInt(result.agent)<5){
                mui.toast("您的微信版本低于5.0无法使用微信支付");
                return;
            }
            wx.chooseWXPay({
                timestamp: result.timeStamp, // 支付签名时间戳 注意这里的s 文档新版大写 但是我的小写才好使
                nonceStr: result.nonceStr, // 支付签名随机串
                package: result.package, // 统一支付接口返回的package包
                signType: result.signType, // 签名方式，'MD5'
                paySign: result.paySign, // 支付签名
                success: function (res) {
                    if (res.errMsg == "chooseWXPay:ok") {
                        //支付订单
                        // alert(""+result.out_trade_no,result.total_fee);
                        mui.alert("订单支付成功！小带们要开始抢单啦", function () {
                            mui.back();
                            mui.openWindow({
                                url: basePath + '/WeiXin/pay/success'
                            });
                        });

                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    }
                    else if (res.errMsg == "chooseWXPay:cancel") {
                        mui.toast("订单支付取消！");
                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    }
                    else if (res.errMsg == "chooseWXPay:fail") {
                        mui.toast("订单支付失败！");
                        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                    }
                }
            });
        },
        error: function(err){
            mui.alert("请检查你的网络连接状态！", function () {
                mui.back();
            });
        }
    })
}

// 提交支付订单
function submitOrder(){
    var cartJsonArray = eval(cartJson);
    var cart = [];
    //剔除无用json数据
    $.each(cartJsonArray, function (i, data) {
        var temp = {dishesId: data.dishesId, count: data.count};
        cart.push(temp);
    });
    //从本地内存中将商家地址取出
    var merchantAdds = window.localStorage.getItem("merchantAdds");
    var cartJsonStr = JSON.stringify(cart);
    var name= $("#shrName").text();//发单人姓名
    var tel= $("#shrTel").text();//发单人电话号码
    var money=$("#money").val();//订单金额
    var remark=$("#remark").val();//留言
    var add=$("#shrAddress").text();//发单人地址
    $.ajax({
        url:basePath+"/WeiXin/subs/submitOrder.do",
        data:{
            cart: cartJsonStr,
            "order.company": undefined,//快递名称，例如圆通快递
            "order.receiver":name,
            "order.acceptAddCode":placeCode,//地址码：学校+校区+ 地点
            "order.takeNum":undefined,
            "order.reservedPhone":tel,
            "order.staId":orderType,
            "order.acceptAddress":merchantAdds,//取件地址，例如大学生超市
            "firm.askTime":time,
            "firm.address":add,
            "firm.orderMoney":newShippingFee * 100,
            "firm.remark":remark,
            "firm.goodsMoney":oldPrices
        },
        dataType:"json",
        type:"POST",
        async:false,
        beforeSend:function(){
            $("#submitOrderBtn").attr('disabled', true);
            $("#submitOrderBtn strong").text('校验中...');
        },
        complete:function(){
            $("#submitOrderBtn").removeAttr('disabled');
            $("#submitOrderBtn strong").text('发布带单');
        },
        success:function(result){
            if(result.success){
                mui.toast("生成订单成功！");

                //**********************存储你发单的校区
                window.localStorage.setItem("campusCode",placeCode.substring(0,6));

                prePay(result.firmId, result.fee);
            }else{
                mui.toast("生成订单失败！");
            }
        },
        error:function(){
            mui.alert("网络异常，请检测网络状态！",function(){
                mui.back();
            });
        }
    });
}

function initAddress() {
    var fdrAddress = JSON.parse(localStorage.getItem("fdrAddress"));
    if(null!=fdrAddress){
        var callName = fdrAddress.callName;
        callName = callName.substring(0, callName.length-2);
        $("#shrName").text(callName);
        $("#shrTel").text(fdrAddress.phoneNum);
        $("#shrAddress").text(fdrAddress.campus+fdrAddress.build+fdrAddress.room);
        $("#shrRoom").text(fdrAddress.build+fdrAddress.room);
        setCampusCode(fdrAddress.campus);
    }
}

//关于收货地址相关操作
function cusAddress() {
    document.getElementById("addressItem").addEventListener('tap', function() {
        mui.openWindow({
            url: basePath+"/WeiXin/personal/EnterManageAdd?pageFrom=order",
            id:"312"
        });
    });
}

//根据收货地址确定发单校区
function setCampusCode(campus) {
    if (campus == '南湖校区'){
        placeCode = '1001010000';
    }else if (campus == '林园校区'){
        placeCode = '1001020000';
    }else {
        placeCode = '1001030000';
    }
}

//关于送达地址的相关操作
var time;
function myArriveTime() {
    time = quick;
    $('#takeOutArriveTime').text(time.format("yyyy-MM-dd hh:mm") + '(尽快)');
    var myPicker = new mui.PopPicker();
    myPicker.setData(initMyTime());
    document.getElementById("takeOutTaskTime").addEventListener('tap', function() {
        myPicker.show(function(items) {
            time = items[0].value;
            if (time == quick){
                $("#takeOutArriveTime").text(items[0].value.format("yyyy-MM-dd hh:mm") + '(尽快)');
            } else {
                $("#takeOutArriveTime").text(items[0].value.format("yyyy-MM-dd hh:mm"));
            }
            //返回 false 可以阻止选择框的关闭
            //return false;
        });
    });
}

//实例化时间
function formatTime(time){
    var hour=time.getHours();
    var min=time.getMinutes();
    var str="";
    if(hour<10){
        str=str+"0"+hour;
    }else{
        str=+hour;
    }
    str+=":";
    if(min<10){
        str=str+"0"+min;
    }else{
        str=str+min;
    }
    return str;
}

//实现依次增加的分钟数
function addMin(time,min){
    var time_old=time.getTime();
    var time_new=new Date();
    time_new.setTime(time_old+1000*60*min);
    return time_new;
}

function initMyTime(){

    var mytime=new Array();

    //在当前时间的基础上，添加一小时
    var t=now.getFullYear()+"/"+(now.getMonth()+1)+"/"+now.getDate()+" "+(now.getHours()+1)+":00:00";
    var temp=new Date();
    temp.setTime(new Date(t).getTime());
    mytime[0]={value:quick,text:'尽快送达'};
    mytime[1]={value:temp,text:formatTime(temp)};

    if(temp.getHours()!=22){
        var i=1;
        while(temp.getHours()<22){
            var tempTime=addMin(temp,15);
            mytime[i]={value:tempTime,text:formatTime(tempTime)};
            temp=tempTime;
            i++;
        }
    }
    return mytime;
}

//将内存中的菜单列表打印到指定位置
function setMenu(cartJson) {
    if (cartJson != null){
        $.each(cartJson, function (i, data) {
            var dish = document.createElement('div');
            dish.id = data.dishesId;
            dish.className = 'mui-table-view';
            var div = document.createElement('div');
            div.className = 'mui-table-view-cell';
            var goodName = document.createElement('span');
            goodName.className = 'goods-name';
            var goodNum = document.createElement('span');
            goodNum.className = 'goods-num';
            var goodPrice = document.createElement('span');
            goodPrice.className = 'goods-price'
            goodName.innerHTML = data.name;
            goodNum.innerHTML = '×' + data.count;
            goodPrice.innerHTML = '￥' + data.price * data.count/100;
            div.appendChild(goodName);
            div.appendChild(goodNum);
            div.appendChild(goodPrice);
            dish.appendChild(div);
            oldPrices = oldPrices + data.price * data.count;
            menu.insertBefore(dish, shippingFeeDiv);
        });
    }
    //将配送费打印到指定位置
    if (shippingFee == null){
        shippingFee = 0;
    }
    newShippingFee = shippingFee;
    $('#shippingFeeDiv .goods-price').html('￥' + shippingFee);
    mui('#orderMoney').numbox().setOption('min',shippingFee);
    $('#money').val(newShippingFee);
    oldAllFee = (oldPrices + newShippingFee*100)/100;
    $('#oldFee').html(oldAllFee);
}

$(function () {
    initWxConfig();//初始化微信提供的配置信息
    init();
});