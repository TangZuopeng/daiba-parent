/**
 * Created by Tangzuopeng on 2016/10/17.
 */

var count = 0;
var campus = 100101;
var orderType = 0;
var timer = 500;
//初始化组件
var allOrder = document.getElementById('allOrder');
var expressOrder = document.getElementById('expressOrder');
var takeoutOrder = document.getElementById('takeoutOrder');
var otherOrder = document.getElementById('otherOrder');

function subtractData(date1, date2) {
    var d1 = new Date(Date.parse(date1));
    var d2 = new Date(Date.parse(date2));
    var difference = (d1 - d2)/60000;
    return difference;
}

//初始化页面，并初始化上拉加载与下拉刷新
$(function () {
    //初始化页面
    init();
    //初始化上拉加载与下拉刷新
    setRefresh();
    // document.getElementById('headRight').setAttribute('href', '#topPopover');
});

//建立下拉刷新与上拉加载
function setRefresh() {
    mui.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
        mui(pullRefreshEl).pullToRefresh({
            down: {
                callback: function() {
                    var self = this;
                    setTimeout(function() {
                        clearAllListAndReset();
                        setRefresh();
                        initOrRefreshOrder(self, false);
                    }, 1000);
                }
            }
        });
    });
}

function setSliderListener(){
    document.getElementById('slider').addEventListener('slide', function(e) {
        if (e.detail.slideNumber === 0) {
            orderType = 0;
            if (allOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    initOrRefreshOrder(null, true);
                }, 500);
            }
        } else if (e.detail.slideNumber === 1) {
            orderType = 30;
            if (expressOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    initOrRefreshOrder(null, true);
                }, 500);
            }
        } else if (e.detail.slideNumber === 2) {
            orderType = 31;
            if (takeoutOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    initOrRefreshOrder(null, true);
                }, 500);
            }
        }else if (e.detail.slideNumber === 3) {
            orderType = 32;
            if (otherOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    initOrRefreshOrder(null, true);
                }, 500);
            }
        }
    });
}

//初始化校区选择框
function initSelect(){

    var userPicker = new mui.PopPicker({
        layer: 1
    });
    userPicker.setData([{
        value: '100101',
        text: '南湖校区'
    }, {
        value: '100102',
        text: '林园校区'
    }, {
        value: '100103',
        text: '北湖校区'
    }
    ]);

    var showUserPickerButton = document.getElementById('showCampus');

    showUserPickerButton.addEventListener('tap', function(event) {
        userPicker.show(function(items) {
            if (items[0].text != null){
                showUserPickerButton.innerText = items[0].text;
                $("#showCampusValue").text(items[0].value);
                campus = $("#showCampusValue").text();
            }else {
                showUserPickerButton.innerText = '南湖校区';
                $("#showCampusValue").text('100101');
                campus = $("#showCampusValue").text();
            }
            clearAllListAndReset();
            setRefresh();
            initOrRefreshOrder(null, true);
            //返回 false 可以阻止选择框的关闭
            //return false;

            //***********************存储你选择的校区
            window.localStorage.setItem("campusCode",items[0].value);
        });
    }, false);
}

//初始化页面
function init() {
    $('.mui-title').html('<span id="showCampus">南湖校区</span><span id="showCampusValue" hidden>11</span><a class="mui-icon mui-icon-arrowdown" style="font-size: 20px;"></a>');
    //取出上次选择的校区码(存在本地的)
    campus = window.localStorage.getItem("campusCode");
    $('#showCampusValue').text(campus);
    if(campus == '100101'){
        $('#showCampus').text("南湖校区");
    }else if(campus == '100102'){
        $('#showCampus').text("林园校区");
    }else if(campus == '100103'){
        $('#showCampus').text("北湖校区");
    }else{
        campus = '100101';
        $('#showCampus').text("南湖校区");
    }
    //获取订单初始化校区选择框选择
    initOrRefreshOrder(null, true);
    initSelect();
    setSliderListener();
}

