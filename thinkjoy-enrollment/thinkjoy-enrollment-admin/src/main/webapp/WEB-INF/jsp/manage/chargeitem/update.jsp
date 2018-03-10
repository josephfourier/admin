<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>

<jsp:include page="/resources/inc/top.jsp" flush="true"/>
<style>
    .lap-radio{}
    #updateForm label{min-width: 72px;
        font-weight: normal;
        text-align: right;}
</style>
<div class="lap-page">
    <div class="lap-section">
        <div class="lap-form">
                <form id="updateForm" method="post" class="form-inline">
                    <div class="form-group">
                        <div class="lap-radio">
                            <label>启用状态</label>
                            <c:set var="STAUTS" value="<%=BaseConstants.Dict.STATUS %>"/>
                            <c:forEach items="${dict:getValueByCode(STAUTS)}" var="at">
                                <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}"
                                       <c:if test="${at.valueKey eq enrollChargeitem.status}">checked</c:if>/>

                                <label for="status_${at.valueKey}">
                                    <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                    <c:if test="${at.valueName eq '正常'}">是</c:if>
                                </label>

                            </c:forEach>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="itemName">缴费类别名称</label>
                        <input id="itemName" type="text" class="form-control" name="itemName" maxlength="100"
                               value="${enrollChargeitem.itemName}">
                    </div>

                    <div class="form-group">
                        <div class="lap-textarea">
                            <label for="description">描述</label>
                            <textarea rows="3" cols="40" id="description" name="description" class="form-control">${enrollChargeitem.description}</textarea>
                        </div>
                    </div>
                    <c:if test="${param.readonly ne 1}">
                    <div class="btns-group">
                        <a href="javascript:;" class="btn submit" data-ajax="show" onclick="updateSubmit();">提交</a>
                        <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
                    </div>
                </c:if>
                </form>
        </div>
    </div>
</div>
<script>

    function updateSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/chargeitem/update/${enrollChargeitem.itemId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#itemName').val() == '') {
                    $('#itemName').focus();
                    return false;
                }
            },
            success: function (result) {
                if (result.code == 1) {
                    parent.layer.closeAll();
                    window.parent.$parentTable.bootstrapTable('refresh');
                } else {
                    layer.msg(result.message);
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
