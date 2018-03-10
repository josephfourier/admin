<%@ page import="com.thinkjoy.common.base.BaseConstants" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户基础信息</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div style="width: 20%; height: 100%;float:left;" id="tree">
        <table style="">
            <ul id="ztree" class="ztree"></ul>
        </table>
    </div>
    <div style="width: 80%; height: 100%;float:right" id="tb">
        <div class="form-inline search-form">


                <div class="search-group">
                    <label>用户类别:</label>
                    <select class="form-control" id="search_usertype" name="search_usertype" onchange="seach_filter()">
                        <option value="">请选择</option>
                        <option value="1">学生</option>
                        <option value="3">老师</option>
                    </select>
                </div>

                <div class="search-group">
                    <label>账号/全名:</label>
                    <input type="text" class="form-control" id="serarch_fullname" name="serarch_fullname">
                </div>



                <div class="search-group">
                    <button class="btn btn-primary" onclick="seach_filter()">查询</button>
                </div>
        </div>

        <div id="toolbar">
            <%--<shiro:hasPermission name="ams:user:role"><a class="waves-effect waves-button" href="javascript:;"--%>
                                                         <%--onclick="approle()"><i class="zmdi zmdi-plus"></i>--%>
                <%--分配角色</a></shiro:hasPermission>--%>
            <shiro:hasPermission name="ams:user:role"><a class="waves-effect waves-button" href="javascript:;"
                                                         onclick="userapp()"><i class="zmdi zmdi-plus"></i>
                批量分配权限</a>
            </shiro:hasPermission>

                <shiro:hasPermission name="ams:user:role"><a class="waves-effect waves-button" href="javascript:;"
                                                             onclick="searchuserapp()"><i class="zmdi zmdi-edit"></i>
                    查看个人权限</a>
                </shiro:hasPermission>
        </div>
        <table id="table"></table>
    </div>

</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var relationCode = '';
    var setting = {
        check: {
            enable: false,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "p", "N": "s"}
        },
        async: {
            enable: true,
            url: '${basePath}/manage/approle/ucenteruser/agencytree'
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };
    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        var serarch_fullname=$("#serarch_fullname").val();
        var search_usertype=$("#search_usertype").val();


        //console.log("名称："+serarch_fullname);
        pageReqeust.relationCode=relationCode;
        pageReqeust.serarch_fullname=serarch_fullname;
        pageReqeust.search_usertype=search_usertype;

        return pageReqeust;
    }

    function seach_filter() {
        var relationCode=$("#relationCode").val();
        var serarch_fullname=$("#serarch_fullname").val();
        var search_usertype=$("#search_usertype").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/approle/ucenteruser/list',
            query: {
                serarch_fullname:serarch_fullname,
                search_usertype:search_usertype,
                relationCode:relationCode
            }
        });
    }
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var nodes = zTree.getSelectedNodes();
        var v = "";
        var vt = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            v = nodes[i].name;
            relationCode = nodes[i].code;
        }
        $table.bootstrapTable('refresh', {url: '${basePath}/manage/approle/ucenteruser/list'});
    }

    $(function () {
        var userType = ${upmsUser.managerType};
        if (userType == 3) {
            $('#tree').hide();
            $('#tb').attr('style', "");
        } else {
            $.fn.zTree.init($('#ztree'), setting);
        }
    })
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/approle/ucenteruser/list',
            height: getHeight(),
            striped: true,
            search: false,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            detailView: true,
            detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'userId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'userId', title: '编号'},
                {field: 'usertype', title: '用户类型',  align: 'center'},
                {field: 'username', title: '账号', align: 'center'},
                {field: 'fullname', title: '全名',  align: 'center'},
//                {field: 'nickname', title: '昵称',  align: 'center'},
                {field: 'idcard', title: '身份证号', align: 'center'},
                {field: 'phone', title: '电话号码', align: 'center'},
                {field: 'sex', title: '性别',  align: 'center', formatter: 'sexFormatter'},
//                {field: 'email', title: '邮箱',  align: 'center'},
//                {field: 'studentCode', title: '学号',  align: 'center'},
//                {field: 'examineeNumber', title: '考生号', align: 'center'},
//                {field: 'description', title: '描述'},

                {field: 'status', title: '状态',  align: 'center', formatter: 'statusFormatter'}

            ]
        });
    });

    //分配角色
    var approleDialog;
    function approle() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: '请选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        } else {
            ucenterUserId = rows[0].userId;

            if (rows[0].status == <%=BaseConstants.Status.LOCKING%>){
                $.confirm({
                    title: false,
                    content: '该账户信息已锁定,不能进行分配操作！',
                    autoClose: 'cancel|3000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: '取消',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
                return;
            }
            approleDialog = $.dialog({
                animationSpeed: 300,
                title: '分配角色',
                content: 'url:${basePath}/manage/approle/ucenteruser/approle/' + ucenterUserId,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    //分配应用
    var userappDialog;
    function userapp() {


        var usertype=$("#search_usertype").val();

        if(usertype==""){
            $.confirm({
                title: false,
                content: '请选择用户类别！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
            return;
        }

        var rows = $table.bootstrapTable('getSelections');
        if (rows.length ==0) {
            $.confirm({
                title: false,
                content: '请选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
            return;
        } else {

            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].userId);
            }

            console.log(ids);

            userappDialog = $.dialog({
                animationSpeed: 300,
                title: '分配应用',
                content: 'url:${basePath}/manage/approle/ucenteruser/userapp/' + ids.join("-")+"?usertype="+usertype,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    function searchuserapp(){

        var usertype=$("#search_usertype").val();
        if(usertype==""){
            $.confirm({
                title: false,
                content: '请选择用户类别！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
            return;
        }

        var rows = $table.bootstrapTable('getSelections');
        if (rows.length !=1) {
            $.confirm({
                title: false,
                content: '请选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
            return;
        } else {

            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].userId);
            }
            console.log(ids);
            userappDialog = $.dialog({
                animationSpeed: 300,
                title: '分配应用',
                content: 'url:${basePath}/manage/approle/ucenteruser/userapp/' + ids.join("-")+"?usertype="+usertype,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    // 格式化操作按钮
    function actionFormatter(value, row, index) {
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    }
    // 格式化状态
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else {
            return '<span class="label label-default">锁定</span>';
        }
    }

    //格式化性别
    function sexFormatter(value, row, index) {
        if (value == 0) {
            return '男';
        }
        if (value == 1) {
            return '女';
        }
        return '-';
    }

</script>
</body>
</html>