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
<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="form-group">
            <label for="className">班级名称</label>
            <input id="className" type="text" class="form-control" name="className" maxlength="64"  onblur="checkName();">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${schoolCode}">
        </div>
        <div class="form-group sl">
            <label for="facultyCode">所属院系</label>
            <select id="facultyCode" name="facultyCode">
                <option value="">请选院系</option>
                <c:forEach var="facultyList" items="${facultyList}">
                    <option value="${facultyList.facultyCode}">${facultyList.facultyName}</option>
                </c:forEach>
                <input id="facultyName" type="hidden" class="form-control" name="facultyName">
            </select>
        </div>
        <div class="form-group sl">
            <label for="specialtyCode">所属专业</label>
            <select id="specialtyCode" name="specialtyCode">
                <option value="">请选专业</option>
            </select>
            <input id="specialtyName" type="hidden" class="form-control" name="specialtyName">
        </div>
        <div class="form-group sl">
            <label for="bzrId">班主任&nbsp;&nbsp;&nbsp;</label>
            <select id="bzrId" name="bzrId">
                <option value="">请选班主任</option>
                <c:forEach var="teacherList" items="${teacherList}">
                    <option value="${teacherList.teacherId}">${teacherList.teacherName}</option>
                </c:forEach>
            </select>
            <input id="bzrName" type="hidden" class="form-control" name="bzrName">
        </div>
        <div class="form-group sl">
            <label for="fdyId">辅导员&nbsp;&nbsp;&nbsp;</label>
            <select id="fdyId" name="fdyId">
                <option value="">请选辅导员</option>
                <c:forEach var="teacherList" items="${teacherList}">
                    <option value="${teacherList.teacherId}">${teacherList.teacherName}</option>
                </c:forEach>
            </select>
            <input id="fdyName" type="hidden" class="form-control" name="fdyName">
        </div>
        <div class="form-group sl">
            <label for="year">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级</label>
            <select id="year" name="year">
                <c:forEach var="list" items="${list}">
                    <option>${list}</option>
                </c:forEach>
            </select>
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
    $(function() {
        //选择学院
        $('#facultyCode').change(function() {
            facultyCode = $(this).val();
            if(facultyCode!=''){
                initspeCode();
            }else{
                $('#specialtyCode').empty();
                $('#specialtyCode').select2({
                    data: [{id: "", text: '请选择班级'}]
                });
            }
        });
    });

    //查询专业列表
    function initspeCode() {
        var schoolCode=${schoolCode};
        //console.log("院系编码："+facultyCode);
        var datas = [{id:0, text: '请选专业'}];
        if (facultyCode != 0) {
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
            });
        }
    }


    function checkName(){
        var className=$('#className').val();
        var schoolCode=${schoolCode};
        if(facultyName!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/class/checkName',
                data: {className:className,schoolCode:schoolCode},
                success: function(result){
                    console.log("返回数据："+result);
                    if(result==true){
                        $("#className").val("");
                        cpflag=true;
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content:'名称不能重复!' ,
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }else{
                        cpflag=false;
                    }
                }
            });
        }
    }

    function createSubmit() {
        var schoolCode=${schoolCode};
        var userType=${userType};
        var facultyName=$("#facultyCode").find("option:selected").text();
        var specialtyName=$("#specialtyCode").find("option:selected").text();
        var bzrId=$("#bzrId").val();
        var fdyId=$("#fdyId").val();
        var bzrName=$("#bzrId").find("option:selected").text();
        var fdyName=$("#fdyId").find("option:selected").text();
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
        //console.log("班主任："+bzrName+"辅导员："+fdyName);
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
            url: '${basePath}/manage/class/create',
            data: $('#createForm').serialize(),
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