//清空所有订单列表
function clearAllListAndReset() {
    allOrder.innerHTML = '<div id="scroll1" class="mui-content mui-scroll-wrapper"><div class="mui-scroll"><div class="mui-loading"><div class="mui-spinner"></div></div></div></div>';
    expressOrder.innerHTML = '<div id="scroll2" class="mui-content mui-scroll-wrapper"><div class="mui-scroll"><div class="mui-loading"><div class="mui-spinner"></div></div></div></div>';
    takeoutOrder.innerHTML = '<div id="scroll3" class="mui-content mui-scroll-wrapper"><div class="mui-scroll"><div class="mui-loading"><div class="mui-spinner"></div></div></div></div>';
    otherOrder.innerHTML = '<div id="scroll4" class="mui-content mui-scroll-wrapper"><div class="mui-scroll"><div class="mui-loading"><div class="mui-spinner"></div></div></div></div>';
}

//初始化首页订单 AND 得到第一批数据
function initOrRefreshOrder(pullRefresh,isInit) {
    //下拉刷新时先将数量清零
    count = 0;
    $.ajax({
        url: basePath + '/WeiXin/main/getFirm.do',
        data: {
            campus: campus,
            count: count,
            orderType: orderType
        },
        dataType: 'json',
        type: "POST",
        success: function (result) {
            initOrderOrRefreshInsert(result);
            count = result.length;
            if (!isInit){
                pullRefresh.endPullDownToRefresh();
            }
        },
        error: function (err) {
            mui.toast("订单拉取失败！");
        }
    });
    mui('.mui-scroll-wrapper').scroll({
        indicators: false //是否显示滚动条
    });
}

//得到更多数据
function getMoreOrder() {
    $.ajax({
        url: basePath + '/WeiXin/main/getFirm.do',
        data: {
            campus: campus,
            count: count,
            orderType: orderType
        },
        dataType: 'json',
        type: "POST",
        success: function (result) {
            getMoreInsert(result);
            count = count + result.length;
        },
        error: function (err) {
            mui.toast("订单拉取失败！");
        }
    });
}

