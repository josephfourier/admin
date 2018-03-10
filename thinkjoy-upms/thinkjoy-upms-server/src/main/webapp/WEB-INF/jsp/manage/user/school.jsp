<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="schoolDialog" class="crudDialog">
	<form id="schoolForm" method="post">
		<div class="form-group">
			<select id="schoolCode" name="schoolCode" multiple="multiple" style="width: 100%">
				<c:forEach items="${ucenterSchools}" var="ucenterSchool">
					<option value="${ucenterSchool.schoolCode}" <c:forEach  items="${upmsUserSchools}" var="upmsUserSchool">
                        <c:if test="${ucenterSchool.schoolCode==upmsUserSchool.schoolCode}">selected="selected"</c:if></c:forEach>>${ucenterSchool.schoolName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="schoolSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="schoolDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
function schoolSubmit() {
    var schoolCode=$("#schoolCode").val();
    var id="${userId}";
    //console.log("用户学校ID:"+schoolCode+";用户ID:"+id);
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/user/school?id=' + id,
        data: $('#schoolForm').serialize(),
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
                schoolDialog.close();
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