/**
 * Created by tangzuopeng on 2017/2/18.
 */
var baseTag;
var baseDoc;
var taskTypeNameArray = [undefined,'通用任务','课业辅导','帮买东西','发个红包','帮充网费','快递代寄','我想知道','叫我起床','代打热水','游戏陪练','帮带饭菜'];
var taskType = 0;
//获取地址码
var placeCode = '1001010000';
//初始化地址数据
var campusTemp = '100101';
//当前时间
var now = new Date();
//当前时间+30分钟
var quick = addMinutes(now, 30);

var taskTypeName;
var payType = '已付';

//各种价格
var defaultMoney;//默认价格
var minMoney;//最低价格
var maxMoney;//最高价格
var addMoney;//价差
var goodsMoney = 0;//物品消费金额

//送达时间
var time;
//外卖类型送达时间
var takeOutTime;

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

//初始化发单人快递地址信息  接单人地址信息
function init(){

    //取出上次选择的校区码(存在本地的)
    campusTemp = window.localStorage.getItem("campusCode");
    if(campusTemp == '100101'){
        $('#campus').text("南湖校区");
        placeCode = '1001010000';
    }else if(campusTemp == '100102'){
        $('#campus').text("林园校区");
        placeCode = '1001020000';
    }else if(campusTemp == '100103'){
        $('#campus').text("北湖校区");
        placeCode = '1001030000';
    }else{
        campusTemp = '100101';
        $('#campus').text("南湖校区");
        placeCode = '1001010000';
    }

    $.ajax({
        url:basePath+"/WeiXin/subs/initSubmit.do",
        dataType:"json",
        type:"POST",
        async:false,
        success:function(result){
            if(result.success){
                var place = result.place;
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

function initTaskTypeName() {
    var temp = localStorage.getItem("taskType");
    if (temp == null){
        temp = 0;
    }
    taskType = parseInt(temp);
    taskTypeName = taskTypeNameArray[taskType];
    $("#taskType").text(taskTypeName);
}

function initCampus() {
    var myPicker = new baseTag.PopPicker();
    myPicker.setData([{
        value: '1001010000',
        text: '南湖校区'
    }, {
        value: '1001020000',
        text: '林园校区'
    }, {
        value: '1001030000',
        text: '北湖校区'
    }
    ]);
    document.getElementById("campusItem").addEventListener('tap', function() {
        myPicker.show(function(items) {
            $("#campus").text(items[0].text);
            placeCode = items[0].value;
            //返回 false 可以阻止选择框的关闭
            //return false;
        });
    });
}

//初始化是否现付按钮监听
document.getElementById("mySwitch").addEventListener("toggle",function(event){
    if(event.detail.isActive){
        $('#goodsMoneyInput').show();
        $('#goodsMoney').val(goodsMoney);
        payType = '已付';
    }else{
        $('#goodsMoneyInput').hide();
        $('#goodsMoney').val(0);
        payType = '到付';
    }
});

function initTaskType() {
    var myPicker = new baseTag.PopPicker();
    myPicker.setData([{
        value: '1',
        text: '通用任务'
    }, {
        value: '2',
        text: '课业辅导'
    }, {
        value: '3',
        text: '帮买东西'
    }, {
        value: '4',
        text: '发个红包'
    }, {
        value: '5',
        text: '帮充网费'
    }, {
        value: '6',
        text: '快递代寄'
    }, {
        value: '7',
        text: '我想知道'
    }, {
        value: '8',
        text: '叫我起床'
    }, {
        value: '9',
        text: '代打热水'
    }, {
        value: '10',
        text: '游戏陪练'
    }, {
        value: '11',
        text: '帮带饭菜'
    }
    ]);
    document.getElementById("taskTypeItem").addEventListener('tap', function() {
        myPicker.show(function(items) {
            $("#taskType").text(items[0].text);
            taskType = items[0].value;
            taskTypeName = items[0].text;
            taskOf();
            //返回 false 可以阻止选择框的关闭
            //return false;
        });
    });
}

//根据任务类型调整填写内容
function taskOf() {
    var isActive = document.getElementById("mySwitch").classList.contains("mui-active");
    if(!isActive){
        mui("#mySwitch").switch().toggle();
    }
    if (taskType == 1){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').hide();
        $('#place').show();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').show();
        $('#taskForInput').show();
        $('#taskAdd').attr('placeholder', '请填写执行任务的地点');
        $('#taskFor').attr('placeholder', '请填写任务简述(用户首页可见)');
        $('#remark').attr('placeholder', '请填写任务内容(带客接单后可见)');
        defaultMoney = 20;
        minMoney = 2;
        maxMoney = 100;
        addMoney = 2;
    }else if (taskType == 2){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').hide();
        $('#place').show();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').show();
        $('#taskForInput').show();
        $('#taskAdd').attr('placeholder', '请填写辅导地点');
        $('#taskFor').attr('placeholder', '请填写辅导科目,章节,时长');
        $('#remark').attr('placeholder', '请填写具体辅导内容');
        defaultMoney = 15;
        minMoney = 10;
        maxMoney = 100;
        addMoney = 5;
    }else if (taskType == 3){
        $('#isComePay').show();
        $("#goodsMoneyInput").show();
        $('#conmsg').show();
        $('#place').hide();
        $('#takeOutTaskTime').show();
        $('#taskTime').hide();
        $('#taskContent').hide();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '如一瓶饮料');
        mui('#goodsMoneyBox').numbox().setOption('min',1);
        mui('#goodsMoneyBox').numbox().setOption('step',1);
        mui('#goodsMoneyBox').numbox().setOption('max',100);
        goodsMoney = 5;
        defaultMoney = 2;
        minMoney = 1;
        maxMoney = 10;
        addMoney = 1;
    }else if (taskType == 4){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#taskForInput').show();
        $('#conmsg').hide();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $('#taskTime').hide();
        $('#taskContent').show();
        $('#taskFor').attr('placeholder', '如:XX带客亲收或看看谁的手速快');
        $('#remark').attr('placeholder', '您想对收到红包的ta说');
        defaultMoney = 0.5;
        minMoney = 0.5;
        maxMoney = 10;
        addMoney = 0.5;
    }else if (taskType == 5){
        $('#isComePay').hide();
        $("#goodsMoneyInput").show();
        $('#conmsg').hide();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $("#taskForInput").hide();
        $('#taskTime').show();
        $('#taskContent').show();
        $('#remark').attr('placeholder', '请填写充值账号');
        $("#goodsMoney").val(20);
        mui('#goodsMoneyBox').numbox().setOption('min',20);
        mui('#goodsMoneyBox').numbox().setOption('step',20);
        mui('#goodsMoneyBox').numbox().setOption('max',100);
        goodsMoney=20;
        defaultMoney = 1;
        minMoney = 1;
        maxMoney = 3;
        addMoney = 1;
    }else if (taskType == 6){
        $("#goodsMoneyInput").show();
        $('#isComePay').show();
        $('#conmsg').show();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').hide();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '请填写需要代寄的快递公司');
        mui('#goodsMoneyBox').numbox().setOption('min',10);
        mui('#goodsMoneyBox').numbox().setOption('step',1);
        mui('#goodsMoneyBox').numbox().setOption('max',100);
        goodsMoney = 10;
        defaultMoney = 2;
        minMoney = 2;
        maxMoney = 20;
        addMoney = 1;
    }else if (taskType == 7){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').hide();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').show();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '请填写想知道的校园资讯与八卦');
        $('#remark').attr('placeholder', '请填写具体详细问题');
        defaultMoney = 3;
        minMoney = 3;
        maxMoney = 20;
        addMoney = 2;
    }else if (taskType == 8){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').hide();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').show();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '如时间,方式,要求性别');
        $('#remark').attr('placeholder', '请描述带客叫您起床的详细安排,可不填');
        defaultMoney = 2;
        minMoney = 2;
        maxMoney = 20;
        addMoney = 1;
    }else if (taskType == 9){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').show();
        $('#place').hide();
        $('#takeOutTaskTime').hide();
        $('#taskTime').show();
        $('#taskContent').hide();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '如两个水壶,早晚两次');
        defaultMoney = 1;
        minMoney = 1;
        maxMoney = 20;
        addMoney = 0.5;
    }else if (taskType == 10){
        $('#isComePay').hide();
        $("#goodsMoneyInput").hide();
        $('#conmsg').hide();
        $('#place').hide();
        $('#taskTime').show();
        $('#takeOutTaskTime').hide();
        $('#taskContent').show();
        $('#taskForInput').show();
        $('#taskFor').attr('placeholder', '如汉子or妹纸,一个小时LOL');
        $('#remark').attr('placeholder', '请详细描述游戏陪练的要求');
        defaultMoney = 6;
        minMoney = 6;
        maxMoney = 100;
        addMoney = 5;
    }else if (taskType == 11){
        $('#isComePay').show();
        $("#goodsMoneyInput").show();
        $('#conmsg').show();
        $('#place').show();
        $('#takeOutTaskTime').show();
        $('#taskTime').hide();
        $('#taskContent').hide();
        $('#taskForInput').show();
        $('#taskAdd').attr('placeholder', '请填写带饭地点');
        $('#taskFor').attr('placeholder', '如香辣肉丝');
        mui('#goodsMoneyBox').numbox().setOption('min',1);
        mui('#goodsMoneyBox').numbox().setOption('step',1);
        mui('#goodsMoneyBox').numbox().setOption('max',100);
        goodsMoney = 5;
        defaultMoney = 2;
        minMoney = 1;
        maxMoney = 10;
        addMoney = 1;
    }
    mui('#orderMoney').numbox().setOption('min',minMoney);
    mui('#orderMoney').numbox().setOption('step',addMoney);
    mui('#orderMoney').numbox().setOption('max',maxMoney);
    $("#money").val(defaultMoney);
    $('#goodsMoney').val(goodsMoney);
}

