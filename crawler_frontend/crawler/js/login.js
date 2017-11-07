var login = function () {

    // 图片验证码对象
    var captchaImgObj = $("#captchaImg");

    var btn_init = function () {
        captchaImgObj.on('click', function () {
            create_captcha();
        }).on("mouseover", function () {
            $(this).css("cursor", "pointer")
        }).on("mouseout", function () {
            $(this).css("cursor", "");
        });
    };

    // 加载验证码
    var create_captcha = function () {
        layer.msg('正在加载验证码', {
            icon: 16
            ,shade: 0.01
            ,time: 1000 * 10 // 10s
        });
        // 加载验证码
        var signOptions = {
            formID : '',
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'captcha/create',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                layer.closeAll();
                if(data.msgCode === '400') {
                    layer.msg('加载验证码失败', {icon: 5});
                }else {
                    captchaImgObj.prop("src", data.content);
                }
            }
        );
    };

    var init = function () {
        btn_init();
        // 清空sessionStorage中关于用户的信息
        sessionStorage.removeItem("userPermissions");
        sessionStorage.removeItem("userName");
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("timestamp");

        $("#loginForm").validate({
            rules: {
                loginAccount: {
                    required : true
                },
                password: {
                    required : true
                },
                captcha: {
                    required : true
                }
            },
            submitHandler: function () {
                commonUtil.showLoadingMessage();
                // 通过表单验证
                var signOptions = {
                    formID : 'loginForm',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'user/login',
                    method : 'POST'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        layer.closeAll();
                        if(data.msgCode === '400') {
                            layer.msg(data.content, {icon: 5});
                        }else {
                            // 将用户信息和用户权限信息转为字符串存在sessionStorage中
                            sessionStorage.setItem("userPermissions", data.content.menuInfo);
                            sessionStorage.setItem("userName", data.content.userInfo.loginAccount);
                            sessionStorage.setItem("userId", data.content.userInfo.id);
                            sessionStorage.setItem("token", data.content.token);
                            sessionStorage.setItem("timestamp", data.content.timestamp);
                            // 跳转到index.html
                            location.href = 'index.html';
                        }
                    }
                );
            }
        });
    };

    return {
        init: init
    }
}();
$(function () {

    var useCaptcha = comm.useCaptcha;
    if(!useCaptcha) {
        $("#captchaDiv").css("display", "none");
        $("#captChaImgDiv").css("display", "none");
    }

    $.validator.setDefaults({
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });

    login.init();

});