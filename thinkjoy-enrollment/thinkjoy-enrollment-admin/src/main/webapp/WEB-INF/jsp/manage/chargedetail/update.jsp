<%@ include file="/tags/taglibs.jsp" %>
<jsp:include page="/resources/inc/topJ.jsp" flush="true"/>
<jsp:include page="/resources/inc/topS.jsp" flush="true"/>

<style>
    #createForm label{min-width: 72px; font-weight: normal;}
    .chk-group{
        margin-bottom: 10px;overflow: hidden;}
    .w{float: left;
        padding-left: 10px;}
    .chk-group>label,
    .chk-group>.lap-chk{display: inline-block;}
    .chk-group>label{
        font-weight: bold !important;
        float: left;}
    .chk-group>.wrap{
        float: left;
        overflow: hidden;
        width: 500px;}

    .chk-group>.wrap>.lap-chk{
        float: left;}
    #tmp{}
    #tmp>label{
        float: left;
        margin-right: 20px;}
    #description{
        width: 550px;}
    .lap-page .lap-form{
        padding-bottom: 0;}
</style>

<div class="lap-page">
    <div class="lap-section">
        <div class="lap-form">
            <form class="form-inline" id="createForm" method="post">
                <div class="form-group block">
                    <div class="lap-radio">
                        <label>启用状态</label>
                        <c:set var="STATUS" value="<%=BaseConstants.Dict.STATUS %>"/>
                        <c:choose>
                            <c:when test="${param.readonly eq 1}">
                                <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">

                                    <c:if test="${at.valueKey eq 1}">
                                        <input id="status_${at.valueKey}" type="radio" name="status" checked value="${at.valueKey}" />

                                        <label for="status_${at.valueKey}">
                                            <c:if test="${at.valueName eq '正常'}">是</c:if>
                                            <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                        </label>
                                    </c:if>

                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">

                                    <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}" <c:if test="${at.valueKey eq 1}">checked</c:if>/>
                                    <label for="status_${at.valueKey}">
                                        <c:if test="${at.valueName eq '正常'}">是</c:if>
                                        <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                    </label>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>


                <div class="form-group">
                    <label for="detailName">缴费项目名称</label>
                    <input id="detailName" type="text" class="form-control" name="detailName" maxlength="100"
                           value="${enrollChargedetail.detailName}">
                    <input id="detailId" type="hidden" name="detailId" value="${enrollChargedetail.detailId}">
                </div>

                <div class="form-group">
                    <label class="sl2">
                        <label>缴费类别</label>
                        <select class="" id="itemId" name="itemId">
                            <c:forEach var="item" items="${enrollChargeitems}">
                                <option value="${item.itemId}"
                                        <c:if test="${item.itemId eq enrollChargedetail.itemId}">selected</c:if> >${item.itemName}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <input id="itemName" type="hidden"  name="itemName"  value="${enrollChargedetail.itemName}">
                </div>

                <div class="form-group">
                    <label for="money">费用</label>
                    <input id="money" type="text" class="form-control" name="money" maxlength="100"
                           value="${enrollChargedetail.money}">
                </div>

                <div class="form-group">
                    <label class="sl2">
                        <label>是否必须</label>
                        <c:set var="ISMUST" value="<%=BaseConstants.Dict.ISMUST %>"/>
                        <select class="ex" id="isMust" name="isMust">
                            <c:forEach items="${dict:getValueByCode(ISMUST)}" var="at">
                                <option value="${at.valueKey}"
                                        <c:if test="${at.valueKey eq enrollChargedetail.isMust}">selected</c:if> >${at.valueName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <%--<div class="form-group" id="tmp">--%>
                    <%--<label id="tmp-label">关联专业</label>--%>
                   <%--<div class="w">--%>
                       <%--<c:forEach items="${ucenterFaculties}" var="faculty">--%>
                          <%--<div class="chk-group">--%>
                              <%--<label>${faculty.facultyName}</label>--%>
                              <%--<div class="wrap">--%>
                                  <%--<c:forEach items="${ucenterSpecialties}" var="specialty">--%>
                                      <%--<c:if test="${faculty.facultyCode eq specialty.facultyCode}">--%>
                                          <%--<div class="lap-chk"><input id="lap-chk-${specialty.specialtyCode}" value="${specialty.specialtyCode}" type="checkbox" name="specialtyCode" <c:forEach items="${enrollChargedetailSpecialties}" var="rltSpe">--%>
                                                                      <%--<c:if test="${rltSpe.specialtyCode eq specialty.specialtyCode}">checked</c:if>--%>
                                          <%--</c:forEach>/><label for="lap-chk-${specialty.specialtyCode}"><span> ${specialty.specialtyName}</span></label></div>--%>
                                      <%--</c:if>--%>
                                  <%--</c:forEach>--%>
                              <%--</div>--%>
                              <%--</div>--%>
                              <%--</c:forEach>--%>
                           <%--</div>--%>
                   <%--</div>--%>
                <div class="form-group block">
                    <label for="description">描述</label>
                    <textarea class="form-control" rows="3" name="description" id="description">${enrollChargedetail.description}</textarea>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${param.readonly ne 1}">
        <div class="btns-group">
            <a href="javascript:;" class="btn submit" data-ajax="show" onclick="createSubmit();">提交</a>
            <a href="javascript:;" class="btn cancel" onclick="closeAll();">取消</a>
        </div>
    </c:if>
</div>
<script>
    var readonly = '${param.readonly}';
    if (readonly == 1) {
        $('input').addClass('form-readonly').attr('readonly', true);
        $('select').prop('disabled', true);
    }
    $('select').select2({width: 240, minimumResultsForSearch: 5});

    function closeAll() {
        parent.layer.closeAll();
    }

    var cpflag=false;
    function createSubmit(){
        var data = $('#createForm').serializeArray();
        var itemName=$("#itemId").find("option:selected").text();
        $("#itemName").val(itemName);
        var itemId=$('#itemId').val();
        if(itemId!=''){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/chargedetail/checkdetail',
                data: data,
                success: function(data){
                    var data = eval('(' + data + ')');
                    if(data.msg==1){
                        cpflag=true;
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content:data.detailName+"已存在" ,
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        createSubmit1();
                    }else{
                        cpflag=false;
                        createSubmit1();
                    }
                }
            });
        }
    }

    function createSubmit1() {
        var data = $('#createForm').serializeArray();
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/chargedetail/update/${enrollChargedetail.detailId}',
            data: data,
            beforeSend: function () {
                if ($('#detailName').val() == '') {
                    $('#detailName').focus().tips({
                        ori: 1,
                        msg: '请填写缴费项目名称',
                        time: 3000
                    });
                    return false;
                }
                if ($('#money').val() == '') {
                    $('#money').focus();
                    smt = false;
                }
            },
            success: function (result) {
                if (result.code == 1) {
                    closeAll();
                    window.parent.table.bootstrapTable('refresh');
                }
                if (result.code != 1) {
                    $('#detailName').focus().tips({
                        ori: 1,
                        msg: result.message,
                        time: 3000
                    });
                }},
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
