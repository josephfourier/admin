<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContent.request.contextPath}"></c:set>
<%@ include file="/resources/inc/top.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>缴费项目管理</title>
</head>

<div class="lap-page">
    <div class="lap-toolbar">
        <shiro:hasPermission name="enroll:student:create">
            <a class="btn add" href="javascript:;">新增</a>
        </shiro:hasPermission>
        <%--<shiro:hasPermission name="enroll:student:delete">--%>
        <%--<a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i>删除报名信息</a>--%>
        <%--</shiro:hasPermission>--%>
        <%--<a class="btn export" href="javascript:;">导出</a>--%>
        
      <%--  <shiro:hasPermission name="enroll:student:delete">
            <a href="javascript:;" class="btn delete">删除</a>
        </shiro:hasPermission>--%>

    </div>
    <div class="lap-table">
        <table id="table"></table>
        <form method="get" action="aa" id="passForm">
            <input id="code" type="hidden" name="code">
        </form>
    </div>
</div>

<script>
    var $parentTable = $('#table').bootstrapTable({
        classes: 'table table-hover',
        url : '${basePath}/manage/chargedetail/list',
        striped : true,
        search : false,
        showRefresh : false,
        showColumns : false,
        minimumCountColumns : 2,
        clickToSelect : false,
        singleSelect: false, //禁止多选
        detailView : false,
        detailFormatter : 'detailFmt',
        pagination : true,
        paginationLoop : false,
        pageList: null,
        sidePagination : 'server',
        silentSort : false,
        smartDisplay : false,
        escape : true,
        searchOnEnterKey : true,
        idField : 'detailId',
        maintainSelected : true,
        queryParams: queryParams,
        selectItemName: 'chk',
        columns : [
            {field : 'chk', checkbox : true},
            {field : 'detailId', title : '缴费项目ID', align: 'center'},
            {field : 'detailName', title : '缴费项目名称', align: 'center'},
            {field : 'itemName', title : '缴费类别名称', align: 'center'},
            {field : 'money', title : '费用', align: 'center'},
            {field : 'isMust', title : '是否必须', align: 'center',formatter: 'statFmt'},
            {field: 'creator', title: '创建者',align: 'center'},
            {field: 'ctime', title: '创建时间',align: 'center',formatter: 'timeFmt'},
            //{field: 'status', title: '启用状态',align: 'center',formatter: 'statusFormatter'},
            {field: 'year', title: '年份',align: 'center'},
            {field: 'schoolCode', title: '学校编码',align: 'center'},
            {field: 'description', title: '描述',align: 'center'},
            {
                field: 'action',
                title: '操作',
                align: 'center',
                width: 300,
                formatter: 'actionFmt'
            }
        ],
        rowStyle: function (row, index) {
            return {};
        },
        cellStyle : function cellStyle(value, row, index) {
            return {}
        }
    });

    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-');}

    function chkFmt(value, row, index) { return (1 + index) + '<input type="checkbox"/>'; }

    function detailFmt(value, row, index) {return ''; }

    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }

    function actionFmt(value, row, index) {
        if (row.status == 1)
            return [
                '<input type="hidden" value="' + row.detailId + '"/>'
                , '<a class="lap-btn btn-disabled">停用</a>'
                , '<shiro:hasPermission name="enroll:student:update"><a class="lap-btn btn-view">查看</a></shiro:hasPermission>'].join(' ');
        return [
            '<input type="hidden" value="' + row.detailId + '"/>'
            , '<a class="lap-btn btn-edit">编辑</a>'
            , '<a class="lap-btn btn-enabled">启用</a>'
            , '<shiro:hasPermission name="enroll:student:delete"><a class="lap-btn btn-delete">删除</a></shiro:hasPermission>'
            , '<shiro:hasPermission name="enroll:student:update"><a class="lap-btn btn-view">查看</a></shiro:hasPermission>'
        ].join(' ');
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
    function timeFormatter(value, row, index) {
        return new Date(value).toLocaleString();
    }

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

    function _enabled(detailId){
        $.ajax({
            url: '${basePath}/manage/chargedetail/enabled',
            type: 'post',
            dataType: 'json',
            data: {detailId: detailId},
            success: function(result) {
                if (result.code == 0) {
                    layer.msg('启用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {
            }
        });
    }
    function _disabled(detailId){
        $.ajax({
            url: '${basePath}/manage/chargedetail/disabled',
            type: 'post',
            dataType: 'json',
            data: {detailId: detailId},
            success: function(result) {
                if (result.code == 0) {
                    layer.msg('停用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {
            }
        });
    }

    //查看
    function _view(detailId){
        _edit(detailId, true);
    }
    //修改
    function _edit(detailId, readonly) {
        var url = '${basePath}/manage/chargedetail/update/' + detailId,
                title = readonly ? '查看缴费项目' : '编辑缴费项目';
        url += readonly ? '?readonly=1' : '';

        var h = readonly ? '400px' : '485px';
        $.open({
            title: title,
            area: ['750px', h],
            content: url
        });
    }

    //刪除
    function _delete(detailId){
        $.confirm({
            content: '确认要删除吗?'
        }, {
            area:['400px', '280px']
        }, function() {
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/chargedetail/delete/' + detailId,
                success: function (result) {
                    if (result.code == 1) {
                        layer.msg('删除成功');
                        $('#table').bootstrapTable('refresh');
                    } else {
                        layer.msg(result.message);
                    }
                }
            });
        });
    }

/*
 * 工具栏事件定义
 *
 */
    function addAction() {
        $.open({
            title: '新增缴费项目',
            area: ['750px', '485px'],
            content: '${basePath}/manage/chargedetail/create'
        });
    }

    //批量删除
    function deleteAction() {
        var rows = $('#table').bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.msg('请选择一条记录');
        } else {
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].batchId);
            }
            $.confirm( {content: '确认要删除吗?'}, {
                area:['400px', '280px']
            }, function() {
                $.ajax({
                    type: 'get',
                    url: '${basePath}/manage/chargedetail/delete/' + ids.join('-'),
                    success: function (result) {
                        if (result.code == 1) {
                            $.msg('删除成功');
                            $('#table').bootstrapTable('refresh');
                        } else {
                            $.msg(result.message);
                        }
                    }
                });
            });
        }
    }
</script>
