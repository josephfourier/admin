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
        <label class="sl2">
            <span>专业</span>
            <select class="ex" name="serarch_firstVolcode" id="serarch_firstVolcode">
                <option value="">请选择专业</option>
                <c:forEach var="specialtyList" items="${specialtyList}">
                    <option value="${specialtyList.specialtyCode}">${specialtyList.specialtyName}</option>
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
            //fillSpecialty();
        },
        change: function(value, date, endDate){
            thisYear = date.year;
            fillBatchSelect2(); // 回调根据年度加载批次
            //fillSpecialty();
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

    //根据年度加载专业
    function fillSpecialty() {
        //console.log("加载专业数据");
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/enroll/specialtyyear',
            data: {year:thisYear, schoolcode:schoolcode},
            success: function(data){
                var jdata = JSON.parse(data);
                var datas = [];
                for (var i = 0; i < jdata.length; ++i) {
                    datas.push({id:jdata[i].specialtyCode, text: jdata[i].specialtyName});
                }
                $('#serarch_firstVolcode').empty().select2({
                    width: 140,
                    data: datas
                });
            }
        });
    }


    $('select').select2({ width: 140, minimumResultsForSearch: 6});

    var $parentTable = $('#table').bootstrapTable({
        classes: 'table table-hover',
        url : '${basePath}/manage/recruit/list?schoolcode='+schoolcode,
        method: 'get',
        //contentType: 'application/x-www-form-urlencoded', // 默认为json
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
        idField : 'studentId',
        maintainSelected : true,
        queryParams: queryParams,
        selectItemName: 'chk',
        columns : [
            {field : 'chk', checkbox : true},
            {field : 'studentId', title : '编号', align: 'center'},
            {field : 'batchYear', title : '招生年度', align: 'center'},
            {field : 'batchName', title : '批次名称', align: 'center'},
            {field : 'studentName', title : '姓名', align: 'center'},
            {field : 'sex', title : '性别', align: 'center',formatter: 'sexFormatter'},
            {field: 'phone', title: '联系电话',align: 'center'},
            {field: 'specialtyName', title: '专业',align: 'center'},
            {field: 'schoolSystem', title: '学制',align: 'center',formatter: 'xzFormatter'},
            {field: 'score', title: '成绩',align: 'center'},
            {field: 'feeStatus', title: '缴费状态',align: 'center',formatter: 'feeFormatter'},
            {field: 'enrollStatus', title: '录取状态',align: 'center',formatter: 'enrollFormatter'},
            {
                field: 'action',
                title: '操作',
                align: 'center',
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
        return [
            '<input type="hidden" value="' + row.studentId + '"/>'
            , '<a class="lap-btn btn-edit">修改专业</a>'
            , '<a class="lap-btn btn-view">查看</a>'
        ].join(' ');
    }
    //格式化性别
    function sexFormatter(value, row, index) {
        return value == 0 ? '男' : '女';
    }
    //格式化缴费状态
    var feeStat = [
        '未缴费',
        '预缴费',
        '已缴费',
        '待退费',
        '已退费'
    ];
    function feeFormatter(value, row, index) {
        return feeStat[value - 1];
    }
    // 格式化学制
    function xzFormatter(value, row, index) { return value == 101 ? '3年' : '5年'; }

    var enrollStat = [
        '未录取',
        '预录取',
        '已录取',
        '已录取被删除'
    ];
    //格式化录取状态
    function enrollFormatter(value, row, index) { return enrollStat[value - 1]; }
    // 格式化状态
    function statusFormatter(value, row, index) { return value == 1 ? '是' : '否'; }

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
        var serarch_firstVolcode = $("#serarch_firstVolcode").val();
        var serarch_enrollStatus = $("#serarch_enrollStatus").val();
        var serarch_studentName = $("#serarch_studentName").val();

        $('#table').bootstrapTable('refresh', {
            url: '${basePath}/manage/recruit/list',
            query: {
                search_year: search_year,
                serarch_batchName: serarch_batchName,
                serarch_firstVolcode: serarch_firstVolcode,
                serarch_enrollStatus: serarch_enrollStatus,
                serarch_studentName:serarch_studentName,
                schoolcode:schoolcode
            }
        });
    }
    var thisYear = new Date().getFullYear();

    $.date({
        elem: '#search_year',
        //value: '2015',
        ready: function(date){
            setyear();
        },
        change: function(value, date, endDate){
            thisYear = date.year;
            setyear(); // 回调根据年度加载批次
        }
    });

    //根据年度加载招生批次
    function setyear(){
        $('#serarch_batchId').empty();
        var search_year=$("#search_year").val();
        if(search_year!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/batchyear',
                data: {year:search_year,schoolcode:schoolcode},
                success: function(data){
                    var data = eval('(' + data + ')');
                    for(var index in data){
                        $("#serarch_batchId").append(" <option value=' "+ data[index].batchId+" ' >"+data[index].batchName+" </option> ");
                    }
                }
            });
        }
    }



    function _view(studentId){
        _edit(studentId, true);
    }

    function _edit(studentId, readonly) {
        var url = '${basePath}/manage/recruit/update/' + studentId,
                title = readonly ? '查看' : '修改专业';
        url += readonly ? '?readonly=1' : '';

        $.open({
            title: title,
            content: url
        });
    }



    function createAction() {
        $.open({
            title: '新增批次',
            area: '450px',
            content: '${basePath}/manage/batch/create'
        });
    }
    function ylqAction() {
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
                content: '确认预录取吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].studentId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/enroll/ylqadmitted/' + ids.join("-"),
                                success: function(result) {
                                    if (result.code != 1) {
                                        if (result.data instanceof Array) {
                                            $.each(result.data, function(index, value) {
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
                                        $table.bootstrapTable('refresh',{url:'${basePath}/manage/enroll/list?schoolcode='+schoolcode});
                                    }
                                },
                                error: function(XMLHttpRequest, textStatus, errorThrown) {
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
    // 录取
    function admittedAction() {
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
                content: '确认录取吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].studentId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/enroll/admitted/' + ids.join("-"),
                                success: function(result) {
                                    if (result.code != 1) {
                                        if (result.data instanceof Array) {
                                            $.each(result.data, function(index, value) {
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
                                        $table.bootstrapTable('refresh',{url:'${basePath}/manage/enroll/list?schoolcode='+schoolcode});
                                    }
                                },
                                error: function(XMLHttpRequest, textStatus, errorThrown) {
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

    // 录取
    function beachPrint() {
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
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].studentId);
            }
            var  postUrl='${basePath}/manage/enroll/reportPrint/' + ids.join("-");
            window.location.href=postUrl;
//            window.open(postUrl);
        }
    }

</script>
