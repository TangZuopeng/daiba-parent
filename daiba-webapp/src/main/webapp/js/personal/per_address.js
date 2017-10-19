var baseTag;
var timer = 300;
(function ($, doc) {
    jQuery('.mui-title').text('编辑收货地址');
    baseTag = $;
    addClickEvent();
    init();
}(mui, document));

// var clickType;//0代表为触发删除地址  1触发修改地址
//为全局添加点击事件
function addClickEvent() {
    //为修改性别添加点击事件
    document.getElementById("addSexItem").addEventListener('tap', function (e) {
        mui('#myPopSex').popover('toggle', document.getElementById("addSexItem"));
        document.querySelector('#myUlSex').addEventListener('selected', function (e) {
            var text = e.detail.el.innerText.trim();
            if (text == "男神") {
                $("#addSex").text("男神");
            } else if (text == "女神") {
                $("#addSex").text("女神");
            }
            mui('#myPopSex').popover('hide');
        });
    });

    //为修改校区添加事件
    document.getElementById("addCampusItem").addEventListener('tap', function (e) {
        mui('#myPopCampus').popover('toggle', document.getElementById("addCampusItem"));
        document.querySelector('#myUlCampus').addEventListener('selected', function (e) {
            //alert(e.detail.el.innerText);
            var text = e.detail.el.innerText.trim();
            if (text == "南湖校区") {
                $("#addCampus").text("南湖校区");
            } else if (text == "林园校区") {
                $("#addCampus").text("林园校区");
            }
            else if (text == "北湖校区") {
                $("#addCampus").text("北湖校区");
            }
            mui('#myPopCampus').popover('hide');
        });
    });

    //为删除地址添加点击事件
    document.getElementById("deleteAddItem").addEventListener('tap', function (e) {
        var btnArray = ['取消', '确认'];
        mui.confirm('删除该地址后，将无法恢复！', '删除地址？', btnArray, function (e) {
            if (e.index == 1) {
                clearTimeout(timer);
                timer = setTimeout(function () {
                    goDelete();
                },300);
            }
        });
    });

    //为保存地址添加事件
    document.getElementById("saveAddItem").addEventListener('tap', function (e) {
        if (mytype == 1) {
            // clickType = 1;
            if (checkSubmit()) {
                goEdit();
            }
            // $('#md-title').text('保存地址？');
            // $('#md-content').text('点击确认保存修改地址');
        } else {
            if (checkSubmit()) {
                goAdd();
            }
        }
    });
}

//修改地址
function goEdit() {
    var name = $("#addName").val();
    var sex = $("#addSex").text();
    var telPhone = $("#addPhoneNum").val();
    var campus = $("#addCampus").text();
    var build = $("#addBuild").val();
    var room = $("#addRoom").val();
    var callName = name + sex;
    $.ajax({
        url: basePath + "/WeiXin/personal/EditDeliveryAddress.do",
        data: {
            conmsgId: conmsgid,
            callName: callName,
            phoneNum: telPhone,
            campus: campus,
            build: build,
            room: room
        },
        dataType: "json",
        type: "POST",
        async: false,
        beforeSend:function(){
            $("#saveAddItem").attr('disabled', 'disabled');
            $("#saveAddItem").text('修改中...');
        },
        complete:function(){
            $("#saveAddItem").removeAttr('disabled');
            $("#saveAddItem").text('保存');
        },
        success: function (result) {
            if (result.success) {
                goBack();
            } else {
                mui.toast('网络或者服务器异常,请重试');
            }
        },
        error: function (error) {
            mui.toast('网络或者服务器异常,请重试!');
        }
    });
}
//删除地址
function goDelete() {
    $.ajax({
        url: basePath + "/WeiXin/personal/DeleteDeliveryAddress.do",
        data: {
            conmsgId: conmsgid
        },
        dataType: "json",
        type: "POST",
        async: false,
        success: function (result) {
            if (result.success) {
                goBack();
            } else {
                mui.toast('网络或者服务器异常,请重试');
            }
        },
        error: function (error) {
            mui.toast('网络或者服务器异常,请重试!');
        }
    });
}

