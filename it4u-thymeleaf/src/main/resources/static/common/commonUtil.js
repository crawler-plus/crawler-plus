var comm = {
    url : '/'
};

var commonUtil = function () {

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

    return {
        getUrlParam: _getUrlParam
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
});