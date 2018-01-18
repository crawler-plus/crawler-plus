var barCodeSearch = function () {
    // 展示数据的url
    var listUrl = 'article/listAllSimpleCrawlerContents';
    var currentPage;
    // 获取数据
    var fetchData = function (data) {
        currentPage = data.page;
        var content = data.rows;
        if(currentPage === 1) {
            $("#dataRow").html("");
            // 用jquery获取模板
            var tpl = $("#resultScript").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            //匹配json内容
            var html = template({"totalSize" : data.total});
            $("#dataRow").append(html);
        }
        // 得到数据的条数
        var length = content.length;
        if(length) {
            // 用jquery获取模板
            var tpl = $("#handleBarScript").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            for(var i = 0; i < length; i ++) {
                //匹配json内容
                var html = template({"title" : content[i].title, "id" : content[i].id, "insertTime": content[i].insertTime});
                // 先将最后一个getMoreLi删除
                $(".getMoreLi:last").remove();
                //输入模板
                $("#dataRow").append(html);
            }
            // 判断当前页是不是最后一页
            var totalPage = data.totalPage;
            // 如果不是最后一页
            if(currentPage < totalPage) {
                // 用jquery获取模板
                var tpl = $("#getMoreScript").html();
                //预编译模板
                var template = Handlebars.compile(tpl);
                //匹配json内容
                var html = template({});
                $("#dataRow").append(html);
            }
        }

        // 点击加载更多按钮
        $(".getMoreLi").on('click', function () {
            currentPage++;
            init(currentPage);
        });

    };

    var init = function (currentPage) {
        if(!currentPage) {
            currentPage = 1;
        }
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + listUrl + "/" + currentPage,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                fetchData(data);
            }
        );
    };

    return {
        init: init
    }
}();
$(function () {
    barCodeSearch.init();
});