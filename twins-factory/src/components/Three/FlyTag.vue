<template>
  <!-- css3渲染器会把该div元素默认渲染为绝对定位，默认HTML标签的几何中心与标注scene世界坐标重合 -->
  <!-- 标签底部竖线距离标注框几何中心距离12px+10px+1px+1px+30px=54px -->
  <div id="flytag" style="display: none; z-index: 20; width: 170px;">
    <div class="tag" style="
        background-color: rgba(0, 255, 255, 0.1);
        padding: 10px;
        color: #fff;
        font-size: 16px;
        height: 24px;
        line-height: 24px;
      ">
      <img src="../../../assets/地点.png" style="width: 18px" />
      包河区-中科大
    </div>
    <div style="margin-left: 65px; height: 1px; width: 40px; background: #00ffff"></div>
    <div style="margin-left: 85px; height: 30px; width: 1px; background: #00ffff"></div>
  </div>
</template>

<script>
// {  CSS3DObject,CSS3DSprite }
import * as THREE from 'three';
import { CSS3DSprite } from "three/examples/jsm/renderers/CSS3DRenderer.js";
import { model } from "../scene/model.js";
import { flyGroup } from "../scene/flyGroup.js"; // 无人机模型
import { lon2xy } from "../scene/math.js";
import EN from '../config.js'//场景中心经纬度坐标
export default {
  name: "flyTag",
  data: function () {
    return {
      height: 300, //无人机飞行高度300米
      E: EN.E,
      N: EN.N,
    };
  },
  mounted: function () {
    model.add(flyGroup);
    var xy = lon2xy(this.E, this.N);
    var x = xy.x;
    var y = xy.y;
    // 设置无人机坐标
    flyGroup.position.set(x, y, this.height);


    const E = this.E; //无人机绕飞的中心
    const N = this.N;
    const center = lon2xy(E, N);

    // 无飞机圆周绕飞动画
    var angle = Math.PI;
    const R = 1000;//绕转半径
    const v1 = new THREE.Vector3(1, 0, 0);
    function loop() {

      requestAnimationFrame(loop);
      angle += 0.01;
      // 相机y坐标不变，在XOY平面上做圆周运动
      const x = R * Math.sin(angle) + center.x;
      const y = R * Math.cos(angle) + center.y;
      flyGroup.position.x = x;
      flyGroup.position.y = y;

      const x2 = R * Math.sin(angle + 0.001) + center.x;
      const y2 = R * Math.cos(angle + 0.001) + center.y;
      const v2 = new THREE.Vector3(x2 - x, y2 - y, 0).normalize();
      const quat = new THREE.Quaternion().setFromUnitVectors(v1, v2);
      // 随着轨迹线的变化，机头始终朝向运动方向
      flyGroup.quaternion.copy(quat);
    }
    loop();
    var label = tag(300); //创建标签对象
    // label.position.z = 100;
    // 54尺寸来自HTML标签元素竖线底部距离方框中心的像素
    label.position.z = 54 + flyGroup.R + 10;//标签标注的竖线底部，指向发光球顶部点附近
    // console.log('flyGroup.R;',flyGroup.R)
    flyGroup.add(label); //标签对象添加到三维场景

    // 创建一个HTML标签
    function tag(size) {
      // 获取div元素(作为标签)
      var div = document.getElementById("flytag");
      div.style.display = "block"; //HTML标签代码中设置了display:none;，这里改为'block'
      // var label = new CSS3DObject(div);//HTML标签对象 类似矩形平面Mesh
      var label = new CSS3DSprite(div); //HTML标签对象 类似Sprite
      // CSS3标签HTML元素渲染大小由自身像素尺寸和scale属性决定
      var w = div.offsetWidth*0.8; //获取标签HTML元素宽度
      // console.log("w", w);
      label.scale.set(size / w, size / w, size / w); //缩放CSS3DObject模型对象
      // 设置HTML元素标签位置
      // label.position.set(x, y, z);
      div.style.pointerEvents = "none"; //避免HTML标签遮挡三维场景的鼠标事件
      return label; //返回CSS3模型标签
    }
  },
};
</script>

<style scoped>
</style>
