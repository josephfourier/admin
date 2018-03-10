<%@ page import="com.thinkjoy.web.common.constant.WebResultConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit"/>
    <link rel="stylesheet" href="${ctx}/resources/home/css/reset.css">
    <link rel="stylesheet" href="${ctx}/resources/home/css/home.css">
    <link rel="stylesheet" href="${ctx}/resources/home/css/mylayer.css">
    <link rel="stylesheet" href="${ctx}/resources/home/css/calendar.css">
    <link rel="stylesheet" href="${ctx}/resources/home/css/lap.css">

    <script type="text/javascript" src="${ctx}/resources/js/jquery-1.9.js"></script>
    <script type="text/javascript" src="${ctx}/resources/home/js/jquery.leoweather.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/home/plugins/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/resources/home/js/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/jquery.validate.min.js"></script>
    <!--[if lt IE 8]>
    <script type="text/javascript" src="${ctx}/resources/js/json.js"></script>
    <![endif]-->
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?5d84a7c755c92c5bcc4445c96138f9b8";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <title>个人中心</title>
</head>
<body>
<div id="header">
    <div class="wrap">
        <div class="logo fl">
            <a href="javascript:;" class="zjy"></a>
            <a href="javascript:;" class="agency">职教云平台</a>
        </div>

        <div class="fr clear">
            <ul class="layui-nav fr">
                <li class="layui-nav-item">
                    <a href="javascript:;" class="lap-nav-user">
                        <i></i>
                        <span class="user">${userIdentity.fullname}</span>
                        <span class="layui-nav-more"></span>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="#">帮助</a></dd>
                        <dd><a href="" id="modify-pwd">修改密码</a></dd>
                        <dd><a href="/sso/logout">退出系统</a></dd>
                    </dl>
                </li>
            </ul>

            <ul class="toolbar fr">
                <li class="role"><c:if test="${isMulti}"><a href="javascript:;"><img
                        src="${ctx}/resources/home/images/head.png" alt=""
                        class="avatar">切换角色</a></c:if></li>
                <%--  <li class="help"><a href="javascript:;">帮助</a></li>
                  <li class="logout"><a href="/sso/logout">退出</a></li>--%>
            </ul>
        </div>
    </div>
</div>

