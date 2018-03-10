var zjy = {
    /* 常量定义  */
    COOKIE_NAME: 'jzy_username',
    COOKIE_PASS: 'jzy_password',
    COOKIE_EXPIRE: 30, 				// 保存一个月
    COOKIE_REMEMBER: 'jzy_remember',
    COOKIE_CAPTCHA: 'jzy_captcha',
    COOKIE_CAPTCHA_EXPIRE: 60, 				// 验证码过期时间
    CAPTCHA_WAIT: 6, 				//下一次验证码等待时间
    LOGIN_SUCCESS: 0,
    USER_NOT_EXIST: 1,
    PASSWORD_ERROR: 2,
    USER_LOCKED: 3
};

$(document).ready(function () {
    /* 读取Cookie */
    var username = $.cookie(zjy.COOKIE_NAME);
    var password = $.cookie(zjy.COOKIE_PASS);
    var remember = $.cookie(zjy.COOKIE_REMEMBER);
    if (username) $('#username').val(username);
    if (password) $('#password').val(password);
    if (remember) $('.chk').addClass('selected');


    var wait = $.cookie(zjy.COOKIE_CAPTCHA) || zjy.CAPTCHA_WAIT;


    if (wait && $.cookie(zjy.COOKIE_CAPTCHA)) {
        if (wait != zjy.CAPTCHA_WAIT) {
            $('#btn-v').addClass('in');
            setTimeout(function () {
                getNextCode($('#btn-v'))
            }, 1000);
        } else {
            $('#btn-v').text('重新获取验证码');
        }
    } else {
        $('#btn-v').text('获取验证码');
    }


    /* 找回密码 */
    var captcha_expire_date;
    $('#btn-v').click(function (e) {
        if (wait != zjy.CAPTCHA_WAIT) return false;
        e.preventDefault();
        captcha_expire_date = new Date();
        getNextCode(this);
        $(this).addClass('in');
        /* 模拟发送验证码 */
        $.ajax({
            url: '/phoneCode',
            type: 'post',
            dataType: 'text',
            data: {},
            success: function (res) {
                alert('验证码是 ' + res);
            }
        });
    });

    function getNextCode(obj) {
        var $obj = $(obj);
        if (wait == 0) {
            $obj.text('重新获取验证码');
            wait = zjy.CAPTCHA_WAIT;
            $.removeCookie(zjy.COOKIE_CAPTCHA, {path: '/'});
            $obj.removeClass('in');
        } else {
            $obj.text('' + wait + 'S');
            --wait;

            if (captcha_expire_date)
                captcha_expire_date.setTime(captcha_expire_date.getTime() + 60 * 1000);

            $.cookie(zjy.COOKIE_CAPTCHA, wait, {
                path: '/',
                expires: captcha_expire_date
            });
            setTimeout(function () {
                getNextCode(obj)
            }, 1000);
        }
    }

    /* 记住密码 */
    $('.chk').click(function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    });

    /* 登录 */
    $('.btn-login').click(function () {
        if ($('.chk').hasClass('selected')) {
            $('#rememberMe').val(true);
            $.cookie(zjy.COOKIE_NAME, $('#username').val(), {
                path: '/',
                expires: zjy.COOKIE_EXPIRE
            });
            $.cookie(zjy.COOKIE_PASS, $('#password').val(), {
                path: '/',
                expires: zjy.COOKIE_EXPIRE
            });
            $.cookie(zjy.COOKIE_REMEMBER, 'true', {
                path: '/',
                expires: zjy.COOKIE_EXPIRE
            });
        } else {
            $('#rememberMe').val(false);
            $.removeCookie(zjy.COOKIE_NAME, {
                path: '/'
            });
            $.removeCookie(zjy.COOKIE_PASS, {
                path: '/'
            });
            $.removeCookie(zjy.COOKIE_REMEMBER, {
                path: '/'
            });
        }
    });


    /* 获取焦点边框颜色控制 */
    $('.form-item').has('label').children('input').bind({
        focus: function (e) {
            $(e.target).parent().addClass('active');
        },
        blur: function (e) {
            $(e.target).parent().removeClass('active')
                .removeClass('err');
            $(e.target).parent().next('.tip').children('label.err').css('display', 'none');
        }
    });

    /* 登录validate */
    $('#login-form').validate({
        onkeyup: false,
        errorPlacement: function (error, element) { //error,element均为jquery对象
            error.appendTo(element.parent().next('.tip'));
        },
        highlight: function (element, errorClass) { // element为DOM元素
            $(element).parent().addClass(errorClass);
        },
        unhighlight: function (element, errorClass) {
            $(element).parent().removeClass(errorClass);
        },
        submitHandler: function (form) { // ajax验证账号是否存在、密码是否正确
            var u = $('#username').val();
            var p = $('#password').val();
            var rm = $('#rememberMe').val();

            var backurl = $("#backurl").val();
            $.ajax({
                url: BASE_PATH + '/sso/login',
                type: 'post',
                data: {username: u, password: p, rememberMe: rm, backurl: backurl},
                dataType: 'text',
                success: function (json) {
                    json = JSON.parse(json)
                    //登录成功
                    if (json.code == 1) {
                        window.location.href = json.data;
                    } else {
                        //alert(json.data);
                        if (10103 == json.code) {
                            $('#username').parent().addClass('err');
                            var tipU = $('#username').parent().next('.tip');
                            var tipULabel = $('#username').parent().next('.tip').children('label.err');
                            if (tipULabel.length == 0) {
                                $('<label class="err">该账号不存在</label>').appendTo(tipU);
                            } else {
                                $(tipULabel).css('display', 'inline-block');
                            }
                        }
                        if (10104 == json.code) {
                            $('#password').parent().addClass('err');
                            var tipP = $('#password').parent().next('.tip');
                            var tipPLabel = $('#password').parent().next('.tip').children('label.err');
                            if (tipPLabel.length == 0) {
                                $('<label class="err p">密码错误</label>').appendTo(tipP);
                            } else {
                                $(tipPLabel).css('display', 'inline-block');
                            }
                        }
                    }
                }
            });

        },
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: '请填写账号信息'
            },
            password: {
                required: '请填写密码'
            }
        }
    });

    /* 修改密码验证 */
