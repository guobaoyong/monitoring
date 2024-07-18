<template>
  <div ref="webgl">
    <!-- 首页加载期间的背景图片，加载完成隐藏 -->
    <!-- <img v-if="percentBool" ref="backimg" class="backimg" src="../../assets/首屏背景.jpg" alt="" width="100%" height="100%"> -->
    <!-- 半透明遮挡背景图片 -->
    <div ref="back" class="back" v-if="percentBool">
      <!-- 进度条 -->
      <el-progress class="percent" :text-inside="false" :stroke-width="6" :percentage="percent" />
    </div>
    <div v-if="!percentBool">
      <scene-tag v-for="(name, i) in SceneTagArr" :key="i" :name="name"></scene-tag>
    </div>

    <!-- v-if="model"：model初始值为空，gltf加载完成才赋值，才会调用control组件 -->
    <control  v-if="!percentBool"></control>

    <choose v-if="!percentBool"></choose>

    <!-- <shou-num v-if="model" :model="model"></shou-num> -->

    <!-- <car-tag v-if="carBool" :model="model" :car="car"></car-tag> -->
    <!-- <div v-if="carBool"> -->
    <!-- <car-tag :model="model" v-for="(obj,i) in carArr" :key="i" :car="obj"></car-tag> -->
    <!-- </div> -->
    <!-- <points-tag v-if="model" :model="model"></points-tag> -->


  </div>
</template>

<script>
// 引入Three.js
import * as THREE from 'three';
// 引入gltf模型加载库GLTFLoader.js
import {
  GLTFLoader
} from 'three/addons/loaders/GLTFLoader.js';
// DRACOLoader解析Draco压缩过的gltf模型
import { DRACOLoader } from 'three/addons/loaders/DRACOLoader.js';
import './ThreeBase/RenderLoop.js';//调用渲染循环，threejs输出Canvas画布渲染动画

import { scene } from './ThreeBase/scene.js';
// import { camera, controls } from './ThreeBase/Camera.js';
import { renderer } from './ThreeBase/Renderer.js';
import { outlinePass } from './ThreeBase/composer.js';
import control from './control.vue';
import choose from './choose.vue';
import SceneTag from "./SceneTag.vue"; //场景标注
// import CarTag from "./CarTag.vue"; //车辆可视化
// import PointsTag from "./PointsTag.vue"; //档杆热点
import { loaderPer } from './ThreeBase/model';//获取gltf模型加载的进度，控制vue组件执行

import {SceneTagArr} from './config.js';
export default {
  name: 'ThreeCanvas',
  data() {
    return {
      percent: 0,//三维场景模型加载进度百分比%
      percentBool: true,//进度条是否显示
      SceneTagArr: SceneTagArr,//场景标注标签名称——和模型中节点名称对应
    };
  },
  created() {
  },
  components: {
    control,
    choose,
    SceneTag,
    // CarTag,
    // PointsTag,
  },
  watch: {


  },
  mounted() {
    // 背景设置
    this.$refs.back.style.width = window.innerWidth + 'px';
    this.$refs.back.style.height = window.innerHeight + 'px';
    // threejs渲染结果canvas画布布局
    // this.$refs.webgl.appendChild(renderer.domElement);
    // // Three.js渲染结果Canvas画布插入到body元素中
    document.body.appendChild(renderer.domElement);
    renderer.domElement.style.position = "absolute";
    renderer.domElement.style.top = "0px";
    renderer.domElement.style.left = "0px";
    renderer.domElement.style.zIndex = "-1"; //canvas全屏，不遮挡其它HTML元素

    // 通过this改变data中进度条相关的数据
    loaderPer(this);



  },

  methods: {

  },
};
</script>

<style scoped>
.percent {
  /* 居中 */
  width: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -4px;
  margin-left: -200px;
  z-index: 11;
}

.back {
  position: absolute;
  top: 0px;
  left: 0px;
  background: rgba(0, 0, 0, 0.7);
  z-index: 10;
}

/* .backimg {
  position: absolute;
  top: 0px;
  left: 0px;
  z-index: 9;
} */
</style>