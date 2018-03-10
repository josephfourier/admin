<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>应用管理</title>
	<jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="ams:app:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增应用</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:app:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑应用</a></shiro:hasPermission>
		<shiro:hasPermission name="ams:app:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除应用</a></shiro:hasPermission>
	</div>
	<table id="table"></table>
	<form  method="get" action="aa" id ="passForm">
		<input id = "code" type = "hidden" name="code">
	</form>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
var $table = $('#table');
$(function() {
	// bootstrap table初始化
	$table.bootstrapTable({
		url: '${basePath}/manage/app/list',
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
			{field: 'appId', title: '编号', align: 'center'},
			{field: 'appCode', title: '应用编码', align: 'center'},
			{field: 'appName', title: '应用名称', align: 'center'},
			{field: 'accessType', title: '接入类型', align: 'center', formatter: 'dictFormatter1'},
			{field: 'icon', title: '应用图标', formatter: 'avatarFormatter', align: 'center'},
			{field: 'deviceType', title: '设备类型', align: 'center', formatter: 'dictFormatter2'},
			{field: 'appClass', title: '应用分类',  align: 'center', formatter: 'dictFormatter3'},
			{field: 'redirectUri', title: '重定向链接', align: 'center'},
			{field: 'creator', title: '创建者', align: 'center'},
			{field: 'ctime', title: '创建时间', align: 'center', formatter: 'timeFormatter'},
			{field: 'description', title: '描述', align: 'center'},
			{field: 'status', title: '启用状态', formatter: 'statusFormatter'},
			{field: 'applicableIdentity', title: '适用身份', align: 'center', formatter: 'dictFormatter4'},
			{field: 'isPersonalization', title: '是否个性化', align: 'center'}
		]
	});
});

//字典显示格式化
function dictFormatter1(value, row, index){

	<c:set var="DICT_CODE" value="<%=BaseConstants.Dict.ACCESSTYPE%>" />

	<c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
	if (value == ${at.valueKey}){
		return  '<span>${at.valueName}</span>';
	}
	</c:forEach>
}

//字典显示格式化
function dictFormatter2(value, row, index){

	<c:set var="DICT_CODE" value="<%=BaseConstants.Dict.DEVICETYPE%>" />

	<c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
	if (value == ${at.valueKey}){
		return '<span>${at.valueName}</span>';
	}
	</c:forEach>
}

//字典显示格式化
function dictFormatter3(value, row, index){

	<c:set var="DICT_CODE" value="<%=BaseConstants.Dict.APPCLASS%>" />

	<c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at">
	if (value == ${at.valueKey}){
		return '<span>${at.valueName}</span>';
	}
	</c:forEach>
}


//字典显示格式化
function dictFormatter4(value, row, index){

	var s = value.split(',')

	var res = '';

	for (var i = 0; i< s.length; i++){

		if (s[i] == '4'){
			res += '<span>教育机构,</span>';
		}
		if (s[i] == '2'){
			res += '<span>家长,</span>';
		}
		if (s[i] == '3'){
			res += '<span>老师,</span>';
		}
		if (s[i] == '1'){
			res += '<span>学生,</span>';
		}
	}
	return res.substring(0,res.lastIndexOf(","));
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

// 格式化应用图标
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
		title: '新增应用',
		content: 'url:${basePath}/manage/app/create',
		onContentReady: function () {
			initMaterialInput();
			initUploader();
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
			title: '编辑应用',
			content: 'url:${basePath}/manage/app/update/' + rows[0].appId,
			onContentReady: function () {
				initMaterialInput();
				initUploader();
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
			content: '确认删除该应用吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].appId);
						}
						$.ajax({
							type: 'get',
							url: '${basePath}/manage/app/delete/' + ids.join("-"),
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