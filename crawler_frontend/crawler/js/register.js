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
        $("#login").on('click', function () {
            location.href = 'login.html';
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
            url: comm.captchaUrl + 'create',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
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

        $("#registerForm").validate({
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
                // 通过表单验证
                var signOptions = {
                    formID : 'registerForm',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'user/register',
                    method : 'POST'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        if(data.msgCode === '400') {
                            layer.msg(data.content, {icon: 5});
                        }else {
                            layer.msg(data.content, {icon: 1});
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

    var useCaptcha = comm.registerUseCaptcha;
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