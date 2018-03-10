<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<jsp:include page="/resources/inc/top.jsp" flush="true"/>

<style>
    #createForm label{min-width: 72px;}
</style>
<div class="lap-page">
    <div class="lap-section">
        <div class="lap-form">
            <form id="createForm" method="post" class="form-inline">

                <div class="form-group">
                    <label for="itemName">模板名称</label>
                    <input id="itemName" type="text" class="form-control" name="itemName" maxlength="100">
                </div>

                <div class="btns-group">
                    <a href="javascript:;" class="btn submit" data-ajax="show" onclick="createSubmit();">提交</a>
                    <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/chargeitem/create',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#itemName').val() == '') {
                    $('#itemName').focus();
                    return false;
                }
            },
            success: function (result) {
                if (result.code == 1) {
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