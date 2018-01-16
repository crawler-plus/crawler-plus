var userInformationUpdate = function () {

    // 用户id
    var userId = commonUtil.getUserId();

    // 获取数据
    var fetchData = function () {

        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'user/queryUser/' + userId,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                $("#name").val(data.content.name);
                $("#version").val(data.content.version);
            }
        );
    };

    var init = function () {
        fetchData();

        $("#userInformationUpdate").validate({
            rules: {
                name : {
                    required: true
                },
                password : {
                    required: true
                }
            },
            submitHandler: function () {
                $("#userId").val(userId);
                // 通过表单验证
                var signOptions = {
                    formID : 'userInformationUpdate',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'user/updateUser',
                    method : 'PUT'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        if(data.msgCode === '400') {
                            toastr.error(data.content);
                        }else {
                            commonUtil.closeTab();
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

    userInformationUpdate.init();

});