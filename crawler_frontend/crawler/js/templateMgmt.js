var templateMgmt = function () {

    /**
     * 注册按钮事件
     */
    var registerBtn = function () {
        // 添加模版按钮
        $("#addTemplateBtn").on('click', function () {
            // 新选项卡打开
            var params = {
            };
            commonUtil.openUrlInIframe("添加模版", commonUtil.buildUrlParam("crawler/addTemplate.html", params));
        });
        // 刷新模版按钮
        $("#refreshTemplateBtn").on('click', function () {
            listAllTemplate();
        });

        // 执行爬取
        $("#executeCron").on('click', function () {
            layer.confirm('确定要执行爬取任务吗？', {
                btn: ['是','否'] //按钮
            }, function(){
                layer.closeAll();
                commonUtil.showLoadingMessage();
                var signOptions = {
                    formID : null,
                    isFormData : false
                };
                var ajaxOptions = {
                    url: comm.url + 'article/executeCron',
                    method : 'GET'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        layer.closeAll();
                        if(data.msgCode === '400') {
                            layer.msg(data.content, {icon: 5});
                        }
                    }
                );
            }, function(){
                layer.closeAll();
            });
        });
    };

    /**
     * 列出所有文章模版
     */
    var listAllTemplate = function () {
        $("#resultRow").empty();
        commonUtil.showLoadingMessage();
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/listAllTemplateConfig',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                layer.closeAll();
                    var dataLen = data.content.length;
                    if(dataLen > 0) {
                        var tpl = $("#resultScript").html();
                        //预编译模板
                        var template = Handlebars.compile(tpl);
                        for(var i = 0; i < dataLen; i ++) {
                            var eachData = data.content[i];
                            var json = {
                                "id": eachData.id,
                                "url": eachData.url,
                                "firstLevelPattern": eachData.firstLevelPattern,
                                "titlePattern": eachData.titlePattern,
                                "timePattern": eachData.timePattern,
                                "contentPattern": eachData.contentPattern
                            };
                            //匹配json内容
                            var html = template(json);
                            $("#resultRow").append(html);
                        }
                    }

                /**
                 * 删除模版
                 */
                $("button[class$=delTemplate]").on('click', function () {
                    var that = $(this);
                    var id = that.prop("name");
                    _checkTemplateConfigExists(id, function () {
                        layer.confirm('确定要删除吗？', {
                            btn: ['是','否'] //按钮
                        }, function(){
                            var signOptions = {
                                formID : null,
                                isFormData : false
                            };
                            var ajaxOptions = {
                                url: comm.url + 'article/removeTemplateConfig/' + id,
                                method : 'DELETE'
                            };
                            dataRequest.requestSend(
                                signOptions,
                                ajaxOptions,
                                function (data) {
                                    layer.closeAll();
                                    listAllTemplate();
                                }
                            );
                        }, function(){
                            layer.closeAll();
                        });
                    });
                });

                /**
                 * 修改模版
                 */
                $("button[class$=editTemplate]").on('click', function () {
                    var that = $(this);
                    var id = that.prop("name");
                    _checkTemplateConfigExists(id, function () {
                        // 新选项卡打开
                        var params = {
                            id:  id
                        };
                        commonUtil.openUrlInIframe("修改模版", commonUtil.buildUrlParam("crawler/editTemplate.html", params));
                    });
                });

            }
        );
    };

    var init = function () {
       // 调用后台接口，查询当前全部的模版
        listAllTemplate();
        registerBtn();
    };

    /**
     * 判断文章配置是否存在
     * @param id
     * @private
     */
    var _checkTemplateConfigExists = function (id, callback) {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/checkTemplateConfigExists/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                if(data.msgCode === '400') {
                    layer.msg(data.content, {icon: 5}, function () {
                        listAllTemplate();
                    });
                }else {
                    callback();
                }
            }
        );
    };

    return {
        init: init
    }
}();
$(function () {

    templateMgmt.init();

    // 滚到页面顶部滚动条初始化
    $.goup({
        trigger: 100,
        bottomOffset: 50,
        locationOffset: 10,
        title: '回顶部',
        titleAsText: true
    });
});