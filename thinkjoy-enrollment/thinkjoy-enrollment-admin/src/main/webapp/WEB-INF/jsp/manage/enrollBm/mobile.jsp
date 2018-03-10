<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>学生报名平台</title>
    <link rel="stylesheet" href="${basePath}/resources/mobile/lib/frozenui/css/frozen.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/citypicker.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/otherpicker.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/nationpicker.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/animate.min.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/lib/calendar/calendar.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/index.css?t=<%=new Date().getTime()%>">

    <script src="${basePath}/resources/mobile/lib/jquery.js"></script>

    <script src="${basePath}/resources/mobile/js/pickcity.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/js/pickother.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/js/picknation.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/lib/calendar/calendar.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/js/dialog.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/lib/layer/layer.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/plugins/jquery.validate.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/lap/js/validate.js?t=<%=new Date().getTime()%>"></script>

</head>

<body ontouchstart>
<div id="success" class="dialog-tip success">
    <div class="result">报名成功</div>
    <button class="dialog-close"></button>
</div>
<div id="fail" class="dialog-tip fail">
    <div class="result">报名失败，请重试</div>
    <button class="dialog-close"></button>
</div>
<header class="ui-header-positive" id="header">
</header>

<section class="ui-container">
    <section id="form">
        <form id="createForm">
            <div class="form-item">
                <div class="form-block">
                    <div class="title">个人信息</div>
                    <div class="ui-form">
                        <div class="ui-form-item ui-form-item-pure">
                            <span>姓名</span>
                            <input type="text" name="studentName" id="studentName">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>性别</span>
                            <div class="sex-radio">
                                <div class="radio checked" data-sex="0">
                                    <i></i>
                                    <span>男</span>
                                </div>
                                <div class="radio no" data-sex="1">
                                    <i></i>
                                    <span>女</span>
                                </div>
                                <input type="hidden" name="sex" value="0">
                            </div>
                        </div>
                        <div class="ui-form-item ui-form-item-pure ui-form-item-link">
                            <span>出生年月</span>
                            <div id="birthday"></div>
                            <input type="text" class="hidden" id="birthday-hidden" name="birthday">
                            <input type="hidden" name="isPoor" value="0">
                            <input type="hidden" name="isLiveschool" value="0">
                        </div>
                        <div class="ui-form-item ui-form-item-pure ui-form-item-link">
                            <span>民族</span>
                            <div id="nation"></div>
                            <input type="text" name="nation" id="nation-hidden" class="hidden">
                        </div>

                        <div class="ui-form-item ui-form-item-pure">
                            <span>身份证号</span>
                            <input type="text" name="idcard">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>手机号码</span>
                            <input type="text" name="phone">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>毕业学校</span>
                            <input type="text" name="gradSchool">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>准考证号</span>
                            <input type="text" name="examnum">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>考生号</span>
                            <input type="text" name="examineeNumber">
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-item">
                <div class="form-block">
                    <div class="title">家庭信息</div>
                    <div class="ui-form">
                        <div class="ui-form-item ui-form-item-pure">
                            <span>与本人关系</span>
                            <input type="text" name="relation">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>家长姓名</span>
                            <input type="text" name="name">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span>家长电话</span>
                            <input type="text" name="relationtel">
                        </div>
                        <div class="ui-form-item ui-form-item-pure ui-form-item-link">
                            <span>家庭地区</span>
                            <div id="hometown">(通知书邮递地址)</div>
                            <input type="text" name="origin" id="origin" class="hidden">
                        </div>
                        <div class="ui-form-item ui-form-item-pure">
                            <span class="no">&nbsp;</span>
                            <input type="text" placeholder="详细住址" class="address" name="address">
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-item">
                <div class="form-block">
                    <div class="title">报考专业</div>
                    <div class="ui-form no">
                        <div class="ui-form-item ui-form-item-pure ui-form-item-link">
                            <span>第一志愿</span>
                            <div id="firstVol" class="picker"></div>
                            <input type="text" name="firstVol" class="hidden">
                        </div>
                        <div class="ui-form-item ui-form-item-pure ui-form-item-link">
                            <span>第二志愿</span>
                            <div id="secondVol" class="picker"></div>
                            <input type="text" name="secondVol" class="hidden">
                        </div>
                    </div>
                </div>
            </div>

            <div class="btn-group">
                <input type="submit" value="立即提交" class="btn btn-submit">
            </div>

            <input type="hidden" name="schoolCode" value="${schoolCode}"/>

            <input type="hidden" name="firstFacultycode">
            <input type="hidden" name="firstFacultyname">
            <input type="hidden" name="firstVolcode">
            <input type="hidden" name="firstTrdrname">

            <input type="hidden" name="firstSchsys">
            <input type="hidden" name="firstSchsys1">

            <input type="hidden" name="enrollVol">
            <input type="hidden" name="specialtyCode">
            <input type="hidden" name="specialtyName">
            <input type="hidden" name="trdrName">
            <input type="hidden" name="schoolSystem">
            <input type="hidden" name="facultyCode">
            <input type="hidden" name="facultyName">


            <input type="hidden" name="secondFacultycode">
            <input type="hidden" name="secondFacultname">
            <input type="hidden" name="secondVolcode">
            <input type="hidden" name="secondTrdrname">
            <input type="hidden" name="secondSchsys">
            <input type="hidden" name="secondSchsys1">
        </form>
    </section>
    <div class="hiddenArea">
        <select id="pro">
            <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                <option value="${ucenterAreas.areaCode}_${ucenterAreas.areaType}">${ucenterAreas.areaName}</option>
            </c:forEach>
        </select>

        <select id="firstVolcode" name="firstVolcode">
            <c:forEach var="specialtyList" items="${specialtyList}">
                <option value="${specialtyList.specialtyCode}">${specialtyList.specialtyName}</option>
            </c:forEach>
        </select>

        <select id="secondVolcode" name="secondVolcode">
            <c:forEach var="specialtyList" items="${specialtyList}">
                <option value="${specialtyList.specialtyCode}">${specialtyList.specialtyName}</option>
            </c:forEach>
        </select>
    </div>
