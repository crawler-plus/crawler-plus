var userMgmt = function () {
    // bootstrap表格
    var bootstrapTableEl = $("#userList");
    // 查询按钮
    var queryBtnEl = $("#queryBtn");
    // 用户姓名
    var name = $("#name");
    // 新增按钮
    var addBtnEl = $("#addUser");
    // 用户查询url
    var userQueryUrl= comm.url + "user/queryAll";
    //  浏览按钮
    var viewBtnEl = '<a class="view ml10 btn btn-sm btn-primary" href="javascript:void(0)" title="查看">查看</a>';
    //  修改按钮
    var updateBtnEl = '<a class="update ml10 btn btn-sm btn-warning" href="javascript:void(0)" title="修改">修改</a>';
    //  删除按钮
    var deleteBtnEl = '<a class="delete ml10 btn btn-sm btn-danger" href="javascript:void(0)" title="删除">删除</a>';

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
                    name: queryObject.name
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

        // 点击新增按钮
        addBtnEl.on("click", function () {
            // 新选项卡打开
            var params = {
                action: "add"
            };
            commonUtil.layerOpen("新增用户信息", commonUtil.buildUrlParam("../crawler/userAdd.html", params));
        });
    };


    var _refreshCurrentTable = function () {
            // 重置查询条件
            queryObject.name = name.val();
            // 刷新表格
            bootstrapTableEl.bootstrapTable("refresh", {
                url: userQueryUrl,
                query: {
                    name: queryObject.name
                }
            });
    };

    /**
     * 判断用户是否存在
     * @param id
     * @private
     */
    var _checkUserExists = function (id, callback) {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'user/checkUserExists/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                if(data.msgCode === '400') {
                    toastr.error(data.content);
                    _refreshCurrentTable();
                }else {
                    callback();
                }
            }
        );
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
            actionArr.push(updateBtnEl);
            actionArr.push(deleteBtnEl);
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
                _checkUserExists(id, function () {
                    var params = {
                        userId: id
                    };
                    commonUtil.layerOpen("查看用户信息", commonUtil.buildUrlParam("../crawler/userDetail.html", params));
                });
            },
            "click .update": function (e, value, row, index) {
                var id = row.id;
                _checkUserExists(id, function () {
                    var params = {
                        userId: id
                    };
                    commonUtil.layerOpen("修改用户信息", commonUtil.buildUrlParam("../crawler/userUpdate.html", params));
                });
            },
            "click .delete": function (e, value, row, index) {
                var id = row.id;
                _checkUserExists(id, function () {
                    layer.confirm('确定要删除吗？', {
                        btn: ['是','否'] //按钮
                    }, function(){
                        var signOptions = {
                            formID : null,
                            isFormData : false
                        };
                        var ajaxOptions = {
                            url: comm.url + 'user/delete/' + id,
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
    userMgmt.init();
});