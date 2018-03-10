<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<style>
	.icon {
		width: 50px;
		height: 50px !important;
		position: absolute;
		top: -19px;
		left: 60px;
	}
	#ava {
		position: relative;
	}
	.form-group.sl{
		border-bottom: 2px solid #eee;
	}
	.form-group.sl>label{
		position: relative;
		margin-right: 5px;
	}
</style>

<div id="createDialog" class="crudDialog">
	<form id="createForm" method="post">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="appCode">应用编码</label>
						<input id="appCode" type="text" class="form-control" name="appCode" maxlength="100">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="appName">应用名称</label>
						<input id="appName" type="text" class="form-control" name="appName" maxlength="100">
					</div>
				</div>
			</div>
			<div class="form-group sl">
				<label for="accessType">应用类别&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="accessType" name="accessType">
					<c:set var="ACCESSTYPE" value="<%=BaseConstants.Dict.ACCESSTYPE %>"/>
					<c:forEach items="${dict:getValueByCode(ACCESSTYPE)}" var="at">
						<option value="${at.valueKey}">${at.valueName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group sl">
				<label for="appClass">应用分类&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="appClass" name="appClass">
					<c:set var="APPCLASS" value="<%=BaseConstants.Dict.APPCLASS %>"/>
					<c:forEach items="${dict:getValueByCode(APPCLASS)}" var="at" varStatus="i">
						<option value="${at.valueKey}" <c:if test="${i eq '1'}">selected</c:if> >${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group sl">
				<label for="applicableIdentity">适用身份&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select  id="applicableIdentity" name="applicableIdentity" multiple="multiple" >
					<c:set var="APPLICABLEIDENTITY" value="<%=BaseConstants.Dict.APPLICABLEIDENTITY %>"/>
					<option value="">请选择...</option>
					<c:forEach items="${dict:getValueByCode(APPLICABLEIDENTITY)}" var="at">
						<option value="${at.valueKey}">${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group sl">
				<label for="deviceType">设备类别&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select  id="deviceType" name="deviceType">
					<c:set var="DEVICETYPE" value="<%=BaseConstants.Dict.DEVICETYPE %>"/>
					<c:forEach items="${dict:getValueByCode(DEVICETYPE)}" var="at">
						<option value="${at.valueKey}">${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group sl">
				<label for="isPersonalization">是否个性化</label>
				<select id="isPersonalization" name="isPersonalization">
					<option value="">请选择...</option>
					<c:set var="PERPERSONALIZATION" value="<%=BaseConstants.Dict.PERPERSONALIZATION %>"/>
					<c:forEach items="${dict:getValueByCode(PERPERSONALIZATION)}" var="at">
						<option value="${at.valueKey}">${at.valueName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="redirectUri">重定向连接</label>
						<input id="redirectUri" type="text" class="form-control" name="redirectUri">
					</div>
				</div>
			</div>

			<div class="bg col-md-12">
				<div class="form-group">
					<label>应用图标</label>
					<img src="" alt="应用图标" class="path">
					<input type="text" class="form-control" name="icon" readonly>
					<input type="text" class="tmp form-control" readonly>
				</div>
				<div id="picker">上传图标</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
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
			</div>

			<%--<div class="col-sm-12">
				<div class="col-lg-8 form-group" id="ava">
					<span>应用图标</span>
					<input id="icon" type="hidden" class="form-control" name="icon" maxlength="150">
				</div>
				<div class="col-lg-4">
					<div id="picker">上传图标</div>
				</div>
			</div>--%>
		</div>

		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>


	function initUploader() {
		//百度上传按钮
		var uploader = WebUploader.create({
			// swf文件路径
			server: '/upload/imageUpload',
			swf: '${basePath}/resources/thinkjoy-admin/plugins/webuploader-0.1.5/Uploader.swf',
			// 文件接收服务端
			method: 'POST',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick: {
				"id": '#picker',
				"multiple": false
			},
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize: false,
			// 选完文件后，是否自动上传。
			auto: true,
			// 只允许选择图片文件
			accept: {
				title: '图片文件',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/bmp,image/gif,image/jpg,image/jpeg,image/png'
			}
		});
		uploader.on('fileQueued', function (file) {
			// 加入队列触发
		});

        uploader.on( 'uploadSuccess', function(file, response) {
            var src = response._raw;
            $('.bg label').hide();
            $('img.path').attr('src', src).show();
            $('input[name=icon]').val(src).hide();
            $('input.tmp').show();
        });

		uploader.on('uploadError', function (file) {
			// console.log('uploadError', file);
		});
	}


function createSubmit() {

    $.ajax({
        type: 'post',
        url: '${basePath}/manage/app/create',
        data: $('#createForm').serialize(),
        beforeSend: function() {

			if ($('#appCode').val() == '') {
				$('#appCode').focus();
				return false;
			}
			if ($('#appName').val() == '') {
				$('#appName').focus();
				return false;
			}
			if ($('#accessType').val() == '') {
				$('#accessType').focus();
				return false;
			}
			if ($('#appClass').val() == '') {
				$('#appClass').focus();
				return false;
			}

			if ($('#applicableIdentity').val() == '' || $('#applicableIdentity').val() == null) {
				$('#applicableIdentity').focus();
				return false;
			}
			if ($('#deviceType').val() == '') {
				$('#deviceType').focus();
				return false;
			}
			if ($('#redirectUri').val() == '') {
				$('#redirectUri').focus();
				return false;
			}
			if ($('#status').val() == '') {
				$('#status').focus();
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