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
	<title>认证配置管理</title>
	<jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="ams:oauthclient:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增认证配置</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:oauthclient:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑认证配置</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:oauthclient:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除认证配置</a></shiro:hasPermission>
	</div>
	<table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/oauthclient/list',
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
		maintainSelected: true,
		toolbar: '#toolbar',
		columns: [
			{field: 'ck', checkbox: true},
			{field: 'id', title: '编号', align: 'center'},
			{field: 'clientId', title: '客户端ID', align: 'center'},
			{field: 'clientSecret', title: '客户端私钥', align: 'center'},
			{field: 'clientName', title: '客户端名称', align: 'center'},
			{field: 'scope', title: '读写权限', align: 'center'},
			{field: 'roleId', title: '资源角色', align: 'center',formatter:'roleFormatter'},
			{field: 'appId', title: '应用系统', align: 'center',formatter:'appFormatter'},
			{field: 'grantTypes', title: '授权类型', align: 'center'},
			{field: 'redirectUri', title: '重定向地址', align: 'center'},
			{field: 'accessTokenValidity', title: 'token有效时间值(秒)', align: 'center'},
			{field: 'refreshTokenValidity', title: 'refresh_token有效时间值(秒)', align: 'center'},
			{field: 'trusted', title: '是否为受信任', align: 'center',formatter:'trustedFormatter'},
			{field: 'isMutiLogin', title: '是否支持同时在线', align: 'center',formatter:'isMutiLoginFormatter'},
			{field: 'description', title: '描述', align: 'center'},
			{field: 'status', title: '状态', formatter: 'statusFormatter'}
		]
	});
});

//格式化资源角色
function roleFormatter(value, row, index) {
	<c:forEach items="${amsRoles}" var="amsRoles">
	if(value==${amsRoles.roleId}){
		return '${amsRoles.roleName}';
	}
	</c:forEach>
}
//格式化资源角色
function appFormatter(value, row, index){
	<c:forEach items="${amsApps}" var="amsApp">
	if(value==${amsApp.appId}){
		return '${amsApp.appName}';
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


// 格式化是否为受信任
function trustedFormatter(value, row, index) {
	if (value == 0) {
		return '否';
	}
	if (value == 1) {
		return '是';
	}
	return '-';
}
// 格式化是否支持同时在线
function isMutiLoginFormatter(value, row, index) {
	if (value == 0) {
		return '否';
	}
	if (value == 1) {
		return '是';
	}
	return '-';
}

//状态格式化
function statusFormatter(value, row, index){
	if (value == 1) {
		return '<span class="label label-success">正常</span>';
	} else if(value == 0) {
		return '<span class="label label-default">锁定</span>';
	}
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
		title: '新增资源',
		content: 'url:${basePath}/manage/oauthclient/create',
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
			title: '编辑资源',
			content: 'url:${basePath}/manage/oauthclient/update/' + rows[0].id,
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
			content: '确认删除该资源吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].id);
						}
						$.ajax({
							type: 'get',
							url: '${basePath}/manage/oauthclient/delete/' + ids.join("-"),
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