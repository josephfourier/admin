<%@ page import="java.util.Date" %>
<%@ page import="sun.util.calendar.CalendarUtils" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<jsp:include page="/resources/inc/top.jsp" flush="true"/>
<script src="${basePath}/resources/thinkjoy-admin/js/common.js"></script>

<body>
<div class="lap-page">
    <div class="lap-section">
        <p class="title">招生批次设置</p>
        <div class="lap-form">
            <form class="form-inline" id="createForm" method="post">
                <div class="form-group">
                    <label for="batchYear">批次年度</label>
                    <input id="batchYear" type="text" class="form-control" name="batchYear" maxlength="100">
                </div>

                <div class="form-group">
                    <label for="batchName">批次名称</label>
                    <input id="batchName" type="text" class="form-control" name="batchName" maxlength="100">
                </div>

                <div class="form-group">
                    <label for="startTime">开始时间</label>
                    <input id="startTime" type="text" class="form-control input-time" name="startTime">
                </div>

                <div class="form-group">
                    <label for="endTime">结束时间</label>
                    <input id="endTime" type="text" class="form-control input-time" name="endTime">
                </div>

                <div class="form-group">
                    <label for="reportDate">报道日期</label>
                    <input id="reportDate" type="text" class="form-control input-time" name="reportDate" maxlength="100">
                </div>


                <div class="form-group">
                    <div class="lap-radio">
                        <label>启用状态</label>
                        <c:set var="STATUS" value="<%=BaseConstants.Dict.STATUS %>"/>
                        <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">

                        <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}" <c:if test="${at.valueKey eq 1}">checked</c:if>/>

                        <label for="status_${at.valueKey}">
                            <c:if test="${at.valueName eq '正常'}">是</c:if>
                            <c:if test="${at.valueName eq '锁定'}">否</c:if>
                        </label>
                        </c:forEach>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="lap-section">
        <p class="title" id="requiredSpe">招生计划维护(至少选择一个专业)</p>
        <div class="lap-table">
            <table id="tableSpe"></table>
        </div>
    </div>
    <div class="btns-group">
        <a href="javascript:;" class="btn submit" data-ajax="show" onclick="createSubmit();">提交</a>
        <a href="javascript:;" class="btn cancel" onclick="closeAll();">取消</a>
    </div>
</div>