/*
    $('.forget-form').validate({
        debug: true,
        onkeyup: false,
        errorElement: 'p',
        errorPlacement: function (error, element) { //error,element均为jquery对象
            if (element.attr('name') == 'captcha')
                error.appendTo(element.parent().next().next('.tip'));
            else
                error.appendTo(element.parent().next('.tip'));
        },
        highlight: function (element, errorClass) { // element为DOM元素
            $(element).parent().addClass(errorClass);
        },
        unhighlight: function (element, errorClass) {
            $(element).parent().removeClass(errorClass);
        },
        submitHandler: function (form) {
            console.log(form);
            var pwd = $('#password').val();
            var uname = $('#username').val();
            console.log(password);
            $.ajax({
                url: '/modifyPassword',
                type: 'post',
                dataType: 'text',
                data: {password: pwd, username: uname},
                success: function (res) {
                    if (res == 'true') {
                        $('.forget-form').hide();
                        $('.mascot_tip').show(500);
                        $('p.tip').show(1000);
                    }
                    else alert('error occured');
                },
                timeout: 5000
            });
        },
        rules: {
            username: {
                required: true,
                remote: {
                    url: '/checkUser',
                    type: 'post',
                    data: {
                        username: function () {
                            return $('input[name=username]').val();
                        }
                    },
                    dataType: 'json'

                }
            },
            password: {
                required: true,
                minlength: 8
            },
            cfmpassword: {
                equalTo: '#password'
            },
            captcha: {
                required: true,
                remote: {
                    url: '/checkCode',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        phoneCode: function () {
                            return $('input[name=captcha]').val();
                        }
                    }
                }
            }

        },
        messages: {
            username: {
                required: '请输入账号或手机号',
                remote: '该账号不存在'
            },
            password: {
                required: '请输入新密码',
                minlength: jQuery.format("密码不能少于{0}位")
            },
            cfmpassword: {
                equalTo: '两次密码输入不一致'
            },
            captcha: {
                required: '请输入验证码',
                remote: '验证码不正确'
            }
        }
    });
*/

    if (window.top !== window.self) {
        top.location.href = location.href;
    }

});