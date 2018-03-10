<%@ include file="/resources/inc/topS.jsp" %>
<%@ include file="/resources/inc/topJ.jsp" %>

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
        <label class="sl2">
            <span>录取状态</span>
            <select class="ex" name="serarch_enrollStatus" id="serarch_enrollStatus">
                <option value="">请选择录取态</option>
                <c:forEach var="enrollstatusDicts" items="${enrollstatusDicts}">
                    <option value="${enrollstatusDicts.valueKey}">${enrollstatusDicts.valueName}</option>
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
        <shiro:hasPermission name="enroll:student:create">
            <a class="btn add" href="javascript:;" onclick="createAction()">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="enroll:student:create">
            <a class="btn import" href="javascript:;" onclick="uploadAction()">导入学生</a>
        </shiro:hasPermission>

        <%-- 确认shiro权限 --%>
        <%--<a class="btn export" href="javascript:;" onclick="updateAction()">导出</a>--%>
        <a class="btn noenroll" href="javascript:;" onclick="wlqAction()">未录取</a>
        <c:if test="${enrollChargedetail.detailId!=null}">
            <a class="btn preenroll" href="javascript:;" onclick="ylqAction()">预录取</a>
        </c:if>
        <a class="btn enroll" href="javascript:;" onclick="admittedAction()">录取</a>
        <a class="btn print" href="javascript:;" onclick="beachPrint()()">打印</a>

    </div>
    <div class="lap-table">
        <table id="table"></table>
    </div>
</div>
<script type="text/javascript" src="${basePath}/resources/lap/js/lap.extends.js"></script>

