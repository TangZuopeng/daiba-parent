/**
 * Created by StphenTmac on 2017/4/5.
 */

//学校
var schoolCode = '1001';
//校区
var campusCode;
//地址类别
var addTypeCode = '0';//1合作、0非合作
//订单类型
var orderType = 31;
//初始化组件
var school = document.getElementById("school");//合作栏
var outsideSchool = document.getElementById("outsideSchool");//非合作栏
//采购商品总数
var count = 0;
//购物车商品数组
var cart = [];
//配送费
var shippingFee = 0;
//已购金额
var fee = 0;

//去除数组中的重复项
function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

//判断数组是否包含这个元素
function contains(arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;
        }
    }
    return false;
}

//移除数组中指定元素
function removeByValue(arr, val) {
    for(var i=0; i<arr.length; i++) {
        if(arr[i] == val) {
            arr.splice(i, 1);
            break;
        }
    }
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
                campusCode = $("#showCampusValue").text();
            }else {
                showUserPickerButton.innerText = '南湖校区';
                $("#showCampusValue").text('100101');
                campusCode = $("#showCampusValue").text();
            }
            //返回 false 可以阻止选择框的关闭
            //return false;

            //***********************存储你选择的校区
            window.localStorage.setItem("campusCode",items[0].value);
            location.reload();
        });
    }, false);
}

//将获取到的菜品信息打印到固定位置
function setDishes(result, merchantId) {
    var dishes = document.getElementById(merchantId);
    if (result != '') {
        var ul = document.createElement('ul');
        ul.className = 'mui-table-view mui-table-view-chevron';
        $.each(result, function (i, data) {
            ul.appendChild(createDishesListFragment(data, i));
        });
        dishes.appendChild(ul);
    } else {
        mui.toast("亲所选择的餐厅今天休息！");
    }
}

//根据商户id从后台获取菜品信息
function getDishes(merchantId) {
    $.ajax({
        url: basePath + '/WeiXin/dishes/getDishes.do',
        data: {
            merchantId: merchantId
        },
        dataType: 'json',
        type: "POST",
        success: function (result) {
            setDishes(result, merchantId);
        },
        error: function (err) {
            mui.toast("菜单信息拉取失败！");
        }
    });
}

//从后台获取商户信息
function getMerchant() {
    var campus = campusCode.substring(4, campusCode.length);
    $.ajax({
        url: basePath + '/WeiXin/dishes/getMerchants.do',
        data: {
            schoolCode: schoolCode,
            campusCode: campus,
            addTypeCode: addTypeCode
        },
        dataType: 'json',
        type: "POST",
        success: function (result) {
            setMerchant(result);
        },
        error: function (err) {
            mui.toast("商户信息拉取失败！");
        }
    });
}

var oldSchoolMerchantId;//存储之前校内选择的商户
var oldOutsideSchoolMerchantId;//存储之前周边选择的商户

