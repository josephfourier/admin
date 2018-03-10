<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<%@ include file="/tags/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>学校部门管理</title>
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
        <div id="toolbar">
            <input type="hidden" id="agencyCode" value="">
            <input type="hidden" id="areaLevel" value="">
            <shiro:hasPermission name="ucenter:department:create"><a class="waves-effect waves-button"
                                                                     href="javascript:;" onclick="createAction()"><i
                    class="zmdi zmdi-plus"></i> 新增部门</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:department:update"><a class="waves-effect waves-button"
                                                                     href="javascript:;" onclick="updateAction()"><i
                    class="zmdi zmdi-edit"></i> 编辑部门</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:department:delete"><a class="waves-effect waves-button"
                                                                     href="javascript:;" onclick="deleteAction()"><i
                    class="zmdi zmdi-close"></i> 删除部门</a></shiro:hasPermission>
        </div>
        <table id="table"></table>
    </div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>

    //初始化树
    var userType = '${upmsUser.managerType}';
    $(document).ready(function () {
        if (userType == '3') {
            $('#tree').hide();
            $('#Remove').hide();
            $('#tb').css('width', '100%');
        } else {
            $.fn.zTree.init($('#ztree'), setting);
        }
    });
    var changeDatas = [];
    var setting = {
        check: {
            enable: false,
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
            beforeClick: beforeClick,
            onClick: onClick
        }
    };


    function beforeClick(treeId, treeNode) {

        var check = (treeNode && !treeNode.isParent);
        if (!check) {
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
        }
        return check;
    }
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var nodes = zTree.getSelectedNodes();
        var v = "";
        var vt = "";
        var level = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            v = nodes[i].name;
            vt = nodes[i].code;
            level = nodes[i].level;
        }

        var areaLevel = $("#areaLevel");
        var agencyCode = $("#agencyCode");

        areaLevel.attr("value", level);
        agencyCode.attr("value", vt);
        $table.bootstrapTable('refresh', {url: '${basePath}/manage/department/list'});

    }
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        var schoolCode;
        if(userType=='3'){
            schoolCode='${upmsUser.relationCode}';
            $("#agencyCode").val(schoolCode);
        }
        $table.bootstrapTable({
            url: '${basePath}/manage/department/list?managerType=${upmsUser.managerType}',
            height: getHeight(),
            striped: false,
            search: false,
            showExport: true,
            exportDataType: 'all',
            exportOptions: {
                ignoreColumn: [0, 1],  //忽略某一列的索引
                fileName: '部门信息导出',  //文件名称设置
                worksheetName: '部门信息',  //表格工作区名称
                tableName: '部门信息导出',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            },
            exportTypes: ['csv', 'excel', 'xlsx'],
            Icons: 'glyphicon-export',
            showRefresh: false,
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
            idField: 'departmentId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'departmentId', title: '部门ID', align: 'center'},
                {field: 'parentId', title: '父部门ID', align: 'center'},
                {field: 'departmentName', title: '部门名称', align: 'center'},
                {field: 'schoolCode', title: '学校编码'},
                {field: 'departmentType', title: '部门类型', formatter: 'departmentFormatter'},
                {field: 'creator', title: '创建人'},
                {field: 'ctime', title: '创建时间', formatter: 'birthdayFormatter'},
                {field: 'status', title: '状态', formatter: 'statusFormatter'},
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.schoolCode = $("#agencyCode").val();
        return pageReqeust;
    }
    //格式化时间
    function birthdayFormatter(value, row, index) {
        //设备信息列表格式化时间
        var jsDate = new Date(value);
        var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate();
        return UnixTimeToDate;
    }

    //部门类型格式化
    function departmentFormatter(value, row, index) {
        <c:set var="DEPTTYPE" value="<%=BaseConstants.Dict.DEPTTYPE%>" />
        <c:forEach items="${dict:getValueByCode(DEPTTYPE)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span>${at.valueName}</span>';
        }
        </c:forEach>
        return '-';
    }
    // 格式化状态
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else {
            return '<span class="label label-default">锁定</span>';
        }
    }
    // 新增
    var createDialog;

    function createAction() {
        var userType = '${upmsUser.managerType}';
        var schoolCode;
        if (userType < 3) {
            schoolCode = $("#agencyCode").val();
            if (schoolCode == '') {
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
        } else {
            schoolCode =${upmsUser.relationCode}
        }

        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增部门',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/department/create?schoolCode=' + schoolCode,
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
        //console.log("部门类型："+rows[0].departmentType);
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
            if(rows[0].departmentType == 1){
                $.confirm({
                    title: false,
                    content: '院系类别的不能编辑！',
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
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: '编辑部门',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/department/update/' + rows[0].departmentId + "/" + userType,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2({
                        width:'140px'
                    });
                }
            });
        }
    }
    // 删除
    var deleteDialog;
    function deleteAction() {
        var schoolCode = $("#agencyCode").val();

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
            if(rows[0].departmentType == 1){
                $.confirm({
                    title: false,
                    content: '院系类别的不能删除！',
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
            deleteDialog = $.confirm({
                type: 'red',
                animationSpeed: 300,
                title: false,
                content: '确认删除该部门信息吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].departmentId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/department/delete/' + ids.join("-"),
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
                                        <%--$table.bootstrapTable('refresh', {url: '${basePath}/manage/department/list?schoolCode=' + schoolCode});--%>
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
</html>