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
<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="form-group">
            <label for="facultyCode">院系编码</label>
            <input id="facultyCode" type="text" class="form-control" name="facultyCode" maxlength="32"  onblur="checkcode();">
        </div>
        <div class="form-group">
            <label for="facultyName">院系名称</label>
            <input id="facultyName" type="text" class="form-control" name="facultyName" maxlength="64" onblur="checkName();">
        </div>
        <div class="form-group">
            <label for="birthday">成立时间</label>
            <input id="birthday" type="text"  name="birthday" class="form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
        </div>
        <div class="form-group">
            <label for="fzrName">院系负责人</label>
            <input id="fzrName" type="text" class="form-control" name="fzrName" maxlength="32">
            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${schoolCode}">
        </div>
        <div class="form-group">
            <label for="tel">联系方式</label>
            <input id="tel" type="text" class="form-control" name="tel" maxlength="32">
        </div>
        <%--<div class="form-group sl">--%>
            <%--<label for="year">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年</label>--%>
            <%--<select id="year" name="year">--%>
                <%--<c:forEach var="list" items="${list}">--%>
                    <%--<option>${list}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</div>--%>
        <div class="form-group">
            <label for="description">院系简介</label>
            <input id="description" type="text" class="form-control" name="description" maxlength="256">
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
    var cpflag=false;
    function checkcode(){
        //console.log("检查编码是否重复！");
        var facultyCode=$('#facultyCode').val();
        var schoolCode=${schoolCode};

        var patrn=/^[-0-9a-zA-Z]*$/;
        if (!patrn.exec(facultyCode)){
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
            $("#facultyCode").val("");
            return false;
        }

            if(facultyCode!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/faculty/checkCodeorName',
                data: {facultyCode:facultyCode,schoolCode:schoolCode},
                success: function(result){
                    console.log("返回数据："+result);
                    if(result==true){
                        $("#facultyCode").val("")
                        cpflag=true;
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content:'编码不能重复!' ,
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

    function checkName(){
        var facultyName=$('#facultyName').val();
        var schoolCode=${schoolCode};
        if(facultyName!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/faculty/checkCodeorName',
                data: {facultyName:facultyName,schoolCode:schoolCode},
                success: function(result){
                    console.log("返回数据："+result);
                    if(result==true){
                        $("#facultyName").val("")
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
        if(cpflag==true){
            $.confirm({
                theme: 'dark',
                animation: 'rotateX',
                closeAnimation: 'rotateX',
                title: false,
                content:'编码不能重复!' ,
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button waves-light'
                    }
                }
            });
            return false;
        }


        //console.log("学校编码："+schoolCode+";用户类型："+userType);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/faculty/create',
            data: $('#createForm').serialize(),
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
                }else{
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