//将获取到的商户信息打印到页面上
function setMerchant(result) {
    var div1 = document.createElement('div');
    var div2 = document.createElement('div');
    div1.className = 'mui-row mui-fullscreen';
    div2.className = 'mui-col-xs-3';
    div1.appendChild(div2);
    if (addTypeCode == '0'){
        var outsideSchoolControls = document.createElement('div');
        outsideSchoolControls.id = 'schoolControls';
        outsideSchoolControls.className = 'mui-segmented-control mui-segmented-control-inverted mui-segmented-control-vertical';
        var outsideSchoolContents = document.createElement('div');
        outsideSchoolContents.id = 'outsideSchoolContents';
        outsideSchoolContents.className = 'mui-col-xs-9';
        outsideSchoolContents.style = 'border-left: 1px solid #c8c7cc';
    } else {
        var schoolControls = document.createElement('div');
        schoolControls.id = 'schoolControls';
        schoolControls.className = 'mui-segmented-control mui-segmented-control-inverted mui-segmented-control-vertical';
        var schoolContents = document.createElement('div');
        schoolContents.id = 'schoolContents';
        schoolContents.className = 'mui-col-xs-9';
        schoolContents.style = 'border-left: 1px solid #c8c7cc';
    }
    if (result != '') {
        if (addTypeCode == '0'){
            $.each(result, function (i, data) {
                outsideSchoolControls.appendChild(createMerchantListItemFragment(data, i));
                outsideSchoolContents.appendChild(createNullDishesDivListFragment(data, i));
            });
            div2.appendChild(outsideSchoolControls);
            div1.appendChild(outsideSchoolContents);
            outsideSchool.replaceChild(div1, outsideSchool.querySelector('.mui-loading'));
            //默认选中第一个
            outsideSchoolControls.querySelector('.mui-control-item').classList.add('mui-active');
            $(outsideSchoolControls).on('tap', '.mui-fullscreen .mui-control-item', function(e) {
                var merchantId = $(e.currentTarget).data("merchantId");
                process(merchantId);
            });
        } else {
            $.each(result, function (i, data) {
                schoolControls.appendChild(createMerchantListItemFragment(data, i));
                schoolContents.appendChild(createNullDishesDivListFragment(data, i));
            });
            div2.appendChild(schoolControls);
            div1.appendChild(schoolContents);
            school.replaceChild(div1, school.querySelector('.mui-loading'));
            //默认选中第一个
            schoolControls.querySelector('.mui-control-item').classList.add('mui-active');
            $(schoolControls).on('tap', '.mui-fullscreen .mui-control-item', function(e) {
                var merchantId = $(e.currentTarget).data("merchantId");
                process(merchantId);
            });
        }
        process(result[0].merchantId);
    } else {
        var div1 = document.createElement('div');
        div1.className = 'mui-row mui-fullscreen';
        div1.style.textAlign = 'center';
        div1.innerHTML = '该类别没有商家入驻，如有需要，可进入其他选项，选择帮带饭菜任务类型，发布任务！';
        if (addTypeCode == '0'){
            outsideSchool.replaceChild(div1, outsideSchool.querySelector('.mui-loading'));
        } else {
            school.replaceChild(div1, school.querySelector('.mui-loading'));
        }
    }
}

//逐个放上每个商户
function createMerchantListItemFragment(data, i) {
    var fragment = document.createDocumentFragment();
    //创建餐厅列表节点
    var a = document.createElement('a');
    a.className = 'mui-control-item';
    a.setAttribute('data-index', ''+i);
    a.innerHTML = data.merchantName;
    $(a).data("merchantId", data.merchantId);
    fragment.appendChild(a);
    return fragment;
}

//逐个商户建立空菜单，以备将菜品打印上去
function createNullDishesDivListFragment(data, i) {
    var fragment = document.createDocumentFragment();
    //创建空菜单div
    var div = document.createElement('div');
    div.id = data.merchantId;
    div.className = 'mui-control-content';
    div.style.display = 'none';
    $(div).data('data', data);
    fragment.appendChild(div);
    return fragment;
}

//新增购物车商品
function addCart(data) {
    if (!contains(cart, data.dishesId)){
        insertCart(data);
        cart.push(data.dishesId);
    } else {
        var count = parseInt($("[data-orderid=" + data.dishesId +"] .foodop-num").html());
        count = count + 1;
        $("[data-orderid=" + data.dishesId +"] .foodop-num").html(count);
    }
}

//删除购物车商品
function removeCart(data) {
    var count = parseInt($("[data-orderid=" + data.dishesId +"] .foodop-num").html());
    if (count == 1){
        removeFood(data);
        removeByValue(cart, data.dishesId);
    } else {
        count = count - 1;
        $("[data-orderid=" + data.dishesId +"] .foodop-num").html(count);
    }
}

//移除购物车中菜品
function removeFood(data) {
    document.getElementById('cartList').removeChild($("[data-orderid=" + data.dishesId +"]")[0]);
}

//购物车中加菜，菜品栏中菜品随之增加
function addDishes(data) {
    var count = parseInt($('#' + data.dishesId + ' .foodop-num').html());
    count = count + 1;
    $('#' + data.dishesId + ' .foodop-num').html(count);
}

//购物车中减菜，菜品栏中菜品随之减少
function removeDishes(data) {
    var count = parseInt($('#' + data.dishesId + ' .foodop-num').html());
    count = count - 1;
    $('#' + data.dishesId + ' .foodop-num').html(count);
}

