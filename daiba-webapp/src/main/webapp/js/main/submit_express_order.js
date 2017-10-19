var baseTag;
var baseDoc;
(function($, doc) {
    baseTag = $;
    baseDoc = doc;
    init();
    initWxConfig();//初始化微信提供的配置信息
    myArriveTime();
    moneyBlur();
    cusAddress();

    //触发物件大小事件
    document.getElementById("weightItem").addEventListener('tap', function(e) {
        mui('.mui-popover').popover('toggle',document.getElementById("weightItem"));
        document.querySelector('.mui-table-view.mui-table-view-radio').addEventListener('selected',function(e){
            var text=e.detail.el.innerText;
            //根据重量修改最小金额。
            editMinMoneyByWeight(text);
            mui('.mui-popover').popover('hide');
        });
    });

    //为减少金额添加事件
    document.getElementById("minuxMoneyBtn").addEventListener('tap', function(e) {
        var tempMoney=document.getElementById("money").value;
        if(tempMoney<=minMoney){
            //最小金额+1是为了防止递减按钮，在触发这个事件完毕以后后续还会减1
            document.getElementById("money").value=minMoney+1;
        }
    });


    //提交订单
    var submitOrderTimer = null;
    var submitOrderBtn = document.getElementById("submitOrderBtn");
    submitOrderBtn.addEventListener('tap', function() {

        submitOrderBtn.setAttribute('disabled','disabled');
        jQuery(submitOrderBtn).text('校验中...');

        //prePay();
        clearTimeout(submitOrderTimer);
        submitOrderTimer = setTimeout(function () {
            if(checkSubmit()){
                submitOrder();
            }
            submitOrderBtn.removeAttribute('disabled');
            jQuery(submitOrderBtn).text('发单');
        },300);
    });
})(mui, document);

//初始化地址数据
//获取地址码
var placeCode;
var companyName;
var acceptAddress;
var campus;
function initPlace(data) {
    var placePicker = new baseTag.PopPicker({
        layer: 3
    });
    placePicker.setData(data);
    var showPlacePickerButton = baseDoc.getElementById('showPlacePicker');
    var placeResult = baseDoc.getElementById('palceResult');
    showPlacePickerButton.addEventListener('tap', function(event) {
        placePicker.show(function(items) {
            placeResult.innerText = "" + (items[0] || {}).text + " " + (items[1] || {}).text + " " + (items[2] || {}).text;
            placeCode=(items[1] || {}).value;
            companyName=(items[2] || {}).text;
            acceptAddress=(items[1] || {}).text;
            //校区
            campus=(items[0] || {}).text;
            //返回 false 可以阻止选择框的关闭
            //return false;
        });
    }, false);
}

//初始化发单人快递地址信息  接单人地址信息
function init(){
    $.ajax({
        url:basePath+"/WeiXin/subs/initSubmit.do",
        dataType:"json",
        type:"POST",
        async:false,
        success:function(result){
            if(result.success){
                var place = result.place;
                initPlace(place);
                var fdrAddress = JSON.parse(localStorage.getItem("fdrAddress"));
                if(null!=fdrAddress){
                    var callName = fdrAddress.callName;
                    callName = callName.substring(0, callName.length-2);
                    $("#shrName").text(callName);
                    $("#shrTel").text(fdrAddress.phoneNum);
                    $("#shrAddress").text(fdrAddress.campus+fdrAddress.build+fdrAddress.room);
                    $("#shrRoom").text(fdrAddress.build+fdrAddress.room);
                }
            }
        },
        error:function(){

        }
    });
}

//关于收货地址相关操作
function cusAddress() {
    document.getElementById("addressItem").addEventListener('tap', function() {
        baseTag.openWindow({
            url: basePath+"/WeiXin/personal/EnterManageAdd?pageFrom=order",
            id:"312"
        });
    });
}

