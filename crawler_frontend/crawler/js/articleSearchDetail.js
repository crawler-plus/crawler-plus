var articleSearchDetail = function () {

    // 文章id
    var id = commonUtil.getUrlParam("id");

    var init = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/getCrawlerContent/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                $("#dataRow").html('');
                fecthData(data);
            }
        );
    };

    // 获取数据
    var fecthData = function (data) {
        // 首先生成第一个模版
        var tpl = $("#handleBarScript1").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        //匹配json内容
        var html = template(data.content);
        //输入模板
        $("#dataRow").html(html);
    };

    return {
        init: init
    }
}();
$(function () {
    articleSearchDetail.init();
});