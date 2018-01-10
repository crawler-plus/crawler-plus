var index = function () {

    var init = function () {

        var userId = sessionStorage.getItem("userId");
        if(null == userId) {
            location.href = 'login.html';
        }
        var userPermissions = sessionStorage.getItem("userPermissions");
        var menuInfo = JSON.parse(userPermissions);
        // 如果当前用户分配了菜单
        if(menuInfo.length) {
            // 用jquery获取模板
            var tpl1 = $("#handleBarScript1").html();
            //预编译模板
            var template1 = Handlebars.compile(tpl1);

            var tpl2 = $("#handleBarScript2").html();
            //预编译模板
            var template2 = Handlebars.compile(tpl2);

            var tpl3 = $("#handleBarScript3").html();
            //预编译模板
            var template3 = Handlebars.compile(tpl3);

            var tpl4 = $("#handleBarScript4").html();
            //预编译模板
            var template4 = Handlebars.compile(tpl4);

            for(var i = 0; i < menuInfo.length; i ++) {
                //匹配json内容
                var html1 = template1({});
                $("#side-menu").append(html1);
                var eachMenuInfo = menuInfo[i];
                var parentMenu = eachMenuInfo[0];

                var parentJson = {"href": parentMenu.url, "text": parentMenu.menuName};
                var html2 = template2(parentJson);
                var html3 = template3({});
                $("#side-menu > li:last").append(html2).append(html3);

                for(var j = 1; j < eachMenuInfo.length; j ++) {
                    var childMenu = eachMenuInfo[j];
                    var childJson = {"href": childMenu.url, "text": childMenu.menuName};
                    var html4 = template4(childJson);
                    $("#side-menu > li:last>ul").append(html4);
                }
            }
        }

        // 点击退出按钮，发起后台请求，清空token信息
        $("#logoutBtn").on('click', function () {
            layer.confirm('确定要退出吗？', {
                btn: ['是','否'] //按钮
            }, function(){
                // 通过表单验证
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'user/logout/' + sessionStorage.getItem("userId") + "/" + sessionStorage.getItem("token"),
                    method : 'GET'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        location.href = 'login.html';
                    }
                );
            }, function(){
                layer.closeAll();
            });
        })
    };

    return {
        init: init
    }
}();
$(function () {
    index.init();
});