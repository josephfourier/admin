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
		<input type="hidden" name="paysettingId" id="paysettingId" value="${ucenterPaySetting.paysettingId}">
		<div class="form-group">
			<label for="mchName">商户名称</label>
			<input id="mchName" type="text" class="form-control" name="mchName" maxlength="20" value="${ucenterPaySetting.mchName}" required="true">
		</div>
		<div class="form-group">
			<label for="mchId">商户号</label>
			<input id="mchId" type="text" class="form-control" name="mchId" maxlength="60" value="${ucenterPaySetting.mchId}" required="true">
		</div>
		<div class="form-group">
			<label for="mckKey">商户私钥</label>
			<input id="mckKey" type="text" class="form-control" name="mckKey" maxlength="60" value="${ucenterPaySetting.mckKey}" required="true">
		</div>

		<div class="radio">
			<div class="radio radio-inline radio-success">
				<input id="status_1" type="radio" name="status" value="1" <c:if test="${ucenterPaySetting.status==1}">checked</c:if>>
				<label for="status_1">正常 </label>
			</div>
			<div class="radio radio-inline">
				<input id="status_0" type="radio" name="status" value="-1" <c:if test="${ucenterPaySetting.status!=1}">checked</c:if>>
				<label for="status_0">锁定 </label>
			</div>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>

function createSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/paysetting/update/${ucenterPaySetting.paysettingId}',
        data: $('#updateForm').serialize(),
        beforeSend: function() {
			if ($('#mchName').val() == '') {
				$('#mchName').focus();
				return false;
			}
			if ($('#mchId').val() == '') {
				$('#mchId').focus();
				return false;
			}
			if ($('#mckKey').val() == '') {
				$('#mckKey').focus();
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