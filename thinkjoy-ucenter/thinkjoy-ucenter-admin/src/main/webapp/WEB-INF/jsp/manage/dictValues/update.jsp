<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<div id="updateDialog" class="crudDialog">
	<form id="updateForm" method="post">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="dictName">字典名称</label>
						<input id="dictName" type="text" class="form-control" name="dictName" maxlength="300" value="${ucenterDictValues.dictName}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="dictCode">字典编码</label>
						<input id="dictCode" type="text" class="form-control" name="dictCode" maxlength="300" value="${ucenterDictValues.dictCode}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="valueKey">字典值编码</label>
						<input id="valueKey" type="text" class="form-control" name="valueKey" maxlength="20" value="${ucenterDictValues.valueKey}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="valueName">字典值名称</label>
						<input id="valueName" type="text" class="form-control" name="valueName" maxlength="20" value="${ucenterDictValues.valueName}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description" maxlength="256" value="${ucenterDictValues.description}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
			<div class="radio">
				<div class="radio radio-inline radio-success">
					<input id="status_1" type="radio" name="status" value="1" <c:if test="${ucenterDictValues.status==1}">checked</c:if>>
					<label for="status_1">正常 </label>
				</div>
				<div class="radio radio-inline">
					<input id="status_0" type="radio" name="status" value="0" <c:if test="${ucenterDictValues.status==0}">checked</c:if>>
					<label for="status_0">锁定 </label>
				</div>
			</div>
			</div>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="updateSubmit();">修改</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>

	function updateSubmit() {
		$.ajax({
			type: 'post',
			url: '${basePath}/manage/dictValues/update/${ucenterDictValues.valueId}',
			data: $('#updateForm').serialize(),
			beforeSend: function() {
				if ($('#dictCode').val() == '') {
					$('#dictCode').focus();
					return false;
				}
				if ($('#valueKey').val() == '') {
					$('#valueKey').focus();
					return false;
				}
				if ($('#valueName').val() == '') {
					$('#valueName').focus();
					return false;
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
					updateDialog.close();
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