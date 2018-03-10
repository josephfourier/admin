<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<style>
	.form-group.sl{
		border-bottom: 2px solid #eee;
	}
	.form-group.sl>label{
		position: relative;
		margin-right: 5px;
	}
</style>
<div id="updateDialog" class="crudDialog">
	<form id="updateForm" method="post">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="resourceName">资源名称</label>
						<input id="resourceName" readonly="readonly" type="text" class="form-control" name="resourceName" maxlength="300" value="${amsResources.resourceName}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="resourceName">资源uri</label>
						<input id="resourceUri" type="text" class="form-control" name="resourceUri" maxlength="300" value="${amsResources.resourceUri}">
					</div>
				</div>
			</div>

			<div class="form-group sl">
				<label for="resourceType">资源协议类型</label>
				<select  id="resourceType" name="resourceType">
					<c:set var="RESOURCETYPE" value="<%=BaseConstants.Dict.RESOURCETYPE %>"/>
					<option value="">请选择...</option>
					<c:forEach items="${dict:getValueByCode(RESOURCETYPE)}" var="at">
						<option value="${at.valueKey}" <c:if test="${at.valueKey eq amsResources.resourceType}">selected</c:if> >${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group sl">
				<label for="resourceClass">资源分类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select  id="resourceClass" name="resourceClass">
					<c:set var="RESOURCECLASS" value="<%=BaseConstants.Dict.RESOURCECLASS %>"/>
					<option value="">请选择...</option>
					<c:forEach items="${dict:getValueByCode(RESOURCECLASS)}" var="at">
						<option value="${at.valueKey}" <c:if test="${at.valueKey eq amsResources.resourceClass}">selected</c:if> >${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description" value="${amsResources.description}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="radio">
					<div class="radio radio-inline radio-success">
						<input id="status_1" type="radio" name="status" value="1"  <c:if test="${amsResources.status==1}">checked</c:if>>
						<label for="status_1">正常 </label>
					</div>
					<div class="radio radio-inline">
						<input id="status_0" type="radio" name="status" value="0"  <c:if test="${amsResources.status==0}">checked</c:if>>
						<label for="status_0">锁定 </label>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="updateSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>

function updateSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/resources/update/${amsResources.resourceId}',
        data: $('#updateForm').serialize(),
        beforeSend: function() {
			if ($('#resourceName').val() == '') {
				$('#resourceName').focus();
				return false;
			}
			if ($('#resourceUri').val() == '') {
				$('#resourceUri').focus();
				return false;
			}
			if ($('#resourceType').val() == '') {
				$('#resourceType').focus();
				return false;
			}
			if ($('#resourceClass').val() == '') {
				$('#resourceClass').focus();
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