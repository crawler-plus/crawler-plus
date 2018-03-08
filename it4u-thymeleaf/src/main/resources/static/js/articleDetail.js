var articleDetail = function () {
    var it4uConst = {
        backToTop : $("#back-to-top"),
        bodyHtml : $('body, html')
    }

    var init = function () {

        it4uConst.backToTop.click(function(){
            it4uConst.bodyHtml.animate({scrollTop:0},1000);
            return false;
        });

        $(window).scroll(function(){
            var $this = $(this),
                scrollTop = $this.scrollTop();//滚动高度
            if (scrollTop > 400){
                it4uConst.backToTop.fadeIn(1000);
            } else {
                it4uConst.backToTop.fadeOut(1000);
            }
        });

        // 获得可视宽度
        var cWidth = document.documentElement.clientWidth;
        // 遍历每一张图片
        $("img").each(function () {
            var $this = $(this),
                actualWidth = parseInt($this.css("width")),// 获得这个图片的实际宽度
                actualHeight = parseInt($this.css("height")), // 获得这个图片的实际高度
                pix = actualHeight / actualWidth; // 获得高度和宽度的比
            // 网站适配，如果是电脑屏幕就把图片变成原图片的4分之一宽度，防止图片太大
            cWidth = cWidth > 1024 ? cWidth / 4 : cWidth;
            // 给宽高设置，防止移动端显示错乱
            $this.css({"width" : cWidth, "height" : cWidth * pix});
        });
    };

    return {
        init: init
    }
}();
$(function () {
    articleDetail.init();
});