//购物车中插入新物品
function insertCart(data) {
    var div1 = document.createElement('div');
    div1.className = 'cart-dtl-item';
    div1.setAttribute('data-orderid', '' + data.dishesId);
    var div2 = document.createElement('div');
    div2.className = 'cart-dtl-item-inner';
    var i = document.createElement('i');
    i.className = 'cart-dtl-dot';
    var p = document.createElement('p');
    p.className = 'cart-goods-name';
    p.innerHTML = data.name;
    var div3 = document.createElement('div');
    div3.className = 'cart-dtl-oprt';
    var addFood = document.createElement('a');
    var span = document.createElement('span');
    var removeFood = document.createElement('a');
    addFood.className = 'add-food';
    span.className = 'foodop-num';
    removeFood.className = 'remove-food';
    addFood.innerHTML = '<i class="icon i-add-food mui-icon iconfont icon-jia"></i>';
    removeFood.innerHTML = '<i style="color: #ACACB4;"  class="icon i-remove-food mui-icon iconfont icon-jian"></i>';
    span.innerHTML = '1';
    var price = document.createElement('span');
    price.className = 'cart-dtl-price';
    price.innerHTML = '￥' + data.price/100;
    //为购物车中菜品的加减绑定点击事件
    addFood.addEventListener('tap', function () {
        span.innerHTML = parseInt($(span).html()) + 1;
        count = count + 1;
        $('#cart .cart-num')[0].innerHTML = count;
        //商品总价
        var prices = $('#prices').html();
        prices = parseInt(prices.substring(1, prices.length));
        fee = prices + data.price/100;
        $('#prices').html('￥' + fee);
        setShippingFee();
        //菜品栏中菜品数量联动
        addDishes(data);
    });
    removeFood.addEventListener('tap', function () {
        count = count - 1;
        $('#cart .cart-num')[0].innerHTML = count;
        //商品总价
        var prices = $('#prices').html();
        prices = parseInt(prices.substring(1, prices.length));
        fee = prices - data.price/100;
        if (fee <= 0){
            if (count <= 0){
                $('#cart i')[0].className = 'icon-cart';
                $('#cart .cart-num').hide();
                $('#cartContents').hide();
            }
            $('#submitOrder span')[0].style.backgroundColor = '#ccc';
            $('#submitOrder span')[0].className = 'inner';
        }
        $('#prices').html('￥' + fee);
        removeCart(data);
        removeDishes(data);
        setShippingFee();
    });
    div3.appendChild(addFood);
    div3.appendChild(span);
    div3.appendChild(removeFood);
    div2.appendChild(i);
    div2.appendChild(p);
    div2.appendChild(div3);
    div2.appendChild(price);
    div1.appendChild(div2);
    $(div1).data("data", data);
    var merchant = document.getElementById(data.dishesId).parentNode.parentNode;
    var merchantData = $(merchant).data("data");
    $(div1).data("merchantAdd", merchantData.merchantAdd);
    document.getElementById('cartList').appendChild(div1);
}

//根据点菜价格决定配送费
function setShippingFee() {
    if (fee <= 12 && fee >= 0){
        shippingFee = 1.5;
    } else if (fee <= 25 && fee > 12){
        shippingFee = 2.5;
    } else if (fee <= 35 && fee > 25){
        shippingFee = 3.5;
    } else if (fee <= 45 && fee > 35){
        shippingFee = 4.5;
    } else if (fee <= 60 && fee > 45){
        shippingFee = 6;
    } else if (fee <= 80 && fee > 60){
        shippingFee = 8;
    } else if (fee <= 100 && fee > 80){
        shippingFee = 10;
    } else {
        shippingFee = 15;
    }
    $('#shippingFee').html('另需配送费 ￥' + shippingFee);
}

