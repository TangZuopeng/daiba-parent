var baseTag;
(function ($, doc) {
    baseTag=$;
    addClickEvent();
    init();
}(mui, document));

//为有点击的地方添加点击事件
function addClickEvent(){

    //为修改头像添加点击事件
    //document.getElementById("uHeakIconLi").addEventListener('tap', function() {
    //    baseTag.openWindow({
    //        url:basePath+"/WeiXin/personal/EnterAlbum"
    //    });
    //});

    //为修改昵称添加点击事件
    document.getElementById("uNickNameLi").addEventListener('tap', function(e) {
        e.detail.gesture.preventDefault();
        var btnArray = ['取消', '确定'];
        mui.prompt('修改昵称', '请输入你想要修改的昵称', 'Welcome', btnArray, function(e) {
            if (e.index == 1) {
                var uNickNameVlue=document.querySelector('.mui-popup-input input').value;
                editNicknameEvent(uNickNameVlue);
            }
        });
    });

    //为修改性别添加点击事件
    document.getElementById("uSexLi").addEventListener('tap', function(e) {
        mui('.mui-popover').popover('toggle',document.getElementById("uSexLi"));
        document.querySelector('.mui-table-view.mui-table-view-radio').addEventListener('selected',function(e){
            //alert(e.detail.el.innerText);
            var text=e.detail.el.innerText.trim();
            editSexEvent(text);
        });
    });

    //为修改密码
    document.getElementById("editPasswordBtn").addEventListener('tap', function() {
        baseTag.openWindow({
            url:basePath+"/WeiXin/personal/changerPassword"
        });
    });
}

//显示或者隐藏认证信息
function isShow(flag){
    if(flag){
        $("#uStudentNumLi").show();
        $("#uRealNameLi").show();
    }else{
        $("#uStudentNumLi").hide();
        $("#uRealNameLi").hide();
    }
}

//初始化数据
function init(){
    $.ajax({
        url: basePath + "/WeiXin/personal/ShowDetailUser.do",
        dataType: "json",
        type: "POST",
        async:false,
        success: function (result) {
            var success=result.success;
            if(success){
                var personalInfo=result.personalInfo;
                var flag=result.flag;
                if(flag){
                    $("#uNickNameId").text(personalInfo.name);//昵称
                    $("#uHeakIconId").attr('src',personalInfo.portrait);//头像
                    if(null!=personalInfo.sex&&personalInfo.sex>0){
                        $("#uSexId").text(personalInfo.sex==1?'男':'女');//性别
                    }else{
                        $("#uSexId").text('未选择');//性别
                    }
                    $("#uPhoneNumId").text(personalInfo.phoneNum);//手机号
                    $("#uBringerId").text(personalInfo.role);//带客信息
                    $("#uStudentNumId").text(personalInfo.studentNum);//学号
                    $("#uRealNameId").text(personalInfo.realName);
                }else{
                    isShow(flag);
                    $("#uNickNameId").text(personalInfo.name);//昵称
                    $("#uHeakIconId").attr('src',personalInfo.portrait);//头像
                    if(null!=personalInfo.sex&&personalInfo.sex>0){
                        $("#uSexId").text(personalInfo.sex==1?'男':'女');//性别
                    }else{
                        $("#uSexId").text('未选择');//性别
                    }
                    $("#uPhoneNumId").text(personalInfo.phoneNum);//手机号
                    $("#uBringerId").text(personalInfo.role);//带客信息
                }
            }else{
                mui.toast("用户信息异常!");
            }
        },
        error: function(){
            mui.toast("网络或者服务器异常!请重试")
        }
    });
}

//修改性别事件
function editSexEvent(text){
    var sexCode=text=='男'?1:2;
    $.ajax({
        url: basePath + "/WeiXin/personal/EditSex.do",
        dataType: "json",
        data:{
           sexCode: sexCode
        },
        type: "POST",
        async:false,
        success:function(result){
            var success=result.success;
            if(success){
                sexCode==1?$("#uSexId").text("男"):$("#uSexId").text("女");
            }else{
                sexCode==1?$("#uSexId").text("女"):$("#uSexId").text("男");
            }
            mui('.mui-popover').popover('hide');
        }
    });
}

//修改昵称事件
function editNicknameEvent(text){
    var nicknameText=text;
    $.ajax({
        url: basePath + "/WeiXin/personal/EditNickName.do",
        dataType: "json",
        data:{
            nickName: nicknameText
        },
        type: "POST",
        async:false,
        success:function(result){
            var success=result.success;
            if(success){
                $("#uNickNameId").text(nicknameText);
            }else{
                mui.toast("修改失败,请重试!");
            }
        },
        error:function(){
            mui.toast("网络或者服务器异常,请重试!");
        }
    });
}