<div class="container">
    <div class="wrap clear">
        <div class="side">
            <div class="side-section">
                <div class="stat">
                    <div class="tab-title">
                        <a href="javascript:" class="title">
                            <c:if test="${not empty userIdentity.schoolName}">${userIdentity.schoolName}</c:if>
                            <c:if test="${not empty userIdentity.agencyName}">${userIdentity.agencyName}</c:if>
                        </a>
                    </div>
                    <div class="main">
                        <div class="item">
                            <div class="n fl">学校人数</div>
                            <div class="d fl">${pv.peopleNum}</div>
                        </div>
                        <div class="item">
                            <div class="n fl">院系数量</div>
                            <div class="d fl">${pv.facultyNum}</div>
                        </div>
                        <div class="item">
                            <div class="n fl">昨天使用人数</div>
                            <div class="d fl">${pv.todayNum}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="side-section">
                <div class="agenda">
                    <div class="tab-title">
                        <a href="javascript:" class="title fl">通知公告</a>
                        <a href="javascript:;" class="more fr">查看&gt;</a>
                    </div>
                    <div class="main">
                        <ul class="clear">
                            <li class="item">
                                <p class="title"><a href="javascript:;" title="关于张帆请假申请">关于张帆请假申请</a></p>
                                <p class="date">2017.08.11</p>
                            </li>
                            <li class="item">
                                <p class="title"><a href="javascript:;" title="关于数字化校园建设合同方案的审批">关于数字化校园建设合同方案的审批</a>
                                </p>
                                <p class="date">2017.08.11</p>
                            </li>
                            <li class="item">
                                <p class="title"><a href="javascript:;" title="行政部外出用车申请">行政部外出用车申请</a></p>
                                <p class="date">2017.08.11</p>
                            </li>
                            <li class="item">
                                <p class="title"><a href="javascript:;"
                                                    title="关于王飞2017年10月1日加班申请">关于王飞2017年10月1日加班申请</a></p>
                                <p class="date">2017.08.11</p>
                            </li>
                            <li class="item">
                                <p class="title"><a href="javascript:;" title="蒋文重庆出差经费报销申请">蒋文重庆出差经费报销申请
                                </a></p>
                                <p class="date">2017.08.11</p>
                            </li>
                            <li class="item">
                                <p class="title"><a href="javascript:;" title="教师测评方案审批">教师测评方案审批
                                </a></p>
                                <p class="date">2017.08.11</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="side-section">
                <div class="side-section">
                    <div class="calend">
                        <div id="calendar" class="calendar"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content">
            <div class="info">
                <div class="user-info fl">
                    <%--   <div class="mod-pwd">
                           <a href="javascript:;" class="fr">修改密码&gt;</a>
                       </div>
                       <div class="user-bar">&lt;%&ndash;<img src="${userIdentity.avatar}" alt=""/>&ndash;%&gt;
                           <div class="avatar fl"><img src="${ctx}/resources/home/images/head_big.png" alt=""></div>
                           <ul class="base-info fl">
                               <li class="u-name">${userIdentity.fullname}</li>
                               <li class="u-phone">${userIdentity.phone}</li>
                               <li class="u-dept">
                                   <c:if test="${not empty userIdentity.schoolName}">${userIdentity.schoolName}</c:if>
                                   <c:if test="${not empty userIdentity.agencyName}">${userIdentity.agencyName}</c:if>
                               </li>
                               <li class="u-job">${userIdentity.usertypeName}</li>
                           </ul>
                       </div>--%>
                </div>
                <%--    <div class="weather-info fr">
                        <ul>
                            <li>
                               <span class="city">
                               </span>
                                <span class="weather">
                               </span>
                            </li>

                            <li class="temperature">
                            </li>

                            <li class="today">
                                <span class="weekday"></span>
                                <span class="now"></span>
                            </li>

                        </ul>

                    </div>--%>
            </div>
            <%--  <div class="tab agenda">
                  <div class="tab-title">
                      <a href="javascript:" class="title fl">我的待办</a>
                      <a href="javascript:;" class="more fr">查看&gt;</a>
                  </div>
              </div>--%>

            <div class="tab app">
                <div class="tab-title">
                    <a href="javascript:" class="title fl active coll-app">
                        <span class="txt">我的收藏</span>
                        <span class="line"></span>
                    </a>
                    <span class="spacer fl"></span>
                    <a href="javascript:" class="title fl all-app">
                        <span class="txt">全部应用</span>
                        <span class="line"></span>
                    </a>
                    <div class="cancel-btn fr">
                        <a href="">取消</a>
                    </div>
                    <div class="edit-btn fr">
                        <a href="">编辑</a>
                    </div>
                </div>
                <div class="main">

                    <div class="collection">

                        <c:forEach items="${cAppClass}" var="cac">
                            <div class="sub-app">
                                <c:set var="DICT_CODE" value="<%=BaseConstants.Dict.APPCLASS%>"/>
                                <p class="sub-title"><c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at"><c:if
                                        test="${at.valueKey eq cac }">${at.valueName}</c:if></c:forEach></p>
                                <div class="apps">
                                    <ul class="clear">
                                        <c:forEach items="${cApps}" var="app">

                                            <c:if test="${app.appClass eq cac}">
                                                <li class="item">
                                                        <%--内部应用直接显示应用链接,外部应用显示链接加参数--%>
                                                    <c:if test="${app.accessType eq 2}">
                                                        <a href="${app.redirectUri}${params}" target="_blank">
                                                            <img src="${app.icon}" alt="">
                                                            <p>${app.appName}</p>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${app.accessType eq 1}">
                                                        <a href="${app.redirectUri}">
                                                            <img src="${app.icon}" alt="">
                                                            <p>${app.appName}</p>
                                                        </a>
                                                    </c:if>
                                                </li>
                                            </c:if>

                                        </c:forEach>

                                    </ul>
                                </div>
                            </div>
                        </c:forEach>

                    </div>

                    <div class="all">

                        <c:forEach items="${appClass}" var="ac">
                            <div class="sub-app">
                                <c:set var="DICT_CODE" value="<%=BaseConstants.Dict.APPCLASS%>"/>
                                <p class="sub-title"><c:forEach items="${dict:getValueByCode(DICT_CODE)}" var="at"><c:if
                                        test="${at.valueKey eq ac }">${at.valueName}</c:if></c:forEach></p>
                                <div class="apps">
                                    <ul class="clear">
                                        <c:forEach items="${apps}" var="app">

                                            <c:if test="${app.appClass eq ac}">
                                                <li class="item">

                                                    <c:set var="flag" value="0"/>
                                                    <c:forEach items="${cApps}" var="cas">
                                                        <c:if test="${app.appId eq cas.appId}">
                                                            <c:set var="flag" value="1"/>
                                                        </c:if>
                                                    </c:forEach>

                                                    <c:if test="${flag eq 0}">
                                                        <img class="fav" src="${ctx}/resources/home/images/fav_0.png"
                                                             alt=""/>
                                                    </c:if>
                                                    <c:if test="${flag eq 1}">
                                                        <img class="fav c" src="${ctx}/resources/home/images/fav_1.png"
                                                             alt=""/>
                                                    </c:if>
                                                        <%--内部应用直接显示应用链接,外部应用显示链接加参数--%>
                                                    <c:if test="${app.accessType eq 2}">
                                                        <a href="${app.redirectUri}${params}" target="_blank">
                                                            <img src="${app.icon}" alt="">
                                                            <p>${app.appName}</p>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${app.accessType eq 1}">
                                                        <a href="${app.redirectUri}">
                                                            <img src="${app.icon}" alt="">
                                                            <p>${app.appName}</p>
                                                        </a>
                                                    </c:if>
                                                    <input type="hidden" value="${app.appId}"/>
                                                </li>
                                            </c:if>

                                        </c:forEach>

                                    </ul>
                                </div>
                            </div>
                        </c:forEach>

                    </div>


                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p>Copyright 2017 西安习悦信息技术有限公司 版权所有 陕ICP备14010330号-13</p>
    <p class="">全通教育集团（广东）股份有限公司</p>
