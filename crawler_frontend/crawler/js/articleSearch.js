var articleSearch = function () {

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
        for(var i = 0; i < content.length; i ++) {
            var eachContent = content[i];
            var json = {"id": eachContent.id, "title" : eachContent.title, "insertTime" : eachContent.insertTime, "url" : eachContent.url};
            //匹配json内容
            var html = template(json);
            //输入模板
            $("#dataRow").append(html);
        }
    };

    var init = function () {
        commonUtil.showLoadingMessage();
        // 通过表单验证
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/listAllCrawlerContents',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                layer.closeAll();
                fecthData(data);
                /**
                 * 查询文章详情
                 */
                $("div[class=eachArticle]").on('click', function () {
                    var that = $(this);
                    var id = that.prop("title");
                    // 新选项卡打开
                    var params = {
                        id:  id
                    };
                    commonUtil.openUrlInIframe("文章详情", commonUtil.buildUrlParam("crawler/articleSearchDetail.html", params));
                });

            }
        );
    };

    return {
        init: init
    }
}();
$(function () {

    articleSearch.init();

    // 滚到页面顶部滚动条初始化
    $.goup({
        trigger: 100,
        bottomOffset: 50,
        locationOffset: 10,
        title: '回顶部',
        titleAsText: true
    });
});