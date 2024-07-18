 import * as THREE from 'three';
 import {
     camera,
     controls
 } from './Camera.js' //相机对象
 import {
     GLTFLoader
 } from 'three/addons/loaders/GLTFLoader.js';
 const person = new THREE.Group();
 const loader = new GLTFLoader(); //创建一个加载器
 let mixer = null; //声明一个混合器变量
 // 加载人模型
 loader.load("./Soldier.glb", function (gltf) {
     //  console.log('动画数据', gltf.animations);
     gltf.scene.traverse(function (obj) {
         if (obj.type === 'SkinnedMesh') {
             // 设置产生投影的网格模型
             obj.castShadow = true;
             // 设置接收阴影的投影面
             obj.receiveShadow = true;
         }
     });
     person.add(gltf.scene);
     //骨骼网格模型作为参数创建一个混合器
     mixer = new THREE.AnimationMixer(gltf.scene);
     // gltf.animations[3]：步行动画  walk
     //  gltf.animations[2]站着静止
     //  gltf.animations[1]：跑步动画
     //  gltf.animations[0]：休闲微小动作状态  idle
     const idleAction = mixer.clipAction(gltf.animations[0]);
     const walkAction = mixer.clipAction(gltf.animations[3]);
     //  所有动画设置为播放模式，通过权重.weight来控制如何执行
     // 稳定状态，一个权重.weight为1，其它的为0，切换状态是此消彼长,0变化1,1变为0，混合作用
     idleAction.play();
     walkAction.play();
     idleAction.weight = 1.0;
     walkAction.weight = 0.0;
     //  切换为休闲状态idle
     person.idle = function () {
         idleAction.enabled = true;
         idleAction.setEffectiveTimeScale(1);
         idleAction.setEffectiveWeight(1);
        //  // idleAction.weight权重上升为1，walkAction.weight权重下降为0
         walkAction.crossFadeTo(idleAction, 1,true);
     };
     // 切换为步行状态walk
     person.walk = function () {
         walkAction.enabled = true;
         walkAction.setEffectiveTimeScale(1);
         walkAction.setEffectiveWeight(1);
        //  // walkAction.weight权重上升为1，idleAction.weight权重下降为0
         idleAction.crossFadeTo(walkAction, 0.5,true);
     };

     // 骨骼辅助显示
     //  const skeletonHelper = new THREE.SkeletonHelper(gltf.scene);
     //  person.add(skeletonHelper);
     //  临时放在门口
     const x = 40,
         y = 1.8, //相机和人眼睛高度相似
         z = 40;
     camera.position.set(x, y, z);
     person.position.copy(camera.position);
     person.position.y = 0;


     person.position.z -= 5;

     //  相机控件以人为中心旋转
     camera.lookAt(person.position);
     controls.target.copy(person.position);
     controls.update();


     // 创建一个时钟对象Clock
     const clock = new THREE.Clock();

     function loop() {
         requestAnimationFrame(loop);
         if (mixer !== null) {
             //clock.getDelta()方法获得两帧的时间间隔
             // 更新混合器相关的时间
             mixer.update(clock.getDelta());
             console.log('walkAction', walkAction.getEffectiveWeight());
             console.log('idleAction', idleAction.getEffectiveWeight());

         }
     }
     loop();
 });




 export default person;