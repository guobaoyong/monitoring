import * as echarts from 'echarts';
export default function (id) {
  const myChart = echarts.init(document.getElementById(id), 'dark');
  const xdata = []
  const ydata = []
  for (let i = 0; i < 24; i++) {
      xdata.push(i + '')
      ydata.push(40 * Math.random().toFixed(1) + 20)
  }
  const option = {
      backgroundColor: 'rgba(255,255,255,0.0)',
      // title: {
      //     text: '白天和晚上用电量对比图',
      //     textStyle: {
      //       fontSize: 16,
      //       fontWeight: 'normal',
      //     }
      // },
      tooltip: {
          trigger: "axis",
          backgroundColor: 'rgba(0,0,0,0.6)',
          textStyle: {
              color: "rgba(220,220,220,1.0)",
              fontSize: "16",
          },
          borderWidth: "0",
      },
      // legend: {
      //     top: "0%",
      //     data: ["交通用电", "居民用电", "工业用电", ],
      //     textStyle: {
      //         color: "rgba(220,220,220,1.0)",
      //         fontSize: "16"
      //     }
      // },

      grid: {
          left: "10",
          top: "30",
          right: "10",
          bottom: "10",
          containLabel: true
      },
      // x轴样式
      xAxis: [{
          type: "category",
          boundaryGap: false,
          // x轴更换数据
          data: xdata,
          // 文本颜色为rgba(255,255,255,.6)  文字大小为 12
          axisLabel: {
              textStyle: {
                  color: "rgba(220,220,220,1.0)",
                  fontSize: 12
              }
          },
          axisTick: {
              show: false, //不显示坐标轴刻度线
          },
          axisLine: {
            lineStyle: {
                color: "rgba(0,150,150,0.5)",
            }
        },
      }],
      // y轴样式
      yAxis: [{
          
          type: "value",
          axisTick: {
              show: false
          },
          axisLine: {
              lineStyle: {
                  color: "rgba(0,150,150,0.5)",
              }
          },
          axisLabel: {
              textStyle: {
                  color: "rgba(220,220,220,1.0)",
                  fontSize: 12
              }
          },
          // 数据分割线虚线
          splitLine: {
              show: false, //坐标轴水平分割线不显示
              // lineStyle: {
              //     color: "rgba(0,200,200,0.3)"
              // }
          }
      }],
      series: [{
              name: "",
              type: "line",
              smooth: true, //折线光滑
              lineStyle: {
                  normal: {
                      color: "rgba( 50, 255,255, 1.0)", //折线颜色
                      width: 2
                  }
              },
              // 用于大屏可视化，设置一个渐变效果
              areaStyle: {

                  normal: {
                      // 折线颜色渐变效果
                      color: new echarts.graphic.LinearGradient(
                          0,
                          0,
                          0,
                          1,
                          [{
                                  offset: 0,
                                  color: "rgba( 50, 255,255, 0.7)"
                              },
                              {
                                  offset: 0.8,
                                  color: "rgba(50, 255,255,  0.0)"
                              }
                          ],
                          false
                      ),
                      // shadowColor: "rgba(0, 0, 0, 0.25)"
                  }
              },
              // 设置拐点 小圆点
              symbol: "circle",
              // 拐点大小
              symbolSize: 5,
              // 设置拐点颜色以及边框
              itemStyle: {
                  color: "rgba( 0, 255,255, 1.0)",
                  borderColor: "rgba( 0, 255,255, 1.0)",
                  borderWidth: 12
              },
              // 数据点是否显示， 鼠标经过显示
              showSymbol: false,
              data: ydata
          },
      ]
  };
  myChart.setOption(option);
  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}