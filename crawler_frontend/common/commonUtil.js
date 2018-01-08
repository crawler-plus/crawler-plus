var comm = {
    url : 'http://127.0.0.1:8050/main/',
    captchaUrl: 'http://127.0.0.1:8050/captcha/',
    useCaptcha: true
};

var commonUtil = function () {

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
    };

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
    };

    /**
     * 获取url中的参数
     * @param name
     */
    var _getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return decodeURIComponent(r[2]);
        return null; //返回参数值
    };

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
                url += "&" + key + "=" + encodeURIComponent(value);
            } else {
                url += "?" + key + "=" + encodeURIComponent(value);
            }
        }
        return url;
    };

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
        var endFunction = function(){};

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
    };

    /**
     * 在Iframe中打开新页面
     * @param title   //页面子标题
     * @param dataUrl //页面URL
     * @private
     */
    var _openUrlInIframe = function (title,dataUrl) {
        window.parent.contabs.openTab(title,dataUrl);
    };

    /**
     * 关闭tab页
     * @returns {boolean}
     */
    var closeTab = function () {
        window.parent.contabs.closeActiveTab();
    };

    /**
     * 禁用所有表单项
     * @private
     */
    var _disableForm = function (formElement) {
        formElement.find("input[type!='hidden']").attr("readonly", "true");
        formElement.find("select").attr("disabled", "true");
    };

    /**
     * 删除所有error样式
     * @private
     */
    var _removeError = function (formElement) {
        formElement.find("input.error").removeClass("error");
    };

    /**
     * 启用所有按钮
     * @private
     */
    var _enableBtn = function (formElement) {
        formElement.find("button").removeAttr("disabled");
    };

    /**
     * 禁用所有按钮
     * @private
     */
    var _disableBtn = function (formElement) {
        formElement.find("button").attr("disabled", "disabled");
    };

    var _showLoadingMessage = function () {
        layer.msg('拼命抓取中,请稍候......', {
            icon: 16
            ,shade: 0.01
            ,time: 1000 * 3600 // 1 hour
        });
    };

    var _getAccessToken = function () {
        return sessionStorage.getItem("token");
    };

    var _getUserId = function () {
        return sessionStorage.getItem("userId");
    };

    var _getTimestamp = function () {
        return sessionStorage.getItem("timestamp");
    };

    return {
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
        getAccessToken: _getAccessToken,
        getUserId: _getUserId,
        getTimestamp: _getTimestamp
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