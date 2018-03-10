<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
        <div class="form-group">
            <label for="schoolCode">学校编码</label>
            <input id="schoolCode" type="text" class="form-control" name="schoolCode" readonly="readonly" value="${trainingdirection.schoolCode}">
        </div>
        <div class="form-group">
            <label for="trdrName">专业培养方向名称</label>
            <input id="trdrName" type="text" class="form-control" name="trdrName" maxlength="128" value="${trainingdirection.trdrName}">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1"  <c:if test="${trainingdirection.status==1}">checked</c:if>>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0"  <c:if test="${trainingdirection.status==0}">checked</c:if>>
                <label for="status_0">锁定 </label>
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
        var userType=${userType};
        var schoolCode=$("#schoolCode").val();
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/traind/update/${trainingdirection.trdrId}',
            data: $('#updateForm').serialize(),
            beforeSend: function() {
                if ($('#trdrName').val() == '') {
                    $('#trdrName').focus();
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