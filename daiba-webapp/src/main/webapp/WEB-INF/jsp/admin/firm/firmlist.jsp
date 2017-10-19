<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2016/12/10
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户</title>
    <%@include file="../include/common.jsp" %>
</head>
<body class="overflow-hidden">
<div class="wrapper preload">
    <%@include file="../include/head.jsp"%>
    <%@include file="../include/menu.jsp"%>

    <div class="main-container">
        <div class="padding-md">

            <div class="sidebar-fix-bottom clearfix">
                <div class="pull-left font-16">
                    <i class="fa fa-home fa-lg"></i>
                    <a href="<%=basePath%>Admin/home">&nbsp; 首页</a>
                    <i class="fa fa-angle-right"></i>
                    <a href="<%=basePath%>Admin/user">订单</a>
                </div>
                <span class="pull-right font-18" id="nowTime"></span>
            </div>
            <div class="container-fluid margin-md">
                <table id="firm-list" class="table table-responsive table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>订单号</th>
                            <th>校区</th>
                            <th>下单时间</th>
                            <th>发单人</th>
                            <th>带客</th>
                            <th>订单状态</th>
                            <th>订单类型</th>
                            <th>金额</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>

        </div>
    </div>

</div>
</body>
</html>
<script type="text/javascript" src="<c:url value="/admin/pulgins/DataTables-1.10.11/media/js/jquery.dataTables.js"/>"></script>
<script>
    $(function () {
        window.setInterval(setNowTime, 1000);
        var table = $("#firm-list").DataTable({
            "bStateSave": true,
            "order": [[ 1, 'asc' ]],
            "autoWidth": true,   // enable/disable fixed width and enable fluid table
            "processing": true, // enable/disable display message box on record load
            "language": {
                "decimal": "",
                "emptyTable": "没有数据",
                "info": "当前为 _START_ 至 _END_ 号记录 , 一共 _TOTAL_ 条记录",
                "infoEmpty": "当前为 0 至 0 号记录 , 一共 0 条记录",
                "infoFiltered": "(找到 _MAX_ 条记录)",
                "infoPostFix": "",
                "thousands": ",",
                "lengthMenu": "显示 _MENU_ 条记录",
                "loadingRecords": "加载中...",
                "processing": "加载中...",
                "search": "查询:",
                "zeroRecords": "没有找到匹配记录",
                "paginate": {
                    "first": "第一页",
                    "last": "最后一页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            },
            "ajax": function (data, fnCallback) {
                $.ajax({
                    "url": basePath + "/Admin/loadFirms.do",
                    "dataType": 'json',
                    "type": "POST",
                    "success": function (result) {
                        fnCallback(result);
                    },
                    "error": function () {
                    }
                });
            },
            "columns": [
                { data: "firmId" ,"bSortable": false},
                {
                    "data": "order.acceptAddCode" ,
                    "render": function(data, type, full) {
                        var campusCode=data.substring(0,2);
                       if(campusCode=='11'){
                            return "南湖校区"
                       }else if (campusCode=='12'){
                           return "林园校区"
                       }
                    }
                },
                {
                    "data":'giveTime',
                    "render": function(data, type, full) {
                        var date=new Date();
                        date.setTime(data.time)
                        return format(date, "yyyy-MM-dd    hh:mm:ss");
                     }
                },
                { data: "user.name","bSortable": false  },
                { data: "order.receiver","bSortable": false  },
                {
                    "data": 'orderState',
                    "render": function (data, type, full) {
                        switch (data){
                            case 0:
                                return "未接单";
                                break;
                            case 1:
                                return "已接单";
                            break;
                            case 2:
                                return "已完成"
                                break;
                            case 3:
                                return "已取消";
                                break;
                            case 4:
                                return "待付款";
                                break;
                        }
                    }
                },
                {
                    data: 'order.staId',
                    "render": function (data, type, full) {
                        switch (data){
                            case 30:
                                return "快递";
                                break;
                            case 31:
                                return "外卖";
                                break;
                        }
                    }
                },
                {
                    "data": 'orderMoney',
                    "render": function (data, type, full) {
                      return data*0.01;
                    }
                }
            ],
            "columnDefs": [
                {
                    "targets": [8],
                    "data": "firmId",
                    "render": function(data, type, full) {
                        return "<a href='<%=basePath%>Admin/lookupFirmDetail?firmId=" + data + "'>查看详情</a>";
                    }
                }
            ]
        });
        table.on( 'order.dt search.dt', function () {
            table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();
    });

    function setNowTime() {
        var nowDate = format(new Date(), "yyyy-MM-dd    hh:mm:ss");
        $("#nowTime").html(nowDate);
        var nowCountdown = "离下一次刷新还有 " + setCountdown() + " s";
        $("#countdown").html(nowCountdown);
    }

</script>