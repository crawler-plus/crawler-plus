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
                        layer.closeAll();
                        if(data.msgCode === '400') {
                            toastr.error(data.content);
                        }else {
                            toastr.success(data.content);
                            commonUtil.closeTab();
                        }
                    },
                    function (data) {
                        layer.closeAll();
                        if(data.status === 400) {
                            var response = JSON.parse(data.responseText);
                            toastr.error(response.errors);
                        }else {
                            toastr.error('请检查网络！');
                        }
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

    addTemplate.init();

});