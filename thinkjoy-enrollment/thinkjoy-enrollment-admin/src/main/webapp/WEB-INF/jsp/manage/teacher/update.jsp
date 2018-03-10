<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<%@ include file="/resources/inc/top.jsp" %>

<style>
    .form-group{ margin-right: 30px;  vertical-align: top; }
    .lap-page .lap-form{  padding-bottom: 0;}
    #depTree{position: relative;}
    #depTree span{
        font-weight: normal;}
    #area-group{overflow: hidden;}
    #departmentName{
        width: 220px;
        display: inline-block;
        height: 40px;
        -webkit-box-shadow: none;
        -moz-box-shadow: none;
        box-shadow: none;
        border-color: #e2e2e2;
        font-weight: normal;
    }
    .ztree-wrap{
        position: absolute;
        left: 55px;
        width: 220px;
        z-index: 9999;
        background: #fff;
        box-shadow: 0 2px 2px 2px rgba(5, 147, 253, .1);
        -webkit-box-shadow: 0 2px 2px 2px rgba(5, 147, 253, .1);
        -moz-box-shadow: 0 2px 2px 2px rgba(5, 147, 253, .1);
        box-shadow: 0 2px 2px 2px rgba(5, 147, 253, .1);
        display: none;
    }
    #ztree{}
    .lap-chk{display: inline-block;   min-width: 100px;  float: left;}
    .hr{ margin:10px 0 20px 0;}
    #line{display: inline-block;vertical-align:middle;  font-weight: normal;}
    #line+textarea{display: inline-block;width:90%;vertical-align: middle;  font-weight: normal;}
</style>

