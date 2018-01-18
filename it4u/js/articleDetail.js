var articleDetail = function () {
    // 文章id
    var id = commonUtil.getUrlParam("id");

    var init = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'article/getCrawlerContent/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                $("#dataRow").html('');
                fecthData(data);
            }
        );
    };

    // 获取数据
    var fecthData = function (data) {
        if(!data.content) {
            layer.msg('没有该文章！', {icon: 5});
        }else {
            // 首先生成第一个模版
            var tpl = $("#handleBarScript1").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            //匹配json内容
            var html = template(data.content);
            //输入模板
            $("#dataRow").html(html);
            $("title").html(data.content.title);
            // 获得可视宽度
            var cWidth = document.documentElement.clientWidth;
            // 遍历每一张图片
            $("img").each(function () {
                // 获得这个图片的实际宽度
                var actualWidth = parseInt($(this).css("width"));
                // 获得这个图片的实际高度
                var actualHeight = parseInt($(this).css("height"));
                // 获得高度和宽度的比
                var pix = actualHeight / actualWidth;
                // 网站适配，如果是电脑屏幕就把图片变成原图片的4分之一宽度，防止图片太大
                if(cWidth > 1024) {
                    cWidth = cWidth / 4;
                }
                // 给宽高设置，防止移动端显示错乱
                $(this).css("width", cWidth);
                $(this).css("height", cWidth * pix);
            });
        }
    };

    return {
        init: init
    }
}();
$(function () {
    articleDetail.init();
});