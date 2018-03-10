<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/home/css/reset.css">
    <link rel="stylesheet" href="${ctx}/home/css/home.css">
    <link rel="stylesheet" href="${ctx}/home/css/mylayer.css">


    <script type="text/javascript" src="${ctx}/home/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/home/js/jquery.leoweather.min.js"></script>
    <script type="text/javascript" src="${ctx}/home/plugins/layer/layer.js"></script>

    <title>个人中心</title>
</head>
<body>
<div id="header">
    <div class="wrap">
        <div class="logo fl">
            <a href="javascript:;" class="zjy"></a>
            <a href="javascript:;" class="agency">西安交通大學雲管理平台</a>
        </div>
        <ul class="toolbar fr">
            <li class="role"><a href="javascript:;"><img src="${ctx}/home/images/head.png" alt=""
                                                         class="avatar">切換角色</a></li>
            <li class="help"><a href="javascript:;">幫助</a></li>
            <li class="logout"><a href="/logout">退出</a></li>
        </ul>
    </div>
</div>

<div class="container">
    <div class="wrap">
        <div class="content">
            <div class="info">
                <div class="user-info fl">
                    <div class="mod-pwd">
                        <a href="javascript:;" class="fr">修改密碼&gt;</a>
                    </div>
                    <div class="user-bar">
                        <div class="avatar fl"><img src="${ctx}/home/images/head_big.png" alt=""></div>
                        <ul class="base-info fl">
                            <li class="u-name">李小琳</li>
                            <li class="u-phone">13746453527</li>
                            <li class="u-dept">行政部門</li>
                            <li class="u-job">代課老師</li>
                        </ul>
                    </div>
                </div>
                <div class="weather-info fr">
                    <ul>
                        <li>
                           <span class="city">
                               <script>$('.city').leoweather({format: '{城市}'});</script>
                           </span>
                            <span class="weather">
                               <script>$('.weather').leoweather({format: '{天气}'});</script>
                           </span>
                        </li>
                        <li class="temperature">
                            <script>$('.temperature').leoweather({format: '{气温}℃'});</script>
                        </li>
                        <li class="today">
                            <span class="weekday"><script>$('.weekday').leoweather({format: '星期{周}'});</script></span>
                            <span class="date"><script>$('.date').leoweather({format: '{月}月{日}日 {时}:{分}:{秒}'});</script></span>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="tab agenda">
                <div class="tab-title">
                    <a href="javascript:" class="title fl">我的待办</a>
                    <a href="javascript:;" class="more fr">查看&gt;</a>
                </div>
                <div class="main">
                    <ul class="clear">
                        <li class="item">
                            <p class="title"><a href="javascript:;">示范校园运动会1</a></p>
                            <p class="date">2017.08.11</p>
                        </li>
                        <li class="item">
                            <p class="title"><a href="javascript:;">示范校园运动会2</a></p>
                            <p class="date">2017.08.11</p>
                        </li>
                        <li class="item">
                            <p class="title"><a href="javascript:;">示范校园运动会3</a></p>
                            <p class="date">2017.08.11</p>
                        </li>
                        <li class="item">
                            <p class="title"><a href="javascript:;">示范校园运动会4</a></p>
                            <p class="date">2017.08.11</p>
                        </li>
                    </ul>
                </div>
            </div>


            <div class="tab app">
                <div class="tab-title">
                    <a href="javascript:" class="title fl active">我的收藏</a>
                    <a href="javascript:" class="title fl">全部应用</a>
                    <%--#############################################################--%>

                    <div class="cancel-btn fr">
                        <a href="">取消</a>
                    </div>
                    <%--#############################################################--%>

                    <div class="edit-btn fr">
                        <a href="">编辑</a>
                    </div>

                </div>
                <div class="main">
                    <div class="collection">
                        <div class="sub-app">
                            <p class="sub-title">学生工作管理</p>
                            <div class="apps">
                                <ul class="clear">
                                    <li class="item">
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理1</p>
                                        </a>
                                    </li>

                                    <li class="item">
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理1</p>
                                        </a>
                                    </li>

                                    <li class="item">
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理1</p>
                                        </a>
                                    </li>

                                </ul>
                            </div>
                        </div>

                        <%-- <div class="sub-app">
                             <p class="sub-title">教务管理</p>
                             <div class="apps">
                                 <ul class="clear">
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_1.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_2.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_3.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_4.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_5.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_5.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>

                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_5.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>

                                     <li class="item">
                                         <img src="${ctx}/home/images/jw_5.png" alt="">
                                         <p><a href="javascript:;">助学金管理</a></p>
                                     </li>
                                 </ul>
                             </div>
                         </div>--%>


                        <%--<div class="sub-app">
                            <p class="sub-title">招生工作管理</p>
                            <div class="apps">
                                <ul class="clear">
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx1.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx2.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx3.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx4.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx5.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/zx1.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>

                                    <li class="item">
                                        <img src="${ctx}/home/images/zx1.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>

                                    <li class="item">
                                        <img src="${ctx}/home/images/zx3.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                </ul>
                            </div>
                        </div>--%>
                    </div>
                    <div class="all">
                        <div class="sub-app">
                            <p class="sub-title">学生工作管理</p>
                            <div class="apps">
                                <ul class="clear">
                                    <li class="item">
                                        <img class="fav" src="${ctx}/home/images/fav_0.png" alt=""/>
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理1</p>
                                        </a>
                                        <input type="hidden" value="1">
                                    </li>


                                    <li class="item">
                                        <img class="fav" src="${ctx}/home/images/fav_0.png" alt=""/>
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理2</p>
                                        </a>
                                        <input type="hidden" value="2">
                                    </li>


                                    <li class="item">
                                        <img class="fav c" src="${ctx}/home/images/fav_1.png" alt=""/>
                                        <a href="#">
                                            <img src="${ctx}/home/images/zs_1.png" alt="">
                                            <p>助学金管理3</p>
                                        </a>
                                        <input type="hidden" value="3">
                                    </li>

                                </ul>
                            </div>
                        </div>


                        <div class="sub-app">
                            <p class="sub-title">教务管理</p>
                            <div class="apps">
                                <ul class="clear">
                                    <li class="item">
                                        <img src="${ctx}/home/images/jw_1.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/jw_1.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>
                                    <li class="item">
                                        <img src="${ctx}/home/images/jw_2.png" alt="">
                                        <p><a href="javascript:;">助学金管理</a></p>
                                    </li>

                                </ul>
                            </div>
                        </div>
                        <%--



                                                <div class="sub-app">
                                                    <p class="sub-title">招生工作管理</p>
                                                    <div class="apps">
                                                        <ul class="clear">
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx1.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx2.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx3.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx4.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx5.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx1.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>

                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx1.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>

                                                            <li class="item">
                                                                <img src="${ctx}/home/images/zx3.png" alt="">
                                                                <p><a href="javascript:;">助学金管理</a></p>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="auth-list">
    <div class="title">
        <p>选择身份</p>
    </div>

    <ul class="list clear">
        <c:forEach var="type" items="${currentUser.types}">
            <c:if test="${type.id eq 1}">
                <li class="item">
                    <a href="">
                        <img src="${ctx}/home/images/ic_js.png" alt="">
                        <p class="type"><a href="#">教师账户</a></p>
                        <p class="organization"><a href="javascript:;">西北工业大学</a></p>
                    </a>
                </li>
            </c:if>
            <c:if test="${type.id eq 2}">
                <li class="item">
                    <a href="">
                        <img src="${ctx}/home/images/ic_xs.png" alt="">
                        <p class="type"><a href="#">学生账户</a></p>
                        <p class="organization"><a href="javascript:;">西北工业大学</a></p>
                    </a>
                </li>
            </c:if>
            <c:if test="${type.id eq 3}">
                <li class="item">
                    <a href="">
                        <img src="${ctx}/home/images/ic_jg.png" alt="">
                        <p class="type"><a href="#">机构账户</a></p>
                        <p class="organization"><a href="javascript:;">西北工业大学</a></p>
                    </a>
                </li>
            </c:if>

            <c:if test="${type.id eq 4}">
                <li class="item">
                    <a href="">
                        <img src="${ctx}/home/images/ic_js.png" alt="">
                        <p class="type"><a href="#">家长账户</a></p>
                        <p class="organization"><a href="javascript:;">西北工业大学</a></p>
                    </a>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>
