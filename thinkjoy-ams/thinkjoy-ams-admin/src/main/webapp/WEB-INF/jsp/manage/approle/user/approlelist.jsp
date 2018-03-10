<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="roleDialog" class="crudDialog">
    <form id="roleForm" method="post">
        <div class="form-group">
            <input type="hidden" value="${uid}">
            <select id="relationCode" name="relationCode">
                <option value="">请选择组织/学校</option>
                <c:forEach var="identitiy" items="${identities}">
                    <option value="${identitiy.relationCode}">${identitiy.relationName}</option>
                </c:forEach>
            </select>
            <select id="usertypeId" name="usertypeId">
                <option value="">请选择用户类型</option>
                <%--<c:forEach var="identitiy" items="${identities}">--%>
                <%--<option value="${identitiy.usertypeId}" >${identitiy.usertypeId}</option>--%>
                <%--</c:forEach>--%>
            </select>
        </div>
        <div class="form-group">
            <select id="roleId" name="roleId" multiple="multiple" style="width: 100%;">
                <%--<c:forEach var="amsrole" items="${amsroles}">--%>
                <%--<option value="${amsrole.approleId}">${amsrole.approleName}</option>--%>
                <%--</c:forEach>--%>
            </select>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="recoverroleSubmit();">恢复设置</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="roleSubmit();">保存设置</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="approleDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    // 选择身份
    var _relationCode = '';
    var usertypeId = '';
    var userId = '';
    $(function () {

        $('#relationCode').on("change", function () {
            userId = ${uid};
            _relationCode = $(this).val();
            initUserType();
        });

        $('#usertypeId').on("change", function () {
            usertypeId = $(this).val();
            initAppRole();
        });


    });

    function initUserType() {
        $.getJSON('${basePath}/manage/approle/ucenteruser/usertypelist', {
            relationCode: _relationCode,
            userId: userId
        }, function (json) {
            var datas = [{id: "", text: '请选择用户类型'}];

            //console.log(json)
            if (json.data instanceof Array) {
                for (var i = 0; i < json.data.length; i++) {
                    var data = {};
                    data.id = json.data[i].usertypeId;
                    if (data.id == '4') {
                        data.text = "教育机构";
                    }
                    if (data.id == '2') {
                        data.text = "家长";
                    }
                    if (data.id == '3') {
                        data.text = "老师";
                    }
                    if (data.id == '1') {
                        data.text = "学生";
                    }
                    datas.push(data);
                }

            }
            $('#usertypeId').empty();
            $('#usertypeId').select2({
                data: datas
            });
        });
    }

    function initAppRole() {
        $.getJSON('${basePath}/manage/approle/ucenteruser/approle/list', {
            relationCode: _relationCode,
            usertypeId: usertypeId,
            userId: userId
        }, function (json) {
            var datas = [];

            if (json.data instanceof Array) {
                if (json.data.length == 0){
                    $.confirm({
                        title: false,
                        content: '请先新增个性化角色！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }

                for (var i = 0; i < json.data.length; i++) {
                    var data = {};
                    data.id = json.data[i].approleId;
                    data.text = json.data[i].approleName;
                    datas.push(data);
                }

            }
            $('#roleId').empty();
            $('#roleId').select2({
                placeholder: '请选择身份角色',
                data: datas
            });
        });
    }

    function roleSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/approle/ucenteruser/approle/' + ucenterUserId,
            data: $('#roleForm').serialize(),
            beforeSend: function () {
                if ($('#relationCode').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择组织/学校！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if ($('#usertypeId').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择用户类型！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if ($('#roleId').val() == '' || $('#roleId').val() == null) {
                    $.confirm({
                        title: false,
                        content: '请选择角色！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }

            },
            success: function (result) {
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
                    approleDialog.close();
                    $table.bootstrapTable('refresh');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
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

    function recoverroleSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/approle/ucenteruser/recoveruserrole/' + ucenterUserId,
            data: $('#roleForm').serialize(),
            beforeSend: function () {
                if ($('#relationCode').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择组织/学校！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if ($('#usertypeId').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择用户类型！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }

            },
            success: function (result) {
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
                    approleDialog.close();
                    $table.bootstrapTable('refresh');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
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