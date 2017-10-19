<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2016/10/4
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>

<link href="<c:url value="/css/dialog.css"/>" rel="stylesheet"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="md-modal md-effect-4" id="modal-4">
    <div class="md-content">
        <h3 id="md-title"></h3>
        <div>
            <span id="md-content"></span>
            <ul id="md-content-list" hidden>
            </ul>
            <form id="md-form" class="mui-input-group" hidden method="post">
                <div class="mui-input-row">
                    <input id='invitationCode' type="text" class="mui-input-clear mui-input"
                           placeholder="请输入邀请码"/>
                </div>
            </form>
            <button class="md-close mui-btn mui-btn-block mui-btn-primary" type="button" id="md-cancle">取消</button>
            <button class="md-confirm mui-btn mui-btn-block mui-btn-primary" type="button" id="md-confirm">确认</button>
        </div>
    </div>
</div>

<!-- 遮罩页面 -->
<div class="md-overlay"></div>

<script>
    // this is important for IEs
    var polyfilter_scriptpath = '/js/';
</script>

<script src="<c:url value="/js/dialog/dialog.js"/> "></script>

