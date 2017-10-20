var login = function () {

    var init = function () {

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
                        toastr.error('请检查网络！');
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