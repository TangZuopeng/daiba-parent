var baseTag;
(function ($, doc) {

    jQuery('.mui-title').text('收货地址管理');
    baseTag=$;
    init();
    document.getElementById("addClick").addEventListener('tap', function() {
        baseTag.openWindow({
            url:basePath+"/WeiXin/personal/EnterAddress?type=0&conmsgId=0",
            id:'313'
        });
    });
}(mui, document));


//初始化数据
function init(){
    $.ajax({
        url: basePath + "/WeiXin/personal/ShowAddresses.do",
        dataType: "json",
        type: "POST",
        async:false,
        success: function (result) {
            var success=result.success;
            if(success){
                var addresses=result.addresses;
                $.each(addresses,function(i,address){
                    insertListItem(address,i);
                });
            }else{
                mui.toast(result.message);
            }
        },
        error: function(){
           mui.alert('请检查您的网络连接状态', function () {
               mui.back();
           });
        }
    });
}


//插入列表项
function insertListItem(obj,i){

    var html='<li class="mui-table-view-cell" id=conmsg_'+i+'> <div id=item'+i+'>';
    html+=' <div><span class="acceptName">'+obj.callName.substring(0,obj.callName.length-2)+'</span> <span class="acceptPhoneNum">'+obj.phoneNum+'</span></div><span class="acceptAdd">'+obj.campus+obj.build+ obj.room+'</span> </div>'

    html+='   </div> <div><a class="span-right mui-icon mui-icon-compose" id=btn_'+i+'></a></div></li>'

    if(i==0){
        $("#myItem").html(html);
    }else{
        $("#conmsg_"+(i-1)).after(html);
    }

    if(pageFrom=='order'){
        document.getElementById("item"+i).addEventListener('tap', function() {
            localStorage.setItem("fdrAddress",JSON.stringify(obj));
            localStorage.setItem("taskType",taskType);
            window.history.go(-1);
            location.replace(document.referrer);
            //mui.back();
        });
    }

    document.getElementById("btn_"+i).addEventListener('tap', function() {
        baseTag.openWindow({
            url:basePath+"/WeiXin/personal/EnterAddress?type=1&conmsgId="+obj.conmsgId,
            id:"314"
        });
    });
}



