﻿<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>

<%@ include file="/resources/inc/top.jsp" %>



<div class="lap-page">
    <div class="lap-searchbar">
        <label class="input">
            <span>招生年度</span>
            <input type="text" id="search_batchYear" class="form-control">
        </label>

        <label class="sl2">
            <span>招生批次</span>
            <select name="search_batchName" id="search_batchName">
                <option value="${item.batchName}">${item.batchName}</option>
            </select>
        </label>
        <a class="btn search" onclick="search_filter();"></a>
    </div>
    <div class="lap-toolbar">
        <shiro:hasPermission name="enroll:batch:create">
            <a class="btn add" href="javascript:;" onclick="createAction()">新增</a>
        </shiro:hasPermission>
            <%--<a class="btn export" href="javascript:;" onclick="updateAction()">导出</a>--%>
    </div>
    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>

<script>

    $.date({ elem: '#search_batchYear' });

    var $parentTable = $('#table').bootstrapTable({
        classes: 'table table-hover',
        url : '${basePath}/manage/batch/list',
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
        idField : 'batchId',
        maintainSelected : true,
        queryParams: queryParams,
        selectItemName: 'chk',
        columns : [  {
            field : 'chk',
            checkbox : true,
        },  {
            field : 'batchId',
            title : '序号',
            align: 'center'
        }, {
            field : 'batchName',
            title : '批次名称',
            align: 'center'
        }, {
            field : 'batchYear',
            title : '招生年度',
            formatter: 'yearFmt',
            align: 'center'
        }, {
            field : 'startTime',
            title : '开始时间',
            align : 'center',
            formatter: 'timeFmt'
        }, {
            field : 'endTime',
            title : '结束时间',
            align : 'center',
            formatter: 'timeFmt'
        }, {
            field : 'status',
            title : '是否启用',
            formatter: 'statFmt',
            align: 'center'
        }, {
            field: 'action',
            title: '操作',
            align: 'center', // halign 标题对齐
            width: 345,
            formatter: 'actionFmt'
        }],
        rowStyle: function (row, index) {
            return {};
            //if (row.status == 1) return { classes: 'fix'/* css:{"text-align":"left", "padding-left":"68px"} */ }
            //return { css:{"text-align":"center"} };
        },
        cellStyle : function cellStyle(value, row, index) {
            return {}
        },
        // ajax请求成功后
        onLoadSuccess: function(data) {
          /*  $('input').iCheck({
                checkboxClass: 'icheckbox_square',
                radioClass: 'iradio_square',
            });*/
        }
    });

    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-');}

    function yearFmt(value, row, index) { return value + '年度'; }

    function chkFmt(value, row, index) { return (1 + index) + '<input type="checkbox"/>'; }

    function detailFmt(value, row, index) {return ''; }

    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }

    function actionFmt(value, row, index) {
        if (row.status == 1)
            return [
                '<input type="hidden" value="' + row.batchId + '"/>'
                , '<a class="lap-btn btn-disabled">停用</a>'
                , '<a class="lap-btn btn-copy">复制</a>'
                , '<a class="lap-btn btn-view">查看</a>'].join(' ');
        return [
            '<input type="hidden" value="' + row.batchId + '"/>'
            , '<a class="lap-btn btn-edit">编辑</a>'
            , '<a class="lap-btn btn-copy">复制</a>'
            , '<a class="lap-btn btn-enabled">启用</a>'
            , '<a class="lap-btn btn-delete">删除</a>'
            , '<a class="lap-btn btn-view">查看</a>'
        ].join(' ');
    }

    function queryParams(pageReqeust) {
        pageReqeust.search_batchYear = $("#search_batchYear").val();
        pageReqeust.search_batchName = $("#search_batchName").val();
        return pageReqeust;
    }

    //查询按钮参数
    function search_filter() {
        var search_batchYear = $("#search_batchYear").val();
        var search_batchName = $("#search_batchName").val();

        $('#table').bootstrapTable('refresh', {
            url: '${basePath}/manage/batch/list',
            query: {
                search_batchYear: search_batchYear,
                search_batchName: search_batchName
            }
        });
    }

    function _enabled(batchId){
        $.ajax({
            url: '${basePath}/manage/batch/enabled',
            type: 'post',
            dataType: 'json',
            data: {batchId: batchId},
            success: function(result) {
                if (result.code == 0) {
                    $.msg('启用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {
            }
        });
    }


    function _copy(batchId){
        $.open({
            title: '复制批次',
            area: '480px',
            content: '${basePath}/manage/batch/copy/' + batchId
        });
    }

    function _view(batchId){
        _edit(batchId, true);
    }

    function _delete(batchId){
        $.confirm({
            content:'确认要删除吗？'}, {
            area:['400px', '280px']
        }, function() {
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/batch/delete/' + batchId,
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

    function _edit(batchId, readonly) {
        var url = '${basePath}/manage/batch/update/' + batchId,
                title = readonly ? '查看批次' : '编辑批次';
        url += readonly ? '?readonly=1' : '';

        $.open({
            title: title,
            area: '480px',
            content: url
        });
    }

    function _disabled(batchId){
        $.ajax({
            url: '${basePath}/manage/batch/disabled',
            type: 'post',
            dataType: 'json',
            data: {batchId: batchId},
            success: function(result) {
                if (result.code == 0) {
                    $.msg('停用成功');
                    $('#table').bootstrapTable('refresh');
                }
            },
            error: function(result) {}
        });
    }

    function initBatch() {
        $.getJSON('${basePath}/manage/batch/list', {
            limit: "10000"
        }, function (json) {
            var datas = [{id: "", text: '请选择'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].batchName;
                data.text = json.rows[i].batchName;
                datas.push(data);
            }
            $('#search_batchName').empty();
            $('#search_batchName').select2({
                width: 140,
                data: datas
            });
        });
    }

    initBatch();

    function createAction() {
        $.open({
            title: '新增批次',
            area: '480px',
            content: '${basePath}/manage/batch/create'
        });
    }
</script>


