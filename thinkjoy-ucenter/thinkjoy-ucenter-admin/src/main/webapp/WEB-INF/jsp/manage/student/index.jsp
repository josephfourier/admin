<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>学生管理</title>
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
                <label>入学年份:</label>
                <input id="search_year" type="text" class="form-control" name="search_year"
                       <%--onfocus="WdatePicker({dateFmt:'yyyy',readOnly:true,isShowToday:false,onpicked:function() {javascript:changeYesrs();}})">--%>
                       onfocus="WdatePicker({dateFmt:'yyyy',readOnly:false,isShowToday:false})">
            </div>
            <div class="search-group">
                <label>学生类别:</label>
                <select class="form-control" id="search_studentType" name="search_studentType">
                    <option value="">请选择</option>
                    <c:set var="STUDENTTYPE" value="<%=BaseConstants.Dict.STUDENTTYPE %>"/>
                    <c:forEach items="${dict:getValueByCode(STUDENTTYPE)}" var="at">
                        <option value="${at.valueKey}">${at.valueName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="search-group">
                <label>院系:</label>
                <select class="form-control" id="search_faculty" name="search_faculty">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="search-group">
                <label>专业:</label>
                <select class="form-control" id="search_specialty" name="search_specialty">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="search-group">
                <label>班级:</label>
                <select class="form-control" id="search_class" name="search_class">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="search-group">
                <label>学生名称:</label>
                <input type="text" class="form-control" id="serarch_studentName">
            </div>
            <div class="search-group">
                <button class="btn btn-primary" onclick="seach_filter()">查询</button>
            </div>
        </div>
        <div id="toolbar">
            <shiro:hasPermission name="ucenter:student:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i
                    class="zmdi zmdi-plus"></i> 新增学生</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:student:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i
                    class="zmdi zmdi-edit"></i> 编辑学生</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:student:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i
                    class="zmdi zmdi-close"></i> 删除学生</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:student:create"><a class="waves-effect waves-button" href="javascript:;" onclick="uploadAction()"><i
                    class="zmdi zmdi-plus"></i> 导入学生</a></shiro:hasPermission>

        </div>
        <table id="table"></table>
    </div>

</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>

//    $('#search_year').val(new Date().getFullYear());//当前年份
//    var year;
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
//            year =  $('#search_year').val();
            initFaculty();
        } else {
            //区域管理员显示该区域的数据,不包括区域下学校的数据
            if (userType == <%=BaseConstants.ManagerType.AREAMANAGER%>) {
                relationCode = '${upmsUser.relationCode}';
//                year =  $('#search_year').val();
                initFaculty();
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
            url: '${basePath}/manage/student/list?t=' + new Date().getTime()
        });
        //选择学校后,院系初始化