<div class="lap-page">
    <div class="lap-section">
        <div class="lap-form inline">
            <form id="updateForm" method="post">
                <div class="form-group">
                    <label class="sl2">
                        <span>招生批次</span>
                        <select id="batchId" name="batchId">
                            <c:forEach var="batch" items="${enrollBatches}">
                                <option value="${batch.batchId}" <c:if test="${batch.batchId eq enrollTeacher.batchId}">selected</c:if>>${batch.batchName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <div class="form-group">
                    <label class="sl2">
                        <span>招生老师</span>
                        <select id="teacherId" type="text"  name="teacherId">
                            <c:forEach var="teacher" items="${ucenterTeachers}">
                                <option value="${teacher.teacherId}" <c:if test="${teacher.teacherId eq enrollTeacher.teacherId}">selected</c:if>>${teacher.teacherName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <div class="hr"></div>

                <div class="form-group">
                    <div class="sl2-group">
                        <label class="sl2" id="sl2_1">
                            <span>负责区域</span>
                            <input id="area" type="hidden" name="area">
                            <select id="ppareaCode" name="ppareaCode">
                                <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                    <option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <label class="sl2" id="sl2_2">
                            <select id="pareaCode" name="pareaCode">
                            </select>
                        </label>
                    </div>
                </div>

                <div class="form-group block">
                    <div id="area-group">
                        <div class="lap-chk chkall">
                            <input type="checkbox" id="lap-chk-all">
                        </div>
                    </div>
                </div>

                <div class="hr"></div>

                <div class="form-group block">
                    <div class="lap-radio">
                        <label>启用状态</label>
                        <c:set var="STATUS" value="<%=BaseConstants.Dict.STATUS %>"/>
                        <c:forEach items="${dict:getValueByCode(STATUS)}" var="at">

                            <input id="status_${at.valueKey}" type="radio" name="status" value="${at.valueKey}" <c:if test="${at.valueKey eq enrollTeacher.status}">checked</c:if>/>

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
    <div class="btns-group">
        <a href="javascript:;" class="btn submit" onclick="createSubmit();">提交</a>
        <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
    </div>
</div>



<script>
    var readonly = '${param.readonly}';
    if (readonly == 1) {
        $('input').addClass('form-readonly').attr('readonly', true);
        $('select').prop('disabled', true);
    }
    var relationCode = '${relationCode}'
            , areaStr = '${enrollTeacher.area}'
            , areaArr = areaStr.split('-')
            , firstLoad = true;

    $('select').select2({width: 220, minimumResultsForSearch: 1});


    //区域级联begin-==========
    var ppareaCode;
    var pareaCode;
    $(function () {
        initPPAreaCode();
        //选择行政区划-市级
        $('#ppareaCode').change(function () {
            ppareaCode = $(this).val();
            initPAreaCode();
        });

        //选择行政区划-区/县级
        $('#pareaCode').change(function () {
            pareaCode = $(this).val();
            initAreaCode();
        });
    });
    function initPPAreaCode() {
        $.getJSON('${basePath}/manage/enroll/areaList', {
            areaType: 1,
            limit: 10000
        }, function (json) {
            var datas = [{id: 0, text: '请选择省'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].areaCode;
                data.text = json.rows[i].areaName;
                datas.push(data);
            }
            $('#ppareaCode').empty();
            $('#ppareaCode').select2({
                data: datas,
                width: 220
            });

            if (firstLoad) $('#ppareaCode').val(areaArr[0]).trigger('change');

            $('#pareaCode').empty();
            $('#pareaCode').select2({
                width: 220
            });
            $('#areaCode').empty();
            $('#areaCode').select2({
                placeholder: "请选择区/县",
                width:220
            });
        });
    }
    function initPAreaCode() {
        $.getJSON('${basePath}/manage/enroll/areaList', {
            areaCode: ppareaCode,
            areaType: 2,
            limit: 10000
        }, function (json) {
            var datas = [{id: 0, text: '请选择市'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].areaCode;
                data.text = json.rows[i].areaName;
                datas.push(data);
            }
            $('#pareaCode').empty();
            $('#pareaCode').select2({
                data: datas
                , width: 220
            });

            if (firstLoad) $('#pareaCode').val(areaArr[1]).trigger('change');

            $('#areaCode').empty();
            $('#areaCode').select2({
                placeholder: "请选择区/县",
                width: 220
            });
        });
    }
    function initAreaCode() {
        $.getJSON('${basePath}/manage/enroll/areaList', {
            areaCode: pareaCode,
            areaType: 3,
            limit: 10000
        }, function (json) {
            $('#area-group>div:not(.chkall)').remove();
            var datas = [];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].areaCode;
                data.text = json.rows[i].areaName;
                datas.push(data);
                var el;
                if (firstLoad) {
                    el = '<div class="lap-chk"><input id="lap-chk-' + json.rows[i].areaCode + '" value="' + json.rows[i].areaCode + '" type="checkbox" name="areaCode" checked/>' + '<label for="lap-chk-' + json.rows[i].areaCode + '"><span>' + json.rows[i].areaName + '</span></label></div>';
                } else
                    el = '<div class="lap-chk"><input id="lap-chk-' + json.rows[i].areaCode + '" value="' + json.rows[i].areaCode + '" type="checkbox" name="areaCode"/>' + '<label for="lap-chk-' + json.rows[i].areaCode + '"><span>' + json.rows[i].areaName + '</span></label></div>';

                $('#area-group').append(el);
            }
            firstLoad = false;

            $('#areaCode').empty();
            $('#areaCode').select2({
                data: datas,
                width: 220
            });
        });
    }
    function initSelect2() {
        initPPAreaCode()
    }
    //区域级联end-==========

    function createSubmit() {
        var res = ppareaCode + '-' + pareaCode + '-';
        $('input[name=areaCode]:checked').each(function () {
            res += $(this).val() + ',';
        });

        $('#area').attr('value', res.substr(0, res.length-1));

        $.ajax({
            type: 'post',
            url: '${basePath}/manage/teacher/update/${enrollTeacher.enrollteacherId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
            },
            success: function (result) {
                if (result.code == 1) {
                    parent.layer.closeAll();
                    window.parent.$parentTable.bootstrapTable('refresh');
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
