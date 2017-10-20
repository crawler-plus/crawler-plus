var comm = {
    url : 'http://127.0.0.1:8088/'
}

var commonUtil = function () {

    /**
     * HTMLCODE字典
     * @type {{httpCode200: string, httpCode500: string, httpCode503: string, httpCode400: string, httpCode401: string, httpCode403: string, httpCode301: string, httpCode302: string}}
     */
    var httpCode = {
        "httpCode200": "服务器已成功处理了请求",
        "httpCode500": "服务器内部错误",
        "httpCode503": "服务不可用",
        "httpCode400": "用户令牌已过期，请重新登陆",
        "httpCode401": "请求要求身份验证",
        "httpCode403": "服务器找不到请求的资源",
        "httpCode301": "请求的网页已永久移动到新位置",
        "httpCode302": "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"
    }

    /**
     * 根据Code，匹配httpcode字典返回错误文字
     * @param code
     * @returns {{}}
     * @private
     */
    var _getHttpMessage = function (code) {
        var result = {};
        switch (String(code)) {
            case "200":
                result.message = httpCode.httpCode200;
                result.code = "200";
                break;
            case "500":
                result.message = httpCode.httpCode500;
                result.code = "500";
                break;
            case "503":
                result.message = httpCode.httpCode503;
                result.code = "503";
                break;
            case "400":
                result.message = httpCode.httpCode400;
                result.code = "400";
                break;
            case "401":
                result.message = httpCode.httpCode401;
                result.code = "401";
                break;
            case "403":
                result.message = httpCode.httpCode403;
                result.code = "403";
                break;
            case "301":
                result.message = httpCode.httpCode301;
                result.code = "301";
                break;
            case "302":
                result.message = httpCode.httpCode302;
                result.code = "302";
                break;
        }
        return result;

    }

    /**
     * 优化bootstrap初始化
     * @param element
     * @param option
     * @private
     */
    var _bootstrapTable = function (element, option) {
        if (!option.responseHandler) {
            option.responseHandler = function (res) {
                return res;
            }
        }

        if (!option.queryParams) {
            option.queryParams = function (params) {
                return {
                    limit: params.limit,
                    offset: params.offset,
                    sortOrder: params.order,
                    page: params.offset / params.limit + 1,
                    queryCriteria: params.search
                }
            }
        }

        if (!option.icons) {
            option.icons = {
                refresh: "glyphicon-repeat",
                toggle: "glyphicon-list-alt",
                columns: "glyphicon-list"
            }
        }

        if (!option.search) {
            option.search = true;
        }

        if (!option.sidePagination) {
            option.sidePagination = 'server';//设置为服务器端分页
        }

        if (!option.pagination) {
            option.pagination = true;
        }

        if (!option.showRefresh) {
            option.showRefresh = false;
        }

        option.search = false;

        if (!option.showToggle) {
            option.showToggle = false;
        }

        if (!option.showColumns) {
            option.showColumns = false;
        }

        if (!option.iconSize) {
            option.iconSize = "outline";
        }

        element.bootstrapTable(option);
    }

    /**
     * 对根据object的key值对Array进行排序
     * @param key
     * @param desc
     * @returns {Function}
     * @private
     */
    var _keySort = function (key, desc) {
        return function (a, b) {
            return desc ? ~~(a[key] < b[key]) : ~~(a[key] > b[key]);
        }
    }

    /**
     * 获取url中的参数
     * @param name
     * @returns {null}
     */
    var _getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }

    /**
     * 构造带参数url
     * @param url
     * @param obj
     * @returns {null}
     */
    var _buildUrlParam = function (url, obj) {
        for (var key in obj) {
            var value = obj[key];
            if (url.indexOf("?") > -1) {
                url += "&" + key + "=" + escape(value);
            } else {
                url += "?" + key + "=" + escape(value);
            }
        }
        return url;
    }

    /**
     * 打开layer
     * @param title 标题
     * @param url   链接
     * @param width     宽度
     * @param height    高度
     */
    var _layerOpen = function (title, url) {
        var height = "90%";
        var width = "800px";
        var endFunction = function(){}

        if (arguments[2]) {
            width = arguments[2];
        }

        if (arguments[3]) {
            height = arguments[3];
        }

        if (arguments[4]) {
            var endFunction = arguments[4]
        }

        layer.open({
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.5,
            area: [width, height],
            content: url, //iframe的url,
            end: endFunction
        });
    }

    /**
     * 在Iframe中打开新页面
     * @param title   //页面子标题
     * @param dataUrl //页面URL
     * @private
     */
    var _openUrlInIframe = function (title,dataUrl) {
        window.parent.contabs.openTab(title,dataUrl);
    }

    /**
     * 关闭tab页
     * @returns {boolean}
     */
    var closeTab=function () {
        window.parent.contabs.closeActiveTab();
    }

    /**
     * 禁用所有表单项
     * @private
     */
    var _disableForm = function (formElement) {
        formElement.find("input[type!='hidden']").attr("readonly", "true");
        formElement.find("select").attr("disabled", "true");
    }

    /**
     * 删除所有error样式
     * @private
     */
    var _removeError = function (formElement) {
        formElement.find("input.error").removeClass("error");
    }

    /**
     * 启用所有按钮
     * @private
     */
    var _enableBtn = function (formElement) {
        formElement.find("button").removeAttr("disabled");
    }

    /**
     * 禁用所有按钮
     * @private
     */
    var _disableBtn = function (formElement) {
        formElement.find("button").attr("disabled", "disabled");
    }

    var _showLoadingMessage = function () {
        layer.msg('拼命抓取中,请稍候......', {
            icon: 16
            ,shade: 0.01
            ,time: 1000 * 3600 // 1 hour
        });
    }

    var _getAccessToken = function () {
        return "12345"
    }


    return {
        getHttpMessage: function (e) {
            return _getHttpMessage(e)
        },
        getObjArrayViaSort: _keySort,
        getUrlParam: _getUrlParam,
        bootstrapTable: _bootstrapTable,
        layerOpen: _layerOpen,
        buildUrlParam: _buildUrlParam,
        openUrlInIframe:_openUrlInIframe,
        closeTab:closeTab,
        disableForm: _disableForm,
        removeError: _removeError,
        enableBtn: _enableBtn,
        disableBtn: _disableBtn,
        showLoadingMessage: _showLoadingMessage,
        getAccessToken: _getAccessToken
    }
}();
$(function () {
    if(window.toastr) {
        toastr.options = {
            closeButton: false,
            debug: false,
            positionClass: "toast-bottom-center",
            onclick: null,
            showDuration: "300",
            hideDuration: "500",
            timeOut: "1000",
            extendedTimeOut: "1000",
            showEasing: "swing",
            hideEasing: "linear",
            showMethod: "fadeIn",
            hideMethod: "fadeOut"
        };
    }

    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
});