</section>
<script>

    var schoolCode = ${schoolCode};
    var backUrl = '${backUrl}' || null;
    console.log(backUrl);

    var calendar = new LCalendar();
    calendar.init({
        'trigger': '#birthday',
        'hidden': '#birthday-hidden',
        'type': 'date', //date datetime  time ym ,
        'minDate': '1900-1-1',
        'maxDate': new Date().getFullYear() + 10 + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()
    });


    $('.radio').click(function () {
        $('.radio').removeClass('checked');
        $(this).addClass('checked');
        var sex = $(this).data('sex');

        $('input[name=sex]').val(sex);
    });


    var provinces = {};
    var specialties = {};
    var options = $('#pro>option');

    $.each(options, function () {
        provinces[this.value] = this.innerHTML;
    });

    options = $('#firstVolcode>option');
    $.each(options, function () {
        specialties[this.value] = this.innerHTML;
    });

    $('#nation').nationPicker({}, function (hidden) {
        $('#nation-hidden').val(hidden.nationCode);
    });

    $('#hometown').cityPicker({
        provinces: provinces,
        remote: {city: '${basePath}/enrollBm/bm/areaList'}
    }, function (hidden) {
        $('#origin').val(hidden.provinceCode + ',' + hidden.cityCode);
    });

    $('#firstVol').otherPicker({
        specialties: specialties,
        schoolCode: schoolCode,
        remote: {specialty: '${basePath}/enrollBm/bm/specialty'}
    }, function (expose) {
        if (expose[0] !== null) {
            var hidden = {};
            $.extend(true, hidden, {
                firstVol: expose[0].specialtyName,
                firstFacultycode: expose[0].facultyCode,
                firstFacultyname: expose[0].facultyName,
                firstTrdrname: expose[0].trdrName,
                firstSchsys: expose[0].schoolSystem == '101' ? '3年' : '5年',
                firstSchsys1: expose[0].schoolSystem,
                firstVolcode: expose[0].specialtyCode,

                enrollVol: 1,
                specialtyCode: expose[0].specialtyCode,
                specialtyName: expose[0].specialtyName,
                trdrName: expose[0].trdrName,
                schoolSystem: expose[0].schoolSystem,
                facultyCode: expose[0].facultyCode,
                facultyName: expose[0].facultyName
            });

            for (key in hidden) {
                $('input[name="' + key + '"]').val(hidden[key]);
            }
        }
    });

    $('#secondVol').otherPicker({
        specialties: specialties,
        schoolCode: schoolCode,
        remote: {specialty: '${basePath}/enrollBm/bm/specialty'}
    }, function (expose) {
        if (expose[0] !== null) {
            var hidden = {};

            $.extend(true, hidden, {
                secondVol: expose[0].specialtyName,
                secondFacultycode: expose[0].facultyCode,
                secondFacultname: expose[0].facultyName,
                secondVolcode: expose[0].specialtyCode,
                secondTrdrname: expose[0].trdrName,
                secondSchsys: expose[0].schoolSystem == '101' ? '3年' : '5年',
                secondSchsys1: expose[0].schoolSystem
            });

            for (key in hidden) {
                $('input[name="' + key + '"]').val(hidden[key]);
            }
        }
    });

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/enrollBm/bm/create',
            data: $('#createForm').serialize(),
            success: function (result) {
                if (result.code == 1) {
                    $.dialog({
                        content: $('#success')
                    }, function () {
                        if (backUrl != null)
                            location.href = backUrl;
                        else
                            window.location.reload();
                    });
                } else {
                    $.dialog({
                        content: $('#fail')
                    }, function () {
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


    $('#createForm').validate({
        onkeyup: false,
        onfocusout: false,
        rules: {
            studentName: 'required',
            birthday: 'required',
            nation: 'required',
            relation: 'required',
            name: 'required',
            idcard: {
                required: true,
                idcard: true,
                remote: {
                    url: '${basePath}/enrollBm/bm/checkIdcard',
                    type: 'post',
                    data: {
                        schoolCode: schoolCode,
                        idcard: function () { return $('input[name=idcard]').val(); },
                        reverse: 1
                    }
                }
            },
            phone: {
                required: true,
                phone: true,
            },
            gradSchool: 'required',
            examnum: 'required',
            examineeNumber: 'required',
            relationtel: 'required',
            address: 'required',
            firstVol: 'required',
            secondVol: 'required',
            origin: 'required'
        },
        messages: {
            studentName: '请填写姓名',
            birthday: '请选择出生年月',
            nation: '请选择民族',
            relation: '请填写与本人关系',
            name: '请填写家长姓名',
            idcard: {
                required: '请填写身份证号码',
                idcard: '请输入有效的身份证号码',
                remote: '身份证已存在'
            },
            phone: {
                required: '请填写手机号码',
                phone:  $.validator.format('请输入有效的手机号码')
            },
            gradSchool: '请填写毕业学校',
            examnum: '请填写准考证号',
            examineeNumber: '请填写考生号',
            relationtel: '请填写家长电话',
            address: '请填写详细地址',
            firstVol: '请选择第一志愿',
            secondVol: '请选择第二志愿',
            origin: '请选择家庭地区'

        },
        submitHandler: function () {
            createSubmit();
        }
    });
</script>
</body>
</html>