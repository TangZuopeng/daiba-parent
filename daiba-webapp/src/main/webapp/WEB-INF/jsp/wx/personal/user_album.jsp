<%--
  Created by IntelliJ IDEA.
  User: TinyDolphin
  Date: 2016/9/17
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>带吧网络</title>
</head>
<body>

<%@include file="../../include/common.jsp" %>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">相册</h1>
</header>

<div class="mui-content">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">

        </div>
    </div>
</div>
</body>
<script src="<c:url value="/js/personal/user_album.js"/> "></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    mui.init();
    $(document).ready(function () {
        $('.mui-scroll-wrapper').scroll({
            indicators: true //是否显示滚动条
        });
    });
</script>


</html>
