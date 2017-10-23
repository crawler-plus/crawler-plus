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
    }

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
            },
            function (data) {
                layer.closeAll();
                layer.msg('加载验证码失败', {icon: 5});
            }
        );
    }

    var init = function () {
        btn_init();
        create_captcha();
        // 清空sessionStorage中关于用户的信息
        sessionStorage.removeItem("userPermissions");
        sessionStorage.removeItem("userName");

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
                            sessionStorage.setItem("userPermissions", JSON.stringify(data.content.menuInfo));
                            sessionStorage.setItem("userName", data.content.userInfo.loginAccount);
                            // 跳转到index.html
                            location.href = 'index.html';
                        }
                    },
                    function (data) {
                        layer.closeAll();
                        layer.msg('请检查网络', {icon: 5});
                    }
                );
            }
        });
    }

    return {
        init: init
    }
}();
$(function () {

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