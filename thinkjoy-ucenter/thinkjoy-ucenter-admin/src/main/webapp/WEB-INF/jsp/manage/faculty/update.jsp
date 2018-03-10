﻿﻿<%@ page contentType="text/html; charset=utf-8"%>
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
            <label for="facultyCode">院系编码</label>
            <input id="facultyCode" type="text" class="form-control" name="facultyCode" readonly="readonly" value="${ucenterFaculty.facultyCode}">
        </div>
        <div class="form-group">
            <label for="facultyName">院系名称</label>
            <input id="facultyName" type="text" class="form-control" name="facultyName" maxlength="64" value="${ucenterFaculty.facultyName}">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${ucenterFaculty.schoolCode}">
        </div>
        <div class="form-group">
            <label for="birthday">成立时间</label>
            <input id="birthday" type="text"  name="birthday" class="Wdate form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" value="<fmt:formatDate value="${ucenterFaculty.birthday}" type="both" pattern="yyyy-MM-dd"/>">
        </div>
        <div class="form-group">
            <label for="fzrName">院系负责人</label>
            <input id="fzrName" type="text" class="form-control" name="fzrName"  maxlength="32" value="${ucenterFaculty.fzrName}">
        </div>
        <div class="form-group">
            <label for="tel">联系方式</label>
            <input id="tel" type="text" class="form-control" name="tel"  maxlength="32" value="${ucenterFaculty.tel}">
        </div>
        <%--<div class="form-group sl">--%>
            <%--<label for="year">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年</label>--%>
            <%--<select id="year" name="year">--%>
                <%--<c:forEach items="${list}" var="list">--%>
                    <%--<option--%>
                            <%--<c:if test="${list eq ucenterFaculty.year}">selected="selected"</c:if>>${list}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</div>--%>

        <div class="form-group">
            <label for="description">院系简介</label>
            <input id="description" type="text" class="form-control" name="description"  maxlength="256" value="${ucenterFaculty.description}">
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterFaculty.status==1}">checked</c:if>>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterFaculty.status==0}">checked</c:if>>
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
            url: '${basePath}/manage/faculty/update/${ucenterFaculty.facultyId}',
            data: $('#updateForm').serialize(),
            beforeSend: function() {
                if ($('#facultyCode').val() == '') {
                    $('#facultyCode').focus();
                    return false;
                }
                if ($('#facultyName').val() == '') {
                    $('#facultyName').focus();
                    return false;
                }
                if ($('#tel').val() == '') {
                    $('#tel').focus();
                    return false;
                }else{
                    var reg = /^1\d{10}$/;
                    if (!reg.test($("#tel").val())) {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: '请输入有效的手机号码!',
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        return false;
                    }
                }


            },
            success: function(result) {
                if (result.code != 1) {
                    if (result.data instanceof Array) {
                        $.each(result.data, function (index, value) {
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