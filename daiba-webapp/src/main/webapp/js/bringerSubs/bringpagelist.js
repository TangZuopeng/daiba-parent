/**
 * Created by tangzuopeng on 2016/10/31.
 */
var orderState = 1;
//初始化组件
var notFinishOrder = document.getElementById('notFinishOrder');
var finishOrder = document.getElementById('finishOrder');
var cancelOrder = document.getElementById('cancelOrder');

$(function () {
    init();
});

function getSub(){
    $.ajax({
        url: basePath + '/WeiXin/bringer/orderList.do',
        data:{
            orderState: orderState
        },
        cache: false,
        dataType: 'json',
        type: 'POST',
        timeout: 10000,
        success: function(result){

            if(result == null){
                mui.alert("您不是带客，没有您接的单，将进入首页！", function () {
                    mui.back();
                    location.href = basePath + '/WeiXin/main/home';
                });
            }

            insert(result);
        },
        error: function(err){
            mui.toast("获取带单失败！");
        }
    });
}

function insert(result){

    if (result != ''){
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
    if (orderState == 1){
        ul.id = 'notFinishOrderList';
        notFinishOrder.querySelector('.mui-scroll').replaceChild(ul, notFinishOrder.querySelector('.mui-loading'));
        $("#notFinishOrderList").on("tap", "li.mui-media", function (e) {
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

function process(data) {
    mui.openWindow({
        url: basePath + '/WeiXin/bringer/showBringerFirmDetail.do?firmId=' + data.firmId
    });
}

function createNullItemList(type){

    var fragment = document.createDocumentFragment();
    var li = document.createElement('li');
    li.className = 'mui-table-view-cell mui-media';
    li.innerHTML = '亲，您暂时没有符合该条件的订单！';
    fragment.appendChild(li);
    return fragment;

}

function createListItemFragment(data, i ,type){

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
    var name = data.user.name;
    //缩小字数
    name = name.length>5?(name.substring(0,5)+'...'):name;
    html += '<div style="margin-top: 0px"><span>发单人：</span><span class="acceptName">' + name + '</span></div>';
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
    if(orderState == 1){
        html += '<div><span>接单时间：</span><span class="finishTime">' + data.acceptTime.substring(5,data.acceptTime.length-3) + '</span></div></div>';
    } else if(orderState == 2){
        html += '<div><span>完成时间：</span><span class="finishTime">' + data.finishTime.substring(5,data.finishTime.length-3) + '</span></div></div>';
    } else {
        html += '<div><span>取消时间：</span><span class="finishTime">' + data.cancleTime.substring(5,data.cancleTime.length-3) + '</span></div></div>';
    }
    //如果用户申请取消该单
    if(data.isApplyCancel == 1){
        html += '<img style="width: 80px;" class="mui-img" src=' + basePath + '/img/msg.png>';
    }
    li.innerHTML = html;
    $(li).data("data", data);
    fragment.appendChild(li);
    return fragment;

}

function setSliderListener(){
    document.getElementById('slider').addEventListener('slide', function(e) {
        if (e.detail.slideNumber == 0) {
            orderState = 1;
            if (notFinishOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        }else if (e.detail.slideNumber == 1) {
            orderState = 2;
            if (finishOrder.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getSub();
                }, 500);
            }
        } else if (e.detail.slideNumber == 2) {
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

