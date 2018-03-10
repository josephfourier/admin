<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp" %>
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
						<label for="clientId">客户端ID</label>
						<input id="clientId" type="text" class="form-control" name="clientId" maxlength="64" readonly="readonly" value="${oauthClientDetails.clientId}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="clientSecret">客户端私钥</label>
						<input id="clientSecret" type="text" class="form-control" name="clientSecret" maxlength="256" value="${oauthClientDetails.clientSecret}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="clientName">客户端名称</label>
						<input id="clientName" type="text" class="form-control" name="clientName" maxlength="128" value="${oauthClientDetails.clientName}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<span>选择应用</span>
					<span>
						<select id="appClass" name="appClass">
							<c:set var="APPCLASS" value="<%=BaseConstants.Dict.APPCLASS %>"/>
							<option value="0">请选择应用分类</option>
							<c:forEach items="${dict:getValueByCode(APPCLASS)}" var="at">
								<option value="${at.valueKey}" <c:if test="${at.valueKey==appClass}">selected</c:if>>${at.valueName}</option>
							</c:forEach>
						</select>
					</span>
					<span>
						<select id="appId" name="appId">
							<option value="0">请选择应用</option>
						</select>
					</span>
				</div>
			</div>
			<div class="form-group sl">
				<label for="roleId">资源角色</label>
				<select id="roleId" name="roleId">
					<option value="0">请选择资源角色</option>
					<c:forEach items="${amsRoles}" var="amsRoles">
						<option value="${amsRoles.roleId}" <c:if test="${oauthClientDetails.roleId==amsRoles.roleId}">selected</c:if>>${amsRoles.roleName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group sl">
				<label for="scope">读写权限</label>
				<select  id="scope" name="scope">
					<option <c:if test="${oauthClientDetails.scope=='read'}">selected</c:if>>read</option>
					<option <c:if test="${oauthClientDetails.scope=='write'}">selected</c:if>>write</option>
					<option <c:if test="${oauthClientDetails.scope=='read write'}">selected</c:if>>read write</option>
				</select>
			</div>
			<div class="form-group sl">
				<label for="grantTypes">授权类型</label>
				<select id="grantTypes" name="grantTypes" multiple="multiple">
					<option <c:forEach var="grantTypes" items="${grantTypes}"><c:if test="${grantTypes=='authorization_code'}">selected</c:if></c:forEach>>authorization_code</option>
					<option <c:forEach var="grantTypes" items="${grantTypes}"><c:if test="${grantTypes=='password'}">selected</c:if></c:forEach>>password</option>
					<option <c:forEach var="grantTypes" items="${grantTypes}"><c:if test="${grantTypes=='refresh_token'}">selected</c:if></c:forEach>>refresh_token</option>
					<option <c:forEach var="grantTypes" items="${grantTypes}"><c:if test="${grantTypes=='implicit'}">selected</c:if></c:forEach>>implicit</option>
					<option <c:forEach var="grantTypes" items="${grantTypes}"><c:if test="${grantTypes=='client_credentials'}">selected</c:if></c:forEach>>client_credentials</option>
				</select>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="redirectUri">重定向地址</label>
						<input id="redirectUri" type="text" class="form-control" name="redirectUri" value="${oauthClientDetails.redirectUri}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="accessTokenValidity">token有效时间值</label>
						<input id="accessTokenValidity" type="text" class="form-control" name="accessTokenValidity" value="${oauthClientDetails.accessTokenValidity}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="refreshTokenValidity">refresh_token有效时间值</label>
						<input id="refreshTokenValidity" type="text" class="form-control" name="refreshTokenValidity" value="${oauthClientDetails.refreshTokenValidity}">
					</div>
				</div>
			</div>
			<div class="form-group sl">
				<label for="trusted">是否为受信任&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="trusted" name="trusted">
					<option value="" selected>请选择...</option>
					<option value="0" <c:if test="${oauthClientDetails.trusted==0}">selected</c:if>>否</option>
					<option value="1" <c:if test="${oauthClientDetails.trusted==1}">selected</c:if>>是</option>
				</select>
			</div>
			<div class="form-group sl">
				<label for="isMutiLogin">是否支持同时在线</label>
				<select id="isMutiLogin" name="isMutiLogin">
					<option value="" selected>请选择...</option>
					<option value="0" <c:if test="${oauthClientDetails.isMutiLogin==0}">selected</c:if>>否</option>
					<option value="1" <c:if test="${oauthClientDetails.isMutiLogin==1}">selected</c:if>>是</option>
				</select>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description" value="${oauthClientDetails.description}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="radio">
					<div class="radio radio-inline radio-success">
						<input id="status_1" type="radio" name="status" value="1" <c:if test="${oauthClientDetails.status==1}">checked</c:if>>
						<label for="status_1">正常 </label>
					</div>
					<div class="radio radio-inline">
						<input id="status_0" type="radio" name="status" value="0" <c:if test="${oauthClientDetails.status==0}">checked</c:if>>
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
	var appClass = ${appClass};
	$(function(){
		$('#appClass').change(function(){
			appClass=$(this).val();
			initApp();
		});
	});
	function initApp(val){
		if(appClass!=0){
			$.getJSON('${basePath}/manage/app/list',{appClass:appClass,limit: 10000},function(json){
				var datas = [{id: 0, text: '请选择应用'}];
				for (var i = 0; i < json.rows.length; i ++) {
					var data = {};
					data.id = json.rows[i].appId;
					data.text = json.rows[i].appName;
					datas.push(data);
				}
				$('#appId').empty();
				$('#appId').select2({
					data : datas,
					width:'140px'
				});
				if (!!val) {
					$('#appId').select2().val(val).trigger('change');
				}
			});
		}else{
			$('#appId').empty();
			$('#appId').select2({
				data : [{id: 0, text: '请选择应用'}],
				width:'140px'
			});
		}
	}
	function initSelect2(){
		initApp(${oauthClientDetails.appId});
	}
function updateSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/oauthclient/update/${oauthClientDetails.id}',
        data: $('#updateForm').serialize(),
        beforeSend: function() {
			if ($('#clientId').val() == '') {
				$('#clientId').focus();
				return false;
			}
			if ($('#clientSecret').val() == '') {
				$('#clientSecret').focus();
				return false;
			}
			if ($('#clientName').val() == '') {
				$('#clientName').focus();
				return false;
			}
			if ($('#appId').val() == 0) {
				$.confirm({
					title: false,
					content: '请选择应用！',
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
			if ($('#roleId').val() == 0) {
				$.confirm({
					title: false,
					content: '请选择资源角色！',
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