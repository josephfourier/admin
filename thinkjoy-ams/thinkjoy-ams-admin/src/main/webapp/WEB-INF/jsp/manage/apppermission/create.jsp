﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<style>
	.form-group .type2 .type3{
		border-bottom: 2px solid #eee;
	}
	.form-group .type2 .type3>label{
		position: relative;
		margin-right: 5px;
	}

	.form-group.sl>label {
		 padding-left: 0;
	}
</style>
<div id="createDialog" class="crudDialog">
	<form id="createForm" method="post">
		<div class="radio">
			<div class="radio radio-inline radio-success">
				<input id="type_0" type="radio" name="type" value="0"  checked>
				<label for="type_0">系统 </label>
			</div>
			<div class="radio radio-inline radio-success">
				<input id="type_1" type="radio" name="type" value="1">
				<label for="type_1">目录 </label>
			</div>
			<div class="radio radio-inline radio-info">
				<input id="type_2" type="radio" name="type" value="2">
				<label for="type_2">菜单 </label>
			</div>
			<div class="radio radio-inline radio-warning">
				<input id="type_3" type="radio" name="type" value="3">
				<label for="type_3">按钮 </label>
			</div>
		</div>
		<div class="form-group">
			<span class="type0 type1 type2 type3">
				<select id="systemId" name="systemId">
					<option value="0">请选择系统</option>
					<c:forEach var="amsApp" items="${amsApps}">
						<option value="${amsApp.appId}">${amsApp.appName}</option>
					</c:forEach>
				</select>
			</span>
			<span class="type2 type3" hidden>
				<select id="pid" name="pid">
					<option value="0">请选择上级</option>
				</select>
			</span>
		</div>
		<div class="form-group">
			<label for="name">名称</label>
			<input id="name" type="text" class="form-control" name="name" maxlength="20">
		</div>
		<div class="form-group type2 type3" hidden>
			<label for="permissionValue">权限值</label>
			<input id="permissionValue" type="text" class="form-control" name="permissionValue" maxlength="50">
		</div>
		<div class="form-group type2 type3" hidden>
			<label for="uri">路径</label>
			<input id="uri" type="text" class="form-control" name="uri" maxlength="100">
		</div>
		<div class="form-group sl type2 type3" hidden>
			<label for="isApproval">是否有审批流程</label>
			<select class="form-control" id="isApproval" name="isApproval">
				<option value="">请选择...</option>
				<c:set var="isApproval" value="<%=BaseConstants.Dict.ISAPPROVAL %>"/>
				<c:forEach items="${dict:getValueByCode(isApproval)}" var="at">
					<option value="${at.valueKey}">${at.valueName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group type2 type3" hidden>
			<label for="approvalUri">审批页面地址</label>
			<input id="approvalUri" type="text" class="form-control" name="approvalUri">
		</div>
		<div class="form-group sl type2 type3" hidden >
			<label for="applicableIdentity">适用身份类型</label>
			<select id="applicableIdentity" name="applicableIdentity" multiple="multiple" style="width:200px;">
				<c:set var="applicableIdentity" value="<%=BaseConstants.Dict.APPLICABLEIDENTITY %>"/>
				<c:forEach items="${dict:getValueByCode(applicableIdentity)}" var="at">
					<option value="${at.valueKey}">${at.valueName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group type1 type3" hidden>
			<label for="icon">图标</label>
			<input id="icon" type="text" class="form-control" name="icon" maxlength="50" value="zmdi zmdi-widgets">
		</div>
		<div class="radio">
			<div class="radio radio-inline radio-success">
				<input id="status_1" type="radio" name="status" value="1" checked>
				<label for="status_1">正常 </label>
			</div>
			<div class="radio radio-inline">
				<input id="status_0" type="radio" name="status" value="0">
				<label for="status_0">锁定 </label>
			</div>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
	var pidType = 0;
	var systemId = 0;
	var type = 0;
	$(function() {
		// 选择分类
		$('input:radio[name="type"]').change(function() {
			type = $(this).val();
			initType();
		});
		// 选择系统
		$('#systemId').change(function() {
			systemId = $(this).val();
			initPid();
		});
	});
	function initType() {
		// 显示对应必填项
		$('.type0,.type1,.type2,.type3').hide(0, function () {
			$('.type' + type).show();
		});
		// 级联菜单
		if (type == 2) {
			pidType = 1;
			initPid();
		}
		if (type == 3) {
			pidType = 2
			initPid();
		}
	}
	function initPid() {
		if (systemId != 0) {
			$.getJSON('${basePath}/manage/apppermission/list', {systemId: systemId, type: pidType, limit: 10000}, function(json) {
				var datas = [{id: 0, text: '请选择上级'}];
				for (var i = 0; i < json.rows.length; i ++) {
					var data = {};
					data.id = json.rows[i].permissionId;
					data.text = json.rows[i].name;
					datas.push(data);
				}
				$('#pid').empty();
				$('#pid').select2({
					data : datas
				});
			});
		} else {
			$('#pid').empty();
			$('#pid').select2({
				data : [{id: 0, text: '请选择上级'}]
			});
		}
	}
	function createSubmit() {
		if($('#isApproval').val() == 'true'){
			if ($('#approvalUri').val() == 0) {
				$('#approvalUri').focus().tips({msg: '请填写审批页面地址'});
				return false;
			}
		}
		$.ajax({
			type: 'post',
			url: '${basePath}/manage/apppermission/create',
			data: $('#createForm').serialize(),
			beforeSend: function() {
				if ($('#systemId').val() == 0) {
					$.confirm({
						title: false,
						content: '请选择系统！',
						autoClose: 'cancel|3000',
						backgroundDismiss: true,
						buttons: {
							cancel: {
								text: '取消',
								btnClass: 'waves-effect waves-button'
							}
						}
					});
					return false;
				}
				if (type == 1) {
					if ($('#name').val() == '') {
						$('#name').focus();
						return false;
					}
				}
				if (type == 2 || type == 3) {
					if ($('#pid').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择上级！',
							autoClose: 'cancel|3000',
							backgroundDismiss: true,
							buttons: {
								cancel: {
									text: '取消',
									btnClass: 'waves-effect waves-button'
								}
							}
						});
						return false;
					}
					if ($('#name').val() == '') {
						$('#name').focus();
						return false;
					}
					if ($('#permissionValue').val() == '') {
						$('#permissionValue').focus();
						return false;
					}
					if ($('#uri').val() == '') {
						$('#uri').focus();
						return false;
					}
				}
			},
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
					createDialog.close();
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
</script>