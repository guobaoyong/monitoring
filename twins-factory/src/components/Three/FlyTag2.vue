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
      杨浦区-复旦大学
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
import { camera } from '../RendererCamera.js';
// import { normalize } from "path";
export default {
  name: "flyTag",
  data: function () {
    return {
      height: 500, //无人机飞行高度300米
      E: 121.49026536464691, //失火经纬度坐标
      N: 31.22289350905988,
    };
  },
  mounted: function () {
    model.add(flyGroup);
    var xy = lon2xy(this.E, this.N);
    var x = xy.x;
    var y = xy.y;
    // 设置无人机坐标
    flyGroup.position.set(x, y, this.height);


    const E = 121.49526536464691; //无人机绕飞的中心
    const N = 31.22489350905988;
    const center = lon2xy(E, N);

    // 无飞机圆周绕飞动画
    var angle = Math.PI;
    const R = 1500;//绕转半径
    const v1 = new THREE.Vector3(1, 0, 0);
    // console.log('flyGroup.position',flyGroup.position);//x:13524234, y:3661730, z:500
    flyGroup.pos = new THREE.Vector3(13524234, 3661730, 500);
    function loop() {

      requestAnimationFrame(loop);
      angle += 0.02;
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


      
      // 相机位置和目标观察点相对无人机位置不变
      const spVec3 = new THREE.Vector3();
      spVec3.subVectors(flyGroup.position, flyGroup.pos);
      camera.position.addVectors(camera.pos, spVec3);
      const target =  new THREE.Vector3();
      target.addVectors(camera.tar, spVec3);
      camera.lookAt(target);




      // camera.position.copy(flyGroup.position);
      // camera.position.z -= 300;
      // camera.position.x += 300;
      // camera.position.y += 300;
      // 这种写法不行，换一种写法，计算出来无人机的实时经纬度坐标，然后把无极人机坐标底部当做原点然后观察
      // 相机空间先调试出来一个最佳无人机观察视角
      // const tar = new THREE.Vector3();
      // tar.copy(flyGroup.position);
      // tar.z = 0;
      // camera.position.addVectors(tar.clone(),camera.vec3L.clone());
      // camera.lookAt(tar);
      // // 相机方向不改变，只改变位置
      // camera.position.copy(flyGroup.position);
      // camera.position.z += 300;
      // const cv = new THREE.Vector3();
      // cv.copy(flyGroup.position);
      // cv.addScaledVector(v2, -2);
      // camera.position.set(cv.x, cv.y, flyGroup.position.z + 2);
      // camera.lookAt(flyGroup.position);
      // camera.quaternion.multiply(quat);//相机姿态和无人机同步
      //  俯视图观察，相机跟着无人机同步姿态旋转，视觉效果，无人机不转动，整个城市转动
      // 模拟人坐在无人机上，同时人的视觉跟随无人机航行方向变化而变化
      // camera.position.copy(flyGroup.position);
      // camera.position.z += 500;
      // camera.lookAt(flyGroup.position);
      // const cv = new THREE.Vector3();
      // cv.copy(flyGroup.position);
      // cv.addScaledVector(v2, -2);
      // camera.quaternion.multiply(quat);//相机姿态和无人机同步
      // camera.rotateZ(Math.PI/2);//无人机朝上
      // // 相机动画：跟随无人机俯视城市
      // camera.position.copy(flyGroup.position);
      // camera.position.z += 500;
      // camera.lookAt(flyGroup.position);
      // //在无人机的位置，看向固定地点
      // camera.position.copy(flyGroup.position);
      // camera.lookAt(center.x, center.y, 0);
    }
    loop();

    // camera.fov = 90;//设置相机动画对应的视角
    // camera.updateProjectionMatrix();



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
      var w = div.offsetWidth; //获取标签HTML元素宽度
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
