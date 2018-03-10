<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>角色管理</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div style="width: 20%; height: 100%;float:left;overflow: auto" id="tree">
        <table style="">
            <ul id="ztree" class="ztree"></ul>
        </table>
    </div>
    <div style="width: 80%; height: 100%;float:right" id="tb">
        <div id="currentNode" type="hidden" value=""></div>
        <div id="toolbar">
            <shiro:hasPermission name="ams:approle:create"><a class="waves-effect waves-button" href="javascript:;"
                                                              onclick="createAction()"><i class="zmdi zmdi-plus"></i>
                新增应用角色</a></shiro:hasPermission>
            <shiro:hasPermission name="ams:approle:update"><a class="waves-effect waves-button" href="javascript:;"
                                                              onclick="updateAction()"><i class="zmdi zmdi-edit"></i>
                编辑应用角色</a></shiro:hasPermission>
            <shiro:hasPermission name="ams:approle:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                              onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
                删除应用角色</a></shiro:hasPermission>
            <shiro:hasPermission name="ams:approle:update"><a class="waves-effect waves-button" href="javascript:;"
                                                              onclick="permission()"><i class="zmdi zmdi-close"></i>
                分配权限</a></shiro:hasPermission>
        </div>
        <table id="table"></table>
    </div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>

    var relationCode = '';
    var currentNode;
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

    var changeDatas = [];
    var setting = {
        check: {
            enable: true,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "", "N": "s"},

            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
        },
        async: {
            enable: true,
            url: '${basePath}/manage/approle/agencytree/-1'
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function (event, treeId, treeNode) {
                if (treeNode.checked == true) {
                    currentNode = treeNode;
                    relationCode = treeNode.code;
                } else {
                    currentNode = '';
                    relationCode = '';
                }
                $("#currentNode").val(currentNode);
                $table.bootstrapTable('refresh', {
                    url: '${basePath}/manage/approle/list'
                });
            }
        }
    };

    function initTree() {
        $.fn.zTree.init($('#ztree'), setting);
    }

    $(function () {
        var userType = ${upmsUser.managerType};
        if (userType == 3) {
            $('#tree').hide();
            $('#tb').attr('style', "");
        } else {
            $.fn.zTree.init($('#ztree'), setting);
        }
    });
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/approle/list',
            height: getHeight(),
            striped: true,
            search: false,
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
            idField: 'approleId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'approleId', title: '应用角色编号', align: 'center'},
                {field: 'approleName', title: '应用角色名称', align: 'center'},
                {field: 'relationName', title: '学校/机构名称', align: 'center'},
                {field: 'appName', title: '应用名称', align: 'center'},
                {field: 'usertypeId', title: '用户类型', align: 'center', formatter: 'dictFormatter4'},
                {field: 'creator', title: '创建者', align: 'center'},
                {field: 'ctime', title: '创建时间', align: 'center', formatter: 'timeFormatter'},
                {field: 'perPersonalization', title: '个性化', align: 'center', formatter: 'perFormatter'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'}
            ]
        });
    });

    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.schoolCode = relationCode;
        return pageReqeust;
    }


    //字典显示格式化
    function dictFormatter4(value, row, index) {

        var s = value;

        var res = '';

        if (s == '4') {
            res = '<span>教育机构</span>';
        }
        if (s == '2') {
            res = '<span>家长</span>';
        }
        if (s == '3') {
            res = '<span>老师</span>';
        }
        if (s == '1') {
            res = '<span>学生</span>';
        }

        return res;
    }

    // 格式化操作按钮
    function actionFormatter(value, row, index) {
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    }

    //状态格式化
    function statusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">正常</span>';
        } else if (value == 0) {
            return '<span class="label label-default">锁定</span>';
        }
    }

    //状态格式化
    function perFormatter(value, row, index) {

        <c:set var="DICT_CODE" value="<%=BaseConstants.Dict.PERPERSONALIZATION%>" />

        <c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
        if (value == ${at.valueKey}) {
            return '<span class="label label-success">${at.valueName}</span>';
        }
        </c:forEach>

    }

    // 格式化角色图标
    function avatarFormatter(value, row, index) {
        return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
    }

    // 格式化时间
    function timeFormatter(value, row, index) {
        return new Date(value).toLocaleString();
    }
    // 新增
    var createDialog;
    function createAction() {
        console.log("userType:"+userType+",currentNode:"+currentNode);
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

        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增角色',
            content: 'url:${basePath}/manage/approle/create',
            onContentReady: function () {
                initMaterialInput();
                init();
                $('select').select2({
                    width:'140px'
                });
            }
        });
    }
    // 编辑
    var approleId;
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
            approleId = rows[0].approleId;
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: '编辑角色',
                content: 'url:${basePath}/manage/approle/update/' + rows[0].approleId,
                onContentReady: function () {
                    initMaterialInput();
                    initSelect2();
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
                content: '确认删除该角色吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].approleId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/approle/delete/' + ids.join("-"),
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

    //分配权限
    var apppermissionsDialog;
    var _appId;
    var perPer;
    function permission() {
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
            approleId = rows[0].approleId;
            _appId = rows[0].appId;
            perPer = rows[0].perPersonalization;
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/approle/permissiontree/' + approleId,
                data: {
                    appId: _appId,
                    perPer: perPer
                },
                success: function (result) {
                    if (result == "") {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: "暂时无可分配权限",
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    } else {
                        approleId = rows[0].approleId;
                        apppermissionsDialog = $.dialog({
                            animationSpeed: 300,
                            title: '分配权限',
                            content: 'url:${basePath}/manage/approle/permission/' + approleId,
                            onContentReady: function () {
                                initMaterialInput();
                                initTree();
                            }
                        });
                    }
                }
            });
        }
    }


</script>
</body>
</html>