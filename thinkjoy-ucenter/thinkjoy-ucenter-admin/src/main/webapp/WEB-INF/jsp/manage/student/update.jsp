<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<style>
    #updateForm .col-sm-12.radioes .radio{display: inline-block;}
    #updateForm .col-sm-12.radioes>label{
        font-weight: normal;
        min-width: 70px;
    }
    .form-group.sl{
        border-bottom: 2px solid #eee;
    }
    .form-group.sl>label{
        position: relative;
        margin-right: 5px;
    }
</style>
<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <div class="col-sm-12">
            <div class="form-group">
                <label for="studentName">学生姓名</label>
                <input id="studentName" type="text" class="form-control" name="studentName"  maxlength="64"
                       value="${ucenterStudent.studentName}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="phone">手机号</label>
                <input id="phone" type="text" class="form-control" name="phone" value="${ucenterStudent.phone}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="idcard">身份证号</label>
                <input id="idcard" type="text" class="form-control" name="idcard" value="${ucenterStudent.idcard}">
            </div>
        </div>
        <div class="form-group sl">
            <label for="sex">性别</label>
            <select class="form-group sl" id="sex" name="sex">
                <option value="">请选性别</option>
                <c:set var="SEX" value="<%=BaseConstants.Dict.SEX %>"/>
                <c:forEach items="${dict:getValueByCode(SEX)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterStudent.sex}">selected</c:if>>${at.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="nation">民族</label>
            <select  id="nation" name="nation">
                <option value="">请选民族</option>
                <c:set var="NATION" value="<%=BaseConstants.Dict.NATION %>"/>
                <c:forEach items="${dict:getValueByCode(NATION)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterStudent.nation}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="facultyCode">院系</label>
            <input type="hidden" id="facultyName" name="facultyName">
            <select id="facultyCode" name="facultyCode">
            </select>
        </div>
        <div class="form-group sl">
            <label for="specialtyCode">专业</label>
            <input type="hidden" id="specialtyName" name="specialtyName">
            <select id="specialtyCode" name="specialtyCode">
            </select>
        </div>
        <div class="form-group sl">
            <label for="classId">班级</label>
            <input type="hidden" id="className" name="className">
            <select id="classId" name="classId">
            </select>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="enterYear">入学年份</label>
                <input id="enterYear" type="text" class="form-control" name="enterYear"
                       onfocus="WdatePicker({dateFmt:'yyyy',readOnly:false,isShowToday:false})" value="${ucenterStudent.enterYear}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="studentCode">学号</label>
                <input id="studentCode" type="text" class="form-control" name="studentCode" maxlength="128"
                       value="${ucenterStudent.studentCode}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="studentNo">学籍号</label>
                <input id="studentNo" type="text" class="form-control" name="studentNo" maxlength="32"
                       value="${ucenterStudent.studentNo}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="examineeNumber">考生号</label>
                <input id="examineeNumber" type="text" class="form-control" name="examineeNumber"  maxlength="128"
                       value="${ucenterStudent.examineeNumber}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="examnum">准考证号</label>
                <input id="examnum" type="text" class="form-control" name="examnum"  maxlength="32" value="${ucenterStudent.examnum}">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="enterTime">入学日期</label>
                <input id="enterTime" type="text" class="form-control" name="enterTime"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
                       value="<fmt:formatDate value="${ucenterStudent.enterTime}" type="both" pattern="yyyy-MM-dd"/>">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="birthday">出生日期</label>
                <input id="birthday" type="text" class="form-control" name="birthday"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
                       value="<fmt:formatDate value="${ucenterStudent.birthday}" type="both" pattern="yyyy-MM-dd"/>">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="mail">邮箱</label>
                <input id="mail" type="text" class="form-control" name="mail"  maxlength="128" value="${ucenterStudent.mail}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="nationality">国籍</label>
                <input id="nationality" type="text" class="form-control" name="nationality"  maxlength="64"
                       value="${ucenterStudent.nationality}">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="origin">籍贯</label>
                <input id="origin" type="text" class="form-control" name="origin" maxlength="64" value="${ucenterStudent.origin}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="domicile">户口所在地</label>
                <input id="domicile" type="text" class="form-control" name="domicile"  maxlength="128"
                       value="${ucenterStudent.domicile}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="fromplace">生源地</label>
                <input id="fromplace" type="text" class="form-control" name="fromplace"  maxlength="64"
                       value="${ucenterStudent.fromplace}">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="gradSchool">毕业学校</label>
                <input id="gradSchool" type="text" class="form-control" name="gradSchool"  maxlength="64"
                       value="${ucenterStudent.gradSchool}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="gradTime">毕业时间</label>
                <input id="gradTime" type="text" class="form-control" name="gradTime"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
                       value="<fmt:formatDate value="${ucenterStudent.gradTime}" type="both" pattern="yyyy-MM-dd"/>">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="gradHeadteacher">毕业班主任姓名</label>
                <input id="gradHeadteacher" type="text" class="form-control" name="gradHeadteacher" maxlength="32"
                       value="${ucenterStudent.gradHeadteacher}">
            </div>
        </div>

        <div class="form-group sl">
            <label for="domicileType">户籍性质</label>
            <select class="form-control" id="domicileType" name="domicileType">
                <option value="">请选户籍性质</option>
                <c:set var="DOMICILETYPE" value="<%=BaseConstants.Dict.DOMICILETYPE %>"/>
                <c:forEach items="${dict:getValueByCode(DOMICILETYPE)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterStudent.domicileType}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group sl">
            <label for="studentType">毕业类别</label>
            <select class="form-control" id="studentType" name="studentType">
                <option value="">请选毕业类别</option>
                <c:set var="STUDENTTYPE" value="<%=BaseConstants.Dict.STUDENTTYPE %>"/>
                <c:forEach items="${dict:getValueByCode(STUDENTTYPE)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterStudent.studentType}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
            <label for="politics">政治面貌</label>
            <select class="form-control" id="politics" name="politics">
                <option value="">请选政治面貌</option>
                <c:set var="POLITICS" value="<%=BaseConstants.Dict.POLITICS %>"/>
                <c:forEach items="${dict:getValueByCode(POLITICS)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterStudent.politics}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group sl">
            <label for="isPoor">是否困生</label>
            <select class="form-control" id="isPoor" name="isPoor">
                <option value="">请选是否</option>
                <option value="true"  <c:if test="${ucenterStudent.isPoor==true}">selected</c:if>>是</option>
                <option value="false" <c:if test="${ucenterStudent.isPoor==false}">selected</c:if>>否</option>
            </select>
        </div>
        <div class="form-group sl">
            <label for="isLiveschool">是否住校</label>
            <select class="form-control" id="isLiveschool" name="isLiveschool">
                <option value="">请选是否</option>
                <option value="true" <c:if test="${ucenterStudent.isLiveschool==true}">selected</c:if>>是</option>
                <option value="false" <c:if test="${ucenterStudent.isLiveschool==false}">selected</c:if>>否</option>
            </select>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="address">家庭地址</label>
                <input id="address" type="text" class="form-control" name="address"  maxlength="128" value="${ucenterStudent.address}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="postalCode">邮政编码</label>
                <input id="postalCode" type="text" class="form-control" name="postalCode"  maxlength="16"
                value="${ucenterStudent.postalCode}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="familyPhone">家庭电话</label>
                <input id="familyPhone" type="text" class="form-control" name="familyPhone"  maxlength="32"
                       value="${ucenterStudent.familyPhone}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="description">描述</label>
                <input id="description" type="text" class="form-control" name="description" maxlength="256"
                       value="${ucenterStudent.description}">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="radio">
                <div class="radio radio-inline radio-success">
                    <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterStudent.status==1}">checked</c:if>>
                    <label for="status_1">正常 </label>
                </div>
                <div class="radio radio-inline">
                    <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterStudent.status==0}">checked</c:if>>
                    <label for="status_0">锁定 </label>
                </div>
            </div>
         </div>

        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    var facultyCode = '${ucenterStudent.facultyCode}';
    var specialtyCode = '${ucenterStudent.specialtyCode}';
    var classId = '${ucenterStudent.classId}';
    var flag = 0;
    var relationCode = '${ucenterStudent.schoolCode}';
    <%--var year='${ucenterStudent.enterYear}';--%>

    function changeYears(){
//        year=$('#enterYear').val();
        initFaculty();
    }

    $('#facultyCode').change(function () {
        facultyCode = $(this).val();

        $('#facultyName').val($(this).find('option').eq(this.selectedIndex).text());

        if(facultyCode!=''){
            if (flag == 1) {
                initSpecilty(specialtyCode);
            } else {
                initSpecilty();
            }
        }else{
            $('#specialtyCode').empty();
            $('#specialtyCode').select2({
                data: [{id: 0, text: '请选择专业'}],
                width:'140px'
            });
            $('#classId').empty();
            $('#classId').select2({
                data: [{id: 0, text: '请选择班级'}],
                width:'140px'
            });
        }


    });

    $('#specialtyCode').change(function () {
        specialtyCode = $(this).val();
        $('#specialtyName').val($(this).find('option').eq(this.selectedIndex).text());
        if(specialtyCode!=''){
            if (flag == 1) {
                initClass(classId);
                flag = 0;
            } else {
                initClass();
            }
        }else{
            $('#classId').empty();
            $('#classId').select2({
                data: [{id: 0, text: '请选择班级'}],
                width:'140px'
            });
        }
    });

    $('#classId').change(function () {
        classId = $(this).val();
        $('#className').val($(this).find('option').eq(this.selectedIndex).text());
    });

    //院系初始化
    function initFaculty(val) {
        $.getJSON('${basePath}/manage/faculty/list', {
            schoolCode: relationCode,
//            search_year: year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择院系'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].facultyCode;
                data.text = json.rows[i].facultyName;
                datas.push(data);
            }
            $('#facultyCode').empty();
            $('#facultyCode').select2({
                data: datas,
                width: '100%'
            });
            $('#specialtyCode').empty();
            $('#specialtyCode').select2({
                data: [{id: 0, text: '请选择专业'}],
                width:'140px'
            });
            $('#classId').empty();
            $('#classId').select2({
                data: [{id: 0, text: '请选择班级'}],
                width:'140px'
            });
            //console.log(${ucenterStudent.facultyCode})
            if (!!val) {
                //console.log(val)
                $('#facultyCode').select2({
                    width: '140px'
                }).val(val).trigger('change');
            }
        });

    }

    //专业初始化
    function initSpecilty(val) {
        $.getJSON('${basePath}/manage/specialty/list', {
            schoolCode: relationCode,
            facultyCode: facultyCode,
//            search_year: year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择专业'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].specialtyCode;
                data.text = json.rows[i].specialtyName;
                datas.push(data);
            }
            $('#specialtyCode').empty();
            $('#specialtyCode').select2({
                data: datas,
                width:'140px'
            });
            $('#classId').empty();
            $('#classId').select2({
                data: [{id: 0, text: '请选择班级'}],
                width:'140px'
            });
            if (!!val) {
                //console.log(val)
                $('#specialtyCode').select2({
                    width: '140px'
                }).val(val).trigger('change');
            }
        });
    }

    //班级初始化
    function initClass(val) {
        $.getJSON('${basePath}/manage/class/list', {
            schoolCode: relationCode,
            search_faculty: facultyCode,
            search_specialty: specialtyCode,
//            search_year: year,
            status:'1',
            limit: "-1"
        }, function (json) {
            var datas = [{id: "", text: '请选择班级'}];
            for (var i = 0; i < json.rows.length; i++) {
                var data = {};
                data.id = json.rows[i].classId;
                data.text = json.rows[i].className;
                datas.push(data);
            }
            $('#classId').empty();
            $('#classId').select2({
                data: datas,
                width:'140px'
            });
            if (!!val) {
                //console.log(val)
                $('#classId').select2({
                    width: '140px'
                }).val(val).trigger('change');
            }
        });
    }

    function initSelect2() {
        flag = 1;
        initFaculty(facultyCode);
    }


    function updateSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/student/update/${ucenterStudent.studentId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#studentName').val() == '') {
                    $('#studentName').focus();
                    return false;
                }
                if ($('#phone').val() == '') {
                    $('#phone').focus();
                    return false;
                }else{
                    var reg = /^1\d{10}$/;
                    if (!reg.test($("#phone").val())) {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: '请输入有效的手机号码!',
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        $('#phone').focus();
                        return false;
                    }
                }
                if ($('#idcard').val() == '') {
                    $('#idcard').focus();
                    return false;
                }else{
                    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                    if (!reg.test($("#idcard").val())) {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: '请输入有效的身份证号!',
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        return false;
                        $('#idcard').focus();
                    }
                }
                if($('#sex').val()==''){
                    $('#sex').focus();
                    return false;
                }
                if($('#nation').val()==''){
                    $('#nation').focus();
                    return false;
                }
                if ($('#facultyCode').val() == '' || $('#facultyName').val() == '') {
                    $('#facultyCode').focus();
                    return false;
                }
                if ($('#specialtyCode').val() == '' || $('#specialtyName').val() == '') {
                    $('#specialtyCode').focus();
                    return false;
                }
                if ($('#classId').val() == '' || $('#className').val() == '') {
                    $('#classId').focus();
                    return false;
                }
                if ($('#enterYear').val() == '') {
                    $('#enterYear').focus();
                    return false;
                }
                if ($('#studentCode').val() == '') {
                    $('#studentCode').focus();
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
                    updateDialog.close();
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