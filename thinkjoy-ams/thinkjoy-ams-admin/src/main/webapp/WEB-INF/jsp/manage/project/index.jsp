﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>项目管理</title>
	<jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="ams:project:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增项目</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:project:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑项目</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:project:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除项目</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:project:update"><a class="waves-effect waves-button" href="javascript:;" onclick="agency()"><i class="zmdi zmdi-close"></i> 关联组织与应用</a></shiro:hasPermission>
		<%--<shiro:hasPermission name="ams:project:update"><a class="waves-effect waves-button" href="javascript:;" onclick="app()"><i class="zmdi zmdi-close"></i> 关联应用</a></shiro:hasPermission>--%>
	</div>
	<table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/project/list',
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
		idField: 'id',
		sortName: 'orders',
        sortOrder: 'desc',
		maintainSelected: true,
		toolbar: '#toolbar',
		columns: [
			{field: 'ck', checkbox: true},
			{field: 'projectId', title: '编号', align: 'center'},
			{field: 'projectName', title: '项目名称', align: 'center'},
			{field: 'areaType', title: '区域类型', align: 'center', formatter: 'dictFormatter'},
			{field: 'creator', title: '创建者', align: 'center'},
			{field: 'ctime', title: '创建时间', align: 'center', formatter: 'timeFormatter'},
			{field: 'description', title: '描述', align: 'center'},
			{field: 'status', title: '启用状态', formatter: 'statusFormatter'}
		]
	});
});

//字典显示格式化
function dictFormatter(value, row, index){

	<c:set var="DICT_CODE" value="<%=BaseConstants.Dict.AREATYPE%>" />

	<c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
	if (value == ${at.valueKey}){
		return  '<span>${at.valueName}</span>';
	}
	</c:forEach>
}

// 格式化操作按钮
function actionFormatter(value, row, index) {
    return [
        '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

//状态格式化
function statusFormatter(value, row, index){
	if (value == 1) {
		return '<span class="label label-success">正常</span>';
	} else if(value == 0) {
		return '<span class="label label-default">锁定</span>';
	}
}

// 格式化项目图标
function avatarFormatter(value, row, index) {
	return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
}

// 格式化时间
function timeFormatter(value , row, index) {
	return new Date(value).toLocaleString();
}
// 新增
var createDialog;
function createAction() {
	createDialog = $.dialog({
		animationSpeed: 300,
		title: '新增项目',
		content: 'url:${basePath}/manage/project/create',
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
			title: '编辑项目',
			content: 'url:${basePath}/manage/project/update/' + rows[0].projectId,
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
			content: '确认删除该项目吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].projectId);
						}
						$.ajax({
							type: 'get',
							url: '${basePath}/manage/project/delete/' + ids.join("-"),
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

//关联组织
var projectId;
var agencyDialog;
function agency () {
	var rows = $table.bootstrapTable('getSelections');
	if (rows.length != 1){
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
	}else {
		projectId = rows[0].projectId;
		agencyDialog = $.dialog({
			animationSpeed: 300,
			title: '关联组织与应用',
			columnClass: 'col-md-7 col-md-offset-3',
			content: 'url:${basePath}/manage/project/agencyschool/' + projectId,
			onContentReady: function () {
				initMaterialInput();
				initTree();
			}
		});
	}
}

//关联应用
var projectId;
var appDialog;
function app () {
	var rows = $table.bootstrapTable('getSelections');
	if (rows.length != 1){
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
	}else {
		projectId = rows[0].projectId;
		appDialog = $.dialog({
			animationSpeed: 300,
			title: '关联应用',
			content: 'url:${basePath}/manage/project/relateapp/' + projectId,
			onContentReady: function () {
				initMaterialInput();
			}
		});
	}
}
</script>
</body>
</html>