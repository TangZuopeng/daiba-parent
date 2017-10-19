<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

<a href="WeiXin/subs/cancleFirm">取消订单</a>



    <form action="WeiXin/Code/sendCode" method="post">
        <input type="text" name="phoneNum"/>
        <input type="submit" value="获取手机验证码">
    </form>

    <a href="WeiXin/login">getUserInfo</a>

    <a href="WeiXin/register">register</a>


    <h2>Helo World!!</h2>
    <p>id:<span id="_id"></span></p>
    <p>data:<span id="_data"></span></p>
    <input type="button" value="getData" onclick="submit()"/>

    <script src="<c:url value="/js/plugins/jquery-1.7.2.min.js"/> "></script>
    <script src="<c:url value="/js/global/common.js"/> "></script>
<script>
    $(document).ready(function() {
    //    submit();
    });

    function submit(){
        $.ajax({
            url: basePath + "getDemo",
            dataType: "json",
            type: "POST",
            async:false,
            success: function (result) {
                alert(JSON.stringify(result));
                        $("#_id").html(result.id);
                        $('#_data').html(JSON.stringify(result));
                    },
            error: function(){
                alert("数据库查询错误")
            }
        });
/*        $.ajax({
            url: basePath + "setDemo",
            dataType: "json",
            type: "POST",
            async:false,
            success: function (result) {
                //                 $("#_id").html(result.openid);
            //    $('#_data').html(JSON.stringify(result));
            },
            error: function(){
                alert("数据库查询错误")
            }
        });*/
    }
</script>
</body>
</html>
