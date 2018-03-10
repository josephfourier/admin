<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>老师管理</title>
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
                <label>学历:</label>
                <select class="form-control" id="search_education" name="search_education">
                    <option value="">请选择学历</option>
                    <c:set var="EDUCATION" value="<%=BaseConstants.Dict.EDUCATION %>"/>
                    <c:forEach items="${dict:getValueByCode(EDUCATION)}" var="at">
                        <option value="${at.valueKey}">${at.valueName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="search-group">
                <label>政治面貌:</label>
                <select class="form-control" id="search_politics" name="search_politics">
                    <option value="">请选择政治面貌</option>
                    <c:set var="POLITICS" value="<%=BaseConstants.Dict.POLITICS %>"/>
                    <c:forEach items="${dict:getValueByCode(POLITICS)}" var="at">
                        <option value="${at.valueKey}">${at.valueName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="search-group">
                <label>性别:</label>
                <select class="form-control" id="search_sex" name="search_sex">
                    <option value="">请选择性别</option>
                    <c:set var="SEX" value="<%=BaseConstants.Dict.SEX %>"/>
                    <c:forEach items="${dict:getValueByCode(SEX)}" var="at">
                        <option value="${at.valueKey}">${at.valueName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="search-group">
                <label>老师名称:</label>
                <input type="text" class="form-control" id="serarch_teacherName">
            </div>
            <div class="search-group">
                <button class="btn btn-primary" onclick="seach_filter()">查询</button>
            </div>
        </div>
        <div id="toolbar">
            <%--<button type="button" onclick="exportData();" class='btn btn-mini btn-info'>导出一</button>--%>

            <shiro:hasPermission name="ucenter:teacher:create"><a class="waves-effect waves-button" href="javascript:;"
                                                                  onclick="createAction()"><i
                    class="zmdi zmdi-plus"></i> 新增老师</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:teacher:update"><a class="waves-effect waves-button" href="javascript:;"
                                                                  onclick="updateAction()"><i
                    class="zmdi zmdi-edit"></i> 编辑老师</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:teacher:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                                  onclick="deleteAction()"><i
                    class="zmdi zmdi-close"></i> 删除老师</a></shiro:hasPermission>
                <shiro:hasPermission name="ucenter:teacher:create"><a class="waves-effect waves-button" href="javascript:;" onclick="uploadAction()"><i
                        class="zmdi zmdi-plus"></i> 导入老师</a></shiro:hasPermission>
        </div>
        <table id="table"></table>
    </div>

</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>


    var relationCode = '';
    var currentNode;
    var changeDatas = [];
    var userType = '${upmsUser.managerType}';
    $(function () {
        if (userType == <%=BaseConstants.ManagerType.SCHMANAGER%>) {
            //学校管理员默认显示该学校的数据
            relationCode = '${upmsUser.relationCode}';
            $('#tree').hide();
            $('#tb').attr('style', "");
        } else {
            //区域管理员显示该区域的数据,不包括区域下学校的数据
            if (userType == <%=BaseConstants.ManagerType.AREAMANAGER%>) {
                relationCode = '${upmsUser.relationCode}';
            }
            $.fn.zTree.init($('#ztree'), setting);
        }
    });

    var setting = {
        check: {
            enable: false,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "p", "N": "s"}
        },
        async: {
            enable: true,
            url: '${basePath}/manage/agency/schooltree'
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

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var nodes = zTree.getSelectedNodes();
        var v = "";
        var vt = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            v = nodes[i].name;
            currentNode = nodes[i];
            relationCode = nodes[i].code;
        }

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/teacher/list'
        });
        //选择学校后,院系初始化
        //initFaculty();
    }

    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/teacher/list',
            height: getHeight(),
            striped: true,
            search: false,
            showExport: true,
            exportDataType: 'all',
            exportOptions: {
                ignoreColumn: [0, 1],  //忽略某一列的索引
                fileName: '老师信息导出',  //文件名称设置
                worksheetName: '老师信息',  //表格工作区名称
                tableName: '老师信息导出',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            },
            exportTypes: ['csv', 'excel', 'xlsx'],
            Icons: 'glyphicon-export',
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
            idField: 'teacherId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'teacherId', title: '编号', align: 'center'},
                {field: 'teacherName', title: '老师姓名'},
                {field: 'sex', title: '性别', align: 'center', formatter: 'sexFormatter'},
                {field: 'nation', title: '民族' , formatter: 'nationFormatter'},
                {field: 'phone', title: '手机号码'},
                {field: 'idcard', title: '身份证号'},
                {field: 'teacherCode', title: '教工号'},
                {field: 'departmentName', title: '部门', align: 'center'},
                {field: 'worktime', title: '参加工作时间', formatter: 'birthdayFormatter'},
                {field: 'birthday', title: '出生日期', formatter: 'birthdayFormatter'},
                {field: 'mail', title: '邮箱'},
                {field: 'education', title: '学历', formatter: 'eduFormatter'},
                {field: 'origin', title: '籍贯'},
                {field: 'politics', title: '政治面貌', formatter: 'polFormatter'},
                {field: 'description', title: '描述'},
                {field: 'schoolCode', title: '学校编码'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'}
            ]
        });
    });


    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.schoolCode = relationCode;
        pageReqeust.search_education = $("#search_education").val();
        pageReqeust.search_politics = $("#search_politics").val();
        pageReqeust.search_sex = $("#search_sex").val();
        pageReqeust.serarch_teacherName=$("#serarch_teacherName").val();
        return pageReqeust;
    }

    function statusFormatter(value, row, index){
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else if(value == 0) {
            return '<span class="label label-default">锁定</span>';
        }
    }

    //数字
    function idcardFormatter(value, row, index) {
        var result = "<p style=\"mso-number-format:'\\@';\">" + value + "</p>"

        console.log(result);
        //result = result + value;
        return result;
    }


    function nationFormatter(value, row, index) {
        <c:set var="NATION" value="<%=BaseConstants.Dict.NATION%>" />
        <c:forEach items="${dict:getValueByCode(NATION)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    function seach_filter() {
        var schoolCode = relationCode;
        var search_education = $("#search_education").val();
        var search_politics = $("#search_politics").val();
        var search_sex = $("#search_sex").val();
        var serarch_teacherName=$("#serarch_teacherName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/teacher/list',
            query: {
                schoolCode: schoolCode,
                search_education: search_education,
                search_politics: search_politics,
                search_sex: search_sex,
                userType: userType,
                serarch_teacherName:serarch_teacherName
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
    //格式化性别
    function sexFormatter(value, row, index) {
        <c:set var="DICT_CODE" value="<%=BaseConstants.Dict.SEX%>" />
        <c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    //格式化学历
    function eduFormatter(value, row, index) {
        <c:set var="EDUCATION" value="<%=BaseConstants.Dict.EDUCATION%>" />
        <c:forEach items="${dict:getValueByCode(EDUCATION)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    //格式化政治面貌
    function polFormatter(value, row, index) {
        <c:set var="POLITICS" value="<%=BaseConstants.Dict.POLITICS%>" />
        <c:forEach items="${dict:getValueByCode(POLITICS)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    //格式化时间
    function birthdayFormatter(value, row, index) {
        //设备信息列表格式化时间
        var jsDate = new Date(value);
        if(value!=null){
            var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate();
            return UnixTimeToDate;
        }else{
            return "";
        }
    }

    // 新增
    var createDialog;
    function createAction() {
        if (userType != <%=BaseConstants.ManagerType.SCHMANAGER%> && currentNode == null) {
            $.confirm({
                title: false,
                content: '请先选择学校节点！',
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

        if (currentNode != null && currentNode.type != <%=BaseConstants.ManagerType.AREAMANAGER%>) {
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content: "请选择学校节点！",
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return;
        }

        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增老师',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/teacher/create?userType=' + userType + "&schoolCode=" + relationCode,
            onContentReady: function () {
                initMaterialInput();
                $('select').select2({
                    width:'140px'
                });
//                initSpecilty();
//                initClass();
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
                title: '编辑老师',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/teacher/update/' + rows[0].teacherId,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2({
                        width:'140px'
                    });
                    initSelect2();
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
                content: '确认删除该老师吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].teacherId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/teacher/delete/' + ids.join("-"),
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

    //导入
    var uploadDialog;
    function uploadAction() {
        if (userType != <%=BaseConstants.ManagerType.SCHMANAGER%> && currentNode == null) {
            $.confirm({
                title: false,
                content: '请先选择学校节点！',
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

        if (currentNode != null && currentNode.type != <%=BaseConstants.ManagerType.AREAMANAGER%>) {
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content: "请选择学校节点！",
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return;
        }

        uploadDialog = $.dialog({
            animationSpeed: 300,
            title: '导入老师',
            columnClass: 'col-md-6 col-md-offset-4',
            content: 'url:${basePath}/manage/teacher/upload?userType=' + userType + "&schoolCode=" + relationCode,
            onContentReady: function () {
                initMaterialInput();
            }
        });
    }
</script>
</body>
</html>