<script>

    var $tableSpe = $('#tableSpe');
    $(function () {
        // bootstrap table初始化
        $tableSpe.bootstrapTable({
            url: '${basePath}/manage/batch/getSpecialtyInfo',
            striped: true,
            search: false,
            showRefresh: false,
            showColumns: false,
            minimumCountColumns: 2,
            clickToSelect: false,
            detailView: false,
            pagination: false,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'specialtyCode',
            maintainSelected: true,
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'specialtyCode', title: '专业代码', sortable: false, align: 'center'},
                {field: 'specialtyName', title: '专业名称', align: 'center'},
                {field: 'trdrName', title: '培养方向', align: 'center'},
                {field: 'schoolSystem', title: '学制', align: 'center',formatter: 'schoolFormatter'},
                {field: 'specialtyNature', title: '专业性质',formatter: 'specltyFormatter'},
                {field: 'enrollmentTarget', title: '招生对象', align: 'center',formatter: 'tarFormatter'},
                {field: 'specialtyType', title: '专业类别', align: 'center',formatter: 'typeFormatter'},
                {field: 'facultyName', title: '所属院系', align: 'center'},
                {field: 'price', title: '专业费用', align: 'center'},
                {field: 'planNum', title: '计划招生人数', align: 'center', formatter: 'planNum'}
            ]
        });
    });

    function planNum(value, row, index) {
        var sc = row['specialtyCode'];
        return "<input class='table-control' id='planNum" + sc + "' type='text' name='planDtos[" + index + "].planNum'>" +
                "<input id='specialtyCode" + sc + "' type='hidden' name='planDtos[" + index + "].specialtyCode' value='" + sc + "'>";
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

        var smt = true;
        if ($('#batchName').val() == '') {
            $('#batchName').focus();
            smt = false;
        }
        if ($('#batchYear').val() == '') {
            $('#batchYear').focus();
            smt = false;
        }
        if ($('#startTime').val() == '') {
            $('#startTime').focus();
            smt = false;
        }
        if ($('#endTime').val() == '') {
            $('#endTime').focus();
            smt = false;
        }
        if ($('#reportDate').val() == '') {
            $('#reportDate').focus();
            smt = false;
        }

        if (!smt) return;

        var rows = $tableSpe.bootstrapTable('getSelections');
        if (rows.length == 0) {
            $('#requiredSpe').tips({
                ori: 1,
                msg: '至少选择一个专业',
                time: 3000
            });

            smt = false;

        } else {
            $('#tableSpe').find('tr.selected').each(function() {
                var tar = $(this).find('.table-control');
                if (tar.val() == '') {
                    tar.tips({
                        ori: 1,
                        msg: '请填写计划招生人数',
                        time: 3000,
                        tipsMore: true
                    });
                    smt = false;
                }
            });
        }

        if (smt) {
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/batch/create',
                data: data,
                beforeSend: function () {},
                success: function (result) {
                    if (result.code == 1) {
                        closeAll();
                        window.parent.$parentTable.bootstrapTable('refresh');
                    }
                    if (result.code != 1) {
                        $('#batchName').focus().tips({
                            ori: 1,
                            msg: result.message,
                            time: 5000
                        });
                        /*if (result.data instanceof Array) {
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

                         }*/
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


    }

    $.date({
        elem: ['#endTime','#startTime','#reportDate'],
        type: 'date'
    });

    $.date({elem: '#batchYear'});

</script>

<%--
<jsp:include page="/resources/inc/head.jsp" flush="true"/>
<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="batchName">批次名称</label>
                        <input id="batchName" type="text" class="form-control" name="batchName" maxlength="100">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="batchYear">批次年度</label>
                        <input id="batchYear" type="text" class="form-control" name="batchYear" maxlength="100"
                               onfocus="WdatePicker({dateFmt:'yyyy',readOnly:true})">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="startTime">开始时间</label>
                        <input id="startTime" type="text" class="form-control" name="startTime" maxlength="100"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="endTime">结束时间</label>
                        <input id="endTime" type="text" class="form-control" name="endTime" maxlength="100"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="reportDate">报道日期</label>
                        <input id="reportDate" type="text" class="form-control" name="reportDate" maxlength="100"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
                    </div>
                </div>
            </div>

            <div class="col-sm-12">
                <div class="form-group">
                    <div class="fg-line">
                        <label for="description">描述</label>
                        <input id="description" type="text" class="form-control" name="description">
                    </div>
                </div>
            </div>


            <div class="col-sm-12">
                <label>启用状态</label>
                <div class="radio">
                    <c:set var="STATUS" value="<%=BaseConstants.Dict.STATUS %>"/>
                    <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">
                        <div class="radio radio-inline
						<c:if test="${at.valueKey eq 1}">radio-info</c:if>
						<c:if test="${at.valueKey eq 0}">radio-danger</c:if>">
                            <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}"
                                   <c:if test="${at.valueKey eq 1}">checked</c:if>/>
                            <label for="status_${at.valueKey}">${at.valueName}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>

    </form>
    <table id="tableSpe"></table>
    <div class="form-group text-right dialog-buttons">
        <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
        <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">取消</a>
    </div>
    <jsp:include page="/resources/inc/footer.jsp" flush="true"/>

</div>
<script>
    var $tableSpe = $('#tableSpe');
    $(function () {
        // bootstrap table初始化
        $tableSpe.bootstrapTable({
            url: '${basePath}/manage/batch/getSpecialtyInfo',
            height: getHeight(),
            striped: true,
            search: false,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            detailView: true,
            //detailFormatter: 'detailFormatter',
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
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'specialtyCode', title: '专业代码', sortable: true, align: 'center'},
                {field: 'specialtyName', title: '专业名称', align: 'center'},
                {field: 'trdrName', title: '培养方向', align: 'center'},
                {field: 'schoolSystem', title: '学制', align: 'center',},
                {field: 'specialtyNature', title: '专业性质',},
                {field: 'enrollmentTarget', title: '招生对象', align: 'center'},
                {field: 'specialtyType', title: '专业类别', align: 'center'},
                {field: 'facultyName', title: '所属院系', align: 'center'},
                {field: 'price', title: '费用', align: 'center'},
                {field: 'planNum', title: '计划人数', align: 'center', formatter: 'planNum'}
            ]
        });
    });

    function planNum(value, row, index) {
        var sc = row['specialtyCode'];
        return "<input id='planNum" + sc + "' type='text' name='planDtos[" + index + "].planNum'>" +
                "<input id='specialtyCode" + sc + "' type='hidden' name='planDtos[" + index + "].specialtyCode' value='" + sc + "'>";
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
            data.push({name, value})

            var node1 = $('#planNum' + rows[i].specialtyCode);
            name = node1.attr("name");
            value = node1.val();

            data.push({name, value})
        }


        $.ajax({
            type: 'post',
            url: '${basePath}/manage/batch/create',
            data: data,
            beforeSend: function () {
                if ($('#batchName').val() == '') {
                    $('#batchName').focus();
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
--%>
