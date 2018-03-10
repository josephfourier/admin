<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<style>
    #birthday.Wdate{
        height: auto;
    }
    .Wdate.form-control[disabled],
    .Wdate.form-control[readonly]{
        background: none;
    }
</style>
<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <c:if test="${userType==1}">
            <div class="form-group">
                <%--<span class="type1 type2 type3">行政区划</span>--%>
                <span class="type1 type2 type3">
                    <select id="ppareaCode" name="ppareaCode">
                        <option value="0">请选择省</option>
                        <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                            <option value="${ucenterAreas.areaCode}" <c:if test="${ppareaCode==ucenterAreas.areaCode}">selected="selected"</c:if>>${ucenterAreas.areaName}</option>
                        </c:forEach>
                    </select>
                </span>
                <span class="type2 type3">
                    <select id="pareaCode" name="pareaCode">
                        <option value="0">请选择市</option>
                    </select>
                </span>
                <span class="type3">
                    <select id="areaCode" name="areaCode">
                        <option value="0">请选择区/县</option>
                    </select>
                </span>
            </div>
        </c:if>
        <div class="form-group">
            <label for="schoolCode">学校编码</label>
            <input id="schoolCode" type="text" class="form-control" name="schoolCode" readonly="readonly" value="${ucenterSchool.schoolCode}">
        </div>
        <div class="form-group">
            <label for="schoolName">学校名称</label>
            <input id="schoolName" type="text" class="form-control" name="schoolName" maxlength="128" value="${ucenterSchool.schoolName}" onblur="isExitSchoolName()">
            <input id="agencyCode" type="hidden" class="form-control" name="agencyCode" value="${ucenterSchool.agencyCode}">
            <input id="areaLevel" type="hidden" class="form-control" name="areaLevel" value="${ucenterSchool.areaLevel}">
            <input id="areaType" type="hidden" class="form-control" name="areaType" value="${ucenterSchool.areaType}">
        </div>
        <div class="form-group">
            <label for="schoolEnname">英文名称</label>
            <input id="schoolEnname" type="text" class="form-control" name="schoolEnname" maxlength="128" value="${ucenterSchool.schoolEnname}"
                   onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
        </div>
        <div class="form-group">
            <label for="address">地址</label>
            <input id="address" type="text" class="form-control" name="address" maxlength="256" value="${ucenterSchool.address}">
        </div>
        <div class="form-group">
            <label for="birthday">建校日期</label>
            <input id="birthday" type="text"  name="birthday" class="Wdate form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" value="<fmt:formatDate value="${ucenterSchool.birthday}" type="both" pattern="yyyy-MM-dd"/>">
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <input id="description" type="text" class="form-control" name="description" maxlength="256" value="${ucenterSchool.description}">
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterSchool.status==1}">checked</c:if>>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterSchool.status==0}">checked</c:if>>
                <label for="status_0">锁定 </label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    var  schoolNameEd =false;
    function isExitSchoolName(){
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/school/isExitSchoollNameInSchoolTable?sid='+'${ucenterSchool.schoolId}'+'&schoolName='+$("#schoolName").val(),
            success: function(result) {
                if (result==true) {
                    schoolNameEd=true;
                    $.confirm({
                        theme: 'dark',
                        animation: 'rotateX',
                        closeAnimation: 'rotateX',
                        title: false,
                        content:'学校名称不能重复!' ,
                        buttons: {
                            confirm: {
                                text: '确认',
                                btnClass: 'waves-effect waves-button waves-light'
                            }
                        }
                    });
                    $("#schoolName").val("");
                    $("#schoolName").focus;
                }else{
                    schoolNameEd =false;
                }
            }
        });
    }
    var areaType = ${ucenterSchool.areaType};
    var ppagencyCode = ${ppagencyCode};
    var ppareaCode=${ppareaCode};
    var pareaCode=${pareaCode};
    $(function() {
        var init = 1;
//        // 选择分类
//        $('input:radio[name="areaType"]').change(function() {
//            areaType = $(this).val();
//            initType();
//        });

        //选择行政区划-市级
        $('#ppareaCode').change(function() {
            ppareaCode = $(this).val();
            initAreaCode();
        });

        //选择行政区划-区/县级
        $('#pareaCode').change(function() {
            pareaCode = $(this).val();
            if (init == 1) {
                initAreaCodeOne(${areaCode});
                init++;
            }else{
                initAreaCodeOne();
            }

        });
    });
    function initType() {
        // 显示对应必填项
//        $('.type1,.type2,.type3').hide(0, function () {
//            //$('.type' + areaType).show();
//        });
    }

    //市級信息
    function initAreaCode(val) {
        if (ppareaCode != 0) {
            $.getJSON('${basePath}/manage/area/list', {areaCode: ppareaCode,status:1, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择市'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#pareaCode').empty();
                $('#pareaCode').select2({
                    data : datas
                });
                $('#areaCode').empty();
                $('#areaCode').select2({
                    data : [{id: 0, text: '请选择区/县'}]
                });
                if (!!val) {
                    $('#pareaCode').select2().val(val).trigger('change');
                }
            });
        } else {
            $('#pareaCode').empty();
            $('#pareaCode').select2({
                data : [{id: 0, text: '请选择市'}]
            });

            $('#areaCode').empty();
            $('#areaCode').select2({
                data : [{id: 0, text: '请选择区/县'}]
            });
        }
    }
    //縣級信息
    function initAreaCodeOne(val) {
        if (pareaCode != 0) {
            $.getJSON('${basePath}/manage/area/list', {areaCode: pareaCode,status:1, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择区/县'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    datas.push(data);
                }
                $('#areaCode').empty();
                $('#areaCode').select2({
                    data : datas
                });

                if (!!val) {
                    $('#areaCode').select2().val(val).trigger('change');
                }
            });
        } else {
            $('#areaCode').empty();
            $('#areaCode').select2({
                data : [{id: 0, text: '请选择区/县'}]
            });
        }
    }
    function initSelect2() {
        //alert("111");
        initAreaCode(${pareaCode});
    }
    function updateSubmit() {
        var schoolCode;
        var agencyCode;
        var areaLevel;
        var userType=${userType};
        if(userType=='3'){
            schoolCode=${ucenterSchool.schoolCode};
        }else{
            agencyCode=${ucenterSchool.agencyCode};
            areaLevel=${ucenterSchool.areaLevel};
        }
        //console.log("区域编码："+areaCode+"区域级别："+areaLevel);
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/school/update/${ucenterSchool.schoolId}',
            data: $('#updateForm').serialize(),
            beforeSend: function() {
                if ($('#schoolName').val() == '') {
                    $('#schoolName').focus();
                    return false;
                }
                if ($('#ppareaCode').val() == 0) {
                    $.confirm({
                        title: false,
                        content: '请选择省份！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if ($('#pareaCode').val() == 0) {
                    $.confirm({
                        title: false,
                        content: '请选择市！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if ($('#areaCode').val() == 0) {
                    $.confirm({
                        title: false,
                        content: '请选择区/县！',
                        autoClose: 'cancel|3000',
                        backgroundDismiss: true,
                        buttons: {
                            cancel: {
                                text: '取消',
                                btnClass: 'waves-effect waves-button'
                            }
                        }
                    });
                    return false;
                }
                if(schoolNameEd){
                    $.confirm({
                        theme: 'dark',
                        animation: 'rotateX',
                        closeAnimation: 'rotateX',
                        title: false,
                        content:'学校名称不能重复!' ,
                        buttons: {
                            confirm: {
                                text: '确认',
                                btnClass: 'waves-effect waves-button waves-light'
                            }
                        }
                    });
                    return false;
                }
            },
            success: function(result) {
                if (result.code != 1) {
                    if (result.data instanceof Array) {
                        $.each(result.data, function(index, value) {
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