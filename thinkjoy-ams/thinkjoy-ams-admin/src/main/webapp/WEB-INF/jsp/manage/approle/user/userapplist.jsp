<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<div id="userappDialog" class="crudDialog">
    <form id="userappForm" method="post">
        <div class="col-sm-12">
            <div class="form-group">
                <input type="hidden" value="${uid}">
                <select class="form-control" id="relationCode" name="relationCode" style="width: 100%" disabled="true">
                        <option value="${upmsUser.relationCode}">${upmsUser.relationName}</option>
                </select>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <select class="form-control" id="usertypeId" name="usertypeId" style="width: 100%" disabled="true">
                    <option value="1" <c:if test="${usertype eq 1}">selected</c:if> >学生</option>
                    <option value="2" <c:if test="${usertype eq 2}"> selected</c:if> >家长</option>
                    <option value="3" <c:if test="${usertype eq 3}"> selected</c:if> >老师</option>
                    <option value="4" <c:if test="${usertype eq 4}"> selected</c:if> >组织机构</option>

                </select>
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <input type="hidden" id="datas" name="datas"/>
                <ul id="ztreeOne" class="ztree"></ul>
            </div>
        </div>

        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="userappSubmit();">保存设置</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="userappDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>


    var setting1 = {
        check: {
            enable: true,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "p", "N": "s"},
        },
        async: {
            enable: true,
            dataFilter : function(treeId, parentNode, responseData){
                if (!(responseData instanceof Array)){
                    return;
                }
                return responseData;
            },
            url: '${basePath}/manage/approle/getAppAndRoleTree',
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onAsyncSuccess: zTreeOnAsyncSuccess,
            onCheck: onCheck,
        }
    };

    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
        var json = JSON.parse(msg);
        if (!(json instanceof Array)){
            if (json.code == 0){
                $.confirm({
                    title: false,
                    content: '暂无个性化应用与角色可分配！',
                    autoClose: 'cancel|3000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: '取消',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
                return;
            }
        }
    }

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztreeOne");
        var nodes = zTree.getCheckedNodes();
        changeDatas = [];
        for (var i = 0, l = nodes.length; i < l; i++) {
            var changeData = {};
            changeData.code = nodes[i].code;
            changeData.type = nodes[i].type;
            changeData.name = nodes[i].name;
            changeDatas.push(changeData);
        }
        $('#datas').attr('value', JSON.stringify(changeDatas))
    }

    $(function () {
        var usertype=$("#usertypeId").val();
        var userIds="${userIds}";
        if(usertype==""){
            return;
        }else{
            setting1.async.otherParam = ["relationCode", ${upmsUser.relationCode}, "usertypeId", usertype,"userIds",userIds ];
            $.fn.zTree.init($('#ztreeOne'), setting1);
        }

    });

    function getuserpersonal() {
        $.getJSON('${basePath}/manage/approle/ucenteruser/getuserpersonal', {
            relationCode: _relationCode,
            userId: userId,
            usertypeId: usertypeId
        }, function (json) {
            if (json.data instanceof Array) {
                for (var i = 0; i < json.data.length; i++) {
                    if (i == 0) {
                        relatedAppIds = json.data[i];
                    } else {
                        relatedApproleIds = json.data[i];
                    }
                }
            }
        });
    }




    function userappSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/approle/ucenteruser/userapp/${userIds}',
            data: {"relationCode":$("#relationCode").val(),"usertypeId":$("#usertypeId").val(),"datas":$("#datas").val()},
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
                    userappDialog.close();
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