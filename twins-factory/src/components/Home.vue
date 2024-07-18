<template>
  <div>
    <!-- <div> -->
      <img src="../assets/head.png" alt="">
    <!-- </div> -->
    <!-- three.js三维场景，以及3D场景对应的标签和UI控制界面 -->
    <three-canvas></three-canvas>

    <!-- 数据可视化图表 -->
    <!-- <echarts></echarts> -->
  </div>
</template>

<script>
import ThreeCanvas from './Three/ThreeCanvas.vue'
// import echarts from "./echarts.vue"; //2D可视化图表
export default {
  name: 'Home',

  data() {
    return {
      
    };
  },
  components:{
    ThreeCanvas,
    // echarts,
  },
  mounted() {
    
  },

  methods: {
    
  },
};
</script>

<style scoped>

</style>