function initOrderOrRefreshInsert(result) {

    //创建点击加载更多按钮
    var getMoreDiv = document.createElement('div');
    getMoreDiv.className = 'js-load-more';
    getMoreDiv.innerHTML = '点击加载更多';
    //为点击加载更多按钮添加点击事件
    getMoreDiv.addEventListener('tap', function () {
        getMoreOrder();
    });
    if (result != '') {
        var ul = document.createElement('ul');
        ul.className = 'mui-table-view';
        $.each(result, function (i, data) {
            ul.appendChild(createListItemFragment(data, i));
        });
    } else {
        var ul = document.createElement('ul');
        ul.className = 'mui-table-view';
        if (count == 0) {
            ul.appendChild(createNullItemList());
        }
    }
    if (orderType == 0){
        ul.id = 'allOrderList';
        allOrder.querySelector('.mui-scroll').replaceChild(ul, allOrder.querySelector('.mui-loading'));
        $("#allOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else if (orderType == 30){
        ul.id = 'expressOrderList';
        expressOrder.querySelector('.mui-scroll').replaceChild(ul, expressOrder.querySelector('.mui-loading'));
        $("#expressOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else if (orderType == 31){
        ul.id = 'takeoutOrderList';
        takeoutOrder.querySelector('.mui-scroll').replaceChild(ul, takeoutOrder.querySelector('.mui-loading'));
        $("#takeoutOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else {
        ul.id = 'otherOrderList';
        otherOrder.querySelector('.mui-scroll').replaceChild(ul, otherOrder.querySelector('.mui-loading'));
        $("#otherOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }
    if (result.length >= 7){
        if (orderType == 0){
            getMoreDiv.id = 'allOrderMore';
            allOrder.querySelector('.mui-scroll').appendChild(getMoreDiv);
            $('#allOrderMore').show();
        }else if (orderType == 30){
            getMoreDiv.id = 'expressOrderMore';
            expressOrder.querySelector('.mui-scroll').appendChild(getMoreDiv);
            $('#expressOrderMore').show();
        }else if (orderType == 31){
            getMoreDiv.id = 'takeoutOrderMore';
            takeoutOrder.querySelector('.mui-scroll').appendChild(getMoreDiv);
            $('#takeoutOrderMore').show();
        }else {
            getMoreDiv.id = 'otherOrderMore';
            otherOrder.querySelector('.mui-scroll').appendChild(getMoreDiv);
            $('#otherOrderMore').show();
        }
    }

}

function getMoreInsert(result) {
    if (orderType == 0){
        $.each(result, function (i, data) {
            document.getElementById('allOrderList').appendChild(createListItemFragment(data, i));
        });
        if (result.length < 5){
            $('#allOrderMore').html('我也是有底线的！');
            $('#allOrderMore').attr("class", "js-load-nomore");
            $('#allOrderMore').attr("disabled", true);
        }
    }else if (orderType == 30){
        $.each(result, function (i, data) {
            document.getElementById('expressOrderList').appendChild(createListItemFragment(data, i));
        });
        if (result.length < 5){
            $('#expressOrderMore').html('我也是有底线的！');
            $('#expressOrderMore').attr("class", "js-load-nomore");
            $('#expressOrderMore').attr("disabled", true);
        }
    }else if (orderType == 31){
        $.each(result, function (i, data) {
            document.getElementById('takeoutOrderList').appendChild(createListItemFragment(data, i));
        });
        if (result.length < 5){
            $('#takeoutOrderMore').html('我也是有底线的！');
            $('#takeoutOrderMore').attr("class", "js-load-nomore");
            $('#takeoutOrderMore').attr("disabled", true);
        }
    }else {
        $.each(result, function (i, data) {
            document.getElementById('otherOrderList').appendChild(createListItemFragment(data, i));
        });
        if (result.length < 5){
            $('#otherOrderMore').html('我也是有底线的！');
            $('#otherOrderMore').attr("class", "js-load-nomore");
            $('#otherOrderMore').attr("disabled", true);
        }
    }
}

//列表点击事件响应
function process(data){

    if (role == '带客') {
        var campus = $('#showCampus').text();
        campus = campus.substring(0, campus.length-2);
        if (campus != reallyCampus){
            if (reallyCampus != '工大'){
                mui.toast("少侠，目前还不支持跨校区接单!");
                return;
            }
        }
        if (data.orderState == 1 || data.orderState == 2) {
            mui.toast("该单已被抢，下次下手要快哦！");
            return;
        }
        if (data.user.userId == use_id) {
            mui.toast("少侠，不可以接自己的单哦，也给别人个机会嘛！");
        } else {
            var btnArray = ['取消', '确认'];
            var acceptAddress = data.order.acceptAddress;
            if (acceptAddress == null || acceptAddress == ''){
                acceptAddress = '  ';
            }
            var address = data.address;
            if (address == null || address == ''){
                address = '';
            }
            mui.confirm("地址:" + acceptAddress + ' -> ' + address, '确认接单？', btnArray, function (e) {
                if (e.index == 1) {
                    clearTimeout(timer);
                    timer = setTimeout(function () {
                        mui.openWindow({
                            url: basePath + '/WeiXin/subs/accpetFirms.do?firmId=' + data.firmId + '&bringerId=' + bir_id + '&acceptAddCode=' + data.order.acceptAddCode
                        });
                    },500);
                }
            });
        }
    } else if (role == '普通用户') {
        mui.toast("您还不是带客，申请成带客才可以接单赚钱哦！");
    } else if (role == 'null') {
        mui.alert("如需接单，请先登录！", function () {
            redirect('/login');
        });
    } else {
        mui.toast("您不是用户、也不是带客、那您是什么呢？");
    }

}

//无订单插入
function createNullItemList() {

    var fragment = document.createDocumentFragment();
    var li = document.createElement('li');
    li.className = 'mui-table-view-cell mui-media';
    li.innerHTML = '单已经被抢完了，小带提示您，下手要快！';
    fragment.appendChild(li);
    return fragment;

}

function createListItemFragment(data, i) {

    var fragment = document.createDocumentFragment();
    //创建列表节点
    var li = document.createElement('li');
    li.className = 'mui-table-view-cell mui-media';
    //创建订单类型图片
    var orderTypeImg = document.createElement('img');
    orderTypeImg.className = 'mui-media-object mui-pull-left orderType-img';
    orderTypeImg.style = 'width:50px';
    if (data.order.staId == 30){
        orderTypeImg.src = basePath + '/img/express.png';
    }else if (data.order.staId == 31){
        orderTypeImg.src = basePath + '/img/takeout.png';
    }else {
        orderTypeImg.src = basePath + '/img/other.png';
    }
    li.appendChild(orderTypeImg);
    //创建超链接
    var a = document.createElement('a');
    //创建头像图片
    var img = document.createElement('img');
    img.className = 'mui-media-object mui-pull-left portrait';
    //basePath + '/img/daiba.png'  data.user.portrait
    img.src = data.user.portrait;
    a.appendChild(img);
    var div1 = document.createElement('div');
    div1.className = 'mui-media-body';
    var p1 = document.createElement('p');
    var p2 = document.createElement('p');
    var p3 = document.createElement('p');
    var p4 = document.createElement('p');
    p1.className = 'font-name';
    if (data.user.sex == '2'){
        p1.innerHTML = '<span class="sex-girl">♀</span>' + data.user.name;
    }else {
        p1.innerHTML = '<span class="sex-boy">♂</span>' + data.user.name;
    }
    //若未设置要求送达时间，默认为今日之内
    var giveTime = data.giveTime;
    var askTime = data.askTime;
    if (askTime == null) {
        askTime = '今日之内';
    } else {
        var difference = subtractData(askTime, giveTime);
        if (difference <= 30){
            askTime = askTime.substring(5, data.askTime.length - 3) + '(尽快)';
        }else {
            askTime = data.askTime.substring(5, data.askTime.length - 3);
        }
    }
    p2.innerHTML = '要求时间：<span class="font-time">' + askTime + '</span>';
    var acceptAdd = data.order.acceptAddress;
    if (acceptAdd == '' || null == acceptAdd){
        acceptAdd = '';
    }
    var add = data.address;
    if (add == '' || null == add){
        add = '';
    }
    add = add.substring(4, add.length);
    p3.innerHTML = '地点：<span class="font-address">' + acceptAdd + '</span>&nbsp->&nbsp<span class="font-address">' + add + '</span>';
    p4.className = 'mui-ellipsis';
    var remark;
    if (data.order.staId == 32){
        remark = '<b class="font-add">' + data.order.company + '：' + '</b>';
    } else {
        remark = '';
    }
    if (data.remark == "" || data.remark == null) {
        remark += "无备注信息";
    } else {
        remark += data.remark;
    }
    p4.innerHTML = remark;
    div1.appendChild(p1);
    div1.appendChild(p2);
    div1.appendChild(p3);
    div1.appendChild(p4);
    a.appendChild(img);
    a.appendChild(div1);
    var div2 = document.createElement('div');
    div2.className = 'money-style';
    var html;
    /*if (data.order.staId == 33){
        html = '<span style="float: right;margin-right: 6px;"><span class="mui-icon iconfont icon-money" ></span><span style="font-size: 12px;">' + data.orderMoney / 100 + '元</span></span>';
    }else {
        html = '<span style="float: right;margin-right: 6px;"><span class="mui-icon iconfont icon-money" ></span><span style="font-size: 12px;">' + (data.orderMoney + data.goodsMoney) / 100 + '元</span></span>';
    }*/
    html = '<span style="float: right;margin-right: 6px;"><span class="mui-icon iconfont icon-money" ></span><span style="font-size: 12px;">' + (data.orderMoney + data.goodsMoney) / 100 + '元</span></span>';
    if (data.orderState == 1) {
        html += '<img style="width: 80px;" class="mui-img" src=' + basePath + '/img/take.png>';
    } else if (data.orderState == 2) {
        html += '<img style="width: 80px;" class="mui-img" src=' + basePath + '/img/finish.png>';
    }
    html += '<span style="float:right;font-size: 10px;margin-top: 6px">' + data.giveTime.substring(5, data.giveTime.length - 3) + '</span>';
    div2.innerHTML = html;
    li.appendChild(a);
    li.appendChild(div2);
    $(li).data("data", data);
    fragment.appendChild(li);
    return fragment;

}
