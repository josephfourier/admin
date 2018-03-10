<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<jsp:include page="/resources/inc/top.jsp" flush="true"/>
<script src="${basePath}/resources/thinkjoy-admin/js/common.js"></script>

<div class="lap-page">
    <div class="lap-section">
        <p class="title">招生批次设置</p>
        <div class="lap-form">
            <form class="form-inline" id="createForm" method="post">
                <div class="form-group">
                    <label for="batchYear">批次年度</label>
                    <input name="batchYear" id="batchYear" class="form-control" value="${enrollbatch.batchYear}"/>
                </div>
                <div class="form-group">
                    <label for="batchName">批次名称</label>
                    <input id="batchName" type="text" class="form-control" name="batchName" maxlength="100"
                           value="${enrollbatch.batchName}">
                </div>

                <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input id="startTime" type="text" class="form-control input-time" name="startTime"
                           value="<fmt:formatDate value="${enrollbatch.startTime}" type="both" pattern="yyyy-MM-dd"/>">
                </div>

                <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input id="endTime" type="text" class="form-control input-time" name="endTime"
                           value="<fmt:formatDate value="${enrollbatch.endTime}" type="both" pattern="yyyy-MM-dd"/>">
                </div>

                <div class="form-group">
                    <label for="reportDate">报道日期</label>
                    <input id="reportDate" type="text" class="form-control input-time" name="reportDate" maxlength="100"
                           value="<fmt:formatDate value="${enrollbatch.reportDate}" type="both" pattern="yyyy-MM-dd"/>">
                </div>

                <div class="form-group">
                    <div class="lap-radio">
                        <label>启用状态</label>
                        <c:set var="STATUS" value="<%=BaseConstants.Dict.STATUS %>"/>
                        <c:choose>
                            <c:when test="${param.readonly eq 1}">
                                <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">
                                  <%--  ${at}----------
                                    <c:if test="${at.valueKey eq 1}">--%>
                                        <input id="status_${at.valueKey}" type="radio" name="status" <c:if test="${at.valueKey eq enrollbatch.status}">checked</c:if>
                                               value="${at.valueKey}"/>

                                        <label for="status_${at.valueKey}">
                                            <c:if test="${at.valueName eq '正常'}">是</c:if>
                                            <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                        </label>
<%--
                                    </c:if>
--%>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">
                                        <input id="status_${at.valueKey}" type="radio" name="status"
                                               value="${at.valueKey}" <c:if test="${at.valueKey eq enrollbatch.status}">checked</c:if>/>
                                    <label for="status_${at.valueKey}">
                                        <c:if test="${at.valueName eq '正常'}">是</c:if>
                                        <c:if test="${at.valueName eq '锁定'}">否</c:if>
                                    </label>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="lap-section">
        <p class="title">招生计划维护(至少选择一个专业)</p>
        <div class="lap-table">
            <table id="tableSpe"></table>
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

    var $tableSpe = $('#tableSpe');
    var $parentTable = $tableSpe.bootstrapTable({
        url: '${basePath}/manage/batch/getUpdtingSpecialtyInfo/' + '${enrollbatch.batchId}',
        striped: true,
        search: false,
        showRefresh: false,
        showColumns: false,
        minimumCountColumns: 2,
        clickToSelect: false,
        detailView: false,
        detailFormatter: 'detailFormatter',
        pagination: false,
        paginationLoop: false,
        sidePagination: 'server',
        silentSort: false,
        smartDisplay: false,
        escape: true,
        searchOnEnterKey: true,
        idField: 'specialtyCode',
        //sortOrder: 'desc',
        maintainSelected: true,
        queryParams: queryParams,
        columns: [
            {field: 'ck', checkbox: true, formatter: 'stateFormatter'},
            {field: 'specialtyCode', title: '专业代码', sortable: false, align: 'center'},
            {field: 'specialtyName', title: '专业名称', align: 'center'},
            {field: 'trdrName', title: '培养方向', align: 'center'},
            {field: 'schoolSystem', title: '学制', align: 'center',formatter: 'schoolFormatter'},
            {field: 'specialtyNature', title: '专业性质',formatter: 'specltyFormatter'},
            {field: 'enrollmentTarget', title: '招生对象', align: 'center'},
            {field: 'specialtyType', title: '专业类别', align: 'center',formatter: 'typeFormatter'},
            {field: 'facultyName', title: '所属院系', align: 'center'},
            {field: 'price', title: '费用', align: 'center'},
            {field: 'planNum', title: '计划人数', align: 'center', formatter: 'planNum'}
        ]
    });

    //复选框默认选中格式化
    function stateFormatter(value, row, index) {
        if (row.planNum != null) {
            return {
                //disabled : true,//设置是否可用
                checked: true//设置选中
            };
        }
    }

    function planNum(value, row, index) {
        if (value == null) {
            value = '';
        }
        var sc = row['specialtyCode'];
        if (readonly == 1) return "<span>" + value + "</span>";

        return "<input class='table-control' id='planNum" + sc + "' type='text' name='planDtos[" + index + "].planNum' value='" + value + "'>" +
                "<input class='table-control' id='specialtyCode" + sc + "' type='hidden' name='planDtos[" + index + "].specialtyCode' value='" + sc + "'>";
    }

    function queryParams() {
    }

    function closeAll() {
        parent.layer.closeAll();
    }

    function createSubmit() {
        var data = $('#createForm').serializeArray()
        var rows = $tableSpe.bootstrapTable('getSelections');
        var ids = new Array();

        for (var i in rows) {
            ids.push(rows[i].specialtyCode);
            var node = $('#specialtyCode' + rows[i].specialtyCode);
            var name = node.attr("name");
            var value = node.attr("value");
            data.push({name, value});

            var node1 = $('#planNum' + rows[i].specialtyCode);
            name = node1.attr("name");
            value = node1.val();

            data.push({name, value})
        }

        $.ajax({
            type: 'post',
            url: '${basePath}/manage/batch/update/${enrollbatch.batchId}',
            data: data,
            beforeSend: function () {
                if ($('#batchName').val() == '') {
                    $('#batchName').focus().tips({
                        ori: 1,
                        msg: '请填写批次名称',
                        time: 3000
                    });
                    return false;
                }
                if ($('#batchYear').val() == '') {
                    $('#batchYear').focus();
                    return false;
                }
                if ($('#startTime').val() == '') {
                    $('#startTime').focus();
                    return false;
                }
                if ($('#endTime').val() == '') {
                    $('#endTime').focus();
                    return false;
                }
                if ($('#reportDate').val() == '') {
                    $('#reportDate').focus();
                    return false;
                }
            },
            success: function (result) {
                if (result.code == 1) {
                    closeAll();
                    window.parent.$parentTable.bootstrapTable('refresh');
                }
                if (result.code != 1) {
                    $('#batchName').focus().tips({
                        ori: 1,
                        msg: result.message,
                        time: 3000
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
</script>
