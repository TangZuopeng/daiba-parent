/**
 * Created by tao on 2016/9/21.
 */
$(document).ready(function() {
    //  此处修改对应页面的标题
    $('.mui-title').text('订单');
    updateTabTarget($('#orderTab'));
    updateType(0);
});

click($('#received')[0], function(event){
    updateType(0);
});
click($('#pending')[0], function(event){
    updateType(1);
});
click($('#finished')[0], function(event){
    updateType(2);
});
click($('#canceled')[0], function(event){
    updateType(3);
});

var mTarget = $('#received');
function updateType(type){
    mTarget.removeClass('checked');
    switch(type){
        case 0:
            getData('01');
            mTarget = $('#received');
            break
        case 1:
            getData('00');
            mTarget = $('#pending');
            break
        case 2:
            getData('02');
            mTarget = $('#finished');
            break
        case 3:
            getData('03');
            mTarget = $('#canceled');
            break
    }
    mTarget.addClass('checked');
}

function getData(status_code){
    $.ajax({
        url: basePath + "WeiXin/main/getOrder.do",
        data: {
            orderState: status_code
        },
        dataType: "json",
        type: "POST",
        async:false,
        success: function (result) {
            if(result.length <= 0) {
                $('#list').empty();
                mui.toast('没有更多数据了');
                return;
            }
            var str = '<ul class="mui-table-view mui-table-view-striped">';
            for(var i = 0; i < result.length; ++ i){
                str += '<li class="mui-table-view-cell" onclick="getInfo(' + result[i].id + ')">';
                str += '快递号：';
                str += '<span class="order">' + result[i].takeNum + '</span>';
                str += '<p class="mui-navigate-right mui-pull-right" style="padding-right: 15%;">' + result[i].fields + '</p>';
                str += '</li>';
            }
            str += '</ul>'
            $('#list').html(str);
        },
        error: function(){
            mui.toast("数据库查询错误")
        }
    });
}

var url = '/subs/orderInfo';
function getInfo(orderId){
    redirect(url + '?orderId=' + orderId)
}