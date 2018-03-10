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
            <label for="specialtyNo">教育局专业代码</label>
            <input id="specialtyNo" type="text" class="form-control" name="specialtyNo" readonly="readonly"  value="${ucenterSpecialty.specialtyNo}">
        </div>
        <div class="form-group">
            <label for="specialtyCode">校内专业代码</label>
            <input id="specialtyCode" type="text" class="form-control" name="specialtyCode" readonly="readonly" value="${ucenterSpecialty.specialtyCode}">
        </div>
        <div class="form-group">
            <label for="specialtyName">专业名称</label>
            <input id="specialtyName" type="text" class="form-control" name="specialtyName" maxlength="64" value="${ucenterSpecialty.specialtyName}">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${ucenterSpecialty.schoolCode}">
        </div>
        <div class="form-group sl">
            <label for="facultyCode">所属院系</label>
            <select id="facultyCode" name="facultyCode">
                <option value="">请选择院系</option>
                <c:forEach items="${facultyList}" var="facultyList">
                    <option value="${facultyList.facultyCode}"
                            <c:if test="${ucenterSpecialty.facultyCode eq facultyList.facultyCode}">selected="selected"</c:if>>${facultyList.facultyName}</option>
                </c:forEach>
                <input id="facultyName" type="hidden" class="form-control" name="facultyName" value="${ucenterSpecialty.facultyName}">
            </select>
        </div>
        <div class="form-group sl">
            <label for="trdrId">培养方向</label>
            <select id="trdrId" name="trdrId">
                <option value="">请选培养方向</option>
                <c:forEach items="${tradList}" var="tradList">
                    <option value="${tradList.trdrId}"
                            <c:if test="${tradList.trdrId eq ucenterSpecialty.trdrId}">selected="selected"</c:if>>${tradList.trdrName}</option>
                </c:forEach>
                <input id="trdrName" type="hidden" class="form-control" name="trdrName" value="${ucenterSpecialty.trdrName}">
            </select>
        </div>
        <div class="form-group sl">
            <label for="specialtyType">专业类别</label>
            <select id="specialtyType" name="specialtyType">
                <option value="">请选专业类别</option>
                <c:forEach items="${facTypeDicts}" var="facTypeDicts">
                    <option value="${facTypeDicts.valueKey}"
                            <c:if test="${facTypeDicts.valueKey eq ucenterSpecialty.specialtyType}">selected="selected"</c:if>>${facTypeDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="specialtyNature">专业性质</label>
            <select id="specialtyNature" name="specialtyNature">
                <option value="">请选专业性质</option>
                <c:forEach items="${facuityDicts}" var="facuityDicts">
                    <option value="${facuityDicts.valueKey}"
                            <c:if test="${facuityDicts.valueKey eq ucenterSpecialty.specialtyNature}">selected="selected"</c:if>>${facuityDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="enrollmentTarget">招生对象</label>
            <select id="enrollmentTarget" name="enrollmentTarget" multiple="multiple" style="width:200px;">
                <c:forEach items="${targetDicts}" var="targetDicts">
                    <option value="${targetDicts.valueKey}"
                            <c:forEach var="targetList" items="${targetList}"><c:if test="${targetDicts.valueKey eq targetList}">selected="selected"</c:if></c:forEach>>${targetDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="schoolSystem">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制</label>
            <select id="schoolSystem" name="schoolSystem">
                <option value="">请选学制</option>
                <c:forEach items="${xzDicts}" var="xzDicts">
                    <option value="${xzDicts.valueKey}"
                            <c:if test="${xzDicts.valueKey eq ucenterSpecialty.schoolSystem}">selected="selected"</c:if>>${xzDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <%--<div class="form-group sl">--%>
            <%--<label for="year">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年</label>--%>
            <%--<select id="year" name="year">--%>
                <%--<c:forEach items="${list}" var="list">--%>
                    <%--<option--%>
                            <%--<c:if test="${list eq ucenterSpecialty.year}">selected="selected"</c:if>>${list}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</div>--%>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterSpecialty.status==1}">checked</c:if>>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterSpecialty.status==0}">checked</c:if>>
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
        var trdrId=$("#trdrId").val();
        var facultyName=$("#facultyCode").find("option:selected").text();
        var trdrName=$("#trdrId").find("option:selected").text();
        $("#facultyName").val(facultyName);
        if(trdrId){
            $("#trdrName").val(trdrName);
        }else{
            $("#trdrName").val("");
        }
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/specialty/update/${ucenterSpecialty.specialtyId}',
            data: $('#updateForm').serialize(),
            beforeSend: function() {
                if ($('#specialtyNo').val() == '') {
                    $('#specialtyNo').focus();
                    return false;
                }
                if ($('#specialtyCode').val() == '') {
                    $('#specialtyCode').focus();
                    return false;
                }
                if ($('#specialtyName').val() == '') {
                    $('#specialtyName').focus();
                    return false;
                }
                if ($('#facultyCode').val() == '') {
                    $('#facultyCode').focus();
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