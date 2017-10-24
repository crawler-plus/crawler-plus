var formSearch = function () {

    var determineState = function () {
        var excludeLink = $(":radio[name='excludeLink']:checked").val(); // 得到是否需要根据a标签中的某一段文本过滤a标签
        if(!parseInt(excludeLink, 10)) {
            $("#excludeLinkStr").prop("disabled", true);
        }
        $(":radio[name='excludeLink']").on('click', function () {
            var val = $(":radio[name='excludeLink']:checked").val();
            if(!parseInt(val, 10)) {
                $("#excludeLinkStr").prop("disabled", true);
            }else {
                $("#excludeLinkStr").prop("disabled", false);
            }
        })
    }

    // 获取数据
    var fecthData = function (data) {
        $("#dataRow").html("");
        var content = data.content;
        var responseType = typeof(content);
        if('object' === responseType) {
            var length = content.length;
            if(length) {
                for(var i = 0; i < length; i ++) {
                    var eachData = content[i];
                    var jsonObj = {content : eachData};
                    // 用jquery获取模板
                    var tpl = $("#handleBarScript").html();
                    //预编译模板
                    var template = Handlebars.compile(tpl);
                    //匹配json内容
                    var html = template(jsonObj);
                    //输入模板
                    $("#dataRow").append(html);
                    $("#dataRow").append("<h3>原网页对应内容：</h3>");
                    $("#dataRow").append(eachData);
                    $("#dataRow").append("<hr>")
                }
            }
        }else if('string' === responseType) {
            var jsonObj = {content : content};
            // 用jquery获取模板
            var tpl = $("#handleBarScript").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            //匹配json内容
            var html = template(jsonObj);
            //输入模板
            $("#dataRow").append(html);
        }
    }

    var init = function () {
        determineState();
        $("#crawlerForm").validate({
            rules: {
                url: {
                    required : true,
                    url : true
                },
                pattern : {
                    required: true
                }
                ,
                excludeLinkStr : {
                    required: true
                }
            },
            submitHandler: function () {
                commonUtil.showLoadingMessage();
                // 通过表单验证
                var url = $("#url").val(); // 得到url的值
                var pattern = $("#pattern").val(); // 得到正则表达式的值
                var excludeLink = $(":radio[name='excludeLink']:checked").val(); // 得到是否需要根据a标签中的某一段文本过滤a标签
                var excludeLinkStr = $("#excludeLinkStr").val(); // 得到待过滤的a标签中href的字符串的值
                var onlyText = $(":radio[name='onlyText']:checked").val(); // 得到是否只查文本
                var jsonObj = {};
                jsonObj.url = url;
                jsonObj.pattern = pattern;
                if(parseInt(excludeLink, 10)) {
                    jsonObj.excludeLink = true;
                }else {
                    jsonObj.excludeLink = false;
                }
                jsonObj.excludeLinkStr = excludeLinkStr;
                if(parseInt(onlyText, 10)) {
                    jsonObj.onlyText = true;
                }else {
                    jsonObj.onlyText = false;
                }
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'crawler/crawler',
                    method : 'POST',
                    data : JSON.stringify(jsonObj)
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        layer.closeAll();
                        var msgCode = data.msgCode;
                        if('400' === msgCode) {
                            toastr.error(data.content);
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

    formSearch.init();

    // 滚到页面顶部滚动条初始化
    $.goup({
        trigger: 100,
        bottomOffset: 50,
        locationOffset: 10,
        title: '回顶部',
        titleAsText: true
    });
});