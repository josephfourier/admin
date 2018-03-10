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
            <label for="theme"></label>
            <input id="theme" type="color" class="form-control" name="theme" maxlength="50">
        </div>
        <div class="form-group">
            <label for="icon">图标</label>
            <input id="icon" type="text" class="form-control" name="icon" maxlength="40">
        </div>
        <div class="form-group">
            <label for="title">标题</label>
            <input id="title" type="text" class="form-control" name="title" maxlength="60" required="true">
        </div>
        <div class="form-group">
            <label for="name">名称</label>
            <input id="name" type="text" class="form-control" name="name" maxlength="60" required="true">
        </div>
        <div class="bg">
            <div class="form-group">
                <label>背景图</label>
                <img src="" alt="背景图" class="path">
                <input type="text" class="form-control" name="banner" readonly>
                <input type="text" class="tmp form-control" readonly>
            </div>
            <div id="picker">上传背景图</div>
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <input id="description" type="text" class="form-control" name="description" maxlength="300">
        </div>
        <div class="form-group">
            <label for="basepath">根目录</label>
            <input id="basepath" type="text" class="form-control" name="basepath" maxlength="100">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="status_1" type="radio" name="status" value="1" checked>
                <label for="status_1">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="status_0" type="radio" name="status" value="-1">
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
    function initUploader() {
        //百度上传按钮
        var uploader = WebUploader.create({
            server: '/upload/imageUpload',
            swf: '${basePath}/resources/thinkjoy-admin/plugins/webuploader-0.1.5/Uploader.swf',
            method: 'POST',
            pick: {
                "id": '#picker',
                "multiple": false
            },
            resize: false,
            auto: true,
            accept: {
                title: '图片文件',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/bmp,image/gif,image/jpg,image/jpeg,image/png'
            }
        });
        uploader.on( 'fileQueued', function(file) {});

        uploader.on( 'uploadBeforeSend', function(obj, data, headers) {});

        uploader.on( 'uploadSuccess', function(file, response) {
            var src = response._raw;
            $('.bg label').hide();
            $('img.path').attr('src', src).show();
            $('input[name=banner]').val(src).hide();
            $('input.tmp').show();
        });

        uploader.on( 'uploadError', function(file) {});
    }

    // 根据路径获取后缀
    function get_suffix(filename) {
        var pos = filename.lastIndexOf('.');
        var suffix = '';
        if (pos != -1) {
            suffix = filename.substring(pos);
        }
        return suffix;
    }
    // 随机字符串
    function random_string(len) {
        len = len || 32;
        var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var maxPos = chars.length;
        var pwd = '';
        for (i = 0; i < len; i++) {
            pwd += chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd;
    }
    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/system/create',
            data: $('#createForm').serialize(),
            beforeSend: function() {
                if ($('#title').val() == '') {
                    $('#title').focus();
                    return false;
                }
                if ($('#name').val() == '') {
                    $('#name').focus();
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