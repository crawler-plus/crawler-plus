var barCodeSearch = function () {
    // 获取数据
    var fecthData = function (data) {
        $("#dataRow").html("");
        var content = data.rows;
        // 用jquery获取模板
        var tpl = $("#resultScript").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        //匹配json内容
        var html = template({});
        $("#dataRow").append(html);
        var length = content.length;
        if(length) {
            // 用jquery获取模板
            var tpl = $("#handleBarScript").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            for(var i = 0; i < length; i ++) {
                //匹配json内容
                var html = template({"title" : content[i].title, "id" : content[i].id, "insertTime": content[i].insertTime});
                //输入模板
                $("#dataRow").append(html);
            }
        }
    };

    var init = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/listAllSimpleCrawlerContents',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                fecthData(data);
            }
        );
    };

    return {
        init: init
    }
}();
$(function () {
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