<%--
enrollStatus
* 录取状态（1-未录取，2-预录取，3-已录取，4-已录取被删除)
--%>

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
        url : '${basePath}/manage/enroll/list?schoolcode='+schoolcode,
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
                halign: 'center', // halign 标题对齐
                formatter: 'actionFmt'
            }
        ],
        rowStyle: function (row, index) { return {}; },
        cellStyle : function cellStyle(value, row, index) { return {}; }
    });

    function timeFmt(value, row, index) { return new Date(value).toLocaleDateString().replace(/[/]/g, '-'); }

    function detailFmt(value, row, index) {return ''; }

    function statFmt(value, row, index) { return value == 1 ? '是' : '否'; }

    var btnGroup = [
        [
            '<a class="lap-btn btn-edit">编辑</a>',
            '<a class="lap-btn btn-view">查看</a>',
            '<a class="lap-btn btn-delete">删除</a>'
        ], [
            '<a class="lap-btn btn-view">查看</a>',
        ], [
            '<a class="lap-btn btn-view">查看</a>'
        ], [
            '<a class="lap-btn btn-view">查看</a>'
        ], [
            '<a class="lap-btn btn-view">查看</a>'
        ]
    ] , feeGroup = [
        '',
        '未缴费',
        '预缴费',
        '已缴费',
        '待退费',
        '已退费'
    ] , enrollGroup = [
        '院校在审阅',
        '未录取',
        '预录取',
        '已录取',
        '已录取被删除'
    ];

    function actionFmt(value, row, index) {
        //console.log(row);
        return btnGroup[row.enrollStatus]
                .concat('<input type="hidden" value="' + row.studentId + '"/>')
                .join(' ');
    }
    //格式化性别
    function sexFormatter(value, row, index) { return value == 0 ? '男' : '女'; }

    //格式化缴费状态
    function feeFormatter(value, row, index) { return feeGroup[value]; }

    // 格式化学制
    function xzFormatter(value, row, index) { return value == 101 ? '3年' : '5年'; }

    //格式化录取状态
    function enrollFormatter(value, row, index) { return enrollGroup[value]; }

    function queryParams(pageReqeust) {
        pageReqeust.search_year = $("#search_year").val();
        pageReqeust.serarch_batchName = $("#serarch_batchName").val();
        pageReqeust.serarch_firstVolcode = $("#serarch_firstVolcode").val();
        pageReqeust.serarch_enrollStatus = $("#serarch_enrollStatus").val();
        pageReqeust.serarch_studentName = $("#serarch_studentName").val();
        return pageReqeust;
    }

    //查询按钮参数
    function search_filter() {
        var search_year = $("#search_year").val();
        var serarch_batchName = $("#serarch_batchName").val();
        var serarch_firstVolcode = $("#serarch_firstVolcode").val();
        var serarch_enrollStatus = $("#serarch_enrollStatus").val();
        var serarch_studentName = $("#serarch_studentName").val();

        $('#table').bootstrapTable('refresh', {
            url: '${basePath}/manage/enroll/list',
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

    function _view(studentId){ _edit(studentId, true); }

    function _edit(studentId, readonly) {
        var url = '${basePath}/manage/enroll/update/' + studentId,
                title = readonly ? '查看录取' : '编辑录取';
        url += readonly ? '?readonly=1' : '';

        $.open({
            title: title,
            content: url
        });
    }

    function _delete(itemId) {
        $.confirm({content:'确认要删除吗?'}, {area:['400px', '280px']}
            , function(){
                $.ajax({
                    type: 'get',
                    url: '${basePath}/manage/enroll/delete/' + itemId,
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

    function createAction() {
        $.open({
            type: 2,
            title: '新增报名信息',
            content: '${basePath}/manage/enroll/create?schoolcode='+schoolcode
        });
    }
    //未录取
    function wlqAction() {
        //console.log("未录取管理操作！")
        var rows = $parentTable.bootstrapTable('getSelections');
        if (rows.length == 0) {
            layer.msg('请选择一条记录', {
                time: 3000
            });
        } else {
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].studentId);
            }

            /* 需要添加确认框 */
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/enroll/wlqadmitted/' + ids.join("-"),
                success: function(result) {
                    if (result.code == 1) {
                        $.msg('操作成功!');
                        $('#table').bootstrapTable('refresh');
                    }else{
                        $.msg('操作失败!缴费状态必须是：未交费或预缴费；录取状态必须是：院校在审阅或预录取');
                        $('#table').bootstrapTable('refresh');
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    $.msg('操作失败!');
                }
            });
        }
    }
    //预录取
    function ylqAction() {
        var rows = $parentTable.bootstrapTable('getSelections');
        if (rows.length == 0) {
            layer.msg('请选择一条记录', {
                time: 3000
            });
        } else {
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].studentId);
            }

            /* 需要添加确认框 */
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/enroll/ylqadmitted/' + ids.join("-"),
                success: function(result) {
                    //console.log("返回数据："+result)
                    if (result.code ==1) {
                        $.msg('预录取成功');
                        $('#table').bootstrapTable('refresh');
                    }else{
                        $.msg('预录取失败！缴费状态必须是：未交费；录取状态必须是：院校在审阅');
                        $('#table').bootstrapTable('refresh');
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
    }
    // 录取
    function admittedAction() {
        var rows = $('#table').bootstrapTable('getSelections');
        if (rows.length == 0) {
          layer.msg('请选择一条记录', {
              time: 3000
          });
        } else {
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].studentId);
            }
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/enroll/admitted/' + ids.join("-"),
                success: function(result) {
                    //console.log(result);
                    if (result.code== 1) {
                        layer.msg('录取成功', {
                            time: 3000
                        });
                        $('#table').bootstrapTable('refresh');
                    }else{
                        layer.msg('录取失败！缴费状态必须是：已缴费；录取状态必须是：预录取', {
                            time: 3000
                        });
                        $('#table').bootstrapTable('refresh');
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
    }

    //批量打印通知书
    function beachPrint() {
        var rows = $('#table').bootstrapTable('getSelections');
        if (rows.length == 0) {
            layer.msg('请选择一条记录', {
                time: 3000
            });
        } else {
            var ids = new Array();
            for (var i in rows) {
                ids.push(rows[i].studentId);
            }
            $.ajax({
                type: 'get',
                url: '${basePath}/manage/enroll/yzPrint/' + ids.join("-"),
                success: function(result) {
                    //console.log("返回数据："+result.code);
                    if (result.code> 0) {
                        var  postUrl='${basePath}/manage/enroll/reportPrint/' + ids.join("-");
                        window.location.href=postUrl;
                    }else{
                        layer.msg('打印失败！录取状态必须是：已录取', {
                            time: 3000
                        });
                        $('#table').bootstrapTable('refresh');
                    }
                },
            });
        }
    }

    //导入
    var uploadDialog;
    function uploadAction() {

        uploadDialog = $.open({
            area: ['400px', '600px'],
            title: '导入学生',
            content: '${basePath}/manage/enroll/upload?schoolCode=' + schoolcode,
        });
    }

</script>
