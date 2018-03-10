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
            <label for="specialtyNo">教育局专业代码</label>
            <input id="specialtyNo" type="text" class="form-control" name="specialtyNo" maxlength="64"  onblur="checkcode();">
        </div>
        <div class="form-group">
            <label for="specialtyCode">校内专业代码</label>
            <input id="specialtyCode" type="text" class="form-control" name="specialtyCode" maxlength="64"  onblur="checkxncode();">
        </div>
        <div class="form-group">
            <label for="specialtyName">专业名称</label>
            <input id="specialtyName" type="text" class="form-control" name="specialtyName" maxlength="64"  onblur="checkName();">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${schoolCode}">
        </div>
        <div class="form-group sl">
            <label for="facultyCode">所属院系</label>
            <select id="facultyCode" name="facultyCode">
                <option value="">请选择院系</option>
                <c:forEach var="facultyList" items="${facultyList}">
                    <option value="${facultyList.facultyCode}">${facultyList.facultyName}</option>
                </c:forEach>
            </select>
            <input id="facultyName" type="hidden" class="form-control" name="facultyName">
        </div>
        <div class="form-group sl">
            <label for="trdrId">培养方向</label>
            <select id="trdrId" name="trdrId">
                <option value="">请选培养方向</option>
                <c:forEach var="tradList" items="${tradList}">
                    <option value="${tradList.trdrId}">${tradList.trdrName}</option>
                </c:forEach>
            </select>
            <input id="trdrName" type="hidden" class="form-control" name="trdrName">
        </div>
        <div class="form-group sl">
            <label for="specialtyType">专业类别</label>
            <select id="specialtyType" name="specialtyType">
                <option value="">请选专业类别</option>
                <c:forEach var="facTypeDicts" items="${facTypeDicts}">
                    <option value="${facTypeDicts.valueKey}">${facTypeDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="specialtyNature">专业性质</label>
            <select id="specialtyNature" name="specialtyNature">
                <option value="">请选专业性质</option>
                <c:forEach var="facuityDicts" items="${facuityDicts}">
                    <option value="${facuityDicts.valueKey}">${facuityDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group sl">
            <label for="enrollmentTarget">招生对象</label>
            <select id="enrollmentTarget" name="enrollmentTarget" multiple="multiple" style="width:200px;">
                <c:forEach var="targetDicts" items="${targetDicts}">
                    <option value="${targetDicts.valueKey}" >${targetDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="schoolSystem">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制</label>
            <select id="schoolSystem" name="schoolSystem">
                <option value="">请选学制</option>
                <c:forEach var="xzDicts" items="${xzDicts}">
                    <option value="${xzDicts.valueKey}">${xzDicts.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <%--<div class="form-group sl">--%>
            <%--<label for="year">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年</label>--%>
            <%--<select id="year" name="year">--%>
                <%--<c:forEach var="list" items="${list}">--%>
                    <%--<option>${list}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</div>--%>
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
    var cpflag=false;
    function checkxncode(){
        //console.log("检查专业编码是否重复！");
        var specialtyCode=$('#specialtyCode').val();
        var schoolCode=${schoolCode};

        var patrn=/^[-0-9a-zA-Z]*$/;
        if (!patrn.exec(specialtyCode)){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'编码格式错误(数字,字母,中划线)!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            $("#specialtyCode").val("");
            return false;
        }

        if(specialtyCode!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/specialty/checkxncode',
                data: {specialtyCode:specialtyCode,schoolCode:schoolCode},
                success: function(result){
                    //console.log("返回数据："+result);
                    if(result==true){
                        $("#specialtyCode").val("")
                        cpflag=true;
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content:'校内编码不能重复!' ,
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

    var xnflag=false;
    function checkcode(){
        var specialtyNo=$('#specialtyNo').val();
        console.log("检查教育局专业编码是否重复！"+specialtyNo);
        var schoolCode=${schoolCode};

        var patrn=/^[-0-9a-zA-Z]*$/;
        if (!patrn.exec(specialtyNo)){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'编码格式错误(数字,字母,中划线)!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            $("#specialtyNo").val("");
            return false;
        }

        if(specialtyNo!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/specialty/checkcode',
                data: {specialtyNo:specialtyNo,schoolCode:schoolCode},
                success: function(result){
                    //console.log("返回数据2："+result);
                    if(result==true){
                        $("#specialtyNo").val("")
                        xnflag=true;
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content:'教育局编码不能重复!' ,
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }else{
                        xnflag=false;
                    }
                }
            });
        }
    }

    function checkName(){
        var specialtyName=$('#specialtyName').val();
        var schoolCode=${schoolCode};
        if(specialtyName!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/specialty/checkname',
                data: {specialtyName:specialtyName,schoolCode:schoolCode},
                success: function(result){
                    console.log("返回数据："+result);
                    if(result==true){
                        $("#specialtyName").val("")
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
        var trdrId=$("#trdrId").val();
        var facultyName=$("#facultyCode").find("option:selected").text();
        var trdrName=$("#trdrId").find("option:selected").text();
        $("#facultyName").val(facultyName);
        if(trdrId){
            $("#trdrName").val(trdrName);
        }else{
            $("#trdrName").val("");
        }
        //console.log("学校编码："+schoolCode+";用户类型："+userType);
        if(xnflag){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'教育局编码不能重复!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return false;
        }
        if(cpflag){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'校内编码不能重复!' ,
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
            url: '${basePath}/manage/specialty/create',
            data: $('#createForm').serialize(),
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