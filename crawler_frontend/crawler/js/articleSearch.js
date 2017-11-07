var articleSearch = function () {
    // bootstrap表格
    var bootstrapTableEl = $("#articleList");
    // 查询按钮
    var queryBtnEl = $("#queryBtn");
    // 用户查询url
    var userQueryUrl= comm.url + "article/listAllCrawlerContents";
    //  浏览按钮
    var viewBtnEl = '<a class="view ml10 btn btn-sm btn-primary" href="javascript:void(0)" title="查看">查看</a>';

    var queryObject = {};
    /**
     * 表格信息初始化
     * @private
     */
    var _tableInit = function () {
        // 初始化表格
        commonUtil.bootstrapTable(bootstrapTableEl, {
            url: userQueryUrl,
            queryParams: function (params) {
                return {
                    // 后台分页参数（不需要修改，也不要删除！）
                    limit: params.limit,
                    offset: params.offset,
                    sortOrder: params.order,
                    page: params.offset / params.limit + 1,
                    // 查询参数
                    userId: queryObject.userId
                }
            }
        });
    };

    /**
     * 按钮事件绑定
     * @private
     */
    var _btnEvent = function () {
        // 点击查询按钮
        queryBtnEl.on("click", function () {
            _refreshCurrentTable();
        });
    };


    var _refreshCurrentTable = function () {
        // 查询条件
        queryObject.userId = commonUtil.getUserId();
        // 刷新表格
        bootstrapTableEl.bootstrapTable("refresh", {
            url: userQueryUrl,
            query: {
                userId: queryObject.userId
            }
        });
    };

    return {
        /**
         * 表格列格式化
         * @param value 列值
         * @param row 行值
         * @param index index
         * @returns {string}
         */
        actionFormatter: function (value, row, index) {
            var actionArr = [];
            actionArr.push(viewBtnEl);
            return actionArr.length > 0 ? actionArr.join(' ') : "-";
        },

        /**
         * 表格事件
         */
        actionEvents: {
            /**
             * 点击事件
             * @param e     jqEvent
             * @param value 单元格值
             * @param row   行对象
             * @param index 行索引
             */
            "click .view": function (e, value, row, index) {
                var id = row.id;
                var params = {
                    id: id
                };
                commonUtil.openUrlInIframe("文章详情", commonUtil.buildUrlParam("crawler/articleSearchDetail.html", params));
            }
        },

        /**
         * 页面初始化
         */
        init: function () {
            _btnEvent();
            _tableInit();
        },
        refreshCurrentTable: _refreshCurrentTable
    }
}();


/**
 * dom加载完成
 */
$(function () {
    articleSearch.init();
});