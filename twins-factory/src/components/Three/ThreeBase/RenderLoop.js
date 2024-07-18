import * as THREE from 'three';
// import './gui.js'
// import {
//   guiControls
// } from './gui.js'
import {
  model
} from './model.js';
import {
  scene
} from './scene.js' //Three.js三维场景
import {
  camera,
  // controls
} from './Camera.js' //相机对象
import {
  renderer
} from './Renderer.js' //渲染器对象
import {
  composer,
  bloomComposer
} from './composer.js' //composer取代renderer渲染器对象渲染场景
import {
  CSS2LabelRenderer
} from './CSS2DRenderer.js';
import {
  CSS3LabelRenderer
} from './CSS3DRenderer.js';

import TWEEN from '@tweenjs/tween.js'
//引入性能监视器stats.js,显示帧率
import Stats from 'three/addons/libs/stats.module.js';
import config from './config.js';
const stats = new Stats();
document.body.appendChild(stats.domElement);



const bloomLayer = new THREE.Layers();
bloomLayer.set(1);
const darkMaterial = new THREE.MeshBasicMaterial({
  color: 0x000000,
});
const materials = {};


// 渲染循环
function render() {
  if (config.BloomBool) {
    scene.traverse((obj) => {
      if (obj.isMesh && bloomLayer.test(obj.layers) === false) {
        materials[obj.uuid] = obj.material; //通过uuid记录先记录下obj对应的材质
        // 图层不是1的mesh设置黑色材质
        obj.material = darkMaterial;
      }
    });
    bloomComposer.render();
    scene.traverse((obj) => {
      if (materials[obj.uuid]) {
        // 设置为黑色材质的mesh恢复为原来自身的颜色
        obj.material = materials[obj.uuid];
        delete materials[obj.uuid];
      }
    });

  }



  stats.update(); //渲染循环中执行stats.update()来刷新时间
  TWEEN.update(); //tween更新(渲染时间相关,便于动画计算)
  CSS2LabelRenderer.render(scene, camera); //渲染HTML标签对象
  CSS3LabelRenderer.render(scene, camera);
  // renderer.render(scene, camera); //执行渲染操作
  composer.render(scene, camera); //执行渲染操作
  requestAnimationFrame(render); //请求再次执行渲染函数render，渲染下一帧
  // console.log(camera.position);//通过相机控件OrbitControls旋转相机，选择一个合适场景渲染角度
  // console.log(controls.target);
}
render();