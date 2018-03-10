<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>

<%@ include file="/resources/inc/top.jsp" %>

<div class="lap-page">
    <div class="lap-searchbar">
        <label class="input">
            <span>招生年度</span>
            <input class="form-control" name="search_batchYear" id="search_batchYear">
        </label>

        <label class="input">
            <span>教工号</span>
            <input type="text" class="form-control" id="search_teacherCode">
        </label>
        <a class="btn search" onclick="search_filter();"></a>
    </div>

    <div class="lap-toolbar">
        <shiro:hasPermission name="enroll:teacher:create">
            <a class="btn add" href="javascript:;" onclick="createAction()">新增</a></shiro:hasPermission>
            <%--<a class="btn export" href="javascript:;" onclick="updateAction()">导出</a>--%>
        <shiro:hasPermission name="enroll:teacher:update">
            <input type="hidden" id="updatePermission" value="1">
        </shiro:hasPermission>
        <shiro:hasPermission name="enroll:teacher:delete">
            <input type="hidden" id="deletePermission" value="1">
        </shiro:hasPermission>
    </div>


    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>

<script>
    var w = LAP.util.getContentWidth() + 500;

    var relationCode = '${relationCode}';
    var $table = $('#table');

    $.date({ elem: '#search_batchYear' });

    // bootstrap table初始化
    var $parentTable = $table.bootstrapTable({
        url: '${basePath}/manage/teacher/list',
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
        idField: 'enrollteacherId',
        sortOrder: 'desc',
        maintainSelected: true,
        toolbar: '#toolbar',
        queryParams: queryParams,
        columns: [
            {field: 'ck', checkbox: true},
            {field: '', title: '序号', align: 'center', formatter: 'idFmt'},
            {field: 'batchName', title: '招生批次', align: 'center', width: 200},
            {field: 'batchYear', title: '招生年度', align: 'center'},
            {field: 'teacherCode', title: '工号'},
            {field: 'teacherName', title: '姓名', align: 'center', width: 70},
//            {field: 'departmentName', title: '隶属部门', align: 'center'},
            {field: 'phone', title: '手机号码', align: 'center'},
            {field: 'area', title: '负责区域', align: 'center', width: 400},
//                {field: 'ctime', title: '创建时间', align: 'center',formatter: 'timeFmt'},
            //{field: 'description', title: '描述', align: 'center'},
//              {field: 'status', title: '启用状态', formatter: 'statFmt', align: 'center'},
//              {field: 'year', title: '年份'},
//             {field: 'schoolCode', title: '学校编码'},
            {
                field: 'action',
                title: '操作',
                align: 'center', // halign 标题对齐
                width: 345,
                formatter: 'actionFmt'
            }
        ]
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.search_batchYear = $("#search_batchYear").val();
        pageReqeust.search_teacherCode = $("#search_teacherCode").val();
        //pageReqeust.schoolCode = relationCode;
        return pageReqeust;
    }

    //查询按钮参数
    function search_filter() {
        var search_batchYear = $("#search_batchYear").val();
        var search_teacherCode = $("#search_teacherCode").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/teacher/list',
            query: {
                //schoolCode: relationCode,
                search_batchYear: search_batchYear,
                search_teacherCode: search_teacherCode
            }
        });
    }

    function actionFmt(value, row, index) {
        var result =  '<input type="hidden" value="' + row.enrollteacherId + '"/>';
        result += '<a class="lap-btn btn-view">查看</a>';

        if (typeof $('#updatePermission') != 'undefined')
            result += '<a class="lap-btn btn-edit">编辑</a>';
        if (typeof $('#deletePermission') != 'undefined')
            result += '<a class="lap-btn btn-delete">删除</a>';

        return result;
    }

    function idFmt(value, row, index) {
        return index + 1;
    }
    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-');}


    //状态格式化
    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }


    // 格式化招生老师图标
    function avatarFormatter(value, row, index) {
        return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
    }

    // 格式化时间
    function timeFormatter(value, row, index) {
        return new Date(value).toLocaleString();
    }


    function _delete(enrollteacherId){
        $.confirm({
            content: '确认要删除吗?'}, {
            area:['400px', '280px']
        }, function(){
            $.ajax({
                type: 'get',
                url: ' ${basePath}/manage/teacher/delete/' + enrollteacherId,
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

    function _view(enrollteacherId){
        _edit(enrollteacherId, true);
    }

    function _edit(enrollteacherId, readonly) {
        var url = '${basePath}/manage/teacher/update/' + enrollteacherId
                , title = readonly ? '查看招生人员' : '编辑招生人员';
        
        url += readonly ? '?readonly=1' : '';

        $.open({
            title: title,
            offset: 'auto',
            area: ['65%', '400px'],
            content: url
        });
    }

    function createAction() {
        $.open({
            title: '新增招生人员',
            area: ['65%', '400px'],
            content: '${basePath}/manage/teacher/create'
        });
    }

</script>
