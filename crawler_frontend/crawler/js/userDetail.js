var userDetail = function () {

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
            },
            function () {
                layer.closeAll();
                toastr.error('请检查网络！');
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
            },
            function () {
                layer.closeAll();
                toastr.error('请检查网络！');
            }
        );

    }

    var init = function () {
        fetchData();
    }

    return {
        init: init
    }
}();
$(function () {

    userDetail.init();

});