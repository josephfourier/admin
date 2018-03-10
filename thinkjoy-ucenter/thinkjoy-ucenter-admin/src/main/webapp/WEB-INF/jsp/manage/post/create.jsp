<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<style>
    #createForm .col-sm-12.radioes .radio{display: inline-block;}
    #createForm .col-sm-12.radioes>label{
        font-weight: normal;
        min-width: 70px;
    }
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
            <label for="postName">职务名称</label>
            <input id="postName" type="text" class="form-control"  name="postName" maxlength="64" >
            <input id="schoolCode" type="hidden" class="form-control"  name="schoolCode" value="${schoolCode}" >
        </div>
        <div class="form-group sl">
            <label for="teacherLevel">级别</label>
            <select  id="teacherLevel" name="teacherLevel">
                <option value="">请选择...</option>
                <c:set var="TEACHERLEVEl" value="<%=BaseConstants.Dict.TEACHERLEVEl %>"/>
                <c:forEach items="${dict:getValueByCode(TEACHERLEVEl)}" var="at">
                    <option value="${at.valueKey}">${at.valueName}</option>
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



    function createSubmit() {
        var schoolCode=${schoolCode};
        var userType=${userType};
        //console.log("学校编码："+schoolCode+";用户类型："+userType);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/post/create',
            data: $('#createForm').serialize(),
            beforeSend: function() {
                if ($('#postName').val() == '') {
                    $('#postName').focus();
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