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
    <title>学校专业管理</title>
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
                <label>教育局专业编码:</label>
                <input type="text" class="form-control" id="serarch_specialtyNo">
            </div>
            <div class="search-group">
                <label>校内专业编码:</label>
                <input type="text" class="form-control" id="serarch_specialtyCode">
            </div>
            <div class="search-group">
                <label>专业名称:</label>
                <input type="text" class="form-control" id="serarch_specialtyName">
            </div>
            <div class="search-group">
                <button  class="btn btn-primary" onclick="seach_filter()" >查询</button>
            </div>
        </div>
        <div id="toolbar">
            <input type="hidden" id="agencyCode" value="">
            <input type="hidden" id="areaLevel" value="">
            <input type="hidden" id="schoolCode" value="">
            <shiro:hasPermission name="ucenter:specialty:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增专业</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:specialty:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑专业</a></shiro:hasPermission>
            <shiro:hasPermission name="ucenter:specialty:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除专业</a></shiro:hasPermission>
            <%--<shiro:hasPermission name="ucenter:specialty:clone"><a class="waves-effect waves-button" href="javascript:;" onclick="cloneAction()"><i class="zmdi zmdi-plus"></i> 克隆上年度专业信息</a></shiro:hasPermission>--%>
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
        pageReqeust.serarch_specialtyNo = $("#serarch_specialtyNo").val();
        pageReqeust.serarch_specialtyCode = $("#serarch_specialtyCode").val();
        pageReqeust.serarch_specialtyName = $("#serarch_specialtyName").val();
        return pageReqeust;
    }
    function seach_filter(){
        var schoolCode=$("#schoolCode").val();
        var serarch_specialtyNo=$("#serarch_specialtyNo").val();
        var serarch_specialtyCode=$("#serarch_specialtyCode").val();
        var serarch_specialtyName=$("#serarch_specialtyName").val();

        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/specialty/list',
            query: {
                schoolCode:schoolCode,
                serarch_specialtyNo:serarch_specialtyNo,
                serarch_specialtyCode:serarch_specialtyCode,
                serarch_specialtyName:serarch_specialtyName,
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
        $table.bootstrapTable('refresh',{url:'${basePath}/manage/specialty/list'});
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
            url: '${basePath}/manage/specialty/list',
            height: getHeight(),
            striped: true,
            search: false,
            showExport: true,
            exportDataType: 'all',
            exportOptions: {
                ignoreColumn: [0, 1],  //忽略某一列的索引
                fileName: '专业信息导出',  //文件名称设置
                worksheetName: '专业信息',  //表格工作区名称
                tableName: '专业信息导出',
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
            idField: 'specialtyId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: queryParams,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'specialtyId', title: '编号', align: 'center'},
                {field: 'specialtyNo', title: '教育局专业编码'},
                {field: 'specialtyCode', title: '校内专业编码'},
                {field: 'specialtyName', title: '专业名称'},
                {field: 'trdrName', title: '培养方向'},
                {field: 'schoolSystem', title: '学制', formatter: 'schoolFormatter'},
                {field: 'specialtyNature', title: '专业性质',formatter: 'specltyFormatter'},
                //{field: 'enrollmentTarget', title: '招生对象',formatter: 'tarFormatter'},
                {field: 'specialtyType', title: '专业类别',formatter: 'typeFormatter'},
                {field: 'facultyName', title: '所属院系'},
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
    //格式化学制
    function schoolFormatter(value, row, index){
        if (value == 101) {
            return '3年';
        }
        if (value == 102) {
            return '5年';
        }
        return '-';
    }
    //格式化专业性质
    function specltyFormatter(value, row, index){
        if (value == 1001) {
            return '中职';
        }
        if (value == 1002) {
            return '高职';
        }
        if (value == 1003) {
            return '大专';
        }
        return '-';
    }
    //格式化招生对象
    function tarFormatter(value, row, index){
        if (value == 110) {
            return '小学毕业';
        }
        if (value == 111) {
            return '初中毕业';
        }
        if (value == 112) {
            return '中职毕业';
        }
        if (value == 113) {
            return '高中毕业';
        }
        return '-';
    }
    //格式化专业类别
    function typeFormatter(value, row, index){
        if (value == 01) {
            return '哲学';
        }
        if (value == 02) {
            return '经济学';
        }
        if (value == 03) {
            return '法学';
        }
        if (value == 04) {
            return '教育学';
        }
        if (value == 05) {
            return '文学';
        }
        if (value == 06) {
            return '历史学';
        }
        if (value == 07) {
            return '理学';
        }
        if (value == 08) {
            return '工学';
        }
        if (value == 09) {
            return '农学';
        }
        if (value == 10) {
            return '医学';
        }
        if (value == 11) {
            return '军事学';
        }
        if (value == 12) {
            return '管理学';
        }
        return '-';
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
            title: '新增专业',
            columnClass: 'col-md-5 col-md-offset-3',
            content: 'url:${basePath}/manage/specialty/create?userType='+userType+"&schoolCode="+schoolCode,
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
                title: '编辑专业',
                columnClass: 'col-md-5 col-md-offset-3',
                content: 'url:${basePath}/manage/specialty/update/' + rows[0].specialtyId+"/"+userType,
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
                content: '确认删除该专业吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].specialtyId);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/manage/specialty/delete/' + ids.join("-"),
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
    //克隆专业信息
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
                            url: '${basePath}/manage/specialty/clone?schoolCode=' +schoolCode ,
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
                                    $table.bootstrapTable('refresh');
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