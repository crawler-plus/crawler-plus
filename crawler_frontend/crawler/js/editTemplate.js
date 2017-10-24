var editTemplate = function () {

    // 模版id
    var id = commonUtil.getUrlParam("id");

    var init = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/getTemplateConfig/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                layer.closeAll();
                var pPata = data.content;
                $("#id").val(pPata.id);
                $("#url").val(pPata.url);
                $("#firstLevelPattern").val(pPata.firstLevelPattern);
                $("#titlePattern").val(pPata.titlePattern);
                $("#timePattern").val(pPata.timePattern);
                $("#contentPattern").val(pPata.contentPattern);
            }
        );

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
                    url: comm.url + 'article/editTemplateConfig',
                    method : 'PUT'
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

    editTemplate.init();

});