//添加地址
function goAdd() {
    var name = $("#addName").val();
    var sex = $("#addSex").text();
    var telPhone = $("#addPhoneNum").val();
    var campus = $("#addCampus").text();
    var build = $("#addBuild").val();
    var room = $("#addRoom").val();
    $.ajax({
        url: basePath + "/WeiXin/personal/AddDeliveryAddress.do",
        data: {
            callName: name + sex,
            phoneNum: telPhone,
            campus: campus,
            build: build,
            room: room
        },
        dataType: "json",
        type: "POST",
        async: false,
        beforeSend:function(){
            $("#saveAddItem").attr('disabled', 'disabled');
            $("#saveAddItem").text('添加中...');
        },
        complete:function(){
            $("#saveAddItem").removeAttr('disabled');
            $("#saveAddItem").text('保存');
        },
        success: function (result) {
            if (result.success) {
                goBack();
                //mui.openWindow({
                //    url: basePath+"/WeiXin/personal/EnterManageAdd?pageFrom=mine",
                //    id:'312'
                //});
            } else {
                mui.toast('网络或者服务器异常,请重试');
            }
        },
        error: function (error) {
            mui.toast('网络或者服务器异常,请重试!');
        }
    });
}

function goBack(){
    window.history.back(-1);
    location.replace(document.referrer);
}

//校验信息提交
function checkSubmit() {
    var name = $("#addName").val();
    var sex = $("#addSex").text();
    var telPhone = $("#addPhoneNum").val();
    var campus = $("#addCampus").text();
    var build = $("#addBuild").val();
    var room = $("#addRoom").val();
    if (name.length < 1) {
        mui.toast('收货人至少要1个字');
        return;
    }

    if (name.length > 6) {
        mui.toast('收货人最多6个字');
        return;
    }

    if (sex == '' || sex.length < 1) {
        mui.toast('请选择性别');
        return;
    }

    if (!checkPhoneNum(telPhone)) {
        mui.toast('您输入的联系电话有误!');
        return;
    }

    if (build == '' || build.length > 5) {
        mui.toast('楼栋最多5位的字');
        return;
    }

    var reg = new RegExp("^[0-9]*$");
    if (room == '' || room.length > 5 || !reg.test(room)) {
        mui.toast('房间号最多5位数字');
        return;
    }
    return true;
}
//修改还是增加操作
function init() {
    if (mytype == 1) {
        $(".mui-title").text('编辑收货地址');
        $.ajax({
            url: basePath + "/WeiXin/personal/GetAddress.do",
            data: {
                conmsgId: conmsgid
            },
            dataType: "json",
            type: "POST",
            async: false,
            success: function (result) {
                if (result.success) {
                    var msg = result.conmessage;
                    var name = msg.callName;
                    var index = name.lastIndexOf('神');
                    var myname = name.substring(0, index - 1);
                    var mysex = name.substring(index - 1);
                    $("#addName").val(myname);
                    $("#addSex").text(mysex);
                    $("#addPhoneNum").val(msg.phoneNum);
                    $("#addCampus").text(msg.campus);
                    $("#addBuild").val(msg.build);
                    $("#addRoom").val(msg.room);
                }
            },
            error: function (error) {
                mui.toast('网络或者服务器异常,请重试!');
            }
        });
    } else {
        $(".mui-title").text('添加收货地址');
        $("#deleteAddItem").hide();
    }
}

/**
 * 检查手机号码是否合法
 *
 * @param phoneNum
 * @returns {boolean}
 */
var checkPhoneNum = function (phoneNum) {
    phoneNum = phoneNum || '';
    var myreg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;
    return phoneNum.length == 11 && myreg.test(phoneNum);
};