//        year =  $('#search_year').val();
        initFaculty();
    }

    var facultyCode;
    $('#search_faculty').change(function () {
        facultyCode = $(this).val();
        if(facultyCode!=''){
            initSpecilty();
        }else{
            $('#search_specialty').empty();
            $('#search_specialty').select2({
                data: [{id: "", text: '请选择专业'}]
            });
            $('#search_class').empty();
            $('#search_class').select2({
                data: [{id: "", text: '请选择班级'}]
            });
        }
    });

    //院系初始化
    function initFaculty() {
        $.getJSON('${basePath}/manage/faculty/list', {
            schoolCode: relationCode,
//            search_year:year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择院系'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].facultyCode;
                data.text = json.rows[i].facultyName;
                datas.push(data);
            }
            $('#search_faculty').empty();
            $('#search_faculty').select2({
                data: datas
            });
            $('#search_specialty').empty();
            $('#search_specialty').select2({
                data: [{id: "", text: '请选择专业'}]
            });
            $('#search_class').empty();
            $('#search_class').select2({
                data: [{id: "", text: '请选择班级'}]
            });
        });
    }

    var specialtyCode;
    $('#search_specialty').change(function () {
        specialtyCode = $(this).val();
        if(specialtyCode!=''){
            initClass();
        }else{
            $('#search_class').empty();
            $('#search_class').select2({
                data: [{id: "", text: '请选择班级'}]
            });
        }
    });

    //专业初始化
    function initSpecilty() {
        $.getJSON('${basePath}/manage/specialty/list', {
            schoolCode: relationCode,
            facultyCode: facultyCode,
//            search_year:year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择专业'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].specialtyCode;
                data.text = json.rows[i].specialtyName;
                datas.push(data);
            }
            $('#search_specialty').empty();
            $('#search_specialty').select2({
                data: datas
            });
            $('#search_class').empty();
            $('#search_class').select2({
                data: [{id: "", text: '请选择班级'}]
            });
        });
    }

    //班级初始化
    function initClass() {
        $.getJSON('${basePath}/manage/class/list', {
            schoolCode: relationCode,
            search_faculty: facultyCode,
            search_specialty: specialtyCode,
//            search_year: year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择班级'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].classId;
                data.text = json.rows[i].className;
                datas.push(data);
            }
            $('#search_class').empty();
            $('#search_class').select2({
                data: datas
            });

        });
    }

    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/student/list',
            height: getHeight(),
            striped: true,
            cache: false,
            search: false,
            showExport: true,
            exportDataType: 'all',
            exportOptions: {
                ignoreColumn: [0, 1],  //忽略某一列的索引
                fileName: '学生信息导出',  //文件名称设置
                worksheetName: '学生信息',  //表格工作区名称
                tableName: '学生信息导出',
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
            idField: 'studentId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'studentId', title: '编号', align: 'center'},
                {field: 'studentName', title: '学生姓名'},
                {field: 'sex', title: '性别', align: 'center', formatter: 'sexFormatter'},
                {field: 'nation', title: '民族' , formatter: 'nationFormatter'},
                {field: 'phone', title: '手机号'},
                {field: 'idcard', title: '身份证号'},
                {field: 'facultyName', title: '院系名称'},
                {field: 'specialtyName', title: '专业名称'},
                {field: 'className', title: '班级名称'},
                {field: 'enterYear', title: '入学年份'},
                {field: 'studentCode', title: '学号'},
                {field: 'studentNo', title: '学籍号'},
                {field: 'examineeNumber', title: '考生号'},
                {field: 'examnum', title: '准考证号'},
                {field: 'enterTime', title: '入学日期', formatter: 'birthdayFormatter'},
                {field: 'birthday', title: '出生日期', formatter: 'birthdayFormatter'},
                {field: 'mail', title: '邮箱'},
                {field: 'nationality', title: '国籍'},
                {field: 'origin', title: '籍贯'},
                {field: 'domicile', title: '户口所在地'},
                {field: 'fromplace', title: '生源地'},
                {field: 'gradSchool', title: '毕业学校'},
                {field: 'gradTime', title: '毕业时间', formatter: 'birthdayFormatter'},
                {field: 'gradHeadteacher', title: '毕业班主任姓名'},
                {field: 'domicileType', title: '户籍性质', formatter: 'domitypeFormatter'},
                {field: 'studentType', title: '毕业类别', formatter: 'stypeFormatter'},
                {field: 'politics', title: '政治面貌', formatter: 'polFormatter'},
                {field: 'isPoor', title: '是否为贫困生', formatter: 'ispoorFormatter'},
                {field: 'isLiveschool', title: '是否住校', formatter: 'isliveschoolFormatter'},
                {field: 'address', title: '家庭地址'},
                {field: 'postalCode', title: '邮政编码'},
                {field: 'familyPhone', title: '家庭电话'},
                {field: 'description', title: '描述'},
//                {field: 'year', title: '年份'},
                {field: 'schoolCode', title: '学校编码'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'}
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.schoolCode = relationCode;
        pageReqeust.search_year = $("#search_year").val();
        pageReqeust.search_studentType = $("#search_studentType").val();
        pageReqeust.search_specialty = $("#search_specialty").val();
        pageReqeust.search_faculty = $("#search_faculty").val();
        pageReqeust.search_class = $("#search_class").val();
        pageReqeust.serarch_studentName=$("#serarch_studentName").val();
        return pageReqeust;
    }

    function seach_filter() {
        var schoolCode = relationCode;
        var search_studentType = $("#search_studentType").val();
        var search_class = $("#search_class").val();
        var search_faculty = $("#search_faculty").val();
        var search_specialty = $("#search_specialty").val();
        var search_year = $("#search_year").val();
        var serarch_studentName=$("#serarch_studentName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/student/list',
            query: {
                schoolCode: schoolCode,
                search_studentType: search_studentType,
                search_class: search_class,
                search_faculty: search_faculty,
                search_specialty: search_specialty,
                search_year: search_year,
                userType: userType,
                serarch_studentName:serarch_studentName
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
    function nationFormatter(value, row, index) {
        <c:set var="NATION" value="<%=BaseConstants.Dict.NATION%>" />
        <c:forEach items="${dict:getValueByCode(NATION)}" var="at">
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

    //格式化学生类别
    function stypeFormatter(value, row, index) {
        <c:set var="STUDENTTYPE" value="<%=BaseConstants.Dict.STUDENTTYPE%>" />
        <c:forEach items="${dict:getValueByCode(STUDENTTYPE)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    //格式化户籍性质
    function domitypeFormatter(value, row, index) {
        <c:set var="DOMICILETYPE" value="<%=BaseConstants.Dict.DOMICILETYPE%>" />
        <c:forEach items="${dict:getValueByCode(DOMICILETYPE)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }

    //格式化是否贫困生
    function ispoorFormatter(value, row, index) {

        if (value == true) {
            return '是';
        }
        if (value == false) {
            return '否';
        }
        return '-';
    }

    //格式化是否住校
    function isliveschoolFormatter(value, row, index) {

        if (value == true) {
            return '是';
        }
        if (value == false) {
            return '否';
        }
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

    function statusFormatter(value, row, index){
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else if(value == 0) {
            return '<span class="label label-default">锁定</span>';
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
            title: '新增学生',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/student/create?userType=' + userType + "&schoolCode=" + relationCode,
            onContentReady: function () {
                initMaterialInput();
                $('select').select2({
                    width:'140px'
                });
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
                title: '编辑学生',
                cache: true,
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/student/update/' + rows[0].studentId,
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
                content: '确认删除该学生吗？',
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
                                url: '${basePath}/manage/student/delete/' + ids.join("-"),
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
            title: '导入学生',
            columnClass: 'col-md-6 col-md-offset-4',
            content: 'url:${basePath}/manage/student/upload?userType=' + userType + "&schoolCode=" + relationCode,
            onContentReady: function () {
                initMaterialInput();
//				$('select').select2();
            }
        });
    }
</script>
</body>
</html>