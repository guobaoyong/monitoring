import * as echarts from 'echarts';
export default function (id) {
    const myChart = echarts.init(document.getElementById(id));

    const colorArr1 = ['#33ffff', '#00aeff', '#00ffc9', '#ff6232', '#fff024', "#aba8ff"]
    const colorArr2 = ['#0099aa', '#006ced', '#00ef99', '#ec2202', '#efc004', "#8b78f6"]

    const colorArr = [];//批量生成渐变色
    for (let i = 0; i < colorArr1.length; i++) {
        colorArr.push(new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
            offset: 0,
            color: colorArr1[i]
        },
        {
            offset: 1,
            color: colorArr2[i]
        }
        ]));

    }

    // const colorArr = ['#33ffff', '#006ced', '#00ff99',  '#fc4212', '#ffe004', "#8b78f6"]


    // const colorArr = [new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
    //                         offset: 0,
    //                         color: '#33ffff'
    //                     },
    //                     {
    //                         offset: 1,
    //                         color: '#0099aa'
    //                     }
    //                 ]), '#006ced', '#00ff99',  '#fc4212', '#ffe004', "#8b78f6"]
    // 指定图表的配置项和数据
    const option = {
        // 调整图表相对canvas画布的分布
        grid: {
            left: "0px",
            top: "0px",
            right: "30px",
            bottom: "0px",
            containLabel: true
        },
        // x轴数据和样式设置
        xAxis: {
            axisTick: {
                show: false, //不显示坐标轴刻度线
            },

            splitLine: {
                show: false, //分割线不显示
            },
            show: false, //标注的具体值不显示
        },
        // y轴样式设置
        yAxis: {
            inverse: true, //改变柱子默认渲染的顺序，和数组data顺序自上而下匹配
            data: ['印度', '中国', '美国', '日本', '越南', '印尼'], //数据放y轴上，柱子水平显示
            axisTick: {
                show: false, //不显示坐标轴刻度线
            },
            axisLine: {
                show: false, //不显示坐标轴
                // show: true, //默认显示坐标轴
                // lineStyle: { //坐标轴样式
                //   color: '#ffffff',
                //   width: 0.2, //调整坐标轴粗细
                // }
            },
            axisLabel: {
                color: '#ffffff', //x轴文字颜色
                fontWeight: 100, //x轴文字粗细
            },
            // inverse: true,
        },

        // 柱子数据和样式设置
        series: [{
            type: 'bar',
            data: [200, 300, 100, 100, 100, 300], //柱子对应数据
            itemStyle: {
                borderRadius: 6,
                color: function (params) { //柱子设置多种颜色
                    return colorArr[params.dataIndex];
                },
                //柱子渐变色
                // color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                //     offset: 0.5,
                //     color: '#33ffff'
                // },
                // {
                //     offset: 1,
                //     color: '#0099aa'
                // }
                // ]),
            },
            barWidth: 12, //柱子粗细
            label: { //柱子标注内容设置
                show: true, //显示柱子数值
                position: 'right', //柱子对应数字显示在柱子右侧
                distance: 5, //标注相对柱子顶部偏移的距离
                color: "#ffffff", //柱子标注内容的颜色
                fontSize: 12, //柱子标注的字体大小
                fontWeight: 100, //柱子标注的字体粗细
            },

            showBackground: true, //显示柱子的背景
            backgroundStyle: {
                color: 'rgba(50, 255, 255, 0.1)', //背景颜色和透明度
                borderRadius: 6, //背景倒角
            }
        }],
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}