//逐个建立菜品
function createDishesListFragment(data, i) {
    var fragment = document.createDocumentFragment();
    //建立菜品项
    var li = document.createElement('li');
    li.id = data.dishesId;
    li.className = 'mui-table-view mui-table-view-cell';
    var div1 = document.createElement('div');
    div1.className = 'food-pic-wrap';
    var img = document.createElement('img');
    img.className = 'food-pic';
    img.src = data.img;
    div1.appendChild(img);
    var div2 = document.createElement('div');
    div2.className = 'food-cont-wrap';
    var foodContent = document.createElement('div');
    foodContent.className = 'food-cont';
    var foodName = document.createElement('div');
    foodName.className = 'food-name';
    foodName.innerHTML = data.name;
    var foodSub = document.createElement('div');
    foodSub.className = 'food-content-sub';
    foodSub.innerHTML = '月销 ' + data.monthVolume;
    var foodPrice = document.createElement('div');
    foodPrice.className = 'food-price-region';
    foodPrice.innerHTML = '￥ ' + data.price/100;
    var food = document.createElement('div');
    food.className = 'clearfix foodop';
    var addFood = document.createElement('a');
    addFood.className = 'add-food';
    addFood.innerHTML = '<i class="i-add-food mui-icon iconfont icon-jia"></i>';
    var removeFood = document.createElement('a');
    removeFood.className = 'remove-food';
    removeFood.innerHTML = '<i style="color: #ACACB4;" class="i-remove-food mui-icon iconfont icon-jian"></i>';
    var span = document.createElement('span');
    span.className = 'foodop-num';
    span.innerHTML = '0';
    food.appendChild(addFood);
    food.appendChild(span);
    food.appendChild(removeFood);
    foodContent.appendChild(foodName);
    foodContent.appendChild(foodSub);
    foodContent.appendChild(foodPrice);
    foodContent.appendChild(food);
    div2.appendChild(foodContent);
    li.appendChild(div1);
    li.appendChild(div2);
    //为菜品的加减绑定点击事件
    addFood.addEventListener('tap', function () {
        span.innerHTML = parseInt($(span).html()) + 1;
        count = count + 1;
        $('#cart .cart-num')[0].innerHTML = count;
        //商品总价
        var prices = $('#prices').html();
        prices = parseInt(prices.substring(1, prices.length));
        fee = prices + data.price/100;
        if (count > 0){
            if (fee > 0){
                $('#submitOrder span')[0].style.backgroundColor = '#ff8400';
                $('#submitOrder span')[0].className = 'inner submit-dishes';
            }
            $('#cart i')[0].className = 'icon-cart icon-cart-active';
            $('#cart .cart-num').show();
        }
        $('#prices').html('￥' + fee);
        setShippingFee(prices);
        addCart(data);
    });
    removeFood.addEventListener('tap', function () {
        if (parseInt($(span).html()) > 0){
            span.innerHTML = parseInt($(span).html()) - 1;
            count = count - 1;
            $('#cart .cart-num')[0].innerHTML = count;
            //商品总价
            var prices = $('#prices').html();
            prices = parseInt(prices.substring(1, prices.length));
            fee = prices - data.price/100;
            if (fee <= 0){
                if (count <= 0){
                    $('#cart i')[0].className = 'icon-cart';
                    $('#cart .cart-num').hide();
                    $('#cartContents').hide();
                }
                $('#submitOrder span')[0].style.backgroundColor = '#ccc';
                $('#submitOrder span')[0].className = 'inner';
            }
            $('#prices').html('￥' + fee);
            setShippingFee(prices);
            removeCart(data);
        }
    });
    $(li).data("data", data);
    fragment.appendChild(li);
    return fragment;
}

function process(merchantId) {
    var dishesDiv = document.getElementById(merchantId);
    //如果和之前选择的店铺一致, 则无需操作
    if (merchantId == oldOutsideSchoolMerchantId || merchantId == oldSchoolMerchantId){
        return;
    }
    if (addTypeCode == '1'){
        if (oldSchoolMerchantId != undefined){
            var oldDishesDiv = document.getElementById(oldSchoolMerchantId);
        }
        if($(dishesDiv).html() == ''){
            getDishes(merchantId);
        }
        dishesDiv.removeAttribute('style');
        if (oldSchoolMerchantId != undefined){
            oldDishesDiv.style.display = 'none';
        }
        oldSchoolMerchantId = merchantId;
    } else if (addTypeCode == '0'){
        if (oldOutsideSchoolMerchantId != undefined){
            var oldDishesDiv = document.getElementById(oldOutsideSchoolMerchantId);
        }
        if($(dishesDiv).html() == ''){
            getDishes(merchantId);
        }
        dishesDiv.removeAttribute('style');
        if (oldOutsideSchoolMerchantId != undefined){
            oldDishesDiv.style.display = 'none';
        }
        oldOutsideSchoolMerchantId = merchantId;
    }
}

//初始化商户信息
function initMerchant() {
    getMerchant();
}