//关于收货地址相关操作
function cusAddress() {
    document.getElementById("addressItem").addEventListener('tap', function() {
        baseTag.openWindow({
            url: basePath+"/WeiXin/personal/EnterManageAdd?pageFrom=order&taskType=" + taskType,
            id:"312"
        });
    });
}

//关于代取外卖类型送达时间的相关操作
function myTakeOutArriveTime() {
    takeOutTime = quick;
    $('#takeOutArriveTime').text(takeOutTime.format("yyyy-MM-dd hh:mm") + '(尽快)');
    var myPicker = new baseTag.PopPicker();
    myPicker.setData(initMyTakeOutTime());
    document.getElementById("takeOutTimeItem").addEventListener('tap', function() {
        myPicker.show(function(items) {

            takeOutTime = items[0].value;
            if (takeOutTime == quick){
                $("#takeOutArriveTime").text(items[0].value.format("yyyy-MM-dd hh:mm") + '(尽快)');
            } else {
                $("#takeOutArriveTime").text(items[0].value.format("yyyy-MM-dd hh:mm"));
            }
            //返回 false 可以阻止选择框的关闭
            //return false;
        });
    });
}

//关于送达时间的相关操作
function myArriveTime() {
    var myPicker = new mui.PopPicker({
        layer: 2
    });
    myPicker.setData(initMyTime());
    document.getElementById("timeItem").addEventListener('tap', function() {
        myPicker.show(function(items) {
            $("#arriveTime").text("" + (items[0] || {}).text + " " + (items[1] || {}).text);
            time=items[1].value;
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
//触发佣金的金额
function moneyBlur(){
    $("#money").blur(function(){
        var m=$("#money").val();
        if(m > maxMoney){
            $("#money").val(maxMoney);
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

//初始化外卖类型时间
function initMyTakeOutTime(){
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
            var tempTime=addMin(temp,30);
            mytime[i]={value:tempTime,text:formatTime(tempTime)};
            temp=tempTime;
            i++;
        }
    }
    return mytime;

}

//初始化时间
function initMyTime(){
    var startTime=new Date();
    //日期数组
    var mydate=new Array();
    for(var i=0; i<3; i++){
        if (i == 0){
            //时间数组
            var mytime=new Array();
            var j = 1;
            var d=startTime.getFullYear()+"/"+(startTime.getMonth()+1)+"/"+(startTime.getDate()+i)+" "+(startTime.getHours()+1)+":00:00";
            var temp = new Date();
            temp.setTime(new Date(d).getTime());
            mytime[0]={value:temp,text:formatTime(temp)};
            while(temp.getHours()<22){
                var tempTime=addMin(temp,15);
                mytime[j]={value:tempTime,text:formatTime(tempTime)};
                temp=tempTime;
                j++;
            }
            var weekArray = "日一二三四五六";
            var week = weekArray[new Date().getDay()];
            mydate[i]={value:null, text:'今天(周' + week + ')', children: mytime};
        }else if (i == 1){
            //时间数组
            var mytime=new Array();
            var j = 1;
            var d=startTime.getFullYear()+"/"+(startTime.getMonth()+1)+"/"+(startTime.getDate()+i)+" "+"07:00:00";
            var temp = new Date();
            temp.setTime(new Date(d).getTime());
            mytime[0]={value:temp,text:formatTime(temp)};
            while(temp.getHours()<22){
                var tempTime=addMin(temp,15);
                mytime[j]={value:tempTime,text:formatTime(tempTime)};
                temp=tempTime;
                j++;
            }
            var weekArray = "日一二三四五六";
            var week = weekArray[(new Date().getDay() + 1)%7];
            mydate[i]={value:null, text:'明天(周' + week + ')', children: mytime};
        }else {
            //时间数组
            var mytime=new Array();
            var j = 1;
            var d=startTime.getFullYear()+"/"+(startTime.getMonth()+1)+"/"+(startTime.getDate()+i)+" "+"07:00:00";
            var temp = new Date();
            temp.setTime(new Date(d).getTime());
            mytime[0]={value:temp,text:formatTime(temp)};
            while(temp.getHours()<22){
                var tempTime=addMin(temp,15);
                mytime[j]={value:tempTime,text:formatTime(tempTime)};
                temp=tempTime;
                j++;
            }
            var weekArray = "日一二三四五六";
            var week = weekArray[(new Date().getDay() + 2)%7];
            mydate[i]={value:null, text:'后天(周' + week + ')', children: mytime};
        }
    }

    return mydate;

}

//支付订单
function checkSubmit(){
    var name= $("#shrName").text();//发单人姓名
    var tel= $("#shrTel").text();//发单人电话号码
    var add=$("#shrAddress").text();//发单人地址
    var remark=$("#taskFor").val();//简介
    var place=$("#taskAdd").val();//任务地点
    var money=$("#money").val();//订单金额
    var goodsMoney=$("#goodsMoney").val();//网费金额
    var require=$("#remark").val();//详细要求
    if (taskType == 0){
        mui.toast('请选择任务类型');
        return;
    }else if (taskType == 1 || taskType == 2){
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        // if (place == ''){
        //     mui.toast('请填写任务地点');
        //     return;
        // }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        // if (require == ''){
        //     mui.toast('请填写任务内容');
        //     return;
        // }
        // if (time == null){
        //     mui.toast('请选择任务时间');
        //     return;
        // }
    }else if (taskType == 3){
        if (tel == ''){
            mui.toast('请选择收货人地址信息');
            return;
        }
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
    }else if (taskType == 4){
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        // if (require == ''){
        //     mui.toast('请填写任务内容');
        //     return;
        // }
    } else if (taskType == 5){
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        if (goodsMoney == ''){
            mui.toast("请选择网费金额");
            return;
        }
        // if (time == null){
        //     mui.toast('请选择任务时间');
        //     return;
        // }
        if (require == ''){
            mui.toast('请填写任务内容');
            return;
        }
    } else if (taskType == 6){
        if (tel == ''){
            mui.toast('请选择寄件人地址信息');
            return;
        }
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        // if (time == null){
        //     mui.toast('请选择任务时间');
        //     return;
        // }
    }else if (taskType == 7 || taskType == 8 || taskType == 10){
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        if (require == ''){
            mui.toast('请填写任务内容');
            return;
        }
        // if (time == null){
        //     mui.toast('请选择任务时间');
        //     return;
        // }
    }else if (taskType == 9){
        if (tel == ''){
            mui.toast('请选择发单人地址信息');
            return;
        }
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        // if (time == null){
        //     mui.toast('请选择任务时间');
        //     return;
        // }
    }else if (taskType == 11){
        if (tel == ''){
            mui.toast('请选择收货人地址信息');
            return;
        }
        if (remark == ''){
            mui.toast('请填写任务简介');
            return;
        }
        if (money == ''){
            mui.toast('请选择任务金额');
            return;
        }
        if (place == ''){
            mui.toast('请填写带饭地点');
            return;
        }
    }

    if (placeCode == null){
        mui.toast('请选择任务校区');
        return;
    }

    if (remark.length > 30){
        mui.toast('简介应小于30字');
        return;
    }
    if (require.length > 45){
        mui.toast('内容应小于45字');
        return;
    }
    if (place.length > 20){
        mui.toast('任务地址应小于20字');
        return;
    }
    return true;
}

// 提交支付订单
function submitOrder(){
    var name= $("#shrName").text();//发单人姓名
    var tel= $("#shrTel").text();//发单人电话号码
    var add=$("#shrAddress").text();//发单人地址
    var remark=$("#taskFor").val();//简介
    var place=$("#taskAdd").val();//任务地点
    var money=$("#money").val();//订单金额
    var goodsMoney=$("#goodsMoney").val();//网费金额
    var require=$("#remark").val();//详细要求
    if (taskType == 1 || taskType == 2){
        goodsMoney = 0;
        name = undefined;tel = undefined;add = undefined;
    }else if (taskType == 3){
        taskTypeName = taskTypeName + '(' + payType + ')';
        time = takeOutTime;
        place = undefined;
    }else if (taskType == 4){
        goodsMoney = 0;
        name = undefined;tel = undefined;add = undefined;
        place = undefined;
        time = undefined;
    }else if (taskType == 5){
        remark = goodsMoney + "元";
        name = undefined;tel = undefined;add = undefined;
        place = undefined;
    }else if (taskType == 6){
        taskTypeName = taskTypeName + '(' + payType + ')';
        place = undefined;require = undefined;
    }else if (taskType == 7 || taskType == 8 || taskType == 10){
        goodsMoney = 0;
        name = undefined;tel = undefined;add = undefined;
        place = undefined;
    }else if (taskType == 9){
        goodsMoney = 0;
        place = undefined;
        require = undefined;
    }else if (taskType == 11){
        taskTypeName = taskTypeName + '(' + payType + ')';
        require = undefined;
    }

    $.ajax({
        url:basePath+"/WeiXin/subs/submitOrder.do",
        data:{
            "order.company":taskTypeName,//任务类型名称，例如圆通快递
            "order.receiver":name,
            "order.acceptAddCode":placeCode,//地址码：学校+校区+ 地点
            "order.takeNum":undefined,
            "order.reservedPhone":tel,
            "order.staId":32,
            "order.acceptAddress":place,//取件地址，例如大学生超市
            "order.content":require,
            "firm.askTime":time,
            "firm.address":add,
            "firm.orderMoney":money * 100,
            "firm.goodsMoney":goodsMoney * 100,
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
                taskTypeName = $("#taskType").text();
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

(function($, doc) {
    baseTag = $;
    baseDoc = doc;
    init();
    initTaskTypeName();
    taskOf();
    initWxConfig();//初始化微信提供的配置信息
    initTaskType();
    initCampus();
    myArriveTime();
    myTakeOutArriveTime();
    moneyBlur();
    cusAddress();
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
    //时间初始化
})(mui, document);



