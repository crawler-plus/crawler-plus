var stat = function () {

    var init = function () {

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        var myChart3 = echarts.init(document.getElementById('main3'));
        var myChart4 = echarts.init(document.getElementById('main4'));


        var option4 = {
            title: {
                text: '按照日期点击量统计',
                x:'center'
            },
            xAxis: {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line'
            }]
        };
        myChart4.setOption(option4);

        $.ajax({
            type: "GET",
            url: comm.url + "operationSystemStat",
            dataType : "json",
            async: true,
            success: function (result) {
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '浏览器点击次数图',
                        x:'center'
                    },
                    tooltip: {},
                    legend: {
                        data:['分布']
                    },
                    xAxis: {
                        //设置字体倾斜
                        axisLabel:{
                            rotate:15 //倾斜度 -90 至 90 默认为0
                        },
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
            async: true,
            success: function (result) {
                var option2 = {
                    title : {
                        text: '访问来源点击次数图',
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
            url: comm.url + "cityStat",
            dataType : "json",
            async: true,
            success: function (result) {
                var option3 = {
                    title : {
                        text: '地市点击次数图',
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
                            name: '地市来源',
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
                myChart3.setOption(option3);
            }
        })
        $.ajax({
            type: "GET",
            url: comm.url + "totalClickCountStat",
            dataType : "json",
            async: true,
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