</div>

<div class="auth-list">
    <div class="title">
        <p>选择身份</p>
    </div>

    <ul class="list clear">
        <c:forEach var="u" items="${userIdentites}">
            <c:if test="${u.usertypeId eq 3}">
                <li class="item">
                    <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                        <img src="${ctx}/resources/home/images/ic_js.png" alt="">
                    </a>
                    <p class="type">${u.usertypeName}账号</p>
                    <p class="organization">
                        <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                        <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                    </p>

                </li>
            </c:if>
            <c:if test="${u.usertypeId eq 1}">
                <li class="item">
                    <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                        <img src="${ctx}/resources/home/images/ic_xs.png" alt="">
                    </a>
                    <p class="type">${u.usertypeName}账号</p>
                    <p class="organization">
                        <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                        <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                    </p>

                </li>
            </c:if>
            <c:if test="${u.usertypeId eq 4}">
                <li class="item">
                    <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                        <img src="${ctx}/resources/home/images/ic_jg.png" alt="">
                    </a>
                    <p class="type">${u.usertypeName}账号</p>
                    <p class="organization">
                        <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                        <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                    </p>

                </li>
            </c:if>

            <c:if test="${u.usertypeId eq 2}">
                <li class="item">
                    <a href="${ctx}/manage/index?userType=${u.usertypeId}">
                        <img src="${ctx}/resources/home/images/ic_js.png" alt="">
                    </a>
                    <p class="type">${u.usertypeName}账号</p>
                    <p class="organization">
                        <c:if test="${not empty u.schoolName}">${u.schoolName}</c:if>
                        <c:if test="${not empty u.agencyName}">${u.agencyName}</c:if>
                    </p>

                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>

