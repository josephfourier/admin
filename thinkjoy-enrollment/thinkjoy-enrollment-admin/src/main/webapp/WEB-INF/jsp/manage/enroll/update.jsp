<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<%@ include file="/resources/inc/top.jsp" %>

<style>
    #base-info{}
    #base-info .form-group{
        margin-bottom: 20px;}
    #base-info input.form-control{
        height: 40px;
        }
    #complex{
        overflow: hidden;}
    #complex p{
        float: left;}
    #complex .form-group{
        float: left;}
    .title{ font-weight: bold;}
    .lap-radio>label{
        padding: 0;
        text-align: right;}
    .sl2{
        text-align: right;}
    .lap-searchbar .star{
        color: #ff452c !important;
        top: 9px !important;
        width: 8px;
        display: inline-block;
    }
    .lap-searchbar .tip{}
</style>

<div class="lap-page">
    <form id="updateForm" method="post">
        <div class="lap-searchbar">
            <label class="input">
                <span class="star">*</span><span>招生年度</span>
                <input id="batchYear" type="text" class="form-control" name="batchYear" readonly="readonly"
                       value="${enrollStudent.year}">
                <input id="year" type="hidden" class="form-control" name="year" value="${enrollStudent.year}">
                <input id="status" type="hidden" class="form-control" name="status" value="${enrollStudent.status}">
            </label>

            <label class="sl2">
                <span>招生批次</span>
                <select id="batchId" name="batchId" onchange="setbatch();">
                    <c:forEach items="${batchList}" var="batchList">
                        <option value="${batchList.batchId}"
                                <c:if test="${batchList.batchId eq enrollStudent.batchId}">selected="selected"</c:if>>${batchList.batchName}</option>
                    </c:forEach>
                </select>
                <input id="batchName" type="hidden" class="form-control" name="batchName"
                       value="${enrollStudent.batchName}">
            </label>

            <label class="sl2">
                <span>招生人员</span>
                <select id="enrollteacherId" name="enrollteacherId">
                    <%--<c:forEach items="${teacherList}" var="teacherList">--%>
                    <%--<option value="${teacherList.enrollteacherId}"--%>
                    <%--<c:if test="${teacherList.enrollteacherId eq enrollStudent.enrollteacherId}">selected="selected"</c:if>>${teacherList.teacherName}</option>--%>
                    <%--</c:forEach>--%>
                </select>
                <input id="teacherName" type="hidden" class="form-control" name="teacherName"
                       value="${enrollStudent.teacherName}">
            </label>

            <label class="sl2">
                <span>录取状态</span>
                <select id="enrollStatus" name="enrollStatus" readonly="readonly">
                    <c:forEach var="enrollstatusDicts" items="${enrollstatusDicts}">
                        <option value="${enrollstatusDicts.valueKey}"
                                <c:if test="${enrollstatusDicts.valueKey eq enrollStudent.enrollStatus}">selected="selected"</c:if>>${enrollstatusDicts.valueName}</option>
                    </c:forEach>
                </select>
            </label>
        </div>

        <div id="base-info">
            <div class="lap-section">
                <p class="title">基本信息</p>

                <div class="lap-form">
                    <div class="form-inline">
                        <div class="form-group">
                            <label for="studentName"><span class="star"><i class="tip">*</i>姓名</span></label>
                            <input id="studentName" type="text" class="form-control base-input" name="studentName"
                                   value="${enrollStudent.studentName}">
                            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode"
                                   value="${enrollStudent.schoolCode}">
                            <input id="feeStatus" type="hidden" class="form-control" name="feeStatus"
                                   value="${enrollStudent.feeStatus}">
                        </div>

                        <div class="form-group">
                            <div class="sl2-group">
                                <label class="sl2" id="sl2_1">
                                    <span class="star"><i class="tip">*</i>籍贯</span>
                                    <select id="ppareaCode2" name="ppareaCode2">
                                        <option value="0">省份</option>
                                        <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                            <option value="${ucenterAreas.areaCode}"
                                                    <c:if test="${ppareaCode2==ucenterAreas.areaCode}">selected="selected"</c:if>>${ucenterAreas.areaName}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label class="sl2" id="sl2_2">
                                    <select id="pareaCode2" name="pareaCode2">
                                        <option value="0">城市</option>
                                    </select>
                                    <input id="origin" type="hidden" class="form-control" name="origin"
                                           alue="${enrollStudent.origin}">
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>民族</span>
                                <select id="nation" name="nation">
                                    <c:forEach var="nationDicts" items="${nationDicts}">
                                        <option value="${nationDicts.valueKey}"
                                                <c:if test="${nationDicts.valueKey eq enrollStudent.nation}">selected="selected"</c:if>>${nationDicts.valueName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>



                        <div class="form-group">
                            <div class="lap-radio">
                                <label>性别</label>
                            <c:choose>
                                <c:when test="${param.readonly eq 1}">
                                    <c:forEach var="sexDicts" items="${sexDicts}">
                                        <c:if test="${sexDicts.valueKey eq enrollStudent.sex}">
                                            <input id="sex_${sexDicts.valueKey}" type="radio" name="sex" value="${sexDicts.valueKey}" checked/>
                                            <label for="sex_${sexDicts.valueKey}">${sexDicts.valueName}</label>
                                        </c:if>

                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="sexDicts" items="${sexDicts}">
                                        <input id="sex_${sexDicts.valueKey}" type="radio" name="sex" value="${sexDicts.valueKey}"
                                               <c:if test="${sexDicts.valueKey eq enrollStudent.sex}">checked</c:if>/>
                                        <label for="sex_${sexDicts.valueKey}">${sexDicts.valueName}</label>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="lap-radio">
                                <label>是否是贫困生</label>

                                <c:choose>
                                    <c:when test="${param.readonly eq 1}">
                                        <input type="radio" name="isPoor" value="${enrollStudent.isPoor}" id="isPoor-${enrollStudent.isPoor}" checked/>

                                        <label for="isPoor-${enrollStudent.isPoor}">
                                            <c:if test="${enrollStudent.isPoor eq false}">
                                                否
                                            </c:if>
                                            <c:if test="${enrollStudent.isPoor eq true}">
                                                是
                                            </c:if>
                                        </label>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="radio" name="isPoor" value="false" id="isPoor-false"
                                               <c:if test="${enrollStudent.isPoor eq false}">checked</c:if>/>
                                        <label for="isPoor-false">否</label>

                                        <input type="radio" name="isPoor" value="true" id="isPoor-true"
                                               <c:if test="${enrollStudent.isPoor eq true}">checked</c:if>/>
                                        <label for="isPoor-true">是</label>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>


                    <div class="form-group">
                        <div class="lap-radio">
                            <label>是否住校</label>

                            <c:choose>
                                <c:when test="${param.readonly eq 1}">
                                    <input type="radio" name="isLiveschool" value="${enrollStudent.isLiveschool}" id="isLiveschool-${enrollStudent.isLiveschool}" checked/>
                                    <label for="isLiveschool-${enrollStudent.isLiveschool}">
                                        <c:if test="${enrollStudent.isLiveschool eq false}">否</c:if>
                                        <c:if test="${enrollStudent.isLiveschool eq true}">是</c:if>
                                    </label>
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="isLiveschool" value="false" id="isLiveschool-false"
                                           <c:if test="${enrollStudent.isLiveschool eq false}">checked</c:if>/>
                                    <label for="isLiveschool-false">否</label>

                                    <input type="radio" name="isLiveschool" value="true" id="isLiveschool-true"
                                           <c:if test="${enrollStudent.isLiveschool eq true}">checked</c:if>/>
                                    <label for="isLiveschool-true">是</label>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="idcard"><span class="star"><i class="tip">*</i>身份证号</span></label>
                        <input id="idcard" type="text" class="form-control" name="idcard"
                               value="${enrollStudent.idcard}">
                    </div>

                    <div class="form-group">
                        <label for="birthday"><span class="star"><i class="tip">*</i>出生日期</span></label>
                        <input id="birthday" type="text" class="form-control base-input" name="birthday"
                               value="<fmt:formatDate value="${enrollStudent.birthday}" type="both" pattern="yyyy-MM-dd"/>">
                    </div>

                    <div class="form-group">
                        <label class="sl2">
                            <span class="star"><i class="tip">*</i>政治面貌</span>
                            <select id="politics" name="politics">
                                <c:forEach var="politicsDicts" items="${politicsDicts}">
                                    <option value="${politicsDicts.valueKey}"
                                            <c:if test="${politicsDicts.valueKey eq enrollStudent.politics}">selected="selected"</c:if>>${politicsDicts.valueName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>


                    <div class="form-group">
                        <div class="sl2-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>户口所在地</span>
                                <select id="ppareaCode" name="ppareaCode">
                                    <option value="0">请选择省</option>
                                    <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                        <option value="${ucenterAreas.areaCode}"
                                                <c:if test="${ppareaCode==ucenterAreas.areaCode}">selected="selected"</c:if>>${ucenterAreas.areaName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <label class="sl2">
                                <select id="pareaCode" name="pareaCode">
                                    <option value="0">城市</option>
                                </select>
                            </label>
                        </div>
                        <input id="domicile" type="hidden" name="domicile" value="${enrollStudent.domicile}">
                    </div>

                    <div class="form-group">
                        <div class="sl2-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>生源地</span>
                                <select id="ppareaCode1" name="ppareaCode1">
                                    <option value="0">请选择省</option>
                                    <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                        <option value="${ucenterAreas.areaCode}"
                                                <c:if test="${ppareaCode1==ucenterAreas.areaCode}">selected="selected"</c:if>>${ucenterAreas.areaName}</option>
                                    </c:forEach>
                                </select>
                            </label>

                            <label class="sl2">
                                <select id="pareaCode1" name="pareaCode1">
                                    <option value="0">城市</option>
                                </select>
                                <input id="fromplace" type="hidden" class="form-control" name="fromplace"
                                       value="${enrollStudent.fromplace}">
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="sl2">
                            <span>户籍性质</span>
                            <select id="domicileType" name="domicileType">
                                <c:forEach var="domicileDicts" items="${domicileDicts}">
                                    <option value="${domicileDicts.valueKey}"
                                            <c:if test="${domicileDicts.valueKey eq enrollStudent.domicileType}">selected="selected"</c:if>>${domicileDicts.valueName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="form-group">
                        <label for="phone"><span class="star"><i class="tip">*</i>手机号码</span></label>
                        <input id="phone" type="text" class="form-control base-input" name="phone"
                               value="${enrollStudent.phone}">
                    </div>
                    <div class="form-group">
                        <label for="familyPhone">固定电话</label>
                        <input id="familyPhone" type="text" class="form-control base-input" name="familyPhone"
                               value="${enrollStudent.familyPhone}">
                    </div>
                    <div class="form-group">
                        <label for="mail">邮箱</label>
                        <input id="mail" type="text" class="form-control base-input" name="mail"
                               value="${enrollStudent.mail}">
                    </div>

                    <div class="form-group lg">
                        <label for="address"><span class="star"><i class="tip">*</i>通讯地址</span></label>
                        <input id="address" type="text" class="form-control base-input" name="address"
                               value="${enrollStudent.address}">
                    </div>
                    <div class="form-group">
                        <label for="postalCode">邮政编码</label>
                        <input id="postalCode" type="text" class="form-control base-input" name="postalCode"
                               value="${enrollStudent.postalCode}">
                    </div>

                    <div class="form-group">
                        <label for="gradSchool"><span class="star"><i class="tip">*</i>毕业学校</span></label>
                        <input id="gradSchool" type="text" class="form-control base-input" name="gradSchool"
                               value="${enrollStudent.gradSchool}">
                    </div>
                    <div class="form-group">
                        <label class="sl2">
                            <span class="star"><i class="tip">*</i>毕业类别</span>
                            <select id="studentType" name="studentType">
                                <c:forEach var="targetDicts" items="${targetDicts}">
                                    <option value="${targetDicts.valueKey}"
                                            <c:if test="${targetDicts.valueKey eq enrollStudent.studentType}">selected="selected"</c:if>>${targetDicts.valueName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="gradTime"><span class="star"><i class="tip">*</i>毕业时间</span></label>
                        <input id="gradTime" type="text" class="form-control base-input" name="gradTime"
                               value="<fmt:formatDate value="${enrollStudent.gradTime}" type="both" pattern="yyyy-MM-dd"/>">
                    </div>

                    <div class="form-group">
                        <label for="gradHeadteacher">班主任姓名</label>
                        <input id="gradHeadteacher" type="text" class="form-control base-input" name="gradHeadteacher"
                               value="${enrollStudent.gradHeadteacher}">
                    </div>
                    <div class="form-group">
                        <label for="examnum">准考证号</label>
                        <input id="examnum" type="text" class="form-control base-input" name="examnum"
                               value="${enrollStudent.examnum}">
                    </div>
                    <div class="form-group">
                        <label for="examineeNumber">考生号</label>
                        <input id="examineeNumber" type="text" class="form-control base-input" name="examineeNumber"
                               value="${enrollStudent.examineeNumber}">
                    </div>
                </div>

            </div>
        </div>

        <div class="lap-section">
            <div class="title" id="complex">
                <p>报考专业</p>

                <div class="form-group">
                <label class="sl2 default">
                    <span>录取志愿</span>
                <select id="enrollVol" name="enrollVol" onchange="setlqzy();">
                    <c:forEach var="lqzyDicts" items="${lqzyDicts}">
                        <option value="${lqzyDicts.valueKey}"
                                <c:if test="${lqzyDicts.valueKey eq enrollStudent.enrollVol}">selected="selected"</c:if>>${lqzyDicts.valueName}</option>
                    </c:forEach>
                </select>
                <input id="specialtyCode" type="hidden" class="form-control" name="specialtyCode"
                       value="${enrollStudent.specialtyCode}">
                <input id="specialtyName" type="hidden" class="form-control" name="specialtyName"
                       value="${enrollStudent.specialtyName}">
                <input id="trdrName" type="hidden" class="form-control" name="trdrName"
                       value="${enrollStudent.trdrName}">
                <input id="schoolSystem" type="hidden" class="form-control" name="schoolSystem"
                       value="${enrollStudent.schoolSystem}">
                <input id="facultyCode" type="hidden" class="form-control" name="facultyCode"
                       value="${enrollStudent.facultyCode}">
                <input id="facultyName" type="hidden" class="form-control" name="facultyName"
                       value="${enrollStudent.facultyName}">
                </label>
            </div>
            </div>

            <div class="lap-form">
                <div class="w">
                    <p>第一志愿</p>

                    <div class="form-inline">
                        <div class="form-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>专业</span>
                                <select id="firstVolcode" name="firstVolcode" onchange="setfirst();">
                                    <option value="0">请选择专业</option>
                                    <c:forEach var="specialtyList" items="${specialtyList}">
                                        <option value="${specialtyList.specialtyCode}"
                                                <c:if test="${specialtyList.specialtyCode eq enrollStudent.firstVolcode}">selected="selected"</c:if>>${specialtyList.specialtyName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <input id="firstVol" type="hidden" class="form-control" name="firstVol"
                                   value="${enrollStudent.firstVol}">
                            <input id="firstFacultycode" type="hidden" class="form-control" name="firstFacultycode"
                                   value="${enrollStudent.firstFacultycode}">
                            <input id="firstFacultyname" type="hidden" class="form-control" name="firstFacultyname"
                                   value="${enrollStudent.firstFacultyname}">
                        </div>
                        <div class="form-group">
                            <label for="firstTrdrname" class="active">培养方向</label>
                            <input id="firstTrdrname" type="text" class="form-control" name="firstTrdrname"
                                   value="${enrollStudent.firstTrdrname}" readonly="readonly"></div>
                        <div class="form-group">
                            <label for="firstSchsys" class="active">学制</label>
                            <input id="firstSchsys" type="text" class="form-control" name="firstSchsys"
                                   value="${enrollStudent.firstSchsys}" readonly="readonly">
                            <input id="firstSchsys1" type="hidden" class="form-control" name="firstSchsys1">
                        </div>
                    </div>
                </div>
                <div class="w">
                    <div class="form-inline">
                        <p>第二志愿</p>

                        <div class="form-group">
                            <label class="sl2">
                                <span>专业</span>
                                <select id="secondVolcode" name="secondVolcode" onchange="setsecond();">
                                    <option value="0">请选择专业</option>
                                    <c:forEach var="specialtyList" items="${specialtyList}">
                                        <option value="${specialtyList.specialtyCode}"
                                                <c:if test="${specialtyList.specialtyCode eq enrollStudent.secondVolcode}">selected="selected"</c:if>>${specialtyList.specialtyName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <input id="secondVol" type="hidden" class="form-control" name="secondVol"  value="${enrollStudent.secondVol}">
                            <input id="secondFacultycode" type="hidden" class="form-control base-info"
                                   name="secondFacultycode" value="${enrollStudent.secondFacultycode}">
                            <input id="secondFacultname" type="hidden" name="secondFacultname" value="${enrollStudent.secondFacultname}">
                        </div>
                        <div class="form-group">
                            <label for="secondTrdrname" class="active">培养方向</label>
                            <input id="secondTrdrname" type="text" class="form-control base-info" name="secondTrdrname"
                                   value="${enrollStudent.secondTrdrname}" readonly="readonly"></div>
                        <div class="form-group">
                            <label for="secondSchsys" class="active">学制</label>
                            <input id="secondSchsys" type="text" class="form-control base-info" name="secondSchsys"
                                   value="${enrollStudent.secondSchsys}" readonly="readonly">
                            <input id="secondSchsys1" type="hidden" name="secondSchsys1">
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <div class="lap-section">
            <p class="title">成绩信息</p>

            <div class="lap-form">
                <div class="form-inline">
                    <div class="form-group">
                        <label for="score">成绩总分数</label>
                        <input id="score" type="text" class="form-control base-info" name="score"
                               value="${enrollStudent.score}">
                    </div>
                </div>
            </div>
        </div>
</div>
</form>
<c:if test="${param.readonly ne 1}">
    <div class="btns-group">
        <a href="javascript:;" class="btn submit" onclick="updateSubmit();">提交</a>
        <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
    </div>
</c:if>
</div>
<script>
    var readonly = '${param.readonly}';
    if (readonly == 1) {
        $('input').addClass('form-readonly').attr('readonly', true);
        $('select').prop('disabled', true);
    } else {
        $.date({
            elem: ['#birthday', '#gradTime'],
            type: 'date'
        });
    }

    var schoolcode = '${relationCode}';

    $('select').select2({
        width: 140
    });
    $('#base-info select').select2({
        width: 108
    });

    $('#nation,#politics,#domicileType,#studentType,#firstVolcode,#secondVolcode').select2({
        width: 216
    });

    var ppareaCode2 =${ppareaCode2};
    var ppareaCode =${ppareaCode};
    var ppareaCode1 =${ppareaCode1};
    $(function () {
        //选择城市(户口所在地)
        $('#ppareaCode').change(function () {
            ppareaCode = $(this).val();
            initAreaCode();
        });
        //选择城市(生源地)
        $('#ppareaCode1').change(function () {
            ppareaCode1 = $(this).val();
            initAreaCode1();
        });
        //选择城市(籍贯)
        $('#ppareaCode2').change(function () {
            ppareaCode2 = $(this).val();
            initAreaCode2();
        });
        initSelect2();
    });
    function initSelect2() {
        //户口所在地
        initAreaCode(${pareaCode});
        //生源地
        initAreaCode1(${pareaCode1});
        //籍贯
        initAreaCode2(${pareaCode2});
        var firstSchsys1 = '${enrollStudent.firstSchsys}';
        var secondSchsys1 = '${enrollStudent.secondSchsys}';
        if (firstSchsys1 == '三年') {
            $("#firstSchsys1").val('101');
        } else {
            $("#firstSchsys1").val('102');
        }
        if (secondSchsys1 == '三年') {
            $("#secondSchsys1").val('101');
        } else {
            $("#secondSchsys1").val('102');
        }
        setbatch();
    }
    //根据招生批次加载招生人员
    function setbatch() {
        $('#enrollteacherId').empty();
        var batchId = $("#batchId").val();
        var stu = "${enrollStudent.enrollteacherId}";
        console.log("批次ID：" + stu);
        if (batchId != '') {
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/enrollteacher',
                data: {batchId: batchId, schoolcode: schoolcode},
                success: function (data) {
                    var data = eval('(' + data + ')');
                    for (var index in data) {
                        console.log("老师ID：" + data[index].enrollteacherId);
                        $("#enrollteacherId").append(" <option value=' " + data[index].enrollteacherId + " ' >" + data[index].teacherName + " </option> ");
                        if (stu == data[index].enrollteacherId) {
                            $('#enrollteacherId option').filter(function () {
                                return this.value == data[index].enrollteacherId;
                            }).attr('selected', true);
                        }
                    }
                }
            });
        }
    }
    //选择第一志愿
    function setfirst(){
        var specialtyCode=$("#firstVolcode").val();
        //console.log("数据："+specialtyCode);
        if(specialtyCode!='' &&  specialtyCode!=0){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/specialty',
                data: {specialtyCode:specialtyCode,schoolcode:schoolcode},
                success: function(data){
                    var schoolSystem=data[0].schoolSystem;
                    var specialtyId=data[0].specialtyId;
                    var specialtyName=data[0].specialtyName;
                    var trdrName=data[0].trdrName;
                    var firstFacultycode=data[0].facultyCode;
                    var firstFacultyname=data[0].facultyName;

                    if(specialtyId){
                        $("#firstTrdrname").val(trdrName);
                        $("#firstVol").val(specialtyName);
                        $("#firstFacultycode").val(firstFacultycode);
                        $("#firstFacultyname").val(firstFacultyname);
                        if(schoolSystem==101){
                            $("#firstSchsys").val('3年');
                            $("#firstSchsys1").val('101');
                        }
                        if(schoolSystem==102){
                            $("#firstSchsys").val('5年');
                            $("#firstSchsys1").val('102');
                        }
                    }else{
                        $("#firstTrdrname").val("");
                        $("#firstVol").val("");
                        $("#firstSchsys").val("");
                        $("#firstFacultycode").val("");
                        $("#firstFacultyname").val("");
                        $("#firstSchsys1").val('');
                    }
                    setlqzy();
                }
            });
        }else{
            $("#firstTrdrname").val("");
            $("#firstVol").val("");
            $("#firstSchsys").val("");
            $("#firstFacultycode").val("");
            $("#firstFacultyname").val("");
            $("#firstSchsys1").val('');
            setlqzy();
        }
    }

    //选择第二志愿
    function setsecond(){
        var specialtyCode=$("#secondVolcode").val();
        //console.log("数据："+specialtyCode);
        if(specialtyCode!=''&&  specialtyCode!=0){
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/specialty',
                data: {specialtyCode:specialtyCode,schoolcode:schoolcode},
                success: function(data){
                    var schoolSystem=data[0].schoolSystem;
                    var specialtyId=data[0].specialtyId;
                    var specialtyName=data[0].specialtyName;
                    var trdrName=data[0].trdrName;
                    var secondFacultycode=data[0].facultyCode;
                    var secondFacultname=data[0].facultyName;
                    if(specialtyId){
                        $("#secondTrdrname").val(trdrName);
                        $("#secondVol").val(specialtyName);
                        $("#secondFacultycode").val(secondFacultycode);
                        $("#secondFacultname").val(secondFacultname);
                        if(schoolSystem==101){
                            $("#secondSchsys").val('3年');
                            $("#secondSchsys1").val('101');
                        }
                        if(schoolSystem==102){
                            $("#secondSchsys").val('5年');
                            $("#secondSchsys1").val('102');
                        }
                    }else{
                        $("#secondTrdrname").val("");
                        $("#secondVol").val("");
                        $("#secondSchsys").val("");
                        $("#secondFacultycode").val("");
                        $("#secondFacultname").val("");
                        $("#secondSchsys1").val("");
                    }
                    setlqzy();
                }
            });
        }else{
            $("#secondTrdrname").val("");
            $("#secondVol").val("");
            $("#secondSchsys").val("");
            $("#secondFacultycode").val("");
            $("#secondFacultname").val("");
            $("#secondSchsys1").val("");
            setlqzy();
        }
    }

    //录取志愿
    function setlqzy(){
        var enrollVol=$("#enrollVol").val();
        var firstVolcode=$("#firstVolcode").val();
        var secondVolcode=$("#secondVolcode").val();
        //console.log("录取志愿ww："+enrollVol);
        if(enrollVol==1){
            var specialtyCode=$("#firstVolcode").val();
            var specialtyName=$("#firstVolcode").find("option:selected").text();
            var trdrName=$("#firstTrdrname").val();
            var schoolSystem=$("#firstSchsys1").val();
            var facultyCode=$("#firstFacultycode").val();
            var facultyName=$("#firstFacultyname").val();
            if(firstVolcode!=0){
                $("#specialtyCode").val(specialtyCode);
                $("#specialtyName").val(specialtyName);
                $("#trdrName").val(trdrName);
                $("#schoolSystem").val(schoolSystem);
                $("#facultyCode").val(facultyCode);
                $("#facultyName").val(facultyName);
            }else{
                $("#specialtyCode").val("");
                $("#specialtyName").val("");
                $("#trdrName").val("");
                $("#schoolSystem").val("");
                $("#facultyCode").val("");
                $("#facultyName").val("");
            }
        }
        if(enrollVol==2){
            var specialtyCode=$("#secondVolcode").val();
            var specialtyName=$("#secondVolcode").find("option:selected").text();
            var trdrName=$("#secondTrdrname").val();
            var schoolSystem=$("#secondSchsys1").val();
            var facultyCode=$("#secondFacultycode").val();
            var facultyName=$("#secondFacultname").val();
            if(secondVolcode!=0){
                $("#specialtyCode").val(specialtyCode);
                $("#specialtyName").val(specialtyName);
                $("#trdrName").val(trdrName);
                $("#schoolSystem").val(schoolSystem);
                $("#facultyCode").val(facultyCode);
                $("#facultyName").val(facultyName);
            }else{
                $("#specialtyCode").val("");
                $("#specialtyName").val("");
                $("#trdrName").val("");
                $("#schoolSystem").val("");
                $("#facultyCode").val("");
                $("#facultyName").val("");
            }
        }
    }
    var areaType = 2;
    var schoolcode = $("#schoolCode").val();
    //户口所在地
    function initAreaCode(val) {
        console.log("省份编码：" + val);
        if (ppareaCode != 0) {
            $.getJSON('${basePath}/manage/recruit/areaList', {
                areaCode: ppareaCode,
                areaType: areaType,
                limit: 10000
            }, function (json) {
                console.log(json);
                var datas = [{id: 0, text: '城市'}];
                for (var i = 0; i < json.rows.length; i++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode').empty();
                $('#pareaCode').select2({
                    data: datas,
                    width: 108
                });
                if (val != null) {
                    $('#pareaCode').select2({width: '108px',}).val(val).trigger('change');
                }
            });
        } else {
            $('#pareaCode').empty();
            $('#pareaCode').select2({
                data: [{id: 0, text: '城市'}]
            });
        }
    }
    //生源地
    function initAreaCode1(val) {
        //console.log("省份编码："+ppareaCode);
        if (ppareaCode1 != 0) {
            $.getJSON('${basePath}/manage/recruit/areaList', {
                areaCode: ppareaCode1,
                areaType: areaType,
                limit: 10000
            }, function (json) {
                var datas = [{id: 0, text: '城市'}];
                for (var i = 0; i < json.rows.length; i++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode1').empty();
                $('#pareaCode1').select2({
                    width: '108px',
                    data: datas
                });
                if (val != null) {
                    console.log("专业编码：" + val);
                    $('#pareaCode1').select2({width: '108px',}).val(val).trigger('change');
                }
            });
        } else {
            $('#pareaCode1').empty();
            $('#pareaCode1').select2({
                data: [{id: 0, text: '城市'}]
            });
        }
    }
    //籍贯
    function initAreaCode2(val) {
        //console.log("省份编码："+ppareaCode);
        if (ppareaCode2 != 0) {
            $.getJSON('${basePath}/manage/recruit/areaList', {
                areaCode: ppareaCode2,
                areaType: areaType,
                limit: 10000
            }, function (json) {
                var datas = [{id: 0, text: '城市'}];
                for (var i = 0; i < json.rows.length; i++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode2').empty();
                $('#pareaCode2').select2({
                    width: '108px',
                    data: datas
                });
                if (val != null) {
                    //console.log("专业编码："+val);
                    $('#pareaCode2').select2({width: '108px',}).val(val).trigger('change');
                }
            });
        } else {
            $('#pareaCode2').empty();
            $('#pareaCode2').select2({
                data: [{id: 0, text: '城市'}]
            });
        }
    }

    function updateSubmit() {
        var batchId=$("#batchId").val();
        var ppareaCode=$("#ppareaCode").val();
        var pareaCode=$("#pareaCode").val();
        var domicile=ppareaCode+","+pareaCode;
        var ppareaCode1=$("#ppareaCode1").val();
        var pareaCode1=$("#pareaCode1").val();
        var batchYear=$("#batchYear").val();
        var nation=$("#nation").val();
        var politics=$("#politics").val();
        var phone=$("#phone").val();
        var address=$("#address").val();
        var gradSchool=$("#gradSchool").val();
        var studentType=$("#studentType").val();
        var gradTime=$("#gradTime").val();
        var firstVolcode=$("#firstVolcode").val();


        var domicile = ppareaCode + "," + pareaCode;
        var ppareaCode1 = $("#ppareaCode1").val();
        var pareaCode1 = $("#pareaCode1").val();
        var fromplace = ppareaCode1 + "," + pareaCode1;
        var ppareaCode2 = $("#ppareaCode2").val();
        var pareaCode2 = $("#pareaCode2").val();
        var origin = ppareaCode2 + "," + pareaCode2;
        var teacherName = $("#enrollteacherId").find("option:selected").text();
        var batchName = $("#batchId").find("option:selected").text();
        $("#domicile").val(domicile);
        $("#fromplace").val(fromplace);
        $("#origin").val(origin);
        $("#teacherName").val(teacherName);
        $("#batchName").val(batchName);
        //console.log("户籍所在地："+domicile+";生源地："+fromplace+";籍贯："+origin);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/enroll/update/${enrollStudent.studentId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#batchYear').val() == '') {
                    $('#batchYear').focus().tips({msg: '招生年度不能为空'});
                    return false;
                }
                if ($('#batchId').val() == '') {
                    $('#batchId').focus().tips({msg: '请选择招生批次'});
                    return false;
                }
                if ($('#studentName').val() == '') {
                    $('#studentName').focus().tips({msg: '用户名不能为空'});
                    return false;
                }

                if ($('#nation').val() == '') {
                    $('#nation').focus().tips({msg: '请选择民族'});
                    return false;
                }

                if ($('#ppareaCode2').val() == 0 || $('#pareaCode2').val() == 0) {
                    $('#ppareaCode2').focus().tips({msg: '请选择籍贯'});
                    return false;
                }

                if ($.trim($('#birthday').val()) == '') {
                    $('#birthday').focus().tips({msg: '请填写出生日期'});
                    return false;
                }

                if ($.trim($('#politics').val()) == '') {
                    $('#politics').focus().tips({msg: '请选择政治面貌'});
                    return false;
                }

                if ($.trim($('#idcard').val()) == '') {
                    $('#idcard').focus().tips({msg: '请填写身份证号码'});
                    return false;
                }

                if ($('#ppareaCode').val() == 0 || $('#pareaCode').val() == 0) {
                    $('#ppareaCode').focus().tips({msg: '请选择户口所在地'});
                    return false;
                }

                if ($('#ppareaCode1').val() == 0 || $('#pareaCode1').val() == 0) {
                    $('#ppareaCode1').focus().tips({msg: '请选择生源地'});
                    return false;
                }
                if ($.trim($('#phone').val()) == '') {
                    $('#phone').focus().tips({msg: '请填写手机号码'});
                    return false;
                }
                if ($.trim($('#address').val()) == '') {
                    $('#address').focus().tips({msg: '请填写详细通讯地址'});
                    return false;
                }
                if ($.trim($('#gradSchool').val()) == '') {
                    $('#gradSchool').focus().tips({msg: '请填写毕业学校'});
                    return false;
                }
                if ($.trim($('#studentType').val()) == '') {
                    $('#studentType').focus().tips({msg: '请选择毕业类别'});
                    return false;
                }
                if ($.trim($('#studentType').val()) == '') {
                    $('#studentType').focus().tips({msg: '请选择毕业类别'});
                    return false;
                }
                if ($.trim($('#gradTime').val()) == '') {
                    $('#gradTime').focus().tips({msg: '请选择毕业时间'});
                    return false;
                }
                if ($('#firstVolcode').val() == 0) {
                    $('#firstVolcode').focus().tips({msg: '请选择专业'});
                    return false;
                }
            },
            success: function (result) {
                console.log(result);
                if (result.code != 1) {
                }
                else {
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
