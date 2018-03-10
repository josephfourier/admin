<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="permissionDialog" class="crudDialog">
	<form id="permissionForm" method="post">
		<div class="form-group">
			<ul id="ztree1" class="ztree"></ul>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="permissionSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="apppermissionsDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
var changeDatas = [];
var setting1 = {
	check: {
		enable: true,
		// 勾选关联父，取消关联子
		chkboxType: { "Y" : "p", "N" : "s" }
	},
	async: {
		enable: true,
		url: '${basePath}/manage/approle/permissiontree/' + approleId,
		otherParam: { appId: _appId,  perPer: perPer}
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onCheck: function() {
			var zTree = $.fn.zTree.getZTreeObj("ztree1")
			var changeNodes = zTree.getChangeCheckedNodes();
			changeDatas = [];
			for (var i = 0; i < changeNodes.length; i ++) {
				var changeData = {};
				changeData.id = changeNodes[i].id;
				changeData.checked = changeNodes[i].checked;
				changeDatas.push(changeData);
			}
		}
	}
};
function initTree() {
	$.fn.zTree.init($('#ztree1'), setting1);
}

function permissionSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/approle/permission/' + approleId,
        data: {datas: JSON.stringify(changeDatas), approleId: approleId},
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
				apppermissionsDialog.close();
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