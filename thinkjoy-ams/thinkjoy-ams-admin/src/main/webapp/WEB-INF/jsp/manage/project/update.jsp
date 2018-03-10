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
						<label for="projectName">项目名称</label>
						<input id="projectName" readonly="readonly" type="text" class="form-control" name="projectName" maxlength="128" value="${amsProject.projectName}">
					</div>
				</div>
			</div>

			<div class="form-group sl">
				<label>区域类型</label>
				<select id="areaType" name="areaType">
					<c:set var="AREATYPE" value="<%=BaseConstants.Dict.AREATYPE%>"/>
					<option value="">请选择...</option>
					<c:forEach items="${dict:getValueByCode(AREATYPE)}" var="at">
						<option value="${at.valueKey}" <c:if test="${at.valueKey eq amsProject.areaType}">selected</c:if>>${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description" maxlength="256" value="${amsProject.description}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="radio">
					<div class="radio radio-inline radio-success">
						<input id="status_1" type="radio" name="status" value="1"  <c:if test="${amsProject.status==1}">checked</c:if>>
						<label for="status_1">正常 </label>
					</div>
					<div class="radio radio-inline">
						<input id="status_0" type="radio" name="status" value="0"  <c:if test="${amsProject.status==0}">checked</c:if>>
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
        url: '${basePath}/manage/project/update/${amsProject.projectId}',
        data: $('#updateForm').serialize(),
        beforeSend: function() {
			if ($('#projectName').val() == '') {
				$('#projectName').focus();
				return false;
			}
			if ($('#areaType').val() == '') {
				$('#areaType').focus();
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