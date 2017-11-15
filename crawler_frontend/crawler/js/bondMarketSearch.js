var bondMarketSearch = function () {
    // bootstrap表格
    var bootstrapTableEl = $("#bondMarketList");
    // 查询按钮
    var queryBtnEl = $("#queryBtn");
    // 查询url
    var queryUrl = comm.url + "bondMarket/queryAll";
    // 爬取按钮
    var crawBtn = $("#craw");

    /**
     * 表格信息初始化
     * @private
     */
    var _tableInit = function () {
        // 初始化表格
        commonUtil.bootstrapTable(bootstrapTableEl, {
            url: queryUrl,
            queryParams: function (params) {
                return {
                    // 后台分页参数（不需要修改，也不要删除！）
                    limit: params.limit,
                    offset: params.offset,
                    sortOrder: params.order,
                    page: params.offset / params.limit + 1
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
        // 点击爬取按钮
        crawBtn.on("click", function () {
            layer.confirm('确定要爬取债券市场数据吗？', {
                btn: ['是','否'] //按钮
            }, function(){
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'bondMarket/craw',
                    method : 'GET'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        toastr.success(data.content);
                        _refreshCurrentTable();
                    }
                );
            }, function(){
                layer.closeAll();
            });
        });
    };


    var _refreshCurrentTable = function () {
            // 刷新表格
            bootstrapTableEl.bootstrapTable("refresh", {
                url: queryUrl
            });
    };

    return {
        /**
         * 页面初始化
         */
        init: function () {
            _btnEvent();
            _tableInit();
        }
    }
}();


/**
 * dom加载完成
 */
$(function () {
    bondMarketSearch.init();
});