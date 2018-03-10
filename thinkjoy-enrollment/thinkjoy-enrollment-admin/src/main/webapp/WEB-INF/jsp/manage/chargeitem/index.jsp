<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="/resources/inc/top.jsp" flush="true"/>
<style>
    body,html{
        height: 100%;}
</style>
<div class="lap-page">
    <div class="lap-toolbar">
        <shiro:hasPermission name="enroll:chargeitem:create">
            <a class="btn add" href="javascript:;">新增</a>
        </shiro:hasPermission>
    </div>
    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>
<script>

    var $parentTable;
    $(function () {
        // bootstrap table初始化
        $parentTable = $('#table').bootstrapTable({
            url: '${basePath}/manage/chargeitem/list',
            //height: getHeight(),
            striped: true,
            search: false,
            showRefresh: false,
            showColumns: false,
            minimumCountColumns: 2,
            clickToSelect: false,
            detailView: false,
            detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'batchId',
            sortOrder: 'desc',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'itemId', title: '序号', sortable: false, align: 'center'},
                {field: 'itemName', title: '缴费类别', align: 'center'},
                {field: 'creator', title: '创建者', align: 'center'},
                {field: 'ctime', title: '创建时间', align: 'center', formatter: 'timeFmt'},
                {field: 'description', title: '描述', align: 'center'},
                {field: 'status', title: '是否启用', align:'center', formatter: 'statFmt'},
                {field: 'year', title: '年份'},
                {field: 'schoolCode', title: '学校编码'},
                {
                    field: 'action',
                    title: '操作',
                    align: 'left',
                    formatter: 'actionFmt'
                }
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.search_batchYear = $("#search_batchYear").val();
        pageReqeust.search_batchName = $("#search_batchName").val();
        return pageReqeust;
    }

    //查询按钮参数
    function seach_filter() {
        var search_batchYear = $("#search_batchYear").val();
        var search_batchName = $("#search_batchName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/chargedetail/list',
            query: {
                search_batchYear: search_batchYear,
                search_batchName: search_batchName
            }
        });
    }


    //状态格式化
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else if (value == 0) {
            return '<span class="label label-default">锁定</span>';
        }
    }

    // 格式化时间
    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-');}
    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }
    function actionFmt(value, row, index) {
        if (row.status == 1)
            return [
                ' <input type="hidden" value="' + row.itemId + '"/>'
                , '<a class="lap-btn btn-disabled">停用</a>'].join(' ');
        return [
            ' <input type="hidden" value="' + row.itemId + '"/>'
            , '<a class="lap-btn btn-enabled">启用</a>'
            , ' <shiro:hasPermission name="enroll:chargeitem:update"> <a class="lap-btn btn-edit" href="javascript:;">编辑</a></shiro:hasPermission>'
            , ' <shiro:hasPermission name="enroll:chargeitem:delete"> <a class="lap-btn btn-delete" href="javascript:;">删除</a></shiro:hasPermission>'].join(' ');
    }

    function _enabled(id){
        $.ajax({
            url: '${basePath}/manage/chargeitem/updateStatus/' + id,
            type: 'post',
            dataType: 'json',
            data: {status: 1},
            success: function(result) {
                if (result == true) {
                    $.msg('启用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {
            }
        });
    }

    function _disabled(id){
        $.ajax({
            url: '${basePath}/manage/chargeitem/updateStatus/' + id,
            type: 'post',
            dataType: 'json',
            data: {status: 0},
            success: function(result) {
                if (result == true) {
                    $.msg('停用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {
            }
        });
    }

    /*批量删除*/
   /* function deleteAction(batchId){
        var rows = $('#table').bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.msg('请至少选择一条记录', {
                time: 3000
            });
        } else {
            $.confirm({content:'确认要删除吗?'}, {area:['400px', '280px']}
            , function(){
                var ids = new Array();
                for (var i in rows) ids.push(rows[i].itemId);

                $.ajax({
                    type: 'get',
                    url: '${basePath}/manage/chargeitem/delete/' + ids.join("-"),
                    success: function (result) {
                        if (result.code == 1) {
                            $.msg('删除成功');
                            $('#table').bootstrapTable('refresh');
                        } else {
                            $.msg(result.message);
                        }
                    }
                });
            }, function(){
            });
        }
    }*/

    function _edit(itemId) {
        $.open({
            title: '编辑缴费类别',
            offset: 'auto',
            area: ['500px', '325px'],
            content:'${basePath}/manage/chargeitem/update/' + itemId
        });
    }

    function _delete(itemId) {
        $.confirm({content:'确认要删除吗?'}, {area:['400px', '280px']}
            , function(){
                $.ajax({
                    type: 'get',
                    url: '${basePath}/manage/chargeitem/delete/' + itemId,
                    success: function (result) {
                        if (result.code == 1) {
                            $.msg('删除成功');
                            $('#table').bootstrapTable('refresh');
                        } else {
                            $.msg(result.message);
                        }
                    }
                });
            }, function(){});
    }

    /* 更新 */
  /*  function editAction() {
        var rows = $('#table').bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.msg('请选择一条记录', {
                time: 3000
            });
        } else {
            $.open({
                title: '编辑缴费类别',
                offset: 'auto',
                area: ['480px', '310px'],
                content:'${basePath}/manage/chargeitem/update/' + rows[0].itemId
            });
        }
    }
*/
    function addAction() {
        $.open({
            title: '新增缴费类别',
            offset: 'auto',
            area: ['500px', '325px'],
            content: '${basePath}/manage/chargeitem/create'
        });
    }
</script>
<%--
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>缴费类别管理</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="enroll:chargeitem:create"><a class="waves-effect waves-button" href="javascript:;"
                                                                onclick="createAction()"><i class="zmdi zmdi-plus"></i>
            新增缴费类别</a></shiro:hasPermission>
        <shiro:hasPermission name="enroll:chargeitem:update"><a class="waves-effect waves-button" href="javascript:;"
                                                                onclick="updateAction()"><i class="zmdi zmdi-edit"></i>
            编辑缴费类别</a></shiro:hasPermission>
        <shiro:hasPermission name="enroll:chargeitem:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                                onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
            删除缴费类别</a></shiro:hasPermission>
    </div>
    <table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>

    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/chargeitem/list',
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
            idField: 'batchId',
            sortOrder: 'desc',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'itemId', title: '缴费类别ID', sortable: true, align: 'center'},
                {field: 'itemName', title: '缴费类别名称', align: 'center'},
                {field: 'creator', title: '创建者', align: 'center'},
                {field: 'ctime', title: '创建时间', align: 'center', formatter: 'timeFormatter'},
                {field: 'description', title: '描述', align: 'center'},
                {field: 'status', title: '启用状态', formatter: 'statusFormatter'},
                {field: 'year', title: '年份'},
                {field: 'schoolCode', title: '学校编码'}
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.search_batchYear = $("#search_batchYear").val();
        pageReqeust.search_batchName = $("#search_batchName").val();
        return pageReqeust;
    }

    //查询按钮参数
    function seach_filter() {
        var search_batchYear = $("#search_batchYear").val();
        var search_batchName = $("#search_batchName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/chargedetail/list',
            query: {
                search_batchYear: search_batchYear,
                search_batchName: search_batchName
            }
        });
    }

    // 格式化操作按钮
    function actionFormatter(value, row, index) {
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    }

    //状态格式化
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else if (value == 0) {
            return '<span class="label label-default">锁定</span>';
        }
    }

    // 格式化缴费类别图标
    function avatarFormatter(value, row, index) {
        return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
    }

    // 格式化时间
    function timeFormatter(value, row, index) {
        return new Date(value).toLocaleString();
    }
    // 新增
    var createDialog;
    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增缴费类别',
            content: 'url:${basePath}/manage/chargeitem/create',
            onContentReady: function () {
                initMaterialInput();
                $('select').select2();
            }
        });
    }
    // 编辑
    var updateDialog;
    function updateAction() {
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
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: '编辑缴费类别',
                content: 'url:${basePath}/manage/chargeitem/update/' + rows[0].itemId,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2();
                }
            });
        }
    }
    // 删除
    var deleteDialog;
    function deleteAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.confirm({
                title: false,
                content: '请至少选择一条记录！',
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
            deleteDialog = $.confirm({
                type: 'red',
                animationSpeed: 300,
                title: false,
                content: '确认删除该缴费类别吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].itemId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/chargeitem/delete/' + ids.join("-"),
                                success: function (result) {
                                    if (result.code != 1) {
                                        if (result.data instanceof Array) {
                                            $.each(result.data, function (index, value) {
                                                $.confirm({
                                                    theme: 'dark',
                                                    animation: 'rotateX',
                                                    closeAnimation: 'rotateX',
                                                    title: false,
                                                    content: value.errorMsg,
                                                    buttons: {
                                                        confirm: {
                                                            text: '确认',
                                                            btnClass: 'waves-effect waves-button waves-light'
                                                        }
                                                    }
                                                });
                                            });
                                        } else {
                                            $.confirm({
                                                theme: 'dark',
                                                animation: 'rotateX',
                                                closeAnimation: 'rotateX',
                                                title: false,
                                                content: result.data.errorMsg,
                                                buttons: {
                                                    confirm: {
                                                        text: '确认',
                                                        btnClass: 'waves-effect waves-button waves-light'
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        deleteDialog.close();
                                        $table.bootstrapTable('refresh');
                                    }
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    $.confirm({
                                        theme: 'dark',
                                        animation: 'rotateX',
                                        closeAnimation: 'rotateX',
                                        title: false,
                                        content: textStatus,
                                        buttons: {
                                            confirm: {
                                                text: '确认',
                                                btnClass: 'waves-effect waves-button waves-light'
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    },
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        }
    }
</script>
</body>
</html>--%>
