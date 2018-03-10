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
	<title>组织机构管理</title>
	<jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<shiro:hasPermission name="ucenter:agency:create"><a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增机构</a></shiro:hasPermission>
		<shiro:hasPermission name="ucenter:agency:update"><a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑机构</a></shiro:hasPermission>
		<%--<shiro:hasPermission name="ucenter:agency:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除机构</a></shiro:hasPermission>--%>
	</div>
	<table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
	var $table = $('#table');
	$(function() {
		// bootstrap table初始化
		$table.bootstrapTable({
			url: '${basePath}/manage/agency/list',
			height: getHeight(),
			striped: true,
			search: false,
			showExport: true,
			exportDataType: 'all',
			exportOptions: {
				ignoreColumn: [0, 1],  //忽略某一列的索引
				fileName: '组织机构信息导出',  //文件名称设置
				worksheetName: '组织机构信息',  //表格工作区名称
				tableName: '组织机构信息导出',
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
			idField: 'agencyId',
			maintainSelected: true,
			toolbar: '#toolbar',
			columns: [
				{field: 'ck', checkbox: true},
				{field: 'agencyId', title: '编号', align: 'center'},
				{field: 'agencyCode', title: '组织机构编码'},
				{field: 'agencyName', title: '组织机构名称'},
				{field: 'pagencyCode', title: '上级机构编码'},
				{field: 'shortName', title: '简称'},
				{field: 'url', title: '主页'},
				{field: 'areaType', title: '区域类型', formatter: 'areaTypeFormatter'},
				{field: 'areaCode', title: '行政区划'},
				{field: 'agencyPhone', title: '机构电话'},
				{field: 'isParent', title: '是否父级', formatter: 'isParentFormatter'},
				{field: 'description', title: '描述'},
				{field: 'status', title: '状态', align: 'center', formatter: 'statusFormatter'}

			]
		});
	});
	// 格式化操作按钮
	function actionFormatter(value, row, index) {
		return [
			'<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
//			'<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
		].join('');
	}

	// 格式化类型
	function areaTypeFormatter(value, row, index) {
		if (value == 1) {
			return '省';
		}
		if (value == 2) {
			return '市';
		}
		if (value == 3) {
			return '区/县';
		}
		return '-';
	}
	// 格式化是否父级
	function isParentFormatter(value, row, index) {
		if (value == 'true') {
			return '是';
		}
		if (value == 'false') {
			return '否';
		}
		return '';
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
			title: '新增机构',
			content: 'url:${basePath}/manage/agency/create',
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
				title: '编辑机构',
				content: 'url:${basePath}/manage/agency/update/' + rows[0].agencyId,
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
				content: '确认删除该机构吗？',
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'waves-effect waves-button',
						action: function () {
							var ids = new Array();
							for (var i in rows) {
								ids.push(rows[i].agencyId);
							}
							$.ajax({
								type: 'get',
								url: '${basePath}/manage/agency/delete/' + ids.join("-"),
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