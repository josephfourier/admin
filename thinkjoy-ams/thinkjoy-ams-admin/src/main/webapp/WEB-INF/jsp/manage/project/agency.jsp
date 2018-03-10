<%--
  Created by IntelliJ IDEA.
  User: wangcheng
  Date: 17/7/27
  Time: 下午4:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="agencyDialog" class="crudDialog">
    <form id="agencyForm" method="post">
        <div class="row">
            <div class="col-sm-6" style="width: 300px;height: 200px;overflow: auto">
                <label for="ztree">组织树</label>
                <div class="form-group">
                    <ul id="ztree" class="ztree"></ul>
                </div>
            </div>

            <div class="col-sm-6" style="width: 200px;height: 200px;overflow: auto">
                <label for="appId">应用名称</label>
                <div class="form-group">

                    <c:forEach items="${allApps}" var="app">

                        <input type="checkbox" name="appId" value="${app.appId}" id="appId"
                               <c:if test="${not empty relatedApps[app.appId]}">checked="checked"</c:if>>
                        <span>${app.appName}</span>
                        <br>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="permissionSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="agencyDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    var changeDatas = [];
    var setting = {
        check: {
            enable: true,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "", "N": "s"}
        },
        async: {
            enable: true,
            url: '${basePath}/manage/project/agencytree/' + projectId
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function () {
                var zTree = $.fn.zTree.getZTreeObj("ztree")
                var changeNodes = zTree.getChangeCheckedNodes();
                changeDatas = [];
                for (var i = 0; i < changeNodes.length; i++) {
                    var changeData = {};
                    changeData.code = changeNodes[i].code;
                    changeData.checked = changeNodes[i].checked;
                    changeDatas.push(changeData);
                }
            }
        }
    };
    function initTree() {
        $.fn.zTree.init($('#ztree'), setting);
    }

    function permissionSubmit() {
        var selectedItems = new Array();
        $("input[name='appId']:checked").each(function () {
            selectedItems.push($(this).val());
        });
        console.log(selectedItems);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/project/agencyschool/' + projectId,
            data: {datas: JSON.stringify(changeDatas), projectId: projectId, appId: selectedItems + ''},
            beforeSend: function () {

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
                    agencyDialog.close();
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