var articleDetail = function () {
    // 文章id
    var id = commonUtil.getUrlParam("id"),
        url = 'article/getCrawlerContent/' + id,
        dataRow = $("#dataRow"),
        title = $("title"),
        handleBarScript1 = $("#handleBarScript1"),
        backToTop = $("#back-to-top"),
        bodyHtml = $('body, html');

    var init = function () {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + url,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                dataRow.html('');
                fecthData(data);
            }
        );

        backToTop.click(function(){
            bodyHtml.animate({scrollTop:0},1000);
            return false;
        });

        $(window).scroll(function(){
            var $this = $(this),
                scrollTop = $this.scrollTop();//滚动高度
            if (scrollTop > 400){
                backToTop.fadeIn(1500);
            } else {
                backToTop.fadeOut(1500);
            }
        });
    };

    // 获取数据
    var fecthData = function (data) {
        var content = data.content;
        if(!content) {
            toastr.error("没有该文章！");
        }else {
            // 首先生成第一个模版
            var tpl = handleBarScript1.html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            //匹配json内容
            var html = template(content);
            //输入模板
            dataRow.prepend(html);
            title.html(content.title);
            // 获得可视宽度
            var cWidth = document.documentElement.clientWidth;
            // 遍历每一张图片
            $("img").each(function () {
                var $this = $(this);
                // 获得这个图片的实际宽度
                var actualWidth = parseInt($this.css("width"));
                // 获得这个图片的实际高度
                var actualHeight = parseInt($this.css("height"));
                // 获得高度和宽度的比
                var pix = actualHeight / actualWidth;
                // 网站适配，如果是电脑屏幕就把图片变成原图片的4分之一宽度，防止图片太大
                if(cWidth > 1024) {
                    cWidth = cWidth / 4;
                }
                // 给宽高设置，防止移动端显示错乱
                $this.css("width", cWidth);
                $this.css("height", cWidth * pix);
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