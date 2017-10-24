var barCodeSearch = function () {

    // 获取数据
    var fecthData = function (data) {
        $("#dataRow").html("");
        var content = data.content;
        // 用jquery获取模板
        var tpl = $("#resultScript").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        //匹配json内容
        var html = template({});
        $("#dataRow").append(html);
        // 用jquery获取模板
        var tpl = $("#handleBarScript").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        //匹配json内容
        var html = template(content);
        //输入模板
        $("#dataRow").append(html);
    }

    var init = function () {
        $("#barCodeForm").validate({
            rules: {
                barCode: {
                    required : true
                }
            },
            submitHandler: function () {
                commonUtil.showLoadingMessage();
                // 通过表单验证
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'barCode/barCode/' + $("#barCode").val(),
                    method : 'GET'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        layer.closeAll();
                        var msgCode = data.msgCode;
                        if('400' === msgCode) {
                            toastr.error('查不到商品数据！');
                        }else {
                            fecthData(data);
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

    barCodeSearch.init();

    // 滚到页面顶部滚动条初始化
    $.goup({
        trigger: 100,
        bottomOffset: 50,
        locationOffset: 10,
        title: '回顶部',
        titleAsText: true
    });
});