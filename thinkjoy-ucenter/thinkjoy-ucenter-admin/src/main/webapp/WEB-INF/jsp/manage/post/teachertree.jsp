<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="teacherDialog" class="crudDialog">
	<form id="teacherForm" method="post">
		<div class="form-group">
			<ul id="ztree1" class="ztree"></ul>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="permissionSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="teacherDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
	var changeDatas = [];
	var setting1 = {
		check: {
			enable: true,
			// 勾选关联父，取消关联子
			chkboxType: { "Y" : "", "N" : "" }
		},
		async: {
			enable: true,
			url: '${basePath}/manage/department/getDepteacherTree?schoolCode=' + ${ucenterPost.schoolCode}+"&postId="+${ucenterPost.postId},
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
		}
	};


	function findcheckedZtree(){

		var treeObj=$.fn.zTree.getZTreeObj("ztree1"),
				nodes=treeObj.getCheckedNodes(true),
				v="";
		for(var i=0;i<nodes.length;i++){
			var changeData = {};
			changeData.id = nodes[i].id;
			changeData.pId=nodes[i].pId;
			changeDatas.push(changeData);
		}
		return changeDatas;
	}

	function initTree() {
		$.fn.zTree.init($('#ztree1'), setting1);
	}

	function permissionSubmit() {

		var changeDatas=findcheckedZtree();
		if($.isEmptyObject(changeDatas)){
			$.confirm({
				theme: 'dark',
				animation: 'rotateX',
				closeAnimation: 'rotateX',
				title: false,
				columnClass: 'col-md-4 col-md-offset-3',
				content: '请选择人员信息',
				buttons: {
					confirm: {
						text: '确认!',
						btnClass: 'waves-effect waves-button waves-light'
					}
				}
			});
			return;
		}
		console.log(changeDatas);
		$.ajax({
			type: 'post',
			url:'${basePath}/manage/post/addteacher/' +${ucenterPost.postId} ,
			data: {datas: JSON.stringify(changeDatas)},
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
					teacherDialog.close();
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