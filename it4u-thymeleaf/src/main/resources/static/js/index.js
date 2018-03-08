var it4u = function () {
    var it4uConst = {
        listUrl : 'fetchArticles', // 展示数据的url
        currentPage : !1, // 当前页数
        totalP : !1,      // 总页数
        outKeyWord : !1, // 查询关键字
        dataRow : $("#dataRow"), // 数据div
        resultScript : $("#resultScript"),
        handleBarScript : $("#handleBarScript"),
        getMoreScript : $("#getMoreScript"),
        closeSpan : $("#closeSpan"),
        searchSpan : $("#searchSpan"),
        keyW : $('#keyword'),
        backToTop : $("#back-to-top"),
        bodyHtml : $('body, html'),
        preferUL : $("#preferUL")
    }

    // 获取数据
    var fetchData = function (data) {
        it4uConst.currentPage = data.page;
        var content = data.rows;
        if(it4uConst.currentPage === 1) {
            it4uConst.dataRow.empty();
            // 用jquery获取模板
            var tpl = it4uConst.resultScript.html(),
                template = Handlebars.compile(tpl),
                html = template({"totalSize" : data.total});
            it4uConst.dataRow.append(html);
        }
        // 得到数据的条数
        var length = content.length;
        if(length) {
            // 盛放数据的数组
            var resArray = [],
                tpl = it4uConst.handleBarScript.html(),
                template = Handlebars.compile(tpl);
            for(var i = 0; i < length; i ++) {
                //匹配json内容
                var html = template({"title" : content[i].title, "id" : content[i].cid, "insertTime": content[i].insertTime});
                resArray.push(html);
            }
            //输入模板
            it4uConst.dataRow.append(resArray.join(""));
            // 先将最后一个getMoreLi删除
            $(".getMoreLi").remove();
            // 判断当前页是不是最后一页
            var totalPage = data.totalPage;
            it4uConst.totalP = totalPage;
            // 如果不是最后一页
            if(it4uConst.currentPage < totalPage) {
                // 用jquery获取模板
                var tpl = it4uConst.getMoreScript.html(),
                    template = Handlebars.compile(tpl),
                    html = template({});
                it4uConst.dataRow.append(html);
            }
        }

        // 点击加载更多按钮
        $(".getMoreLi").on('click', function () {
            _toNextPage();
        });
    };

    var init = function () {
        _fetchDataFromServer();
        it4uConst.closeSpan.on('click', function () {
            it4uConst.keyW.val("").focus();
        });

        it4uConst.searchSpan.on('click', function () {
            _keySearch();
        });

        it4uConst.keyW.keydown(function(e){
            if(e.keyCode === 13){
                _keySearch();
            }
        });

        it4uConst.backToTop.click(function(){
            it4uConst.bodyHtml.animate({scrollTop:0},1000);
            return false;
        });

        $(window).scroll(function(){
            var $this = $(this),
                body = document.body,
                viewH = body.clientHeight,//可见高度
                contentH = body.scrollHeight,//内容高度
                scrollTop = $this.scrollTop(),//滚动高度
                offset = contentH - viewH - scrollTop;
            var x = offset >= 0 ? offset : -offset;
            if(it4uConst.totalP != it4uConst.currentPage) {
                if(x >= 0 && x <= 1) { //到达底部0-1px时,加载新内容
                    _toNextPage();
                }
            }
            if (scrollTop > 400){
                it4uConst.backToTop.fadeIn(1000);
            } else {
                it4uConst.backToTop.fadeOut(1000);
            }
        });

        it4uConst.preferUL.find(".btn-link").on("click", function () {
            var val = $.trim($(this).text());
            it4uConst.keyW.val(val);
            it4uConst.outKeyWord = val;
            _fetchDataFromServer(!1, val);
        }).on("mouseover", function () {
            $(this).css("cursor", "pointer");
        });
    };

    var _toNextPage = function () {
        it4uConst.currentPage++;
        _fetchDataFromServer(it4uConst.currentPage, it4uConst.outKeyWord);
    }

    /**
     *
     */
    var _keySearch = function () {
        var keyword = it4uConst.keyW.val();
        it4uConst.outKeyWord = keyword;
        _fetchDataFromServer(!1, keyword);
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
        var jsonObj = {
            currentPage : currentPage,
            keyword : $.trim(keyword)
        }, signOptions = {
            formID : null,
            isFormData : false
        }, ajaxOptions = {
            url: comm.url + it4uConst.listUrl,
            method : 'POST',
            data : JSON.stringify(jsonObj)
        }
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
    it4u.init();
});