/**
 * Created by tao on 2016/10/4.
 */
var fee;
var timer = 300;

document.getElementById("cancel").addEventListener('tap', function () {
    var btnArray = ['取消', '确认'];
    mui.confirm('取消订单之后，钱将立即退还到您的微信账户！', '确认取消订单？', btnArray, function (e) {
        if (e.index == 1) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                goCancel();
            }, 300);
        }
    });
});

document.getElementById("applyCancel").addEventListener('tap', function () {
    var btnArray = ['取消', '确认'];
    mui.confirm('申请后请拨打带客电话，如果带客同意，该订单将取消！', '确认申请？', btnArray, function (e) {
        if (e.index == 1) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                goApplyCancel();
            }, 300);
        }
    });
});

function createListItemFragment(data, i) {
    var fragment = document.createDocumentFragment();
    var dish = document.createElement('div');
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
    goodPrice.innerHTML = '￥' + data.price * data.count / 100;
    div.appendChild(goodName);
    div.appendChild(goodNum);
    div.appendChild(goodPrice);
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

function init() {
    if (firmId == null) {
        mui.alert("网络错误，请重试！", function () {
            mui.back();
        });
        return;
    }
    $.ajax({
        url: basePath + "/WeiXin/subs/showFirmDetail.do",
        data: {
            firmId: firmId
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            var orderMoney = result.firmDetail.orderMoney;
            var goodsMoney = result.firmDetail.goodsMoney;
            // if (result.firmDetail.order.staId == 31 && orderMoney >= 150 && goodsMoney >= 700) {
            //     orderMoney -= 150;
            // }
            fee = orderMoney + goodsMoney;
            if (result.firmDetail == null) {
                mui.toast('无法获取订单详情，您可能不是这个订单的主人！');
                return;
            }
            if (result.firmDetail.order.staId == 30) {
                $('#takeNumItem').show();
                $('#takeNum').text(result.firmDetail.order.takeNum);
                $('#taskPlaceSpan').text('取件地点：');
                $('#taskPlace').text(result.firmDetail.order.acceptAddress + result.firmDetail.order.company);
                var remark = result.firmDetail.remark;
                if (remark == '' || null == remark) {
                    remark = '无';
                }
                $('#content').text(remark);
            } else if (result.firmDetail.order.staId == 31) {
                $('#taskPlaceItem').hide();
                $('#taskForItem').show();
                $('#taskForSpan').text('备注：');
                var remark = result.firmDetail.remark;
                if (remark == '' || null == remark) {
                    remark = '无';
                }
                $('#taskFor').text(remark);
                $('#contentItem').hide();
                insertCartsList(result.cartsList);
            } else if (result.firmDetail.order.staId == 32) {
                $('#taskTypeItem').show();
                $('#taskType').text(result.firmDetail.order.company);
                $('#taskForItem').show();
                $('#taskFor').text(result.firmDetail.remark);
                $('#askTimeSpan').text('任务时间：');
                $('#contentSpan').text('任务内容：');
                if (result.firmDetail.order.acceptAddress == '' || null == result.firmDetail.order.acceptAddress) {
                    $('#taskPlaceItem').hide();
                } else {
                    $('#taskPlace').text(result.firmDetail.order.acceptAddress);
                }
                var content = result.firmDetail.order.content;
                if (content == '' || null == content) {
                    content = '无';
                }
                $('#content').text(content);
            }
            if (result.firmDetail.orderState == 0) {
                $('#order_status').text('未接单');
                $('#one')[0].style.visibility = '';
            } else if (result.firmDetail.orderState == 1) {
                var name = result.bringerUser.name;
                if (name.length >= 5) {
                    name = name.substring(0, 3) + '..';
                }
                $('#bringerDiv').show();
                $('#bringerName').text(name);
                $('#bringerPhone').text(result.bringerUser.phoneNum);
                $('#bringerPhone').attr('href', "tel:" + result.bringerUser.phoneNum);
                $('#order_status').text('已接单');
                //订单类型若为31, 无法申请取消
                if (result.firmDetail.order.staId != 31) {
                    $('#three').removeAttr('disabled');
                    $('#three')[0].style.visibility = '';
                }
            } else if (result.firmDetail.orderState == 2) {
                var name = result.bringerUser.name;
                if (name.length >= 5) {
                    name = name.substring(0, 3) + '..';
                }
                $('#bringerDiv').show();
                $('#bringerName').text(name);
                $('#bringerPhone').text(result.bringerUser.phoneNum);
                $('#bringerPhone').attr('href', "tel:" + result.bringerUser.phoneNum);
                $('#order_status').text('已完成');
            } else if (result.firmDetail.orderState == 3) {
                $('#order_status').text('已取消');
            } else {
                mui.alert("您的订单未付款，无法获取订单详情", function () {
                    mui.back();
                });
            }
            if (result.firmDetail.order.receiver == '' || null == result.firmDetail.order.receiver) {
                $('#addressItem').hide();
            } else {
                $('#shrName').text(result.firmDetail.order.receiver);
                $('#shrTel').text(result.firmDetail.order.reservedPhone);
                $('#shrAddress').text(result.firmDetail.address);
            }
            $('#orderMoney').text((result.firmDetail.orderMoney) / 100 + '元');
            var askTime = result.firmDetail.askTime;
            if (askTime == null) {
                if (result.firmDetail.order.staId == 30) {
                    askTime = '今日之内';
                } else {
                    askTime = "尽快送达";
                }
            } else {
                askTime = askTime.substring(0, result.firmDetail.askTime.length - 3);
            }
            $('#askTime').text(askTime);
            //如果不是快捷取餐, 显示商品金额
            if (result.firmDetail.order.staId != 31) {
                if (result.firmDetail.goodsMoney != 0) {
                    $('#goodsMoneyItem').show();
                    $('#goodsMoney').text((result.firmDetail.goodsMoney) / 100 + '元');
                }
            }
        },
        error: function () {
            mui.alert("网络错误，请重试！", function () {
                mui.back();
            });
        }
    });
}

function goConfirm() {
    $.ajax({
        url: basePath + "/WeiXin/subs/confirmAcceptFirm.do",
        data: {
            firmId: firmId
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
                mui.toast('确认收货失败，该订单状态不是已接单状态');
            } else if (result.confirmMessage == 3) {
                mui.toast('确认收货失败，该订单不存在，可能被删除');
            } else if (result.confirmMessage == 4) {
                mui.toast('确认收货失败，您可能不是这个订单的主人！');
            }
        },
        error: function () {
            mui.toast('确认收货失败，请检查网络是否正常');
        }
    });
}

