var headerFooterContentMgmt = function () {

    var fetchData = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'headerFooterContent/get',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                $("#headerContent").html(data.content.headerContent);
                $("#footerContent").html(data.content.footerContent);
                $("#version").val(data.content.version);
            }
        );
    }

    var init = function () {
        fetchData();
        $("#headerFooterForm").validate({
            rules: {
                headerContent: {
                    required : true
                },
                footerContent: {
                    required : true
                }
            },
            submitHandler: function () {
                // 通过表单验证
                var signOptions = {
                    formID : 'headerFooterForm',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'headerFooterContent/edit',
                    method : 'PUT'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        if(data.msgCode === '400') {
                            toastr.error(data.content);
                        }else {
                            toastr.success(data.content);
                        }
                        fetchData();
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

    headerFooterContentMgmt.init();

});