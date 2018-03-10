﻿<%@ page contentType="text/html; charset=utf-8"%>
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
    <title>学校班级管理</title>
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
                <label>年级:</label>
                <input id="search_year" type="text" class="form-control" name="search_year"
                       onfocus="WdatePicker({dateFmt:'yyyy',readOnly:false,isShowToday:false})">
            </div>
            <div class="search-group">
                <label>班级名称:</label>
                <input type="text" class="form-control" id="serarch_className">
            </div>
            <div class="search-group">
                <button  class="btn btn-primary" onclick="seach_filter()" >查询</button>
            </div>
        </div>
        <div id="toolbar">
            <input type="hidden" id="agencyCode" value="">
            <input type="hidden" id="areaLevel" value="">
            <input type="hidden" id="schoolCode" value="">
            <shiro:hasPermission name="ucenter:class:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增班级</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:class:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑班级</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:class:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除班级</a></shiro:hasPermission>
            <%--<shiro:hasPermission name="ucenter:class:clone"><a class="waves-effect waves-button" href="javascript:;" onclick="cloneAction()"><i class="zmdi zmdi-plus"></i> 克隆上年度班级信息</a></shiro:hasPermission>--%>
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
        pageReqeust.search_year = $("#search_year").val();
        pageReqeust.serarch_className = $("#serarch_className").val();
        return pageReqeust;
    }
    function seach_filter(){
        var schoolCode=$("#schoolCode").val();
        var serarch_className=$("#serarch_className").val();
        var search_year=$("#search_year").val();
        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/class/list',
            query: {
                schoolCode:schoolCode,
                serarch_className:serarch_className,
                search_year:search_year,
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
        var areaLevel = $("#areaLevel");
        var agencyCode = $("#agencyCode");
        var schoolCode=$("#schoolCode");
        areaLevel.attr("value", level);
        schoolCode.attr("value", vt);
        //console.log("等级:"+level+";code:"+vt+"======");
        $table.bootstrapTable('refresh',{url:'${basePath}/manage/class/list'});
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
            url: '${basePath}/manage/class/list',
            height: getHeight(),
            striped: true,
            search: false,
            showExport: true,
            exportDataType: 'all',
            exportOptions: {
                ignoreColumn: [0, 1],  //忽略某一列的索引
                fileName: '班级信息导出',  //文件名称设置
                worksheetName: '班级信息',  //表格工作区名称
                tableName: '班级信息导出',
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
            idField: 'classId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'classId', title: '编号', align: 'center'},
                {field: 'className', title: '班级名称'},
                {field: 'facultyName', title: '所属院系'},
                {field: 'specialtyName', title: '所属专业'},
                {field: 'bzrName', title: '班主任'},
                {field: 'fdyName', title: '辅导员'},
                {field: 'year', title: '年级'},
                {field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'},
            ]
        });
    });

    //格式化时间
    function birthdayFormatter(value,row,index){
        //设备信息列表格式化时间
        var jsDate = new Date(value);
        var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate();
        //+ ' ' + jsDate.getHours() + ':' + jsDate.getMinutes() + ':' + jsDate.getSeconds();
        return UnixTimeToDate ;
    }
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
            title: '新增班级',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/class/create?userType='+userType+"&schoolCode="+schoolCode,
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
                title: '编辑班级',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/class/update/' + rows[0].classId+"/"+userType,
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
                content: '确认删除该班级吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].classId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/class/delete/' + ids.join("-"),
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

    //克隆班级信息
    function cloneAction() {
        var schoolCode=$("#schoolCode").val();
        if(schoolCode.length!=10){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content: "请先选择学校节点！",
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return;
        }
        deleteDialog = $.confirm({
            type: 'red',
            animationSpeed: 300,
            title: false,
            content: '确认克隆吗？',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.ajax({
                            type: 'POST',
                            url: '${basePath}/manage/class/clone?schoolCode=' +schoolCode ,
                            success: function(result) {
                                //console.log("返回结果："+result.code);
                                if (result.code > 0) {
                                    $.confirm({
                                        theme: 'dark',
                                        animation: 'rotateX',
                                        closeAnimation: 'rotateX',
                                        title: false,
                                        content: '克隆成功！',
                                        buttons: {
                                            confirm: {
                                                text: '确认',
                                                btnClass: 'waves-effect waves-button waves-light'
                                            }
                                        }
                                    });
                                } else {
                                    $.confirm({
                                        theme: 'dark',
                                        animation: 'rotateX',
                                        closeAnimation: 'rotateX',
                                        title: false,
                                        content: '克隆失败！',
                                        buttons: {
                                            confirm: {
                                                text: '确认',
                                                btnClass: 'waves-effect waves-button waves-light'
                                            }
                                        }
                                    });
                                }
                                {
                                    deleteDialog.close();
                                    $table.bootstrapTable('refresh',{url:'${basePath}/manage/class/list?schoolCode='+schoolCode});
                                }
                            }

                        });
                    }
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'waves-effect waves-button'
            }
        });
    }
</script>
</body>
</html>