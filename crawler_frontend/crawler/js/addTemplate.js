var addTemplate = function () {

    var init = function () {
        $("#templateForm").validate({
            rules: {
                url: {
                    required : true
                },
                firstLevelPattern: {
                    required : true
                },
                titlePattern: {
                    required : true
                },
                timePattern: {
                    required : true
                },
                contentPattern: {
                    required : true
                }
            },
            submitHandler: function () {
                commonUtil.showLoadingMessage();
                $("#userId").val(commonUtil.getUserId());
                // 通过表单验证
                var signOptions = {
                    formID : 'templateForm',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'article/saveTemplateConfig',
                    method : 'POST'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        commonUtil.closeTab();
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

    addTemplate.init();

});