//顶部导航栏监听事件
/*function setSliderListener(){
    document.getElementById('slider').addEventListener('slide', function(e) {
        if (e.detail.slideNumber === 0) {
            orderType = 33;
            addTypeCode = '1';
            //合作与非合作不可同时下单
            clearCart();
            if (school.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getMerchant();
                }, 500);
            }
        } else if (e.detail.slideNumber === 1) {
            orderType = 31;
            addTypeCode = '0';
            //合作与非合作不可同时下单
            clearCart();
            if (outsideSchool.querySelector('.mui-loading')) {
                setTimeout(function() {
                    getMerchant();
                }, 500);
            }
        }
    });
}*/

//购物车点击事件
document.getElementById('cartIcon').addEventListener('tap', function () {
    //购物车如果有物品
    if ($('#cart i').is('.icon-cart-active')){
        //如果购物车内容隐藏则显示，购物车内容显示则隐藏
        if ($('#cartContents').is(':visible')){
            $('#cartContents').hide();
        } else {
            $('#cartContents').show();
        }
    }
});

//清空购物车
function clearCart() {
    $("#cartList .cart-dtl-item").each(function () {
        var data = $(this).data("data");
        var num = parseInt($(this).find('.foodop-num').html());
        for(var i = 0; i < num; i++){
            count = count - 1;
            if (count == 0){
                $('#submitOrder span')[0].style.backgroundColor = '#ccc';
                $('#submitOrder span')[0].className = 'inner';
                $('#cart i')[0].className = 'icon-cart';
                $('#cart .cart-num').hide();
                $('#cartContents').hide();
            }
            $('#cart .cart-num')[0].innerHTML = count;
            //商品总价
            var prices = $('#prices').html();
            prices = parseInt(prices.substring(1, prices.length));
            fee = prices - data.price/100;
            $('#prices').html('￥' + fee);
            removeCart(data);
            removeDishes(data);
            setShippingFee();
        }
    });
}

//点击清空购物车
document.getElementById('clearCart').addEventListener('tap', function () {
    clearCart();
});

document.getElementById('submitOrder').addEventListener('tap', function () {
    //找带客按钮
    if ($('#submitOrder span').is('.submit-dishes')){
        submitDishes();
    }
});

//提交订单
function submitDishes() {
    //购物车中商品json数组
    var cartJson = [];
    //购物车中商品对应商家的地址
    var merchantAdds = [];
    $("#cartList .cart-dtl-item").each(function () {
        var data = $(this).data("data");
        var merchantAdd = $(this).data("merchantAdd");
        var num = parseInt($(this).find('.foodop-num').html());
        var temp = {dishesId: data.dishesId, name: data.name, count: num, price: data.price};
        cartJson.push(temp);
        merchantAdds.push(merchantAdd);
    });
    //将商家地址去重
    merchantAdds = unique(merchantAdds);
    var merchantAddsStr = '';
    for (var i = 0; i < merchantAdds.length; i++){
        if (i != merchantAdds.length - 1){
            merchantAddsStr = merchantAddsStr + merchantAdds[i] + ',';
        } else {
            merchantAddsStr = merchantAddsStr + merchantAdds[i]
        }
    }
    var cart = JSON.stringify(cartJson);
    //将点好的菜品信息存入本地内存
    window.localStorage.setItem("dishes",cart);
    //将配送费信息存入本地内存
    window.localStorage.setItem("shippingFee",shippingFee);
    //将商家地址信息存入本地内存
    window.localStorage.setItem("merchantAdds", merchantAddsStr);
    mui.openWindow({
        url: basePath + '/WeiXin/dishes/enterSubmitDishesOrder?orderType=' + orderType
    });
}

//初始化页面
function init() {
    $('.mui-title').html('<span id="showCampus">南湖校区</span><span id="showCampusValue" hidden>11</span><a class="mui-icon mui-icon-arrowdown" style="font-size: 20px;"></a>');
    //取出上次选择的校区码(存在本地的)
    campusCode = window.localStorage.getItem("campusCode");
    $('#showCampusValue').text(campusCode);
    if(campusCode == '100101'){
        $('#showCampus').text("南湖校区");
    }else if(campusCode == '100102'){
        $('#showCampus').text("林园校区");
    }else if(campusCode == '100103'){
        $('#showCampus').text("北湖校区");
    }else{
        campusCode = '100101';
        $('#showCampus').text("南湖校区");
    }
    initSelect();
    // setSliderListener();
    initMerchant();
}

$(function () {
    init();
});