<script>
    $(function () {
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
                    type : "POST",
                    url : "${ctx}/manage/userAppCollections/update",
                    data : {addIds:JSON.stringify(addIds), removeIds:JSON.stringify(removeIds),userId: ${userIdentity.userId},usertypeId: ${userIdentity.usertypeId},relationCode: ${userIdentity.relationCode}},
                    dataType : "json",
                    success : function(resut){
                        if (resut.code == <%=WebResultConstant.SUCCESS.code%>){
                            alert(resut.data);
                            location.reload();
                        }else {
                            alert(resut.data);
                        }

                    }
                });

                //###############
                $('.cancel-btn').hide();

            } else {

                // 清空上次可能存在操作记录
                try {
                    clearOperQueque();
                } catch(ex) {}

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
                $target.removeClass('c').attr('src', '${ctx}/home/images/fav_0.png');
                operQueue.push(
                    function() {$target.addClass('c').attr('src', '${ctx}/home/images/fav_1.png');}
                );
            } else {
                $target.addClass('c').attr('src', '${ctx}/home/images/fav_1.png');
                operQueue.push(
                    function() {$target.removeClass('c').attr('src', '${ctx}/home/images/fav_0.png');}
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
                content: $('.auth-list')
            });
        });
    });
</script>
</body>
</html>