//取消订单申请退款
function goCancel() {
    $.ajax({
        url: basePath + "/WeiXin/pay/cancelOrder.do",
        data: {
            out_trade_no: firmId,
            total_fee: fee
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result) {
                mui.toast("申请退款成功");
                cancelOrder();
            } else {
                mui.toast('申请退款失败，请检查网络是否正常');
            }
        },
        error: function () {
            mui.toast('申请退款失败，请检查网络是否正常');
        }
    });
}

//带客接单后用户申请取消订单
function goApplyCancel() {
    $.ajax({
        url: basePath + "/WeiXin/subs/applyCancelFirm.do",
        data: {
            out_trade_no: firmId
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result.cancelMessage == 1) {
                mui.toast('申请已发出，请耐心等待带客响应！');
            } else if (result.cancelMessage == 2) {
                mui.toast('申请已提交，请勿重复提交！');
            } else if (result.cancelMessage == 3) {
                mui.toast('申请失败，该订单不是已接单状态');
            } else if (result.cancelMessage == 4) {
                mui.toast('申请失败，该订单不存在，可能被删除');
            } else if (result.cancelMessage == 5) {
                mui.toast('申请失败，您不是该订单的主人');
            }
        },
        error: function () {
            mui.toast('申请取消失败，请检查网络是否正常');
        }
    });
}

//退款成功后取消订单
function cancelOrder() {
    $.ajax({
        url: basePath + "/WeiXin/subs/cancelFirm.do",
        data: {
            firmId: firmId
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result.cancelMessage == 1) {
                mui.alert("取消订单成功，返回我发的单", function () {
                    toOrder();
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

function toOrder() {
    location.href = basePath + '/WeiXin/main/order';
    return;
}
