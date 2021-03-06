﻿
<%@ include file="/tags/taglibs.jsp" %>

<jsp:include page="/resources/inc/top.jsp" flush="true"/>
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
                        <c:set var="STAUTS" value="<%=BaseConstants.Dict.STATUS %>"/>
                        <c:forEach items="${dict:getValueByCode(STAUTS)}" var="at" varStatus="status">
                            <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}"
                                   <c:if test="${at.valueKey eq 1}">checked</c:if>/>

                            <label for="status_${at.valueKey}">
                                <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                <c:if test="${at.valueName eq '正常'}">是</c:if>
                            </label>
                        </c:forEach>
                    </div>
                </div>


                <div class="form-group">
                    <label for="detailName">缴费项目名称</label>
                    <input id="detailName" type="text" class="form-control" name="detailName" maxlength="100">
                </div>

                <div class="form-group">
                    <label class="sl2">
                        <label>缴费类别</label>
                        <select class="" id="itemId" name="itemId">
                            <c:forEach var="item" items="${enrollChargeitems}">
                                <option value="${item.itemId}">${item.itemName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <div class="form-group">
                    <label for="money">费用</label>
                    <input id="money" type="text" class="form-control" name="money">
                    <input id="itemName" type="hidden"  name="itemName">
                </div>

                <div class="form-group">
                    <label class="sl2">
                        <label>是否必须</label>
                        <select class="ex" id="isMust" name="isMust">
                            <c:set var="ISMUST" value="<%=BaseConstants.Dict.ISMUST %>"/>
                            <c:forEach items="${dict:getValueByCode(ISMUST)}" var="at">
                                <option value="${at.valueKey}">${at.valueName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <%--<div class="form-group" id="tmp">--%>
                    <%--<label>关联专业</label>--%>
                    <%--<div class="w">--%>
                        <%--<c:forEach items="${ucenterFaculties}" var="faculty">--%>
                            <%--<div class="chk-group">--%>
                                <%--<label>${faculty.facultyName}</label>--%>
                               <%--<div class="wrap">--%>
                                   <%--<c:forEach items="${ucenterSpecialties}" var="specialty">--%>
                                       <%--<c:if test="${faculty.facultyCode eq specialty.facultyCode}">--%>
                                           <%--&lt;%&ndash; <input id="specialtyCode" type="checkbox" name="specialtyCode" value="${specialty.specialtyCode}">--%>
                                            <%--<label for="specialtyCode">${specialty.specialtyName}</label>--%>
            <%--&ndash;%&gt;--%>
                                           <%--<div class="lap-chk"><input id="lap-chk-${specialty.specialtyCode}" value="${specialty.specialtyCode}" type="checkbox" name="specialtyCode"/><label for="lap-chk-${specialty.specialtyCode}"><span> ${specialty.specialtyName}</span></label></div>--%>
                                       <%--</c:if>--%>
                                   <%--</c:forEach>--%>
                               <%--</div>--%>
                            <%--</div>--%>
                        <%--</c:forEach>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <div class="form-group block">
                    <label for="description">描述</label>
                    <textarea class="form-control" rows="3" name="description" id="description"></textarea>
                </div>
            </form>
        </div>
    </div>

    <div class="btns-group">
        <a href="javascript:;" class="btn submit" data-ajax="show" onclick="createSubmit();">提交</a>
        <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
    </div>
</div>
<script>

    /* */
    $('select').select2({width: 240, minimumResultsForSearch: 5});
    var cpflag=false;
    function createSubmit(){
        var data = $('#createForm').serializeArray();
        var itemId=$('#itemId').val();
        var itemName=$("#itemId").find("option:selected").text();
        $("#itemName").val(itemName);
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
        var smt = true;
        if ($('#detailName').val() == '') {
            $('#detailName').focus().tips({
                msg: '请填写缴费项目名称',
                time: 3000
            });
            return false;
        }
        if ($('#money').val() == '') {
            $('#money').focus();
            smt = false;
        }
        if (!smt) return;

        if (smt) {
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/chargedetail/create',
                data: data,
                beforeSend: function () {
                },
                success: function (result) {
                    //console.log(result);
                    if (result.code == 1) {
                        parent.layer.closeAll();
                       window.parent.$parentTable.bootstrapTable('refresh');
                    }
                    if (result.code != 1) {
                        $('#detailName').focus().tips({
                            ori: 1,
                            msg: result.message,
                            time: 5000
                        });
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
    }

</script>