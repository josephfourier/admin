<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>


<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="form-group">
			<span class="type1 type2 type3">
				<select id="ppareaCode" name="ppareaCode">
                    <option value="0">请选择省</option>
                    <c:forEach var="ucenterAreas" items="${ucenterAreas}">
                        <option value="${ucenterAreas.areaCode}">${ucenterAreas.areaName}</option>
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

        <div class="form-group">
            <label for="schoolName">学校名称</label>
            <input id="schoolName" type="text" class="form-control" name="schoolName" maxlength="128" onblur="isExitSchoolName()">
            <input id="agencyCode" type="hidden" class="form-control" name="agencyCode" value="${agencyCode}">
            <input id="areaLevel" type="hidden" class="form-control" name="areaLevel" value="${areaLevel}">
        </div>
        <div class="form-group">
            <label for="schoolEnname">英文名称</label>
            <input id="schoolEnname" type="text" class="form-control" name="schoolEnname" maxlength="128"
                   onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
        </div>
        <div class="form-group">
            <label for="address">地址</label>
            <input id="address" type="text" class="form-control" name="address" maxlength="256">
        </div>
        <div class="form-group">
            <label for="birthday">建校日期</label>
            <input id="birthday" type="text"  name="birthday" class="form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})">
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <input id="description" type="text" class="form-control" name="description" maxlength="256">
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1" checked>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="0">
                <label for="status_0">锁定 </label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    var  schoolNameEd =false;
    function isExitSchoolName(){
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/school/isExitSchoollNameInSchoolTable?schoolName='+$("#schoolName").val(),
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

    var ppagencyCode = 0;
    $(function() {
        // 选择分类
//        $('input:radio[name="areaType"]').change(function() {
//            areaType = $(this).val();
//            initType();
//        });
        // 选择上级机构
        $('#ppagencyCode').change(function() {
            ppagencyCode = $(this).val();
            initPid();
        });

        //选择行政区划-市级
        $('#ppareaCode').change(function() {
            ppareaCode = $(this).val();
            initAreaCode();
        });

        //选择行政区划-区/县级
        $('#pareaCode').change(function() {
            pareaCode = $(this).val();
            initAreaCodeOne();
        });
        /*
        //學校拼音
        $("#schoolName").on("keyup keydown change blur",function (){
            var pycode = $(this).toPinyin();
            $("#schoolEnname").val(pycode);
        });*/

    });

    function initPid() {
        if (ppagencyCode != 0) {
            $.getJSON('${basePath}/manage/agency/list', {pagencyCode: ppagencyCode, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择上级机构'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].agencyCode;
                    data.text = json.rows[i].agencyName;
                    datas.push(data);
                }
                $('#pagencyCode').empty();
                $('#pagencyCode').select2({
                    data : datas
                });
            });
        } else {
            $('#pagencyCode').empty();
            $('#pagencyCode').select2({
                data : [{id: 0, text: '请选择上级机构'}]
            });
        }
    }

    function initAreaCode() {
        if (ppareaCode != 0) {
            $.getJSON('${basePath}/manage/area/list', {areaCode: ppareaCode, limit: 10000}, function(json) {
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
    function initAreaCodeOne() {
        //console.log("市级编码："+pareaCode);
        if (pareaCode != 0) {
            $.getJSON('${basePath}/manage/area/list', {areaCode: pareaCode, limit: 10000}, function(json) {
                var datas = [{id: 0, text: '请选择区/县'}];
                for (var i = 0; i < json.rows.length; i ++) {
                    var data = {};
                    data.id = json.rows[i].areaCode;
                    data.text = json.rows[i].areaName;
                    //console.log("县级编码："+data.id+";县名："+data.text);
                    datas.push(data);
                }
                $('#areaCode').empty();
                $('#areaCode').select2({
                    data : datas
                });
            });
        } else {
            $('#areaCode').empty();
            $('#areaCode').select2({
                data : [{id: 0, text: '请选择区/县'}]
            });
        }
    }

    function createSubmit() {
        var schoolCode;
        var  agencyCode=$("#agencyCode").val();
        var areaLevel;
        var userType=${userType};
        if(userType=='3'){
            schoolCode=${schoolCode};
        }else{
            areaLevel=$("#areaLevel").val();
        }
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/school/create',
            data: $('#createForm').serialize(),
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
                        content: '请选择区/县级！',
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
                    $.confirm({
                        theme: 'dark',
                        animation: 'rotateX',
                        closeAnimation: 'rotateX',
                        title: false,
                        content:result.message ,
                        buttons: {
                            confirm: {
                                text: '确认',
                                btnClass: 'waves-effect waves-button waves-light'
                            }
                        }
                    });
                } else {
                    createDialog.close();
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