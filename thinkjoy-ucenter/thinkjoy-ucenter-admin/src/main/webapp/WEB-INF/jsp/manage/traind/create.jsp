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
            <label for="schoolCode">学校编码</label>
            <input id="schoolCode" type="text" class="form-control" readonly="readonly" name="schoolCode" value="${schoolCode}">
        </div>
        <div class="form-group">
            <label for="trdrName">专业培养方向名称</label>
            <input id="trdrName" type="text" class="form-control" name="trdrName" maxlength="128" onblur="checkName();" >
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

    function checkName(){
        var trdrName=$('#trdrName').val();
        var schoolCode=${schoolCode};
        if(facultyName!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/traind/checkName',
                data: {trdrName:trdrName,schoolCode:schoolCode},
                success: function(result){
                    console.log("返回数据："+result);
                    if(result==true){
                        $("#trdrName").val("")
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
        //console.log("学校编码："+schoolCode+";用户类型："+userType);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/traind/create',
            data: $('#createForm').serialize(),
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