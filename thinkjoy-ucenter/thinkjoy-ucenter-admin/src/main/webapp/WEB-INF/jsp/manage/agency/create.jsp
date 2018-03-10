﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="createDialog" class="crudDialog">
	<form id="createForm" method="post">
		<div class="radio">
			<div class="radio radio-inline radio-success">
				<input id="areaType_1" type="radio" name="areaType" value="1" checked>
				<label for="areaType_1">省级 </label>
			</div>
			<div class="radio radio-inline radio-info">
				<input id="areaType_2" type="radio" name="areaType" value="2">
				<label for="areaType_2">市级 </label>
			</div>
			<div class="radio radio-inline radio-warning">
				<input id="areaType_3" type="radio" name="areaType" value="3">
				<label for="areaType_3">区/县级</label>
			</div>
		</div>
		<div class="form-group">
			<span class="type1 type2 type3">行政区划</span>
			<span class="type1 type2 type3">
				<select id="ppareaCode" name="ppareaCode">
					<option value="0">请选择省</option>
					<c:forEach var="ucenterAreas" items="${ucenterAreas}">
						<option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
					</c:forEach>
				</select>
			</span>
			<span class="type2 type3" hidden>
				<select id="pareaCode" name="pareaCode">
					<option value="0">请选择市</option>
				</select>
			</span>
			<span class="type3" hidden>
				<select id="areaCode" name="areaCode">
					<option value="0">请选择区/县</option>
				</select>
			</span>
		</div>
		<div class="form-group">
			<span class="type2 type3" hidden>上级机构</span>
			<span class="type2 type3" hidden>
				<select id="ppagencyCode" name="ppagencyCode">
					<option value="0">请选择上级机构</option>
					<c:forEach var="ucenterAgencys" items="${ucenterAgencys}">
						<option value="${ucenterAgencys.agencyCode}">${ucenterAgencys.agencyName}</option>
					</c:forEach>
				</select>
			</span>
			<span class="type3" hidden>
				<select id="pagencyCode" name="pagencyCode">
					<option value="0">请选择上级机构</option>
				</select>
			</span>
		</div>
		<div class="form-group">
			<label for="agencyName">机构名称</label>
			<input id="agencyName" type="text" class="form-control" name="agencyName" maxlength="64">
		</div>
		<div class="form-group">
			<label for="shortName">机构简称</label>
			<input id="shortName" type="text" class="form-control" name="shortName" maxlength="64">
		</div>
		<div class="form-group">
			<label for="url">机构主页</label>
			<input id="url" type="text" class="form-control" name="url" maxlength="256">
		</div>
		<div class="form-group">
			<label for="agencyPhone">机构电话</label>
			<input id="agencyPhone" type="text" class="form-control" name="agencyPhone" maxlength="32">
		</div>
		<div class="form-group">
			<label for="description">描述</label>
			<input id="description" type="text" class="form-control" name="description" maxlength="256">
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
	var areaType = 0;
	var ppagencyCode = 0;
	$(function() {
		// 选择分类
		$('input:radio[name="areaType"]').change(function() {
			areaType = $(this).val();
			initType();
		});
		// 选择上级机构
		$('#ppagencyCode').change(function() {
			ppagencyCode = $(this).val();
			initPid();
		});

		//选择行政区划-市级
		$('#ppareaCode').change(function() {
			ppareaCode = $(this).val();
			initAreaCode();
		});

		//选择行政区划-区/县级
		$('#pareaCode').change(function() {
			pareaCode = $(this).val();
			initAreaCodeOne();
		});

	});
	function initType() {
		// 显示对应必填项
		$('.type1,.type2,.type3').hide(0, function () {
			$('.type' + areaType).show();
		});
	}
	function initPid() {
		if (ppagencyCode != 0) {
			$.getJSON('${basePath}/manage/agency/list', {pagencyCode: ppagencyCode, areaType: areaType,status:1, limit: 10000}, function(json) {
				var datas = [{id: 0, text: '请选择上级机构'}];
				for (var i = 0; i < json.rows.length; i ++) {
					var data = {};
					data.id = json.rows[i].agencyCode;
					data.text = json.rows[i].agencyName;
					datas.push(data);
				}
				$('#pagencyCode').empty();
				$('#pagencyCode').select2({
					data : datas
				});
			});
		} else {
			$('#pagencyCode').empty();
			$('#pagencyCode').select2({
				data : [{id: 0, text: '请选择上级机构'}]
			});
		}
	}

	function initAreaCode() {
		if (ppareaCode != 0) {
			$.getJSON('${basePath}/manage/area/list', {areaCode: ppareaCode, areaType: areaType, limit: 10000}, function(json) {
				var datas = [{id: 0, text: '请选择市'}];
				for (var i = 0; i < json.rows.length; i ++) {
					var data = {};
					data.id = json.rows[i].areaCode;
					data.text = json.rows[i].areaName;
					datas.push(data);
				}
				$('#pareaCode').empty();
				$('#pareaCode').select2({
					data : datas
				});
				$('#areaCode').empty();
				$('#areaCode').select2({
					data : [{id: 0, text: '请选择区/县'}]
				});
			});
		} else {
			$('#pareaCode').empty();
			$('#pareaCode').select2({
				data : [{id: 0, text: '请选择市'}]
			});
			$('#areaCode').empty();
			$('#areaCode').select2({
				data : [{id: 0, text: '请选择区/县'}]
			});
		}
	}
	function initAreaCodeOne() {
		if (pareaCode != 0) {
			$.getJSON('${basePath}/manage/area/list', {areaCode: pareaCode, areaType: areaType, limit: 10000}, function(json) {
				var datas = [{id: 0, text: '请选择区/县'}];
				for (var i = 0; i < json.rows.length; i ++) {
					var data = {};
					data.id = json.rows[i].areaCode;
					data.text = json.rows[i].areaName;
					datas.push(data);
				}
				$('#areaCode').empty();
				$('#areaCode').select2({
					data : datas
				});
			});
		} else {
			$('#areaCode').empty();
			$('#areaCode').select2({
				data : [{id: 0, text: '请选择区/县'}]
			});
		}
	}

	function createSubmit() {
		$.ajax({
			type: 'post',
			url: '${basePath}/manage/agency/create',
			data: $('#createForm').serialize(),
			beforeSend: function() {
				if ($('#agencyName').val() == '') {
					$('#agencyName').focus();
					return false;
				}
				if (areaType == 1) {
					if ($('#ppareaCode').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择省！',
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

				}
				if (areaType == 2) {
					if ($('#ppagencyCode').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择上级机构！',
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

					if ($('#pareaCode').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择市！',
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
				}
				if(areaType == 3){
					if ($('#pagencyCode').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择上级机构！',
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

					if ($('#areaCode').val() == 0) {
						$.confirm({
							title: false,
							content: '请选择区/县！',
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
				}
				if ($('#agencyPhone').val() != '') {
					var reg = /^1\d{10}$/;
					if (!reg.test($("#agencyPhone").val())) {
						$.confirm({
							theme: 'dark',
							animation: 'rotateX',
							closeAnimation: 'rotateX',
							title: false,
							content: '请输入有效的手机号码!',
							buttons: {
								confirm: {
									text: '确认',
									btnClass: 'waves-effect waves-button waves-light'
								}
							}
						});
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