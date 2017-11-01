var userUpdate = function () {

    // 用户id
    var userId = commonUtil.getUrlParam("userId");

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
                $("#loginAccount").val(data.content.loginAccount);
                $("#name").val(data.content.name);
                $("#password").val(data.content.password);
                $("#version").val(data.content.version);
            }
        );


        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'user/getRoleByUserId/' + userId,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                var tpl = $("#handleBarScript").html();
                //预编译模板
                var template = Handlebars.compile(tpl);
                for (var i = 0; i < data.content.length; i ++) {
                    var obj = data.content[i];
                    var json = {"id": obj.id, "roleName" : obj.roleName, "isRefByUser" : obj.isRefByUser};
                    var html = template(json);
                    $("#checkbox-area").append(html);
                }

                $('.i-checks').iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                });
            }
        );

    }

    var init = function () {
        fetchData();

        $("#userUpdate").validate({
            rules: {
                name : {
                    required: true
                },
                password : {
                    required: true
                }
            },
            submitHandler: function () {
                _checkUserExists(userId, function () {
                    // 放置用户角色的数组
                    var checkedArr = [];
                    var $checkbox = $(":checkbox");
                    $checkbox.each(function (index, obj) {
                        var currentObj = $(obj);
                        if(currentObj.parent("div").hasClass("checked")) {
                            checkedArr.push(currentObj.val());
                        }
                    });
                    var checkedArrStr = checkedArr.join(",");
                    $("#userRoleStr").val(checkedArrStr);
                    $("#userId").val(userId);
                    commonUtil.showLoadingMessage();
                    // 通过表单验证
                    var signOptions = {
                        formID : 'userUpdate',
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
                            layer.closeAll();
                            if(data.msgCode === '400') {
                                toastr.error(data.content);
                            }else {
                                commonUtil.closeTab();
                            }
                        }
                    );
                });
            }
        });

    }

    /**
     * 判断用户是否存在
     * @param id
     * @private
     */
    var _checkUserExists = function (id, callback) {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'user/checkUserExists/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                if(data.msgCode === '400') {
                    toastr.error(data.content);
                }else {
                    callback();
                }
            }
        );
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

    userUpdate.init();

});