//关于送达地址的相关操作
var time;
function myArriveTime() {
    var myPicker = new baseTag.PopPicker();
    myPicker.setData(initMyTime());
    document.getElementById("timeItem").addEventListener('tap', function() {
        myPicker.show(function(items) {
            $("#arriveTime").text(items[0].text);
            time=items[0].value;
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
    var startTime=new Date();
    var mytime=new Array();

    //在当前时间的基础上，添加一小时
    var t=startTime.getFullYear()+"/"+(startTime.getMonth()+1)+"/"+startTime.getDate()+" "+(startTime.getHours()+1)+":00:00";
    var temp=new Date();
    temp.setTime(new Date(t).getTime());
    mytime[0]={value:undefined,text:'今日之内'};
    mytime[1]={value:temp,text:formatTime(temp)};

    if(temp.getHours()!=22){
        var i=2;
        while(temp.getHours()<22){
            var tempTime=addMin(temp,30);
            mytime[i]={value:tempTime,text:formatTime(tempTime)};
            temp=tempTime;
            i++;
        }
    }
    return mytime;
}

//用于标识物件的大小
var WEIGHT_SMALL="小件(一人可拿多件)";
var WEIGHT_MIDDLE="中件(一人可拿3-4件)";
var WEIGHT_BIG="大件(一人可拿2件)";
var WEIGHT_BIGPLUS="超大件(一人可拿1件)";
var MIN_MONEY_SMALL=1.5;//小件的最小金额
var MIN_MONEY_MIDDLE=2.5;//中件的最小金额
var MIN_MONEY_BIG=4.5;//大件的最小金额
var MIN_MONEY_BIGPLUS=6.5;//超大件的最小金额
//最小金额
var minMoney=MIN_MONEY_SMALL;
//根据物件大小来修改最小金额
function editMinMoneyByWeight(text){
    text=text.trim();
    if(WEIGHT_SMALL==text){
        $("#weightText").text(WEIGHT_SMALL);
        minMoney=MIN_MONEY_SMALL;
    }else if(WEIGHT_MIDDLE==text){
        $("#weightText").text(WEIGHT_MIDDLE);
        minMoney=MIN_MONEY_MIDDLE;
    }else if(WEIGHT_BIG==text){
        $("#weightText").text(WEIGHT_BIG);
        minMoney=MIN_MONEY_BIG;
    }else if(WEIGHT_BIGPLUS==text){
        $("#weightText").text(WEIGHT_BIGPLUS);
        minMoney=MIN_MONEY_BIGPLUS;
    }
    //修改最小金额
    $(".mui-numbox").attr("data-numbox-min",minMoney);
    //更新最小金额
    $("#money").val(minMoney);
}

//触发佣金的金额
function moneyBlur(){
    $("#money").blur(function(){
        var m=$("#money").val();
        if(m>8.5){
            $("#money").val(8.5);
            return;
        }
        if(m>=parseInt(m)&&m<parseInt(m)+0.5){
            $("#money").val(parseInt(m));
            return;
        }
        if(m>=parseInt(m)+0.5){
            $("#money").val(parseInt(m)+0.5);
            return;
        }
    });
}



//支付订单
function checkSubmit(){
    //alert($("#shrRoom").text());
    var place=$("#palceResult").text();
    var qjh=$("#shrQjh").val();
    var name= $("#shrName").text();//发单人姓名
    var tel= $("#shrTel").text();//发单人电话号码
    var add=$("#shrAddress").text();//发单人地址
    var money=$("#money").val();//订单金额
    var remark=$("#remark").val();//留言
    var size=$('#weightText').text();//物件大小
    if(place==''){
        mui.toast("请选择快递地址");
        return;
    }

    var reg = new RegExp("^[0-9]*$");
    if(qjh==''||qjh.length>8||!reg.test(qjh)){
        mui.toast('取件号应该为小于8位的数字');
        return;
    }

    if(tel==''){
        mui.toast('请选择收件人地址信息');
        return;
    }

    if(size == '请选择'){
        mui.toast('请选择物件大小');
        return;
    }

    if(time==''){
        mui.toast('请选择送达时间');
        return;
    }

    if(money==''){
        mui.toast('请选择金额');
        return;
    }
    if(remark.length>50){
        mui.toast('备注应小于50字');
        return;
    }
    return true;
}

// 提交支付快递订单
function submitOrder(){
    var qjh=$("#shrQjh").val();
    var name= $("#shrName").text();//发单人姓名
    var tel= $("#shrTel").text();//发单人电话号码
    var money=$("#money").val();//订单金额
    var remark=$("#remark").val();//留言
    var add=$("#shrAddress").text();//发单人地址
    $.ajax({
        url:basePath+"/WeiXin/subs/submitOrder.do",
        data:{
            "order.company":companyName,//快递名称，例如圆通快递
            "order.receiver":name,
            "order.acceptAddCode":placeCode,//地址码：学校+校区+ 地点
            "order.takeNum":qjh,
            "order.reservedPhone":tel,
            "order.staId":30,
            "order.acceptAddress":acceptAddress,//取件地址，例如大学生超市
            "firm.askTime":time,
            "firm.address":add,
            "firm.orderMoney":money * 100,
            "firm.remark":remark
        },
        dataType:"json",
        type:"POST",
        async:false,
        beforeSend:function(){
            $("#submitOrderBtn").attr('disabled', true);
            $("#submitOrderBtn").text('校验中...');
        },
        complete:function(){
            $("#submitOrderBtn").removeAttr('disabled');
            $("#submitOrderBtn").text('发单');
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

function initWxConfig(){
    $.ajax({
        url: basePath + '/WeiXin/JSSDK/getJSSDK.do',
        data: {
            mUrl: basePath + '/WeiXin/subs/enterSubmitOrder'
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
    // alert(fee);
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