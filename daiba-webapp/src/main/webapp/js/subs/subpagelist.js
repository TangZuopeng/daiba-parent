/**
 * Created by tangzuopeng on 2016/10/25.
 */
var orderState = 0;
//初始化组件
var notAcceptOrder = document.getElementById('notAcceptOrder');
var acceptOrder = document.getElementById('acceptOrder');
var cancelOrder = document.getElementById('cancelOrder');
var finishOrder = document.getElementById('finishOrder');

function removeBracket(str) {
    var nstr = str.replace(/\([^\)]*\)/g,"");
    return nstr;
}

$(function () {
    init();
});

function getSub(){
    if(use_id == 0){
        mui.toast("您尚未登录，请登录！");
        return;
    }
    $.ajax({
        url: basePath + '/WeiXin/main/orderList.do',
        data:{
            useId: use_id,
            orderState: orderState
        },
        cache: false,
        dataType: 'json',
        type: 'POST',
        timeout: 10000,
        success: function(result){
            insert(result);
        },
        error: function(err){
            mui.alert("网络错误，请重试！", function () {
                mui.back();
            });
        }
    });
}

function insert(result){

    if (result != '') {
        var ul = document.createElement('ul');
        ul.className = 'mui-table-view';
        $.each(result, function (i, data) {
            ul.appendChild(createListItemFragment(data, i));
        });
    }else {
        var ul = document.createElement('ul');
        ul.className = 'mui-table-view';
        if (result.length == 0) {
            ul.appendChild(createNullItemList());
        }
    }
    if (orderState == 0){
        ul.id = 'notAcceptOrderList';
        notAcceptOrder.querySelector('.mui-scroll').replaceChild(ul, notAcceptOrder.querySelector('.mui-loading'));
        $("#notAcceptOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else if (orderState == 1){
        ul.id = 'acceptOrderList';
        acceptOrder.querySelector('.mui-scroll').replaceChild(ul, acceptOrder.querySelector('.mui-loading'));
        $("#acceptOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else if (orderState == 2){
        ul.id = 'finishOrderList';
        finishOrder.querySelector('.mui-scroll').replaceChild(ul, finishOrder.querySelector('.mui-loading'));
        $("#finishOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }else {
        ul.id = 'cancelOrderList';
        cancelOrder.querySelector('.mui-scroll').replaceChild(ul, cancelOrder.querySelector('.mui-loading'));
        $("#cancelOrderList").on("tap", "li.mui-media", function (e) {
            var data = $(e.currentTarget).data("data");
            process(data);
        });
    }
}

//无订单插入
function createNullItemList(){

    var fragment = document.createDocumentFragment();
    var li = document.createElement('li');
    li.className = 'mui-table-view-cell mui-media';
    li.innerHTML = '亲，您暂时没有符合该条件的订单！';
    fragment.appendChild(li);
    return fragment;

}

function process(data){
    mui.openWindow({
        url: basePath + '/WeiXin/subs/orderInfo?firmId=' + data.firmId + '&acceptAddCode=' + data.order.acceptAddCode
    });
}

function createListItemFragment(data, i){

    var fragment = document.createDocumentFragment();
    var li = document.createElement('li');
    li.className = 'mui-table-view-cell mui-media';
    //创建订单类型图片
    var orderTypeImg = document.createElement('img');
    orderTypeImg.className = 'mui-media-object mui-pull-left orderType-img';
    orderTypeImg.style = 'width:50px';
    var path = '';
    if (data.order.staId == 30){
        path += basePath + '/img/express.png';
    }else if (data.order.staId == 31){
        path += basePath + '/img/takeout.png';
    }else {
        path += basePath + '/img/other.png';
    }
    var html = '<div><img style="width:50px" class="mui-media-object mui-pull-left orderType-img" src='+ path + '>';
     var company = data.order.company;
    if (data.order.staId == 32){
        company = removeBracket(company);
        html += '<div style="margin-top: 0px"><span>任务类型：</span><span class="acceptName">' + company + '</span></div>';
    }else {
        html += '<div style="margin-top: 0px"><span>收件人：</span><span class="acceptName">' + data.order.receiver + '</span></div>';
    }
    var address = data.address;
    var acceptAdd = data.order.acceptAddress;
    if (address != null){
        address = address.substring(4, address.length);
        html += '<div style="margin-top: 8px"><span class="acceptAdd">' + address + '</span></div></div>';
    }else if (acceptAdd != null){
        html += '<div style="margin-top: 8px"><span class="acceptAdd">' + acceptAdd + '</span></div></div>';
    }else {
        html += '<div style="margin-top: 8px"><span class="acceptAdd">' + '</span></div></div>';
    }
    html += '<div><span class="span-right mui-icon mui-icon-arrowright"></span></div>';
    var reservedPhone = data.order.reservedPhone;
    if (reservedPhone == '' || null == reservedPhone){
        reservedPhone = '无';
    }
    html += '<div><div><span>手机号码：</span><span class="acceptPhoneNum">' + reservedPhone + '</span></div>';
    //根据订单状态显示不同时间
    if(orderState == 0){
        html += '<div><span>发单时间：</span><span class="finishTime">' + data.giveTime.substring(5,data.giveTime.length-3) + '</span></div></div>';
    } else if(orderState == 1){
        html += '<div><span>接单时间：</span><span class="finishTime">' + data.acceptTime.substring(5,data.acceptTime.length-3) + '</span></div></div>';
    } else if(orderState == 2){
        html += '<div><span>完成时间：</span><span class="finishTime">' + data.finishTime.substring(5,data.finishTime.length-3) + '</span></div></div>';
    } else {
        html += '<div><span>取消时间：</span><span class="finishTime">' + data.cancleTime.substring(5,data.cancleTime.length-3) + '</span></div></div>';
    }
    li.innerHTML = html;
    $(li).data("data", data);
    fragment.appendChild(li);
    return fragment;

}

function setSliderListener(){
    document.getElementById('slider').addEventListener('slide', function(e) {
        if (e.detail.slideNumber === 0){
            orderState = 0;
            if (notAcceptOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        }else if (e.detail.slideNumber === 1) {
            orderState = 1;
            if (acceptOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        } else if (e.detail.slideNumber === 2) {
            orderState = 2;
            if (finishOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        }else if (e.detail.slideNumber === 3) {
            orderState = 3;
            if (cancelOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        }
    });
}

function init(){
    setSliderListener();
    getSub();
}

