<%--
  Created by IntelliJ IDEA.
  User: Tangzuopeng
  Date: 2016/10/1
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
  <title></title>
</head>
<body>
<%@include file="../../include/common.jsp" %>
  <form>
    <input type="button" id="pay" value="支付" />
  </form>
</body>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">

  function init(){
    $.ajax({
      url: basePath + '/WeiXin/JSSDK/getJSSDK.do',
      data: {
        mUrl: basePath + '/WeiXin/pay/'
      },
      dataType: 'json',
      success: function(result){
        wx.config({
          debug: true,
          appId: result.appId,
          timestamp: result.timestamp,
          nonceStr: result.nonceStr,
          signature: result.signature,
          jsApiList: ['chooseWXPay']
        });
      },
      error: function(){
        mui.alert("请检查你的网络连接状态！", function () {
          mui.back();
        });
      }
    });
  }

  $(document).ready(function() {
    init();
    document.getElementById('pay').addEventListener('tap', function(){
      $.ajax({
        url: basePath + '/WeiXin/pay/pay.do',
        dataType: 'json',
        success: function(result){
          wx.chooseWXPay({
            timestamp: result.timeStamp, // 支付签名时间戳 注意这里的s 文档新版大写 但是我的小写才好使
            nonceStr: result.nonceStr, // 支付签名随机串
            package: result.package, // 统一支付接口返回的package包
            signType: result.signType, // 签名方式，'MD5'
            paySign: result.paySign, // 支付签名
            success: function (res) {
              if (res.err_msg == "get_brand_wcpay_request:ok") {

                alert("支付成功");

                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
              }
              else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                alert("cancel");
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
              }
              else if (res.err_msg == "get_brand_wcpay_request:fail") {
                alert("fail");
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
              }
            }
          });
        },
        error: function(){
          mui.toast("获取支付信息失败！");
        }
      })
    });
  });
</script>
</html>
