<%@ include file="/resources/inc/topS.jsp" %>
<%@ include file="/resources/inc/topJ.jsp" %>

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
    <form id="createForm" method="post">
    <div class="lap-searchbar">
        <label class="input">
            <span class="star">*</span><span>招生年度</span>
            <input type="text" name="batchYear" id="batchYear" class="form-control">
        </label>
        <label class="sl2">
            <span>招生批次</span>
            <select id="batchId" name="batchId">
                <c:forEach var="batchList" items="${batchList}">
                    <option value="${batchList.batchId}">${batchList.batchName}</option>
                </c:forEach>
            </select>
            <input id="batchName" type="hidden" name="batchName">
        </label>
        <label class="sl2">
            <span>招生人员</span>
            <select id="enrollteacherId" name="enrollteacherId">
            </select>
            <input id="teacherName" type="hidden" name="teacherName">
        </label>
    </div>

    <div id="base-info">

            <div class="lap-section">
                <p class="title">基本信息</p>
                <div class="lap-form">
                    <div class="form-inline">
                        <div class="form-group">
                            <label><span class="star"><i class="tip">*</i>姓名</span></label>
                            <input id="studentName" type="text" class="form-control base-input" name="studentName">
                            <input id="schoolCode" type="hidden" class="form-control" name="schoolCode" value="${schoolcode}">
                            <input id="status" type="hidden" class="form-control" name="status" value="1">
                        </div>

                        <div class="form-group">
                            <div class="sl2-group">
                                <label class="sl2" id="sl2_1">
                                    <span class="star"><i class="tip">*</i>籍贯</span>
                                    <select id="ppareaCode2" name="ppareaCode2">
                                        <option value="0">省份</option>
                                        <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                            <option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label class="sl2" id="sl2_2">
                                    <select id="pareaCode2" name="pareaCode2">
                                        <option value="0">城市</option>
                                    </select>
                                </label>
                            </div>
                            <input id="origin" type="hidden" class="form-control" name="origin">
                        </div>

                        <div class="form-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>民族</span>
                                <select id="nation" name="nation">
                                    <c:forEach var="nationDicts" items="${nationDicts}">
                                        <option value="${nationDicts.valueKey}">${nationDicts.valueName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>

                        <div class="form-group">
                            <div class="lap-radio">
                                <label>性别</label>
                                <c:forEach var="sexDicts" items="${sexDicts}">
                                    <input type="radio" name="sex" value="${sexDicts.valueKey}" id="sex-${sexDicts.valueKey}" checked>
                                    <label for="sex-${sexDicts.valueKey}">${sexDicts.valueName}</label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="lap-radio">
                                <label>是否是贫困生</label>
                                <c:forEach var="ispoorDicts" items="${ispoorDicts}">
                                    <input type="radio" name="isPoor" value="${ispoorDicts.valueKey}" id="isPoor-${ispoorDicts.valueKey}" checked>
                                    <label for="isPoor-${ispoorDicts.valueKey}">${ispoorDicts.valueName}</label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="lap-radio">
                                <label>是否住校</label>
                                <c:forEach var="isliveschoolDicts" items="${isliveschoolDicts}">
                                    <input type="radio" name="isLiveschool" value="${isliveschoolDicts.valueKey}" id="isLiveschool-${isliveschoolDicts.valueKey}" checked>
                                    <label for="isLiveschool-${isliveschoolDicts.valueKey}">${isliveschoolDicts.valueName}</label>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="idcard"><span class="star"><i class="tip">*</i>身份证号</span></label>
                            <input id="idcard" type="text" class="form-control base-input" name="idcard">
                        </div>

                        <div class="form-group">
                            <label for="birthday"><span class="star"><i class="tip">*</i>出生日期</span></label>
                            <input id="birthday" type="text" class="form-control input-time" name="birthday">
                        </div>

                        <div class="form-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>政治面貌</span>
                            <select id="politics" name="politics">
                                <c:forEach var="politicsDicts" items="${politicsDicts}">
                                    <option value="${politicsDicts.valueKey}">${politicsDicts.valueName}</option>
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
                                            <option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label class="sl2">
                                    <select id="pareaCode" name="pareaCode">
                                        <option value="0">请选择市</option>
                                    </select>
                                </label>
                            </div>
                            <input id="domicile" type="hidden" class="form-control" name="domicile" >
                        </div>

                        <div class="form-group">
                            <div class="sl2-group">
                                <label class="sl2">
                                    <span class="star"><i class="tip">*</i>生源地</span>
                                    <select id="ppareaCode1" name="ppareaCode1">
                                        <option value="0">请选择省</option>
                                        <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                                            <option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
                                        </c:forEach>
                                    </select>
                                </label>

                                <label class="sl2">
                                    <select id="pareaCode1" name="pareaCode1">
                                        <option value="0">请选择市</option>
                                    </select>
                                    <input id="fromplace" type="hidden" class="form-control" name="fromplace" >
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="sl2">
                                <span>户籍性质</span>
                                <select id="domicileType" name="domicileType">
                                    <c:forEach var="domicileDicts" items="${domicileDicts}">
                                        <option value="${domicileDicts.valueKey}">${domicileDicts.valueName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>



                        <div class="form-group">
                            <label for="phone"><span class="star"><i class="tip">*</i>手机号码</span></label>
                            <input id="phone" type="text" class="form-control base-input" name="phone">
                        </div>
                        <div class="form-group">
                            <label for="familyPhone">固定电话</label>
                            <input id="familyPhone" type="text" class="form-control base-input" name="familyPhone">
                        </div>
                        <div class="form-group">
                            <label for="mail">邮箱</label>
                            <input id="mail" type="text" class="form-control base-input" name="mail">
                        </div>

                        <div class="form-group lg">
                            <label> <span class="star"><i class="tip">*</i>通讯地址</span></label>
                            <input id="address" type="text" class="form-control base-input" name="address">
                        </div>
                        <div class="form-group">
                            <label for="postalCode">邮政编码</label>
                            <input id="postalCode" type="text" class="form-control base-input" name="postalCode">
                        </div>

                        <div class="form-group">
                            <label for="gradSchool"><span class="star"><i class="tip">*</i>毕业学校</span></label>
                            <input id="gradSchool" type="text" class="form-control base-input" name="gradSchool">
                        </div>
                        <div class="form-group">
                            <label class="sl2">
                                <span class="star"><i class="tip">*</i>毕业类别</span>
                                <select id="studentType" name="studentType">
                                    <c:forEach var="targetDicts" items="${targetDicts}">
                                        <option value="${targetDicts.valueKey}">${targetDicts.valueName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="form-group">
                            <label for="gradTime"><span class="star"><i class="tip">*</i>毕业时间</span></label>
                            <input id="gradTime" type="text"class="form-control input-time"  name="gradTime">
                        </div>

                        <div class="form-group">
                            <label for="gradHeadteacher">班主任姓名</label>
                            <input id="gradHeadteacher" type="text" class="form-control base-input" name="gradHeadteacher">
                        </div>
                        <div class="form-group">
                            <label for="examnum">准考证号</label>
                            <input id="examnum" type="text" class="form-control base-input" name="examnum">
                        </div>
                        <div class="form-group">
                            <label for="examineeNumber">考生号</label>
                            <input id="examineeNumber" type="text" class="form-control base-input" name="examineeNumber">
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
                            <input id="specialtyCode" type="hidden" name="specialtyCode">
                            <input id="specialtyName" type="hidden" name="specialtyName">
                            <input id="trdrName" type="hidden" name="trdrName">
                            <input id="schoolSystem" type="hidden" name="schoolSystem">
                            <input id="facultyCode" type="hidden" name="facultyCode">
                            <input id="facultyName" type="hidden" name="facultyName">
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
                                            <option value="${specialtyList.specialtyCode}">${specialtyList.specialtyName}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <input id="firstVol" type="hidden" class="form-control" name="firstVol">
                                <input id="firstFacultycode" type="hidden" class="form-control" name="firstFacultycode">
                                <input id="firstFacultyname" type="hidden" class="form-control" name="firstFacultyname">
                            </div>
                            <div class="form-group">
                                <label for="firstTrdrname" class="active">培养方向</label>
                                <input id="firstTrdrname" type="text" class="form-control base-input" name="firstTrdrname" readonly="readonly">
                            </div>
                            <div class="form-group">
                                <label for="firstSchsys" class="active">学制</label>
                                <input id="firstSchsys" type="text" class="form-control base-input" name="firstSchsys" readonly="readonly">
                                <input id="firstSchsys1" type="hidden" name="firstSchsys1">
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
                                        <option value="${specialtyList.specialtyCode}">${specialtyList.specialtyName}</option>
                                    </c:forEach>
                                </select>
                                </label>
                                <input id="secondVol" type="hidden" class="form-control" name="secondVol">
                                <input id="secondFacultycode" type="hidden" class="form-control base-info" name="secondFacultycode">
                                <input id="secondFacultname" type="hidden"  name="secondFacultname">
                            </div>
                            <div class="form-group">
                                <label for="secondTrdrname" class="active">培养方向</label>
                                <input id="secondTrdrname" type="text" class="form-control base-info" name="secondTrdrname" readonly="readonly">
                            </div>
                            <div class="form-group">
                                <label for="secondSchsys" class="active">学制</label>
                                <input id="secondSchsys" type="text" class="form-control base-info" name="secondSchsys" readonly="readonly">
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
                            <input id="score" type="text" class="form-control base-input" name="score">
                        </div>
                    </div>
                </div>
            </div>
    </div>
    </form>
    <div class="btns-group">
        <a href="javascript:;" class="btn submit" onclick="createSubmit();">提交</a>
        <a href="javascript:;" class="btn cancel" onclick="parent.layer.closeAll();">取消</a>
    </div>
</div>
<script>
    var thisYear = new Date().getFullYear()
            , schoolcode = ${schoolcode}
            , batchId = -1;

    $.date({
        elem: '#batchYear',
        //value: '2015',
        ready: function(date){
            //fillBatchSelect2();
        },
        change: function(value, date, endDate){
            thisYear = date.year;
            fillBatchSelect2(); // 回调根据年度加载批次
        }
    });

    $.date({
        elem: ['#birthday', '#gradTime'],
        type: 'date'
    });


    function fillBatchSelect2() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/enroll/batchyear',
            data: {year:thisYear, schoolcode:schoolcode},
            success: function(data){
                var jdata = JSON.parse(data);

                var datas = [];
                for (var i = 0; i < jdata.length; ++i) {
                    datas.push({id:jdata[i].batchId, text: jdata[i].batchName});
                }

                $('#batchId').empty().select2({
                    width: 140,
                    data: datas
                });
            }
        });
    }

    $('#batchId').change('change', function () {
        fillTeacherSelect2();
    }).trigger('change');

    function fillTeacherSelect2() {
        $('#enrollteacherId').empty();
        batchId = $("#batchId").val();

        if(batchId != -1) {
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/enrollteacher',
                data: {batchId:batchId, schoolcode:schoolcode},
                success: function(data){
                    var data = eval('(' + data + ')');
                    for(var index in data){
                        $("#enrollteacherId").append("<option value='"+ data[index].enrollteacherId+"'>"+data[index].teacherName+"</option>");
                    }
                }
            });
        }
    }


    $('select').select2({ width:140 });

    $('#base-info select').select2({ width:108 });

    $('#nation,#politics,#domicileType,#studentType,#firstVolcode,#secondVolcode').select2({ width: 216 });

    $('#ppareaCode').change(function() {
        ppareaCode = $(this).val();
        initAreaCode();
    });
    //选择城市(生源地)
    $('#ppareaCode1').change(function() {
        ppareaCode1 = $(this).val();
        initAreaCode1();
    });
    //选择城市(籍贯)
    $('#ppareaCode2').change(function() {
        ppareaCode2 = $(this).val();
        initAreaCode2();
    });

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
        if(specialtyCode != '' && specialtyCode!= 0) {
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
    var areaType=2;
    var schoolcode=${schoolcode};
    //户口所在地
    function initAreaCode() {
        //console.log("省份编码："+ppareaCode);
        if (ppareaCode != 0) {
            $.getJSON('${basePath}/manage/enroll/areaList', {areaCode: ppareaCode, areaType: areaType, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择市'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode').empty();
                $('#pareaCode').select2({
                    width: 108,
                    data : datas

                });
            });
        } else {
            $('#pareaCode').empty();
            $('#pareaCode').select2({
                data : [{id: 0, text: '请选择市'}]
            });
        }
    }
    //生源地
    function initAreaCode1() {
        //console.log("省份编码："+ppareaCode);
        if (ppareaCode1 != 0) {
            $.getJSON('${basePath}/manage/enroll/areaList', {areaCode: ppareaCode1, areaType: areaType, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择市'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode1').empty();
                $('#pareaCode1').select2({
                    width: 108,
                    data : datas

                });
            });
        } else {
            $('#pareaCode1').empty();
            $('#pareaCode1').select2({
                data : [{id: 0, text: '请选择市'}]
            });
        }
    }
    //籍贯
    function initAreaCode2() {
        //console.log("省份编码："+ppareaCode);
        if (ppareaCode2 != 0) {
            $.getJSON('${basePath}/manage/enroll/areaList', {areaCode: ppareaCode2, areaType: areaType, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择市'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode2').empty();
                $('#pareaCode2').select2({
                    width: 108,
                    data : datas

                });
            });
        } else {
            $('#pareaCode2').empty();
            $('#pareaCode2').select2({
                data : [{id: 0, text: '请选择市'}]
            });
        }
    }

    function createSubmit() {
        var schoolcode=${schoolcode};
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

        var domicile=ppareaCode+","+pareaCode;
        var ppareaCode1=$("#ppareaCode1").val();
        var pareaCode1=$("#pareaCode1").val();origin
        var fromplace=ppareaCode1+","+pareaCode1;
        var ppareaCode2=$("#ppareaCode2").val();
        var pareaCode2=$("#pareaCode2").val();
        var origin=ppareaCode2+","+pareaCode2;
        var teacherName=$("#enrollteacherId").find("option:selected").text();
        var batchName=$("#batchId").find("option:selected").text();
        $("#domicile").val(domicile);
        $("#fromplace").val(fromplace);
        $("#origin").val(origin);
        $("#teacherName").val(teacherName);
        $("#batchName").val(batchName);
        //console.log("户籍所在地："+domicile+";生源地："+fromplace+";籍贯："+origin);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/enroll/create',
            data: $('#createForm').serialize(),
            beforeSend: function() {
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

                if ($.trim($('#idcard').val()) == '') {
                    $('#idcard').focus().tips({msg: '请填写身份证号码'});
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
            success: function(result) {
                console.log(result);

                if (result.code == 1) {
                    parent.layer.closeAll();
                    window.parent.$parentTable.bootstrapTable('refresh');
                }

            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
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
