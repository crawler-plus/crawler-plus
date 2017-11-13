var logMgmt = function () {
    // bootstrap表格
    var bootstrapTableEl = $("#logList");
    // 查询按钮
    var queryBtnEl = $("#queryBtn");
    // 用户登录帐号
    var loginAccount = $("#loginAccount");
    // log查询url
    var logQueryUrl = comm.url + "log/queryAll";
    // 删除按钮
    var deleteAll = $("#delete");
    var queryObject = {};

    /**
     * 表格信息初始化
     * @private
     */
    var _tableInit = function () {
        // 初始化表格
        commonUtil.bootstrapTable(bootstrapTableEl, {
            url: logQueryUrl,
            queryParams: function (params) {
                return {
                    // 后台分页参数（不需要修改，也不要删除！）
                    limit: params.limit,
                    offset: params.offset,
                    sortOrder: params.order,
                    page: params.offset / params.limit + 1,
                    // 查询参数
                    loginAccount: queryObject.loginAccount
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

        // 点击删除按钮
        deleteAll.on("click", function () {
            layer.confirm('确定要删除吗？', {
                btn: ['是','否'] //按钮
            }, function(){
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'log/delete',
                    method : 'DELETE'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        if(data.msgCode === '400') {
                            toastr.error(data.content);
                        }else {
                            toastr.success(data.content);
                        }
                        _refreshCurrentTable();
                    }
                );
            }, function(){
                layer.closeAll();
            });
        });
    };


    var _refreshCurrentTable = function () {
            // 重置查询条件
            queryObject.loginAccount = loginAccount.val();
            // 刷新表格
            bootstrapTableEl.bootstrapTable("refresh", {
                url: logQueryUrl,
                query: {
                    loginAccount: queryObject.loginAccount
                }
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
    logMgmt.init();
});