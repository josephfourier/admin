<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>职务管理</title>
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
                <label>职务名称:</label>
                <input type="text" class="form-control" id="serarch_postName">
            </div>
            <div class="search-group">
                <button  class="btn btn-primary" onclick="seach_filter()" >查询</button>
            </div>
        </div>
        <div id="toolbar">
            <input type="hidden" id="schoolCode" value="">
            <shiro:hasPermission name="ucenter:post:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增职务</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:post:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑职务</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:post:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除职务</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:post:addteacher"><a class="waves-effect waves-button" href="javascript:;" onclick="addTeacher()"><i class="zmdi zmdi-plus"></i> 添加人员</a></shiro:hasPermission>
        </div>
        <table id="table"></table>
    </div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        pageReqeust.userType=userType;
        pageReqeust.schoolCode = $("#schoolCode").val();
        pageReqeust.serarch_postName = $("#serarch_postName").val();
        return pageReqeust;
    }
    function seach_filter(){
        var schoolCode=$("#schoolCode").val();
        var serarch_postName=$("#serarch_postName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/post/list',
            query: {
                schoolCode:schoolCode,
                serarch_postName:serarch_postName,
                userType: userType
            }
        });
    }
    //初始化树
    var userType='${upmsUser.managerType}'
    $(document).ready(function(){
        if(userType=='3'){
            $('#tree').hide();
            $('#Remove').hide();
            $('#tb').css('width', '100%');
        }else{
            $.fn.zTree.init($('#ztree'), setting);
        }
    });
    var changeDatas = [];
    var setting = {
        check: {
            enable: false,
            // 勾选关联父，取消关联子
            chkboxType: { "Y" : "p", "N" : "s" }
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
        var vt="";
        var level="";
        for (var i=0, l=nodes.length; i<l; i++) {
            v = nodes[i].name;
            vt = nodes[i].code;
            level=nodes[i].level;
        }
        var schoolCode=$("#schoolCode");
        schoolCode.attr("value", vt);
        //console.log("等级:"+level+";code:"+vt+"======");
        $table.bootstrapTable('refresh',{url:'${basePath}/manage/post/list'});
    }
    var $table = $('#table');
    $(function() {
        // bootstrap table初始化
        var schoolCode;
        if(userType=='3'){
            schoolCode='${upmsUser.relationCode}';
            $("#schoolCode").val(schoolCode);
        }
        //console.log("====:"+userType+";ppppp:"+schoolCode);
        $table.bootstrapTable({
            url: '${basePath}/manage/post/list',
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
            idField: 'postId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'postId', title: '编号', align: 'center'},
                {field: 'postName', title: '职务名称'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'},
            ]
        });
    });

    // 格式化
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
        var schoolCode=$("#schoolCode").val();
        //console.log("学校编码："+schoolCode+"；编码长度："+schoolCode.length);
        if(schoolCode.length!=10){
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
        //console.log("====:"+userType+";ppppp:"+agencyCode);
        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增职务',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/post/create?userType='+userType+"&schoolCode="+schoolCode,
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
                title: '编辑职务',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/post/update/' + rows[0].postId+"/"+userType,
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
        var schoolCode=$("#schoolCode").val();
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
                content: '确认删除该职务？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].postId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/post/delete/' + ids.join("-"),
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
                                        $table.bootstrapTable('refresh');
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

    // 添加人员
    var updateDialog;
    function addTeacher() {
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
            teacherDialog = $.dialog({
                animationSpeed: 300,
                title: '添加人员',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/post/addteacher/' + rows[0].postId+"/"+userType,
                onContentReady: function () {
                    initMaterialInput();
                    initTree();
                    $('select').select2({
                        width:'140px'
                    });
                }
            });
        }
    }
</script>
</body>
</html>