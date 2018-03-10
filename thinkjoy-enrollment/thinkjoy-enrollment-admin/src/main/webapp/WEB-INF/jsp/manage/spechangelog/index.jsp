<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContent.request.contextPath}"></c:set>
<%@ include file="/resources/inc/top.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>专业变更日志</title>
</head>
<style>
    /*
        html,body{height:100%;}
    */
</style>

<div class="lap-page">
    <div class="lap-searchbar">
        <label class="input">
            <span>年度</span>
            <input id="search_year" type="text" class="form-control" name="search_year" onchange="setyear();">
        </label>
        <label class="sl2">
            <span>批次名称</span>
            <select class="ex" name="serarch_batchName" id="serarch_batchName">
                <option value="">请选择批次</option>
                <c:forEach var="batchList" items="${batchList}">
                    <option value="${batchList.batchName}">${batchList.batchName}</option>
                </c:forEach>
            </select>
        </label>
        <label class="input">
            <span>姓名</span>
            <input id="serarch_studentName" type="text" class="form-control" name="serarch_studentName">
        </label>
        <a class="btn search" onclick="search_filter();"></a>
    </div>
    <div class="lap-toolbar">
        <%--<shiro:hasPermission name="enroll:batch:create">--%>
        <%--<a class="btn add" href="javascript:;" onclick="createAction()">新增</a>--%>
        <%--</shiro:hasPermission>--%>
        <%--<a class="btn export" href="javascript:;" onclick="updateAction()">导出</a>--%>

    </div>
    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>

<script>
    var thisYear = new Date().getFullYear()
            , schoolcode = ${schoolcode};

    $.date({
        elem: '#search_year',
        //value: '2015',
        ready: function(date){
            fillBatchSelect2();
        },
        change: function(value, date, endDate){
            thisYear = date.year;
            fillBatchSelect2(); // 回调根据年度加载批次
        }
    });

    //根据年度加载招生批次
    function fillBatchSelect2() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/enroll/batchyear',
            data: {year:thisYear, schoolcode:schoolcode},
            success: function(data){
                var jdata = JSON.parse(data);

                var datas = [];
                for (var i = 0; i < jdata.length; ++i) {
                    datas.push({id:jdata[i].batchName, text: jdata[i].batchName});
                }

                $('#serarch_batchName').empty().select2({
                    width: 140,
                    data: datas
                });
            }
        });
    }
    $('select').select2({ width: 140, minimumResultsForSearch: 6});

    $('#table').bootstrapTable({
        classes: 'table table-hover',
        url : '${basePath}/manage/spechangelog/list?&schoolcode='+schoolcode,
        method: 'get',
        contentType: 'application/x-www-form-urlencoded', // 默认为json
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
        idField : 'changeId',
        maintainSelected : true,
        queryParams: queryParams,
        selectItemName: 'chk',
        columns : [
            {field : 'chk', checkbox : true},
            {field : 'changeId', title : '编号', align: 'center'},
            {field : 'batchYear', title : '招生年度', align: 'center'},
            {field : 'batchName', title : '批次名称', align: 'center'},
            {field : 'studentName', title : '姓名', align: 'center'},
            {field : 'sex', title : '性别', align: 'center',formatter: 'sexFormatter'},
            {field: 'idcard', title: '身份证号码',align: 'center'},
            {field: 'beforeSpecialty', title: '变更前专业',align: 'center'},
            {field: 'afterSpecialty', title: '变更后专业',align: 'center'},
            {field: 'modifier', title: '修改人',align: 'center'},
            {field: 'mtime', title: '修改时间',align: 'center',formatter: 'timeFmt'},
            {
                field: 'action',
                title: '操作',
                align: 'center', // halign 标题对齐
                formatter: 'actionFmt'
            }
        ],
        rowStyle: function (row, index) {
            return {};
        },
        cellStyle : function cellStyle(value, row, index) {
            return {}
        },
    });

    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-');}

    function chkFmt(value, row, index) { return (1 + index) + '<input type="checkbox"/>'; }

    function detailFmt(value, row, index) {return ''; }

    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }

    function actionFmt(value, row, index) {
        if (row.status == 1)
            return [
                '<input type="hidden" value="' + row.changeId + '"/>'
                , '<a class="lap-btn btn-delete">删除</a>'].join(' ');
        return [
            '<input type="hidden" value="' + row.changeId + '"/>'
            , '<a class="lap-btn btn-delete">删除</a>'
            , '<a class="lap-btn btn-view">查看</a>'
        ].join(' ');
    }
    //格式化性别
    function sexFormatter(value, row, index) {
        if (value == 0) {
            return '男';
        } else if(value == 1){
            return '女';
        }
    }
    //格式化缴费状态
    function feeFormatter(value, row, index) {
        if (value == 1) {
            return '未缴费';
        } else if(value == 2){
            return '预缴费';
        }else if(value == 3){
            return '已缴费';
        }else if(value == 4){
            return '待退费';
        }else if(value == 5){
            return '已退费';
        }
    }
    // 格式化学制
    function xzFormatter(value, row, index) {
        if (value == 101) {
            return '3年';
        } else {
            return '5年';
        }
    }
    //格式化录取状态
    function enrollFormatter(value, row, index) {
        if (value == 1) {
            return '未录取';
        } else if(value == 2){
            return '预录取';
        }else if(value == 3){
            return '已录取';
        }else if(value == 4){
            return '已录取被删除';
        }
    }
    // 格式化状态
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else {
            return '<span class="label label-default">锁定</span>';
        }
    }

    function queryParams(pageReqeust) {
        pageReqeust.search_year = $("#search_year").val();
        pageReqeust.serarch_batchName = $("#serarch_batchName").val();
        pageReqeust.serarch_firstVolcode = $("#serarch_firstVolcode").val();
        pageReqeust.serarch_enrollStatus = $("#serarch_enrollStatus").val();
        pageReqeust.serarch_studentName = $("#serarch_studentName").val();
        pageReqeust.schoolcode;
        return pageReqeust;
    }

    //查询按钮参数
    function search_filter() {
        var search_year = $("#search_year").val();
        var serarch_batchName=$("#serarch_batchName").val();
        var serarch_studentName = $("#serarch_studentName").val();

        $('#table').bootstrapTable('refresh', {
            url: '${basePath}/manage/spechangelog/list',
            query: {
                search_year: search_year,
                serarch_batchName: serarch_batchName,
                serarch_studentName:serarch_studentName,
                schoolcode:schoolcode
            }
        });
    }

    /* 绑定事件 */
    $(document).on('click', '.lap-btn', function(e) {
        var index = '_' + $(this).prop('class').replace(/lap-btn|btn-|\s/gi, '')
                , id = $(this).parents('td').find('input').val()
                , that = this;
        window[index + 'Batch']["call"](that, id);
    });


    function _view(batchId){
        _edit(batchId, true);
    }

    function _delete(batchId){
        layer.confirm('<div class="msg">确认要删除吗？</div>', {
            skin: 'common confirm',
            title: '提示',
            area:['400px', '280px'],
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/spechangelog/delete/' + batchId,
                success: function (result) {
                    if (result.code == 1) {
                        layer.msg('删除成功');
                        $('#table').bootstrapTable('refresh');
                    } else {
                        layer.msg(result.message);
                    }
                }
            });
        }, function(){
        });
    }
</script>
