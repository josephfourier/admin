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

	<div style="width: 20%; height: 100%;float:left;">
		<table style="">
		  <ul id="ztree" class="ztree"></ul>
		</table>
	</div>

	<div style="width: 80%; height: 100%;float:right">
		<div id="toolbar">
			<shiro:hasPermission name="ucenter:school:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增学校</a></shiro:hasPermission>
			<shiro:hasPermission name="ucenter:school:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑学校</a></shiro:hasPermission>
			<shiro:hasPermission name="ucenter:school:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除学校</a></shiro:hasPermission>
		</div>
		<table id="table"></table>
	</div>

</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>

	var changeDatas = [];
	var setting = {
		check: {
			enable: false,
			// 勾选关联父，取消关联子
			chkboxType: { "Y" : "p", "N" : "s" }
		},
		async: {
			enable: true,
			<%--url: '${basePath}/manage/permission/role/' + 1--%>
			url: '${basePath}/manage/area/getAreaTree
			<%--url: '${basePath}/manage/user/getTree/1'--%>
			<%--url: '${basePath}/manage/area/role/1'--%>


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
		$table.bootstrapTable('refresh',{url:'${basePath}/manage/school/list'});
	}


	<%--var setting = {--%>
		<%--view: {--%>
			<%--dblClickExpand: true--%>
		<%--},--%>
		<%--async: {--%>
			<%--enable: true,--%>
			<%--url: '${basePath}/manage/user/getTree/1'--%>
		<%--},--%>
		<%--data: {--%>
			<%--simpleData: {--%>
				<%--enable: true--%>
			<%--}--%>
		<%--},--%>
		<%--callback: {--%>
			<%--beforeClick: beforeClick,--%>
			<%--onClick: onClick--%>
		<%--}--%>
	<%--};--%>

	$(document).ready(function(){
		$.fn.zTree.init($('#ztree'), setting);
	});

var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/school/list',
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
		idField: 'schoolId',
		maintainSelected: true,
		toolbar: '#toolbar',
		columns: [
			{field: 'ck', checkbox: true},
			{field: 'schoolId', title: '编号', sortable: true, align: 'center'},
            {field: 'schoolCode', title: '学校编码'},
			{field: 'schoolName', title: '学校名称'},
			{field: 'schoolEnname', title: '英文名称'},
			{field: 'agencyCode', title: '所属机构'},
			{field: 'areaCode', title: '所属区域'},
			{field: 'address', title: '地址'},
			{field: 'birthday', title: '建校日期', formatter: 'birthdayFormatter'},
			{field: 'description', title: '描述'},
			{field: 'status', title: '状态', sortable: true, align: 'center', formatter: 'statusFormatter'},
			{field: 'action', title: '操作', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
		]
	});
});
// 格式化操作按钮
function actionFormatter(value, row, index) {
    return [
		'<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
		'<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}
//格式化时间
function birthdayFormatter(value,row,index){
	//设备信息列表格式化时间
	var jsDate = new Date(value);
	var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate();
	//+ ' ' + jsDate.getHours() + ':' + jsDate.getMinutes() + ':' + jsDate.getSeconds();
	return UnixTimeToDate ;
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
	createDialog = $.dialog({
		animationSpeed: 300,
		title: '新增学校',
		content: 'url:${basePath}/manage/school/create',
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
			content: 'url:${basePath}/manage/school/update/' + rows[0].schoolId,
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