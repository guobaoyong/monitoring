<template>
  <div style="">
    <div class="numCon" style="left: 640px;">
      <div style="color:#ffffff;padding: 10px 20px;">人次</div>
      <div id="all" class="DS-DIGIT" style="letter-spacing: 16px; font-size: 64px; color: #00ffff">
        68650
      </div>
    </div>
    <div class="numCon" style="left: 1060px;">
      <div style="color:#ffffff;padding: 10px 20px;">车次</div>
      <div id="all" class="DS-DIGIT" style="letter-spacing: 16px; font-size: 64px; color: #00ffff">
        33351
      </div>
    </div>
    <div class="data" style="left: 10px;">
      <div style="height: 240px">
        <div style="color:#ffffff;padding: 10px 20px;">车次(每小时)</div>
        <div id="echarts1" style="height: 200px"></div>
      </div>
      <div id="echarts2" style="height: 360px"></div>
      <div style="height: 280px">
        <div style="color:#ffffff;padding: 10px 20px;">计划完成度</div>
        <div id="echarts3" style="width: 350px; height: 240px"></div>
      </div>
    </div>
    <div class="data" style="right: 10px;">
      <div id="echarts4" style=" height: 240px"></div>
      <div id="echarts5" style=" height: 360px"></div>
      <div id="echarts6" style=" height: 280px"></div>
    </div>
  </div>
</template>

<script>
import line from "./echarts/line.js";
import ring from "./echarts/ring.js";
import shuizhu from "./echarts/shuizhu.js";
import shuzhu from "./echarts/shuzhu.js";
import water from "./echarts/water.js";
import linedan from "./echarts/linedan.js";
export default {
  name: "echarts",
  mounted: function () {
    linedan("echarts1");
    shuzhu("echarts2");
    water("echarts3");
    line("echarts4");
    ring("echarts5");
    shuizhu("echarts6");
  },
};
</script>


<style scoped>
.data {
  background-image: url(../assets/数据背景.png);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: absolute;
  padding: 10px 16px;
  top: 90px;
  width: 360px;
}

.data>div:not(:first-child) {
  margin-top: 40px;
  width: 350px;
}

.numCon {
  background-image: url(../assets/数字背景.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
        bottom: 10px;
        font-size: 16px;
        color: #33bbbb;
        padding: 10px 20px;
        width: 230px;
        border-radius: 0px;
}
</style>
