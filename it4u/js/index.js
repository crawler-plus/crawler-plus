var barCodeSearch = function () {
    // 展示数据的url
    var listUrl = 'article/listAllSimpleCrawlerContents';
    var currentPage;
    var outKeyWord;
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
            _fetchDataFromServer(currentPage, outKeyWord);
        });

    };

    var init = function () {
        _fetchDataFromServer();
        $("#closeSpan").on('click', function () {
            $("#keyword").val("").focus();
        });

        $("#searchSpan").on('click', function () {
            _keySearch();
        });

        $('#keyword').keydown(function(e){
            if(e.keyCode==13){
                _keySearch();
            }
        });
    };

    /**
     *
     */
    var _keySearch = function () {
        var keyword = $("#keyword").val();
        outKeyWord = keyword;
        _fetchDataFromServer(null, keyword);
    }

    /**
     * 从服务器端获取数据的通用方法
     * @private
     */
    var _fetchDataFromServer = function (currentPage, keyword) {
        if(!currentPage) {
            currentPage = 1;
        }
        if(!keyword) {
            keyword = "";
        }
        var jsonObj = {};
        jsonObj.currentPage = currentPage;
        jsonObj.keyword = keyword;
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + listUrl,
            method : 'POST',
            data : JSON.stringify(jsonObj)
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                fetchData(data);
            }
        );
    }

    return {
        init: init
    }
}();
$(function () {
    barCodeSearch.init();
});