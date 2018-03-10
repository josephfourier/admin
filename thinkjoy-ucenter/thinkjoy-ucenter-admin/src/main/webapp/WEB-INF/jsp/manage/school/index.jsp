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
	<title>学校管理</title>
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
                <label>学校编码:</label>
                <input type="text" class="form-control" id="serarch_schoolCode">
            </div>
            <div class="search-group">
                <label>学校名称:</label>
                <input type="text" class="form-control" id="serarch_schoolName">
            </div>
            <div class="search-group">
                <button  class="btn btn-primary" onclick="seach_filter()" >查询</button>
            </div>
        </div>
        <div id="toolbar">
            <input type="hidden" id="agencyCode" value="">
            <input type="hidden" id="areaLevel" value="">
            <c:if test="${upmsUser.managerType=='1'}">
                <shiro:hasPermission name="ucenter:school:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增学校</a></shiro:hasPermission>
            </c:if>
            <shiro:hasPermission name="ucenter:school:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑学校</a></shiro:hasPermission>
            <c:if test="${upmsUser.managerType=='1'}">
                <shiro:hasPermission name="ucenter:school:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除学校</a></shiro:hasPermission>
            </c:if>
        </div>
	    <table id="table"></table>
    </div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    /**查询条件与分页数据 */
    function queryParams(pageReqeust) {
        var schoolCode;
        var agencyCode='';
        var serarch_schoolCode=$("#serarch_schoolCode").val();
        var serarch_schoolName=$("#serarch_schoolName").val();
        if(userType=='3'){
            schoolCode="${upmsUser.relationCode}";
        }
        if(userType=='2'){
            agencyCode="${upmsUser.relationCode}";
        }
        if(userType=='1'){
            agencyCode=$("#agencyCode").val();
        }
        pageReqeust.userType=userType;
        pageReqeust.schoolCode =schoolCode;
        pageReqeust.agencyCode =agencyCode;
        pageReqeust.serarch_schoolName = serarch_schoolName;
        return pageReqeust;
    }
    function seach_filter(){
        var schoolCode;
        var agencyCode='';
        var serarch_schoolCode=$("#serarch_schoolCode").val();
        var serarch_schoolName=$("#serarch_schoolName").val();
        if(userType=='3'){
            schoolCode="${upmsUser.relationCode}";
        }
        if(userType=='2'){
            agencyCode="${upmsUser.relationCode}";
        }
        if(userType=='1'){
            agencyCode=$("#agencyCode").val();
        }
        $table.bootstrapTable('refresh', {
            url: '${basePath}/manage/school/list',
            query: {
                schoolCode:schoolCode,
                serarch_schoolName:serarch_schoolName,
                agencyCode:agencyCode,
                userType: userType
            }
        });
    }

    //初始化树
    var userType='${upmsUser.managerType}';
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
            //url: '${basePath}/manage/area/getAreaTree'
            url: '${basePath}/manage/agency/agencytree'
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
        areaLevel.attr("value", level);
        agencyCode.attr("value", vt);
        //console.log("等级:"+level+";code:"+vt+"======");
        $table.bootstrapTable('refresh',{url:'${basePath}/manage/school/list'});
    }
var $table = $('#table');
$(function() {
	// bootstrap table初始化
    var schoolCode;
    var agencyCode='';
    if(userType=='3'){
        schoolCode="${upmsUser.relationCode}";
    }
    if(userType=='2'){
        agencyCode="${upmsUser.relationCode}";
    }
    //console.log("====:"+userType+";ppppp:"+schoolCode);
	$table.bootstrapTable({
		url: '${basePath}/manage/school/list',
        height: getHeight(),
        striped: true,
        search: false,
        showExport: true,
        exportDataType: 'all',
        exportOptions: {
            ignoreColumn: [0, 1],  //忽略某一列的索引
            fileName: '学校信息导出',  //文件名称设置
            worksheetName: '学校信息',  //表格工作区名称
            tableName: '学校信息导出',
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
		idField: 'schoolId',
		maintainSelected: true,
		toolbar: '#toolbar',
        queryParams: queryParams,
		columns: [
			{field: 'ck', checkbox: true},
			{field: 'schoolId', title: '编号', align: 'center'},
            {field: 'schoolCode', title: '学校编码'},
			{field: 'schoolName', title: '学校名称'},
			{field: 'schoolEnname', title: '英文名称'},
			{field: 'agencyCode', title: '所属机构'},
			{field: 'areaCode', title: '所属区域'},
			{field: 'address', title: '地址'},
			{field: 'birthday', title: '建校日期', formatter: 'birthdayFormatter'},
			{field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'}
		]
	});
});
// 格式化操作按钮
    /*
function actionFormatter(value, row, index) {
    if (${upmsUser.managerType=='1'})
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a id="Remove" class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    else
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　'
        ].join('');}*/
//格式化时间
function birthdayFormatter(value,row,index){
	//设备信息列表格式化时间
	var jsDate = new Date(value);
    if(value!=null){
        var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate();
        //+ ' ' + jsDate.getHours() + ':' + jsDate.getMinutes() + ':' + jsDate.getSeconds();
        return UnixTimeToDate ;
    }else{
        return "";
    }
//	return new Date(value).toLocaleString();
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
    var schoolCode;
    var agencyCode;
    var areaLevel;
    if(userType=='3'){
        schoolCode="${upmsUser.relationCode}";
    }else{
        //console.log(33);
        agencyCode=$("#agencyCode").val();
        areaLevel=$("#areaLevel").val();
    }
    if(!agencyCode){
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
		title: '新增学校',
		columnClass: 'col-md-5 col-md-offset-3',
		content: 'url:${basePath}/manage/school/create?agencyCode='+agencyCode+'&areaLevel='+areaLevel+"&userType="+userType+"&schoolCode="+schoolCode,
		onContentReady: function () {
			initMaterialInput();
			$('select').select2();
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
			title: '编辑学校',
			columnClass: 'col-md-5 col-md-offset-3',
			content: 'url:${basePath}/manage/school/update/' + rows[0].schoolId+"/"+userType,
			onContentReady: function () {
				initMaterialInput();
				$('select').select2();
				initType();
				initSelect2();
			}
		});
	}
}
// 删除
var deleteDialog;
function deleteAction() {
    var schoolCode;
    var agencyCode;
    var areaLevel;
    if(userType=='3'){
        schoolCode="${upmsUser.relationCode}";
    }else{
         agencyCode=$("#agencyCode").val();
         areaLevel=$("#areaLevel").val();
    }
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
			content: '确认删除该学校吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].schoolId);
						}
						$.ajax({
							type: 'get',
							url: '${basePath}/manage/school/delete/' + ids.join("-"),
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
</script>
</body>
</html>