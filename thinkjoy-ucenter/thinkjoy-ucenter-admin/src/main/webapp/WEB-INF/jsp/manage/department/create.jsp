<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<style>
	#createForm .col-sm-12.radioes .radio{display: inline-block;}
	#createForm .col-sm-12.radioes>label{
		font-weight: normal;
		min-width: 70px;
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
		<input type="hidden" name="schoolCode" id="schoolCode" value="${schoolCode}">
		<div class="form-group">
			<label for="departmentName">部门名称</label>
			<input id="departmentName" type="text" class="form-control"   name="departmentName"  maxlength="64">
		</div>
		<%--<div class="form-group sl">--%>
			<%--<label for="departmentType">部门类型</label>--%>
			<%--<select  id="departmentType" name="departmentType">--%>
				<%--<option value="">请选择...</option>--%>
				<%--<c:set var="DEPTTYPE" value="<%=BaseConstants.Dict.DEPTTYPE %>"/>--%>
				<%--<c:forEach items="${dict:getValueByCode(DEPTTYPE)}" var="at">--%>
					<%--<option value="${at.valueKey}">${at.valueName}</option>--%>
				<%--</c:forEach>--%>
			<%--</select>--%>
		<%--</div>--%>
		<div class="form-group">
			<label for="description">部门描述</label>
			<input id="description" type="text" class="form-control" name="description" maxlength="256">
		</div>
		<div  id="depTree">
			<div class="form-group">
				<div class="fg-line" >
					<input type="text"  id="parentName" name="parentName" class="form-control" value=""  readonly="readonly" onclick="initDepTree()" placeholder="父部门选择">
					<input type="hidden" id="parentId" name="parentId">
					<div id="ztreeDemo"  class="ztree" style="display: none;width:auto;height: auto"></div>
				</div>
			</div>
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

var setting = {
	view: {
		dblClickExpand: true
	},
	async: {
		enable: true,
		url: '${basePath}/manage/department/getDepTree?schoolCode=${schoolCode}'
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onClick
	}
};
function hideMenu(){
	$('#ztreeDemo').hide();
}

function beforeClick(treeId, treeNode) {
	hideMenu()
	return true;

}
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("ztreeDemo"),
			nodes = zTree.getSelectedNodes(),
			v = "";
	var vt="";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name;
		vt += nodes[i].id;
	}
	var parentName = $("#parentName");
	var parentId = $("#parentId");
	parentName.attr("value", v);
	parentId.attr("value", vt);

}


$(document).ready(function(){
	$.fn.zTree.init($('#ztreeDemo'), setting);
});

function initDepTree() {
	$('#ztreeDemo').show();
	setDisabledNode();
}

//删除锁定节点和其子节点
function setDisabledNode(){
	var treeObj = $.fn.zTree.getZTreeObj("ztreeDemo");
	var disabledNodes = treeObj.getNodesByParam("status", 0);
	console.log(disabledNodes);

	for (var i=0; i<disabledNodes.length; i++) {
		treeObj.removeChildNodes(disabledNodes[i]);//删除锁定节点的子节点
		treeObj.removeNode(disabledNodes[i])//删除锁定节点
	}
}



function createSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/department/create',
        data: $('#createForm').serialize(),
        beforeSend: function() {
            if ($('#departmentName').val() == '') {
                $('#departmentName').focus();
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