<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/resources/inc/topJ.jsp" %>
<%@ include file="/resources/inc/topS.jsp" %>

<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="uploadDialog">
    <div class="modal-header">
        <a href="${basePath}/export/template/${baseModel}">
            <span><b style="color: red; text-decoration: underline;">下载模板</b></span>
        </a>
    </div>
    <div class="modal-body">
        <form id="importFile" name="importFile" class="form-horizontal" method="post" enctype="multipart/form-data">
            <div class="box-body">
                <div>
                    <label class="control-label">请选择要导入的Excel文件：</label>
                    <input id="excelFile" name="excelFile" class="file-loading" type="file" multiple accept=".xls,.xlsx"
                           onclick="resetMessage()"> <br>
                </div>
            </div>
        </form>
    </div>
    <div id="importMessage">
    </div>
    <div id="errorMessage" style="display:none;">
        <a id="errorUrl" href="#">
            <span><b style="color: red; text-decoration: underline;">错误信息下载</b></span>
        </a>
    </div>
</div>

<script>
    var schoolCode = '${schoolCode}';
    initUpload("excelFile", "${basePath}/upload/uploadDatas");
    function initUpload(ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            uploadAsync: true, //默认异步上传
            showCaption: true,//是否显示标题
            showUpload: true, //是否显示上传按钮
            browseClass: "btn btn-primary", //按钮样式
            allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
            maxFileCount: 1,//最大上传文件数限制
            previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
            showPreview: true, //是否显示预览
            previewFileIconSettings: {
                'docx': '<i ass="fa fa-file-word-o text-primary"></i>',
                'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
                'xls': '<i class="fa fa-file-excel-o text-success"></i>',
                'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
                'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
                'pdf': '<i class="fa fa-file-archive-o text-muted"></i>',
                'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            },
            uploadExtraData: function () {
                var extraValue = "excel";
                return {"excelType": extraValue, "schoolCode": schoolCode, "baseModel": '${baseModel}'};
            }
        });
    }
    //fileuploaded   filebatchuploadsuccess
    $("#excelFile").on("fileuploaded", function (event, data, previewId, index) {
        console.log(data);
        $("#errorMessage").hide();

        if (data.response.code == 90001) {

            $.msg(data.files[index].name + ":" + data.response.message);
//            $.confirm({
//                theme: 'dark',
//                animation: 'rotateX',
//                closeAnimation: 'rotateX',
//                title: false,
//                content: data.files[index].name + ":" + data.response.message,
//                buttons: {
//                    confirm: {
//                        text: '确认',
//                        btnClass: 'waves-effect waves-button waves-light'
//                    }
//                }
//            });
        } else if (data.response.code == 90002) {
            $.msg(data.files[index].name + ":" + data.response.message);
//
//            $.confirm({
//                theme: 'dark',
//                animation: 'rotateX',
//                closeAnimation: 'rotateX',
//                title: false,
////        content: data.files[index].name + ":"+data.response.message,
//                content: data.response.message,
//                buttons: {
//                    confirm: {
//                        text: '确认',
//                        btnClass: 'waves-effect waves-button waves-light'
//                    }
//                }
//            });

            $("#errorMessage").show();
            $("#errorUrl").attr('href', data.response.data);

            //重置
            $("#excelFile").fileinput("clear");
            $("#excelFile").fileinput("reset");
//      $('#excelFile').fileinput('refresh');
            $('#excelFile').fileinput('enable');
        } else if (data.response.code == 90003) {
            $.msg(data.response.message);

//            $.confirm({
//                theme: 'dark',
//                animation: 'rotateX',
//                closeAnimation: 'rotateX',
//                title: false,
//                content: data.response.message,
//                buttons: {
//                    confirm: {
//                        text: '确认',
//                        btnClass: 'waves-effect waves-button waves-light'
//                    }
//                }
//            });
            //重置
            $("#excelFile").fileinput("clear");
            $("#excelFile").fileinput("reset");
            $('#excelFile').fileinput('enable');
        } else {
            $.msg('未知异常!');
//            $.confirm({
//                theme: 'dark',
//                animation: 'rotateX',
//                closeAnimation: 'rotateX',
//                title: false,
//                content: "未知异常!",
//                buttons: {
//                    confirm: {
//                        text: '确认',
//                        btnClass: 'waves-effect waves-button waves-light'
//                    }
//                }
//            });

            //重置
            $("#excelFile").fileinput("clear");
            $("#excelFile").fileinput("reset");
            $('#excelFile').fileinput('enable');
        }

        //$parentTable.bootstrapTable('refresh');
    });


    function resetMessage() {
        $("#errorMessage").hide();
        $("#importMessage").html("");
    }
    var my_interval;
    $(document).on('click', '.fileinput-upload-button', function () {
        //轮询  查导入结果信息
        my_interval = setInterval('refreshImportDatas()', 1000);//轮询执行，1000ms一次
    });

    function refreshImportDatas() {
        $.ajax({
            type: 'post',
            url: '${basePath}/upload/refreshImportDatas',
            data: '',
            success: function (result) {
                console.log(result);
                var importMessage = "总行数:" + result.importTotal + "  已处理行数:" + result.importCount + "  成功行数:" + result.importSuccessCount + "  错误数:<b style='color: red;'>" + result.importErrorCount + "</b>";

                if (result.importTotal > 0) {
                    $("#importMessage").html(importMessage);
                }

                if (result.isFinished == true) {
                    clearInterval(my_interval);
                    resetImportDatas();//重置导入进度信息
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log('错误！');
            }
        });
    }

    //重置导入进度信息
    function resetImportDatas() {
        $.ajax({
            type: 'post',
            url: '${basePath}/upload/resetImportDatas',
            data: '',
            success: function (result) {
                console.log('已重置!');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log('错误！');
            }
        });
    }
</script>