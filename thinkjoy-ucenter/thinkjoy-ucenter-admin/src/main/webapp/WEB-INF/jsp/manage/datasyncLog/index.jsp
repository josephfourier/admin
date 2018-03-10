﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.thinkjoy.common.base.BaseConstants" %>
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
	<title>数据同步日志管理</title>
	<jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
	<!--查询条件-->
	<div class="form-inline search-form">
		<div class="search-group">
			<label>系统名称:</label>
			<input type="text" class="form-control" id="search_datasyncSystem">
		</div>
		<div class="search-group" id="div_schoolName">
			<label>学校名称:</label>
			<input type="text" class="form-control" id="search_schoolName">
		</div>
		<div class="search-group">
			<label>导入时间:</label>
			<input  type="text"  class="form-control" id="search_datasyncTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		</div>
		<div class="search-group">
			<label>操作用户:</label>
			<input type="text" class="form-control" id="search_creator">
		</div>
		<div class="search-group">
			<label>操作状态:</label>
			<select class="form-control"  id="search_status">
				<option value="">请选择</option>
				<option value="1">成功</option>
				<option value="0">失败</option>
			</select>
		</div>
		<div class="search-group">
			<button  class="btn btn-primary" onclick="seach_filter()" >查询</button>
		</div>
	</div>
	<!--end-->
	<div id="toolbar">
		<shiro:hasPermission name="ucenter:datasyncLog:delete"><a class="waves-effect waves-button" href="javascript:;" onclick="deleteAction()"><i class="zmdi zmdi-close"></i> 删除日志</a></shiro:hasPermission>
	</div>
	<table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
	var relationCode = '';
	var userType = '${upmsUser.managerType}';
	$(function () {
		if (userType == <%=BaseConstants.ManagerType.SCHMANAGER%>) {
			//学校管理员默认显示该学校的数据
			relationCode = '${upmsUser.relationCode}';
			$('#div_schoolName').hide();
		} else {
			//区域管理员显示该区域的数据,不包括区域下学校的数据
			if (userType == <%=BaseConstants.ManagerType.AREAMANAGER%>) {
				relationCode = '';
			}
		}
	});

	var $table = $('#table');
	$(function() {
		// bootstrap table初始化
		$table.bootstrapTable({
			url: '${basePath}/manage/datasyncLog/list',
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
			idField: 'datasyncId',
			maintainSelected: true,
			toolbar: '#toolbar',
			queryParams:queryParams,
			columns: [
				{field: 'ck', checkbox: true},
				{field: 'datasyncId', title: '编号', align: 'center'},
				{field: 'datasyncSystem', title: '同步系统'},
				{field: 'schoolCode', title: '学校编码'},
				{field: 'schoolName', title: '学校名称'},
				{field: 'tableName', title: '同步表'},
				{field: 'data', title: '同步数据'},
				{field: 'dataLog', title: '日志信息'},
				{field: 'creator', title: '创建用户'},
				{field: 'datasyncTime', title: '同步时间', formatter: 'timeFormatter'},
				{field: 'status', title: '操作状态', align: 'center', formatter: 'statusFormatter'}
			]
		});
	});

	// 格式化时间
	function timeFormatter(value , row, index) {
		//设备信息列表格式化时间
		var jsDate = new Date(value);
		var UnixTimeToDate = jsDate.getFullYear() + '-' + (jsDate.getMonth() + 1) + '-' + jsDate.getDate() + ' ' + jsDate.getHours() + ':' + jsDate.getMinutes() + ':' + jsDate.getSeconds();
		return UnixTimeToDate ;
//		return new Date(value).toDateString();
	}

	// 格式化状态
	function statusFormatter(value, row, index) {
		if (value == 1) {
			return '<span class="label label-success">成功</span>';
		} else {
			return '<span class="label label-default">失败</span>';
		}
	}

	/**查询条件与分页数据 */
	function queryParams(pageReqeust) {
		pageReqeust.schoolCode = relationCode;
		pageReqeust.datasyncSystem=$("#search_datasyncSystem").val();
		pageReqeust.schoolName=$("#search_schoolName").val();
		pageReqeust.status=$("#search_status").val();
		pageReqeust.datasyncTime=$("#search_datasyncTime").val();
		pageReqeust.creator=$("#search_creator").val();
		console.log(pageReqeust);
		return pageReqeust;
	}

	//条件查询
	function seach_filter(){
		$table.bootstrapTable('refresh',{url:'${basePath}/manage/datasyncLog/list'});
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
				content: '确认删除该日志吗？',
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'waves-effect waves-button',
						action: function () {
							var ids = new Array();
							for (var i in rows) {
								ids.push(rows[i].datasyncId);
							}
							$.ajax({
								type: 'get',
								url: '${basePath}/manage/datasyncLog/delete/' + ids.join("-"),
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