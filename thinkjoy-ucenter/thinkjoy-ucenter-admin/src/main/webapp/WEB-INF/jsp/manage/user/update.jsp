<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>


<style>


</style>

<div id="updateDialog" class="crudDialog">
	<form id="updateForm" method="post">
		<input type="hidden" id="uid" name ="uid" value="${ucenterUser.uid}">
		<div class="row">

			<div class="checkbox">
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_1" type="checkbox" name="userType" value="1" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 1}">checked </c:if></c:forEach> >
					<label for="usertype_1">学生 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_2" type="checkbox" name="userType" value="2" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 2}">checked </c:if></c:forEach> >
					<label for="usertype_2">家长 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_3" type="checkbox" name="userType" value="3"  <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 3}">checked </c:if></c:forEach> >
					<label for="usertype_3">老师 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_4" type="checkbox" name="userType" value="4" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 4}">checked </c:if></c:forEach>>
					<label for="usertype_4">组织机构 </label>
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
			url: '${basePath}/manage/user/update/${ucenterUser.userId}',
			data: $('#updateForm').serialize(),
			beforeSend: function() {
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