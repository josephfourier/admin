<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContent.request.contextPath}"></c:set>
<%@ include file="/resources/inc/top.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>录取管理</title>
</head>

<div class="lap-page">
    <div class="lap-toolbar">
        <%--<shiro:hasPermission name="enroll:batch:create">--%>
        <%--<a class="btn add" href="javascript:;" onclick="createAction()">新增</a>--%>
        <%--</shiro:hasPermission>--%>
            <shiro:hasPermission name="enroll:template:create">
                <span id="createPermission"></span>
               <%-- <a class="btn add" href="javascript:;" onclick="tempOPT();">学校通知书模板设置</a>--%>
            </shiro:hasPermission>
    </div>
    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>

<script>

    var $table = $('#table');
    var schoolcode=${schoolcode};
    var hasCreatePermission = $('#createPermission').length == 0 ? false : true;

    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/template/list?schoolcode='+schoolcode,
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
            columns : [
                //{field: 'ch', checkbox: false},
                {field: 'templateId', title: '序号', align: 'center'},
                {field: 'templateName', title: '模板名称'},
                //{field: 'templateType', title: '批次名称'},
                {field: 'previewUrl', title: '预览模板', formatter:'PVFormatter'},
                {field: 'status', title: '选择模板',formatter:'tempFormatter'}
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.search_batchYear = $("#search_batchYear").val();
        pageReqeust.search_batchName = $("#search_batchName").val();
        return pageReqeust;
    }

    //状态格式化
    function tempFormatter(value, row, index) {
        if (hasCreatePermission)
            return value == 0 ? ['<a class="lap-btn btn-selected">已选择</a>','<input type="hidden" value="' + row.templateId +'"'].join('') : ['<a class="lap-btn btn-unselected">未选择</a>','<input type="hidden" value="' + row.templateId +'"'].join('');
       return "";
    }

    function PVFormatter(value, row, index) {
        return '<a class="lap-btn  btn-preview" href='+value+'>预览</a>';
    }


    $(document).on('click', '.lap-btn', function(e) {
        var index = '_' + $(this).prop('class').replace(/lap-btn|btn-|\s/gi, '')
                , id = $(this).parents('td').find('input').val()
                , that = this;
        window[index]["call"](that, id);
    });

    function _unselected(id) {
        $.ajax({
            url: '${ctx}/manage/template/tempOPT',
            type: 'post',
            dataType: 'json',
            data: {templateId: id, schoolcode:schoolcode},
            success: function(result) {
                if (result.data == 0) {
                    layer.msg('模板设置成功', {time: 3000});
                    $table.bootstrapTable('refresh',{url:'${basePath}/manage/template/list?schoolcode='+schoolcode});
                }
            },
            error: function(result) {
            }
        });
    }


    // 编辑
    var updateDialog;
    function tempOPT() {
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
            $.ajax({
                url: '${ctx}/manage/template/tempOPT',
                type: 'post',
                dataType: 'json',
                data: {"templateId": rows[0].templateId,"schoolcode":schoolcode},
                success: function(result) {
                    //console.log(result);
                    if (result.data == 0) {
                        $table.bootstrapTable('refresh',{url:'${basePath}/manage/template/list?schoolcode='+schoolcode});
                    }
                },
                error: function(result) {
                }
            });
        }
    }
</script>
