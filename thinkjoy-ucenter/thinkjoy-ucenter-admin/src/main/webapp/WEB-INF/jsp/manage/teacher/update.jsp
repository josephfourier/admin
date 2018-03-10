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
    .form-group.sl>label {
        position: relative;
        margin-right: 5px;
    }
</style>
<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <div class="col-sm-12">
            <div class="form-group">
                <label for="teacherName">老师姓名</label>
                <input id="teacherName" type="text" class="form-control" name="teacherName"  maxlength="64"
                       value="${ucenterTeacher.teacherName}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="phone">手机号</label>
                <input id="phone" type="text" class="form-control" name="phone"
                       value="${ucenterTeacher.phone}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="idcard">身份证号</label>
                <input id="idcard" type="text" class="form-control" name="idcard"
                       value="${ucenterTeacher.idcard}">
            </div>
        </div>
        <div class="form-group sl">
            <label for="sex">性别</label>
            <select id="sex" name="sex">
                <option value="">请选性别</option>
                <c:set var="SEX" value="<%=BaseConstants.Dict.SEX %>"/>
                <c:forEach items="${dict:getValueByCode(SEX)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterTeacher.sex}">selected</c:if>>${at.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group sl">
             <label for="nation">民族 </label>
            <select  id="nation" name="nation">
                <option value="">请选民族</option>
                <c:set var="NATION" value="<%=BaseConstants.Dict.NATION %>"/>
                <c:forEach items="${dict:getValueByCode(NATION)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterTeacher.nation}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="teacherCode">教工号</label>
                <input id="teacherCode" type="text" class="form-control" name="teacherCode"  maxlength="64"
                       value="${ucenterTeacher.teacherCode}">
            </div>
        </div>
        <div class="col-sm-12" id="depTree">
            <div class="form-group">
                <div class="fg-line">
                    <input type="text" id="departmentName" name="departmentName" class="form-control"
                           value="${ucenterTeacher.departmentName}"
                           onclick="initDepTree()" placeholder="部门选择" readonly="readonly">
                    <input type="hidden" id="departmentId" name="departmentId" value="${ucenterTeacher.departmentId}">
                    <input type="hidden" id="deps" name="deps">
                    <div id="ztreeDemo" class="ztree" style="display: block;width:auto;height: 150px; overflow: auto"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="worktime">参加工作时间</label>
                <input id="worktime" type="text" class="form-control" name="worktime"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
                       value="<fmt:formatDate value="${ucenterTeacher.worktime}" type="both" pattern="yyyy-MM-dd"/>">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="birthday">出生日期</label>
                <input id="birthday" type="text" class="form-control" name="birthday"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
                       value="<fmt:formatDate value="${ucenterTeacher.birthday}" type="both" pattern="yyyy-MM-dd"/>">
            </div>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="mail">邮箱</label>
                <input id="mail" type="text" class="form-control" name="mail"  maxlength="128" value="${ucenterTeacher.mail}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="origin">籍贯</label>
                <input id="origin" type="text" class="form-control" name="origin"  maxlength="64" value="${ucenterTeacher.origin}">
            </div>
        </div>

        <div class="form-group sl">
            <label for="education">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历</label>
            <select class="form-control" id="education" name="education">
                <option value="">请选学历</option>
                <c:set var="EDUCATION" value="<%=BaseConstants.Dict.EDUCATION %>"/>
                <c:forEach items="${dict:getValueByCode(EDUCATION)}" var="at">
                    <option value="${at.valueKey}"
                            <c:if test="${at.valueKey eq ucenterTeacher.education}">selected</c:if> >${at.valueName}</option>
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
                            <c:if test="${at.valueKey eq ucenterTeacher.politics}">selected</c:if> >${at.valueName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-sm-12">
            <div class="form-group">
                <label for="description">描述</label>
                <input id="description" type="text" class="form-control" name="description"   maxlength="256"
                       value="${ucenterTeacher.description}">
            </div>
        </div>
        <div class="col-sm-12">
            <div class="radio">
                <div class="radio radio-inline radio-success">
                    <input id="status_1" type="radio" name="status" value="1"  <c:if test="${ucenterTeacher.status==1}">checked</c:if>>
                    <label for="status_1">正常 </label>
                </div>
                <div class="radio radio-inline">
                    <input id="status_0" type="radio" name="status" value="0"  <c:if test="${ucenterTeacher.status==0}">checked</c:if>>
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
    var flag = 0;
    var relationCode = '${ucenterTeacher.schoolCode}';
    var specialtyCodes = "${specialtyCodes}";
    var classIds = "${classIds}";
    var year=new Date().getFullYear();

//    $(function () {
//        initClass(classIds);
//    });

    //部门树begin
    var setting = {
        view: {
            dblClickExpand: true
        },
        check: {
            chkboxType:{ "Y" : "", "N" : ""},
            enable: true
        },
        async: {
            enable: true,
            url: '${basePath}/manage/department/getDepTreeByTeacherId?schoolCode=${ucenterTeacher.schoolCode}&teacherId=${ucenterTeacher.teacherId}'
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
        }
    };
    function hideMenu() {
        $('#ztreeDemo').hide();
    }

    function beforeClick(treeId, treeNode) {
        hideMenu()
        return true;

    }
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztreeDemo"),
                nodes = zTree.getSelectedNodes(),
                v = "";
        var vt = "";
        nodes.sort(function compare(a, b) {
            return a.id - b.id;
        });
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name;
            vt += nodes[i].id;
        }
        var departmentName = $("#departmentName");
        var departmentId = $("#departmentId");
        departmentName.attr("value", v);
        departmentId.attr("value", vt);

    }

    $(document).ready(function () {
        $.fn.zTree.init($('#ztreeDemo'), setting);
    });


    var depIds="";
    function findcheckedZtree(){
        depIds="";
        var treeObj=$.fn.zTree.getZTreeObj("ztreeDemo"),
                nodes=treeObj.getCheckedNodes(true),
                v="";
        for(var i=0;i<nodes.length;i++){
            depIds+=nodes[i].id+",";
        }
    }

    function initDepTree() {
        $('#ztreeDemo').show();
        setDisabledNode();
    }
    //删除锁定节点和其子节点
    function setDisabledNode(){
        var treeObj = $.fn.zTree.getZTreeObj("ztreeDemo");
        var disabledNodes = treeObj.getNodesByParam("status", 0);
        console.log(disabledNodes)

        for (var i=0; i<disabledNodes.length; i++) {
            treeObj.removeChildNodes(disabledNodes[i]);//删除锁定节点的子节点
            treeObj.removeNode(disabledNodes[i])//删除锁定节点
        }
    }
    //部门树end


    var flag = 0;

    function initSelect2() {
        flag = 1;
        initSpecilty(specialtyCodes);
    }




    function checkData(){
        if ($('#teacherName').val() == '') {
            $('#teacherName').focus();
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
        if ($('#teacherCode').val() == '') {
            $('#teacherCode').focus();
            return false;
        }

        findcheckedZtree();
        if(depIds==""){
            console.log("部门是空的!");
            $('#departmentName').focus();
            return false;
        }else {
            $("#deps").val(depIds);
        }
        return true;
    }

    function updateSubmit() {
        if(checkData()){
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/teacher/update/${ucenterTeacher.teacherId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
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
    }
</script>