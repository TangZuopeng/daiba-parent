/**
 * Created by Tangzuppeng on 2016/10/23.
 */
var timer = 300;
document.getElementById("cancel").addEventListener('tap', function () {
    var btnArray = ['取消', '确认'];
    mui.confirm('取消前务必和用户联系，沟通一致！', '同意取消订单？', btnArray, function (e) {
        if (e.index == 1) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                goCancel();
            },300);
        }
    });
});

document.getElementById("confirm").addEventListener('tap', function () {
    var btnArray = ['取消', '确认'];
    mui.confirm('请您确认已将物品送达主人手中', '确认送达？', btnArray, function (e) {
        if (e.index == 1) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                goConfirm();
            },300);
        }
    });
});

function goConfirm() {
    $.ajax({
        url: basePath + "/WeiXin/bringer/confirmAcceptFirm.do",
        data: {
            firmId: firm.firmId
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result.confirmMessage == 1) {
                mui.alert("订单已完成，返回订单首页", function () {
                    toOrder();
                });
            } else if (result.confirmMessage == 2) {
                mui.toast('确认送达失败，该订单状态不是已接单状态');
            } else if (result.confirmMessage == 3) {
                mui.toast('确认送达失败，该订单不存在，可能被删除');
            } else if (result.confirmMessage == 4) {
                mui.toast('确认送达失败，您可能不是这个订单的主人！');
            }
        },
        error: function () {
            mui.toast('确认送达失败，请检查网络是否正常');
        }
    });
}

function toOrder() {
    location.href = basePath + '/WeiXin/bringer/bringerOrder';
    return;
}

//退款成功后，取消订单
function cancelOrder() {
    // alert("取消订单");
    $.ajax({
        url: basePath + "/WeiXin/bringer/cancelFirm.do",
        data: {
            firmId: firm.firmId
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result.cancelMessage == 1) {
                mui.alert("取消订单成功，返回我接的单", function () {
                    toBringerOrder();
                });
            } else if (result.cancelMessage == 2) {
                mui.toast('取消订单失败，该订单不是可取消状态');
            } else if (result.cancelMessage == 3) {
                mui.toast('取消订单失败，该订单不存在，可能被删除');
            } else if (result.cancelMessage == 4) {
                mui.toast('权限异常！');
            }
        },
        error: function () {
            mui.toast('取消订单失败，请检查网络是否正常');
        }
    });
}

//带客同意取消订单后的退款操作
function goCancel() {
    $.ajax({
        url: basePath + "/WeiXin/pay/cancelOrder.do",
        data: {
            out_trade_no: firm.firmId,
            total_fee: firm.orderMoney + firm.goodsMoney
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result) {
                cancelOrder();
            } else {
                mui.toast('退款失败，请检查网络是否正常');
            }
        },
        error: function () {
            mui.toast('退款失败，请检查网络是否正常');
        }
    });
}

function toBringerOrder() {
    location.href = basePath + '/WeiXin/bringer/bringerOrder';
    return;
}

function createListItemFragment(data, i) {
    var fragment = document.createDocumentFragment();
    var dish = document.createElement('div');
    dish.className = 'mui-table-view';
    var div = document.createElement('div');
    div.className = 'mui-table-view-cell';
    var goodName = document.createElement('span');
    goodName.className = 'goods-name';
    var goodMoney = document.createElement('span');
    goodMoney.className = 'goods-money';
    var goodNum = document.createElement('span');
    goodNum.className = 'goods-num';
    var goodPlace = document.createElement('span');
    goodPlace.className = 'goods-place';
    goodName.innerHTML = data.name;
    goodMoney.innerHTML = '￥'+data.price/100;
    goodNum.innerHTML = '×' + data.count;
    goodPlace.innerHTML = data.merchant_add.substring(2) + data.merchant_name;
    div.appendChild(goodName);
    div.appendChild(goodMoney);
    div.appendChild(goodPlace);
    div.appendChild(goodNum);
    dish.appendChild(div);
    fragment.appendChild(dish);
    return fragment;
}

function insertCartsList(cartsList) {
    var orderList = document.getElementById('orderList');
    var contents = document.getElementById('contents');
    $.each(cartsList, function (i, data) {
        contents.insertBefore(createListItemFragment(data, i), orderList);
    });
}

function init(){

    if(firm == null || sendUser == null){
        mui.toast("信息获取失败，请重试！");
        return;
    }
    var name = sendUser.name;
    if (name.length >= 5){
        name = name.substring(0,3) + '...';
    }
    if (firm.order.staId == 30){
        $('#takeNumItem').show();
        $('#takeNum').text(firm.order.takeNum);
        $('#taskPlaceSpan').text('取件地点：');
        $('#taskPlace').text(firm.order.acceptAddress + firm.order.company);
        $('#content').text(firm.remark);
    } else if (firm.order.staId == 31){
        $('#taskPlaceItem').hide();
        $('#taskForItem').show();
        $('#taskForSpan').text('备注：');
        var remark = firm.remark;
        if (remark == null || remark ==''){
            remark = '无';
        }
        $('#taskFor').text(remark);
        $('#contentItem').hide();
        insertCartsList(cartsList);
    } else if (firm.order.staId == 32){
        $('#taskTypeItem').show();
        $('#taskType').text(firm.order.company);
        $('#taskForItem').show();
        $('#taskFor').text(firm.remark);
        $('#contentSpan').text('任务内容：');
        if (firm.order.acceptAddress == '' || null == firm.order.acceptAddress){
            $('#taskPlaceItem').hide();
        }else {
            $('#taskPlace').text(firm.order.acceptAddress);
        }
        var content = firm.order.content;
        if (content == '' || null == content){
            content = '无';
        }
        $('#content').text(content);
    }
    if (firm.orderState == 1){
        if(firm.isApplyCancel == 1){
            $('#one')[0].style.visibility = '';
        }
        $('#order_status').text('未完成');
        $('#two')[0].style.visibility = '';
        $('#giverDiv').show();
        $('#giverName').text(name);
        $('#giverPhone').text(sendUser.phoneNum);
        $('#giverPhone').attr('href', "tel:"+sendUser.phoneNum);
    }else if (firm.orderState == 2){
        $('#order_status').text('已完成');
        $('#giverDiv').show();
        $('#giverName').text(name);
        $('#giverPhone').text(sendUser.phoneNum);
        $('#giverPhone').attr('href', "tel:"+sendUser.phoneNum);
    }else{
        $('#order_status').text('已取消');
    }
    if (firm.order.receiver == '' || null == firm.order.receiver){
        $('#addressItem').hide();
    }else {
        $('#shrName').text(firm.order.receiver);
        $('#shrTel').text(firm.order.reservedPhone);
        $('#shrAddress').text(firm.address);
    }
    var money = firm.orderMoney;
    $('#orderMoney').text(money/100 + '元');
    var askTime = firm.askTime;
    if(askTime == null){
        if (firm.order.staId == 30){
            askTime = "今日之内";
        } else {
            askTime = "尽快送达";
        }
    } else {
        askTime = askTime.substring(0,firm.askTime.length-3);
    }
    $('#askTime').text(askTime);
    if (firm.order.staId != 31){
        if (firm.goodsMoney != 0){
            $('#goodsMoneyItem').show();
            $('#goodsMoney').text((firm.goodsMoney)/100 + '元');
        }
    }
}