<div id="modify-panel">
    <form class="modify-form" method="post" id="modify-form">
        <p class="form-title">修改密码</p>
        <div class="form-item">
            <label for="originalPassword" class="label pwd-label"></label>
            <input type="password" id="originalPassword" placeholder="请输入原密码" name="originalPassword">
        </div>
        <div class="tip"></div>

        <div class="form-item">
            <label for="newPassword" class="label pwd-label"></label>
            <input type="password" id="newPassword" placeholder="请输入新密码" name="newPassword">
        </div>
        <div class="tip"></div>
        <div class="form-item">
            <label for="confirmPassword" class="label pwd-label"></label>
            <input type="password" id="confirmPassword" placeholder="请再次输入新密码" name="confirmPassword">
        </div>
        <div class="tip"></div>
        <div class="form-item sm">
            <button class="btn-modify">确认修改</button>
        </div>
    </form>
</div>

<script>
    $(function () {
        $('.layui-nav-item').hover(function () {
            var $dl = $(this).find('dl').show();
            $(this).find('.layui-nav-more').addClass('mored');

            var offsetTop = $dl.offset().top;
            console.log(offsetTop);
            $dl.css('top', offsetTop + 10).animate({
                top: offsetTop
            }, 150);
        }, function () {
            $(this).find('dl').hide();
            $(this).find('.layui-nav-more').removeClass('mored');
        });


        $('.tab.app>.tab-title>.title').hover(function () {
            $(this).addClass('toggled');
        }, function () {
            $(this).removeClass('toggled');
        }).click(function () {
            $('.tab.app>.tab-title>.title').removeClass('active');
            var idx = $('.tab.app>.tab-title>.title')
                .index($(this).addClass('active').get(0));

            if (idx == 0) {
                $('.collection').show();
                $('.all, .edit-btn').hide();
            } else {
                $('.collection').hide();
                $('.all, .edit-btn').show();

                $('.edit-btn a').removeClass().html('编辑');
                $('.all .apps .fav').removeClass('edit');
            }
            //###################
            $('.cancel-btn').hide();
        });

        $('.edit-btn>a').click(function (e) {
            e.preventDefault();
            if ($(this).hasClass('active')) {
                $(this).removeClass('active').html('编辑');
                $('.all .apps .fav').removeClass('edit');

                /* 发送请求 保存数据*/


                var addIds = [];
                var removeIds = [];
                $('.all .apps .fav.c').each(function (index, element) {
                    addIds.push($(element).siblings('input').val());
                });

                $('.all .apps .fav:not(.c)').each(function (index, element) {
                    removeIds.push($(element).siblings('input').val());
                });


                $.ajax({
                    type: "POST",
                    url: "${ctx}/manage/userAppCollections/update",
                    data: {
                        addIds: JSON.stringify(addIds),
                        removeIds: JSON.stringify(removeIds),
                        userId: "${userIdentity.userId}",
                        usertypeId: "${userIdentity.usertypeId}",
                        relationCode: "${userIdentity.relationCode}"
                    },
                    dataType: "json",
                    success: function (resut) {
                        if (resut.code == <%=WebResultConstant.SUCCESS.code%>) {
                            //alert(resut.data);
                            location.reload();
                        } else {
                            //alert(resut.data);
                        }

                    }
                });

                $('.cancel-btn').hide();

            } else {

                // 清空上次可能存在操作记录
                try {
                    clearOperQueque();
                } catch (ex) {
                }

                $(this).addClass('active').html('保存');
                $('.all .apps .fav').addClass('edit');

                //###############
                $('.cancel-btn').show();
            }
        });

        var operQueue = []; // 操作队列
        $('.all .apps .item').click(function (e) {
            if ($('.edit-btn>a').hasClass('active')) {
                e.preventDefault();
            } // 是编辑状态
            var $target = $(this).children('img.fav');
            if ($target.hasClass('c')) {
                $target.removeClass('c').attr('src', '${ctx}/resources/home/images/fav_0.png');
                operQueue.push(
                    function () {
                        $target.addClass('c').attr('src', '${ctx}/resources/home/images/fav_1.png');
                    }
                );
            } else {
                $target.addClass('c').attr('src', '${ctx}/resources/home/images/fav_1.png');
                operQueue.push(
                    function () {
                        $target.removeClass('c').attr('src', '${ctx}/resources/home/images/fav_0.png');
                    }
                );
            }
        });

        $('.cancel-btn').click(function (e) {
            e.preventDefault();
            $(this).hide();
            $('.edit-btn>a').removeClass('active').html('编辑');
            $('.all .apps .fav').removeClass('edit');
            clearOperQueque();
        });

        function clearOperQueque() {
            for (var i = 0, len = operQueue.length; i < len; ++i) {
                operQueue.pop()();
            }
        }

        $('.role a').click(function (event) {
            event.preventDefault();
            layer.open({
                type: 1,
                title: false,
                skin: ' _lay',
                closeBtn: 1,
                area: ['520px', '300px'],
                offset: ['200px', '500px'],
                content: $('.auth-list')
            });
        });

        /* 修改密码 */
        $('#modify-pwd').click(function (event) {
            event.preventDefault();
            $(':input', '#modify-form').val('');
            $('span.tip-err').html('');
            $('.tip-err').removeClass('tip-err');
            layer.open({
                type: 1,
                title: false,
                skin: ' _lay',
                closeBtn: 1,
                area: ['400px', '382px'],
                offset: '150px',
                content: $('#modify-panel')
            });
        });

        $('#modify-form .form-item input').focus(function () {
            $(this).parents('.form-item').addClass('highlight')
        }).blur(function () {
            $(this).parents('.form-item').removeClass('highlight')
        });

        $('#modify-form input').focus(function () {
            $(this).parent().removeClass('err').next().find('label.err').remove();
        });

        $('#modify-form').validate({
            onkeyup: false,
            errorElement: 'span',
            errorClass: 'tip-err',
            errorPlacement: function (error, element) {
                error.appendTo(element.parents('.form-item').next());
            },
            highlight: function (element, errorClass) {
                $(element).parents('.form-item').addClass(errorClass);
            },
            unhighlight: function (element, errorClass) {
                $(element).parents('.form-item').removeClass(errorClass);
            },
            rules: {
                originalPassword: {
                    required: true,
                    remote: {
                        url: '/manage/checkOriginalPassword',
                        type: 'post',
                        data: {
                            originalPassword: function () {
                                return $('#originalPassword').val();
                            }
                        }
                    }
                },
                newPassword: {required: true, minlength: 6},
                confirmPassword: {
                    required: true,
                    equalTo: '#newPassword'
                }
            },
            messages: {
                originalPassword: {
                    required: '请输入您的原始密码',
                    remote: $.validator.format('原始密码不正确')
                },
                newPassword: {
                    required: '请输入您的新密码',
                    minlength: $.validator.format('密码长度不能少于{0}位')
                },
                confirmPassword: {
                    required: '请再次输入新密码',
                    equalTo: '两次密码输入不一致'
                }

            },
            submitHandler: function () {
                $.ajax({
                    type: 'post',
                    url: '/manage/editpwd',
                    dataType: 'json',
                    data: $('#modify-form').serialize(),
                    success: function (result) {
                        if (result.code == 1) {
                            _validate($('#originalPassword'), '原始密码不正确');
                        }

                        if (result.code == 2) {
                            _validate($('#confirmPassword'), '两次密码输入不一致');
                        }

                        if (result.code == 3) {
                            $(':input', '#modify-form').val('');
                            layer.closeAll();
                            layer.msg('密码修改成功', {
                                time: 3000,
                                offset: '350px'
                            });
                        }
                    },
                    error: function () {
                    },
                    timeout: 5000
                });
            }
        });

        function _validate(obj, msg) {
            obj.parent().addClass('err');
            var tipU = obj.parent().next('.tip');
            var tipULabel = obj.parent().next('.tip').children('label.err');
            if (tipULabel.length == 0) {
                $('<label class="err">' + msg + '</label>').appendTo(tipU);
            } else {
                $(tipULabel).css('display', 'inline-block');
            }
        }
    });
</script>
</body>
</html>