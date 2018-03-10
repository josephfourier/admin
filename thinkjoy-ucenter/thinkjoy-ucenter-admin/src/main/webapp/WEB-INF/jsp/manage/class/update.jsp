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
            <label for="className">班级名称</label>
            <input id="className" type="text" class="form-control" name="className" maxlength="64" value="${ucenterClass.className}">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${ucenterClass.schoolCode}">
        </div>
        <div class="form-group sl">
            <label for="facultyCode">所属院系</label>
            <select id="facultyCode" name="facultyCode">
                <option value="">请选院系</option>
                <c:forEach items="${facultyList}" var="facultyList">
                    <option value="${facultyList.facultyCode}"
                            <c:if test="${facultyList.facultyCode eq ucenterClass.facultyCode}">selected="selected"</c:if>>${facultyList.facultyName}</option>
                </c:forEach>
            </select>
            <input id="facultyName" type="hidden" class="form-control" name="facultyName" value="${ucenterClass.facultyName}">
        </div>
        <div class="form-group sl">
            <label for="specialtyCode">所属专业</label>
            <select id="specialtyCode" name="specialtyCode">
                <option value="0">请选专业</option>
            </select>
            <input id="specialtyName" type="hidden" class="form-control" name="specialtyName" value="${ucenterClass.specialtyName}">
        </div>
        <div class="form-group sl">
            <label for="bzrId">班主任&nbsp;&nbsp;&nbsp;</label>
            <select id="bzrId" name="bzrId">
                <option value="">请选班主任</option>
                <c:forEach items="${teacherList}" var="teacherList">
                    <option value="${teacherList.teacherId}"
                            <c:if test="${teacherList.teacherId eq ucenterClass.bzrId}">selected="selected"</c:if>>${teacherList.teacherName}</option>
                </c:forEach>
            </select>
            <input id="bzrName" type="hidden" class="form-control" name="bzrName" value="${ucenterClass.bzrName}">
        </div>
        <div class="form-group sl">
            <label for="fdyId">辅导员&nbsp;&nbsp;&nbsp;</label>
            <select id="fdyId" name="fdyId">
                <option value="">请选辅导员</option>
                <c:forEach items="${teacherList}" var="teacherList">
                    <option value="${teacherList.teacherId}"
                            <c:if test="${teacherList.teacherId eq ucenterClass.fdyId}">selected="selected"</c:if>>${teacherList.teacherName}</option>
                </c:forEach>
            </select>
            <input id="fdyName" type="hidden" class="form-control" name="fdyName" value="${ucenterClass.fdyName}">
        </div>
        <div class="form-group sl">
            <label for="year">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级</label>
            <select id="year" name="year">
                <c:forEach items="${list}" var="list">
                    <option
                            <c:if test="${list eq ucenterClass.year}">selected="selected"</c:if>>${list}</option>
                </c:forEach>
            </select>
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterClass.status==1}">checked</c:if>>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterClass.status==0}">checked</c:if>>
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
    $(function() {
        //选择学院
        $('#facultyCode').change(function() {
            facultyCode = $(this).val();
           if(facultyCode!='') {
               initspeCode(null, facultyCode);
           }else{
               $('#specialtyCode').empty();
               $('#specialtyCode').select2({
                   data: [{id: "", text: '请选专业'}]
               });
           }
        });
    });
    function initSelect2() {
        var specialtyCode='${ucenterClass.specialtyCode}';
        var facultyCode='${ucenterClass.facultyCode}';
        initspeCode(specialtyCode,facultyCode);
    }

    var schoolCode=${ucenterClass.schoolCode};
    //查询专业列表
    function initspeCode(val,facultyCode) {
//        var schoolCode=$("#schoolCode").val();
        //console.log("院系00编码："+val);
        var datas = [{id: 0, text: '请选专业'}];
        if (facultyCode != 0) {
            <%--$.getJSON('${basePath}/manage/specialty/list', {schoolCode: schoolCode, facultyCode: facultyCode, limit: 10000}, function(json) {--%>
            $.getJSON('${basePath}/manage/class/specialtylist', {schoolCode: schoolCode, facultyCode: facultyCode, limit: 10000}, function(json) {
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].specialtyCode;
                    data.text = json.rows[i].specialtyName;
                    datas.push(data);
                }
                $('#specialtyCode').empty();
                $('#specialtyCode').select2({
                    width:'140px',
                    data : datas
                });
                if (val!=null) {
                    console.log("专业编码："+val);
                    $('#specialtyCode').select2({width:'140px',}).val(val).trigger('change');
                }
            });
        }
    }
    function updateSubmit() {
        var userType=${userType};
//        var schoolCode=$("#schoolCode").val();
        var facultyName=$("#facultyCode").find("option:selected").text();
        var specialtyName=$("#specialtyCode").find("option:selected").text();
        var bzrName=$("#bzrId").find("option:selected").text();
        var fdyName=$("#fdyId").find("option:selected").text();
        var bzrId=$("#bzrId").val();
        var fdyId=$("#fdyId").val();
        $("#facultyName").val(facultyName);
        $("#specialtyName").val(specialtyName);
        if(bzrId){
            $("#bzrName").val(bzrName);
        }else{
            $("#bzrName").val("");
        }
        if(fdyId){
            $("#fdyName").val(fdyName);
        }else{
            $("#fdyName").val("");
        }
        if($('#facultyCode').val() == 0){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'院系不能为空!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return false;
        }
        if($('#specialtyCode').val() == 0){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'专业不能为空!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return false;
        }
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/class/update/${ucenterClass.classId}',
            data: $('#updateForm').serialize(),
            beforeSend: function() {
                if ($('#className').val() == '') {
                    $('#className').focus();
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