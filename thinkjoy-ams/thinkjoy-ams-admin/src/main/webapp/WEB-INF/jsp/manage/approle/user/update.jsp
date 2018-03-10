<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>


<style>


</style>

<div id="updateDialog" class="crudDialog">
	<form id="updateForm" method="post">
		<input type="hidden" id="uid" name ="uid" value="${ucenterUser.uid}">
		<div class="row">

			<div class="checkbox">
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_1" type="checkbox" name="userType" value="1" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 1}">checked </c:if></c:forEach> >
					<label for="usertype_1">学生 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_2" type="checkbox" name="userType" value="2" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 2}">checked </c:if></c:forEach> >
					<label for="usertype_2">家长 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_3" type="checkbox" name="userType" value="3"  <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 3}">checked </c:if></c:forEach> >
					<label for="usertype_3">老师 </label>
				</div>
				<div class="checkbox checkbox-inline checkbox-success">
					<input id="usertype_4" type="checkbox" name="userType" value="4" <c:forEach items="${list}" var="pa" ><c:if test="${pa.usertypeId == 4}">checked </c:if></c:forEach>>
					<label for="usertype_4">组织机构 </label>
				</div>
			</div>

			<div class="col-sm-12"  id="schoolTag" style="display:none">
				<div class="form-group">
					<div class="fg-line">
						<input type="text" id="schoolName" name="schoolName" class="form-control" value="${schoolName}"  onclick="initTreeTwo()";  placeholder="学校选择">
						<input type="hidden" id="schoolCode" name="schoolCode" value="${schoolcode}">
						<div id="ztreeTwo"  class="ztree" style="display: none;width:auto;height: auto"></div>
					</div>
				</div>
			</div>

			<div class="col-sm-12" id="orgTag" style="display:none">
				<div class="form-group">
					<div class="fg-line" >
						<input type="text" id="orgName" name="orgName" class="form-control" value="${agencyName}"  onclick="initTree()";  placeholder="组织选择">
						<input type="hidden" id="orgCode" name="orgCode" value="${agencycode}">
						<div id="ztree"  class="ztree" style="display: none;width:auto;height: auto"></div>
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="username">用户账号</label>
						<input id="username" type="text" class="form-control" name="username" maxlength="300" value="${ucenterUser.username}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="fullname">用户全名</label>
						<input id="fullname" type="text" class="form-control" name="fullname" maxlength="20" value="${ucenterUser.fullname}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="nickname">昵称</label>
						<input id="nickname" type="text" class="form-control" name="nickname" maxlength="20" value="${ucenterUser.nickname}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="idcard">身份证号</label>
						<input id="idcard" type="text" class="form-control" name="idcard" maxlength="20" value="${ucenterUser.idcard}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="phone">手机号</label>
						<input id="phone" type="text" class="form-control" name="phone" maxlength="20" value="${ucenterUser.phone}">
					</div>
				</div>
			</div>

			<div class="col-sm-12">
				<div class="form-group">
					<span>性别</span>
					<select class="form-control" id="sex" name="sex">
						<option value="">请选择</option>
						<option value="0" <c:if test="${ucenterUser.sex==0}">selected</c:if>>男</option>
						<option value="1" <c:if test="${ucenterUser.sex==1}">selected</c:if>>女</option>
					</select>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="email">邮箱</label>
						<input id="email" type="text" class="form-control" name="email" maxlength="20" value="${ucenterUser.email}">
					</div>
				</div>
			</div>
			<div class="col-sm-12" id="studentCode1" style="display:none">
				<div class="form-group">
					<div class="fg-line">
						<label for="studentCode">学号</label>
						<input id="studentCode" type="text" class="form-control" name="studentCode" maxlength="20" value="${ucenterUser.studentCode}">
					</div>
				</div>
			</div>
			<div class="col-sm-12" id="examineeNumber2" style="display:none">
				<div class="form-group">
					<div class="fg-line">
						<label for="examineeNumber">考生号</label>
						<input id="examineeNumber" type="text" class="form-control" name="examineeNumber" maxlength="20" value="${ucenterUser.examineeNumber}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<label for="description">描述</label>
						<input id="description" type="text" class="form-control" name="description" maxlength="256" value="${ucenterUser.description}">
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="radio">
					<div class="radio radio-inline radio-success">
						<input id="status_1" type="radio" name="status" value="1" <c:if test="${ucenterUser.status==1}">checked</c:if>>
						<label for="status_1">正常 </label>
					</div>
					<div class="radio radio-inline">
						<input id="status_0" type="radio" name="status" value="0" <c:if test="${ucenterUser.status==0}">checked</c:if>>
						<label for="status_0">锁定 </label>
					</div>
				</div>
			</div>


			<div class="col-sm-12" id="orgZtree" style="display: none">
				<div class="form-group">
					<div class="fg-line">
						<label for="examineeNumber1">考试号1</label>
						<input id="examineeNumber1" type="text" class="form-control" name="examineeNumber1" maxlength="20">
					</div>
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

	$(document).ready(function(){
		$.fn.zTree.init($('#ztree'), setting);
		$.fn.zTree.init($('#ztreeTwo'), settingTwo);
		checkboxOnClick();
		initinputcheckbox();
	});

	function initinputcheckbox(){

		var userytype1=$("#usertype_1").is(':checked');
		var userytype2=$("#usertype_2").is(':checked');
		var userytype3=$("#usertype_3").is(':checked');
		var userytype4=$("#usertype_4").is(':checked');
		if (userytype1==true||userytype1==true||userytype1==true){
			$("#schoolTag").css("display","block");
		}
		if (userytype1==false||userytype1==false||userytype1==false){
			$("#schoolTag").css("display","none");
		}

		if(userytype4==true){
			$("#orgTag").css("display","block");
		}

		if(userytype4==false){
			$("#orgTag").css("display","none");
		}
		//学生考试号，学号展示
		if(userytype1==true){
			$("#studentCode1").css("display","block");
			$("#examineeNumber2").css("display","block");
		}else{
			$("#studentCode1").css("display","none");
			$("#examineeNumber2").css("display","none");
		}

	}
	function updateSubmit() {
		$.ajax({
			type: 'post',
			url: '${basePath}/manage/user/update/${ucenterUser.userId}',
			data: $('#updateForm').serialize(),
			beforeSend: function() {
				if($('#usertype_1').is(':checked') == true||$('#usertype_2').is(':checked') == true||$('#usertype_3').is(':checked') == true){
					if($('#schoolCode').val()==''){
						$('#schoolCode').focus().tips({msg: '请选择学校！'});
						return false;
					}
				}
				if($('#usertype_4').is(':checked') == true){
					if($('#orgCode').val()=='') {
						$('#orgCode').focus().tips({msg: '请选择组织机构！'});
						return false;
					}
				}
				if ($('#username').val() == '') {
					$('#username').focus();
					return false;
				}
				if ($('#fullname').val() == '') {
					$('#fullname').focus();
					return false;
				}
				if($('#usertype_3').is(':checked') == true){
					if ($('#phone').val() == '') {
						$('#phone').focus();
						return false;
					}
				}

				if ($('#sex').val() == '') {
					$.confirm({
						title: false,
						content: '请选择性别！',
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
				if($('#usertype_1').is(':checked') == true){
					if ($('#studentCode').val() == '') {
						$('#studentCode').focus();
						return false;
					}
					if ($('#examineeNumber').val() == '') {
						$('#examineeNumber').focus();
						return false;
					}
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







	function checkboxOnClick() {

		$("input[type=checkbox]").click(function() {

			var userytype1=$("#usertype_1").is(':checked');
			var userytype2=$("#usertype_2").is(':checked');
			var userytype3=$("#usertype_3").is(':checked');
			var userytype4=$("#usertype_4").is(':checked');

			if((userytype1==true||userytype2==true||userytype3==true)&&userytype4==false){
				$("#orgTag").css("display","none");
				$("#schoolTag").css("display","block");
			}

			if((userytype1==true||userytype2==true||userytype3==true)&&userytype4==true){
				$("#orgTag").css("display","block");
				$("#schoolTag").css("display","block");
			}

			if((userytype1==true&&userytype2==true&&userytype3==true)&&userytype4==true){
				$("#schoolTag").css("display","block");
				$("#orgTag").css("display","block");
			}

			if((userytype1==false&&userytype2==false&&userytype3==false)&&userytype4==false){
				$("#schoolTag").css("display","none");
				$("#orgTag").css("display","none");
			}
			if((userytype1==false&&userytype2==false&&userytype3==false)&&userytype4==true){
				$("#schoolTag").css("display","none");
				$("#orgTag").css("display","block");
			}

			//学生考试号，学号展示
			if(userytype1==true){
				$("#studentCode1").css("display","block");
				$("#examineeNumber2").css("display","block");
			}else{
				$("#studentCode1").css("display","none");
				$("#examineeNumber2").css("display","none");
			}

		});
	}

	//ztree配置


	var setting = {
		view: {
			dblClickExpand: true
		},
		async: {
			enable: true,
			url: '${basePath}/manage/user/getTree/1'
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		}
	};

	function initTree() {
		$('#ztree').show();
	}





	function onBodyDown(event) {
		if (!(event.target.id == "orgName")) {
			hideMenu();
		}
	}

	function beforeClick(treeId, treeNode) {
		hideMenu()
		return true;

	}


	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("ztree"),
				nodes = zTree.getSelectedNodes(),
				v = "";
		var vt="";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			vt += nodes[i].id + ",";
		}


		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vt.length > 0 ) vt = vt.substring(0, vt.length-1);
		var orgObj = $("#orgName");
		var orgCode= $("#orgCode")
		orgObj.attr("value", v);
		orgCode.attr("value", vt);
	}

	function hideMenu() {
		$("#ztree").fadeOut("fast");
	}



	//ztreeTwo配置


	var settingTwo = {
		view: {
			dblClickExpand: true
		},
		async: {
			enable: true,
			url: '${basePath}/manage/user/getschoolTree/1'
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClickTwo,
			onClick: onClickTwo
		}
	};

	function initTreeTwo() {
		$('#ztreeTwo').show();
	}





	function onBodyDownTwo(event) {
		if (!(event.target.id == "schoolName")) {
			hideMenuTwo();
		}
	}

	function beforeClickTwo(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check){
			alert("请选择叶子节点");
		}else{
			hideMenuTwo();
		}
		return check;

	}


	function onClickTwo(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("ztreeTwo"),
				nodes = zTree.getSelectedNodes(),
				v = "";
		var vt="";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			vt += nodes[i].code + ",";
		}


		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vt.length > 0 ) vt = vt.substring(0, vt.length-1);
		var schoolObj = $("#schoolName");
		var schoolCode = $("#schoolCode");
		schoolObj.attr("value", v);
		schoolCode.attr("value", vt);
	}

	function hideMenuTwo() {
		$("#ztreeTwo").fadeOut("fast");
	}

</script>