var comm = {
    url : 'http://127.0.0.1:1007/main/'
};

var commonUtil = function () {

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

    return {
        getObjArrayViaSort: _keySort,
        getUrlParam: _getUrlParam,
        buildUrlParam: _buildUrlParam,
        openUrlInIframe:_openUrlInIframe,
        closeTab:closeTab
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