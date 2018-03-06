let stat = function () {

    let init = function () {

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        $.ajax({
            type: "GET",
            url: comm.url + "operationSystemStat",
            dataType : "json",
            async: false,
            success: function (result) {
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '网站浏览器点击次数分布图',
                        x:'center'
                    },
                    tooltip: {},
                    legend: {
                        data:['分布']
                    },
                    xAxis: {
                        data: result.namesList
                    },
                    yAxis: {},
                    series: [{
                        name: '分布统计图',
                        type: 'bar',
                        data: result.valuesList
                    }]
                };
                myChart.setOption(option);
            }
        })
        $.ajax({
            type: "GET",
            url: comm.url + "terminalStat",
            dataType : "json",
            async: false,
            success: function (result) {
                var option2 = {
                    title : {
                        text: '站点访问来源点击次数分布图',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: result.namesList
                    },
                    series : [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:result.data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                myChart2.setOption(option2);
            }
        })
        $.ajax({
            type: "GET",
            url: comm.url + "totalClickCountStat",
            dataType : "json",
            async: false,
            success: function (result) {
                $("#totalClickCount").text(result.totalClickCount);
            }
        })
    };

    return {
        init: init
    }
}();
$(function () {
    stat.init();
});