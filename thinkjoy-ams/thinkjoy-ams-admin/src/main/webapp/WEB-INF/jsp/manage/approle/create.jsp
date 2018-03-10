<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
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
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="approleName">应用角色名称</label>
                        <input id="approleName" type="text" class="form-control" name="approleName" maxlength="64">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <input type="text" id="relationName" name="relationName" class="form-control"
                               readonly="readonly" placeholder="学校/机构选择"
                               <c:if test="${upmsUser.managerType eq 3}">value='${upmsUser.relationName}'</c:if>>
                        <input type="hidden" id="relationCode" name="relationCode"
                               <c:if test="${upmsUser.managerType eq 3}">value='${upmsUser.relationCode}'</c:if>>
                        <ul id="ztree" class="ztree" style="width:auto;height: auto"></ul>
                    </div>
                </div>
            </div>

            <div class="form-group sl">
                <label for="perPersonalization">权限个性化</label>
                <select class="form-control" id="perPersonalization" name="perPersonalization">
                    <c:set var="perPersonalization" value="<%=BaseConstants.Dict.PERPERSONALIZATION %>"/>
                    <option value="">请选择...</option>
                    <c:forEach items="${dict:getValueByCode(perPersonalization)}" var="at">
                        <option value="${at.valueKey}">${at.valueName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group sl">
                    <label for="usertypeId">用户类型&nbsp;&nbsp;&nbsp;</label>
                    <select id="usertypeId" name="usertypeId">
                        <c:set var="usertypeId" value="<%=BaseConstants.Dict.APPLICABLEIDENTITY %>"/>
                        <option value="">请选择...</option>
                        <c:forEach items="${dict:getValueByCode(usertypeId)}" var="at">
                            <option value="${at.valueKey}">${at.valueName}</option>
                        </c:forEach>
                    </select>
                </div>

            <div class="form-group sl">
                <label for="appName">关联应用&nbsp;&nbsp;&nbsp;</label>
                <input type="hidden" name="appName" id="appName"/>
                <select id="appId" name="appId">
                    <option value="">请选择...</option>
                </select>
            </div>
            </div>


            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="description">描述</label>
                        <input id="description" type="text" class="form-control" name="description"  maxlength="256">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
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
            </div>
        </div>

        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>

    function init() {
        if (currentNode != null && currentNode != '') {
            var rno = $('#relationName');
            var rco = $('#relationCode');
            rno.attr("value", currentNode.name);
            rco.attr("value", currentNode.code);
        }
    }

    var usertypeId = '';

    jQuery.fn.removeSelected = function () {
        $('#usertypeId').select2({
            width:'140px'
        }).val("").trigger('change');
    };

    $('#perPersonalization').change(function () {
        $("#usertypeId").removeSelected();
    });
    $('#usertypeId').change(function () {
        usertypeId = $(this).val();
        if (usertypeId == '') {
            return;
        }
        initApp();
    });

    $('#appId').change(function () {
        $('#appName').val($(this).find('option').eq(this.selectedIndex).text());
    });

    function initApp() {
        $.getJSON('${basePath}/manage/approle/ucenteruser/userapp/list', {
            relationCode: relationCode,
            usertypeId: usertypeId,
            perPer: $('#perPersonalization').val(),
        }, function (json) {
            if (json.code != 1) {
                $.confirm({
                    theme: 'dark',
                    animation: 'rotateX',
                    closeAnimation: 'rotateX',
                    title: false,
                    content: json.data.errorMsg,
                    buttons: {
                        confirm: {
                            text: '确认',
                            btnClass: 'waves-effect waves-button waves-light'
                        }
                    }
                });
                createDialog.close();
                return;
            }

            var datas = [{id: "", text: '请选择应用'}];
            for (var i = 0; i < json.data.length; i++) {
                var data = {};
                data.id = json.data[i].appId;
                data.text = json.data[i].appName;
                datas.push(data);
            }
            $('#appId').empty();
            $('#appId').select2({
                //placeholder: '请选择应用',
                data: datas,
                width:'140px'
            });
        });
    }


    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/approle/create',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#approleName').val() == '') {
                    $('#approleName').focus();
                    return false;
                }
                if ($('#perPersonalization').val() == '') {
                    $('#perPersonalization').focus();
                    return false;
                }

                if ($('#relationName').val() == '' || $('#relationCode').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择学校/机构！',
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
                if ($('#usertypeId').val() == '' || $('#usertypeId').val() == null) {
                    $('#usertypeId').focus();
                    return false;
                }

                if ($('#perPersonalization').val() == <%=BaseConstants.PerPersonal.YES%>) {
                    if ($('#appId').val() == ''
                            || $('#appId').val() == null
                            || $('#appName').val() == ''
                            || $('#appName').val() == null) {
                        $.confirm({
                            title: false,
                            content: '个性化角色必须选择应用！',
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
                    createDialog.close();
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