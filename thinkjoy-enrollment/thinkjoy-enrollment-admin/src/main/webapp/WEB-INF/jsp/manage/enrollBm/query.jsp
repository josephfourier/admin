<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>${ucenterSchool.schoolName}学校录取查询</title>
    <link rel="stylesheet" href="${basePath}/resources/mobile/lib/frozenui/css/frozen.css?t=<%=new Date().getTime()%>">
    <link rel="stylesheet" href="${basePath}/resources/mobile/css/query.css?t=<%=new Date().getTime()%>">

    <script src="${basePath}/resources/mobile/lib/jquery.js"></script>

    <script src="${basePath}/resources/mobile/js/dialog.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/mobile/lib/layer/layer.js?t=<%=new Date().getTime()%>"></script>
    <script src="${basePath}/resources/plugins/jquery.validate.js?t=<%=new Date().getTime()%>"></script>
</head>

<body ontouchstart>
<header class="ui-header-positive" id="header">
    <div class="img-container">
        <img src="${basePath}/resources/mobile/imgs/query-header.png" alt="">
    </div>
</header>
<section class="ui-container">
    <form name="selectResult" action="${basePath}/enrollBm/bm/queryResult" method="get" id="query-form">
        <input type="hidden" id="schoolCode" name="schoolCode" value="${ucenterSchool.schoolCode}">
        <div class="id-container">
            <input type="text" id="idcard"  name="idcard" placeholder="请输入身份证号码">
            <div class="tip" id="tip" style="display: none;">
                <i></i><span>身份证号码不存在</span>
            </div>
        </div>
        <button class="query">立即查看</button>
    </form>
</section>
<script>

    var sid = ${ucenterSchool.schoolCode};
    $('#idcard').focus(function() {
        if ($('#tip').is(':visible')) $('#tip').hide();
    });

    $('#query-form').validate({
        onkeyup: false,
        onfocusout: false,
        rules: {
            idcard: "required",
        },
        messages: {
            idcard:{
                required: '请输入身份证号码',
            }
        },
        submitHandler: function () {
            $.ajax({
                url: '${basePath}/enrollBm/bm/checkIdcard',
                type: 'post',
                data: {schoolCode: sid, idcard: function() {
                    return $('#idcard').val();
                }},
                success: function(result) {
                    if (result == 'false') {
                        $("#tip").show();
                    } else {
                        $('#query-form')[0].submit();
                    }
                }
            });
        }
    });
   /* $('.query').click(function(e) {
       var idcard = $('#idcard').val();

        if ($.trim(idcard) == '') {
            alert(1)
            $('#idcard').focusin();
            return false;
        }
        e.preventDefault();
        $.ajax({
            url: '${basePath}/enrollBm/bm/checkID',
            type: 'post',
            data: {schoolCode: sid, idcard: function() {
                return $('#idcard').val();
            }},
            success: function(result) {
                if (result == 'false') {
                    $("#tip").show();
                } else {
                   $('#query-form')[0].submit();
                }
            }
        });
    })*/

</script>
</body>
</html>