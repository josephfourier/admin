<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <div class="form-group">
            <label for="username">帐号</label>
            <input id="username" type="text" class="form-control" name="username" maxlength="20"
                   value="${user.username}" readonly>
        </div>
        <div class="form-group">
            <label for="realname">姓名</label>
            <input id="realname" type="text" class="form-control" name="realname" maxlength="20"
                   value="${user.realname}">
        </div>

        <div class="bg">
            <div class="form-group">
                <img src="${user.avatar}" alt="头像" class="path show">
                <input type="text" class="form-control" name="avatar" readonly value="${user.avatar}">
                <input type="text" class="tmp form-control" readonly>
            </div>
            <div id="picker">上传头像</div>
        </div>
        <div class="form-group">
            <label for="phone">电话</label>
            <input id="phone" type="text" class="form-control" name="phone" maxlength="20" value="${user.phone}">
        </div>
        <div class="form-group">
            <label for="email">邮箱</label>
            <input id="email" type="text" class="form-control" name="email" maxlength="50" value="${user.email}">
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="managerType_1" type="radio" name="managerType" value="1" <c:if test="${user.managerType==1}">checked</c:if>>
                <label for="managerType_1">超级管理员 </label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="managerType_2" type="radio" name="managerType" value="2" <c:if test="${user.managerType==2}">checked</c:if>>
                <label for="managerType_2">区域管理员</label>
            </div>
            <div class="radio radio-inline radio-success">
                <input id="managerType_3" type="radio" name="managerType" value="3" <c:if test="${user.managerType==3}">checked</c:if>>
                <label for="managerType_3">学校管理员</label>
            </div>
        </div>

        <div class="form-group" id="orgZtree">
            <div class="form-group">
                <div class="fg-line">
                    <input type="text" id="relationName" name="relationName" class="form-control" readonly="readonly" placeholder="请选择学校/机构" value="${user.relationName}">
                    <input type="hidden" id="relationCode" name="relationCode" value="${user.relationCode}">
                    <ul id="ztree" class="ztree" style="width:auto;height: auto"></ul>
                </div>
            </div>
        </div>

        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="locked_0" type="radio" name="locked" value="0" <c:if test="${user.locked==0}">checked</c:if>>
                <label for="locked_0">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="locked_1" type="radio" name="locked" value="1" <c:if test="${user.locked==1}">checked</c:if>>
                <label for="locked_1">锁定 </label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>


    $(document).ready(function(){
        var  val= $('input[name="managerType"]:checked').val();
        if(val==1){
            $("#orgZtree").hide();
            $('#relationName').attr("value","全国");
            $('#relationCode').attr("value","0");
        }
    })


    $('input[name=managerType]').change(function(){
        var  val= $('input[name="managerType"]:checked').val();
        if(val==1){
            $("#orgZtree").hide();
            $('#relationName').attr("value","全国");
            $('#relationCode').attr("value","0");
        }else{
            $("#orgZtree").show();
            $('#relationName').attr("value","");
            $('#relationCode').attr("value","");

            var zTree = $.fn.zTree.getZTreeObj("ztree");
            var node = zTree.getSelectedNodes();
            zTree.checkNode(node);
        }

    });

    var changeDatas = [];
    var setting = {
        check: {
            enable: true,
            // 勾选关联父，取消关联子
            chkboxType: {"Y": "", "N": "s"},

            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
        },
        async: {
            enable: true,
            url: '${basePath}/manage/user/agencytree/' + userId
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function(){

                var zTree = $.fn.zTree.getZTreeObj("ztree");

                var node = zTree.getCheckedNodes();

                var managerType = $('input[name=managerType]:checked').val();

                if(node.length>0) {
                    var ty = node[0].type;
                    if (ty == 1) {
                        if (managerType == 3) {
                            $.confirm({
                                theme: 'dark',
                                animation: 'rotateX',
                                closeAnimation: 'rotateX',
                                title: false,
                                content: "学校管理员只能选择学校",
                                buttons: {
                                    confirm: {
                                        text: '确认',
                                        btnClass: 'waves-effect waves-button waves-light'
                                    }
                                }
                            });

                            zTree.checkNode(node);
                            return false;
                        }
                    } else {
                        if (managerType != 3) {
                            $.confirm({
                                theme: 'dark',
                                animation: 'rotateX',
                                closeAnimation: 'rotateX',
                                title: false,
                                content: "非学校管理员只能选择机构",
                                buttons: {
                                    confirm: {
                                        text: '确认',
                                        btnClass: 'waves-effect waves-button waves-light'
                                    }
                                }
                            });

                            zTree.checkNode(node);
                            return false;
                        }
                    }

                    var rn = node[0].name;
                    var rc = node[0].code;

                    var rno = $('#relationName');

                    var rco = $('#relationCode');

                    rno.attr("value", rn);

                    rco.attr("value", rc);
                }
            }
        }
    };

    function initTree() {
        $.fn.zTree.init($('#ztree'), setting);
    }


    function initUploader() {
        //百度上传按钮
        var uploader = WebUploader.create({

            server: '/upload/imageUpload',
            // swf文件路径
            swf: '${basePath}/resources/thinkjoy-admin/plugins/webuploader-0.1.5/Uploader.swf',
            // 文件接收服务端
            method: 'POST',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                "id": '#picker',
                "multiple": false
            },
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            // 选完文件后，是否自动上传。
            auto: true,
            // 只允许选择图片文件
            accept: {
                title: '图片文件',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/bmp,image/gif,image/jpg,image/jpeg,image/png'
            }
        });
        uploader.on('fileQueued', function (file) {
          // 加入队列
        });
        uploader.on('uploadSuccess', function (file, response) {
            var src = response._raw;
            $('.bg label').hide();
            $('img.path').attr('src', src).show();
            $('input[name=avatar]').val(src).hide();
            $('input.tmp').show();
        });
        uploader.on('uploadError', function (file) {
           // console.log('uploadError', file);
        });
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
            url: '${basePath}/manage/user/update/${user.userId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#username').val() == '') {
                    $('#username').focus();
                    return false;
                }
                if ($('#relationName').val() == '' || $('#relationCode').val() == '') {
                    $.confirm({
                        title: false,
                        content: '请选择学校/机构！',
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

                if ($('#phone').val() != '') {
                    var reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/ ;
                    if (!reg.test($("#phone").val())) {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: '请输入有效的电话!',
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        $('#email').focus();
                        return false;
                    }
                }

                if ($('#email').val() != '') {
                    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                    if (!reg.test($("#email").val())) {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: '请输入有效的邮箱!',
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                        $('#email').focus();
                        return false;
                    }
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