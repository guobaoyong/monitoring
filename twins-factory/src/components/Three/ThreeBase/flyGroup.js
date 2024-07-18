import * as THREE from 'three';


import {
    GLTFLoader
} from 'three/addons/loaders/GLTFLoader.js';





const flyGroup = new THREE.Group() // 一架无人机

flyGroup.R = 100;




const loader = new GLTFLoader();

loader.load('./无人机居中.glb', function (gltf) {
    gltf.scene.traverse(function (obj) {
        if (obj.type === 'Mesh') {
            // 设置产生投影的网格模型
            obj.castShadow = true;
            // 设置接收阴影的投影面
            obj.receiveShadow = true;
        }
    })
    const fly = new THREE.Group();
    fly.rotateY(Math.PI);//绕转世后，调整无人机头部对准绕转中心
    fly.add(gltf.scene);
    // fly.scale.set(5, 5, 5); //根据需要放大无人机
    // console.log('gltf', gltf);
    flyGroup.add(fly);
    fly.traverse(function (child) {
        if (child.isMesh) {
            const material = child.material
            child.material = new THREE.MeshLambertMaterial({
                color: material.color,
            })
        }
    });
    // obj作为混合器的参数，可以播放obj包含的帧动画数据
    //声明一个混合器变量
    const mixer = new THREE.AnimationMixer(fly);
    // console.log('gltf.animations[0]', gltf.animations);
    // obj.animations[0]：获得剪辑clip对象
    const AnimationAction = mixer.clipAction(gltf.animations[0]);
    AnimationAction.timeScale = 15; //默认1，可以调节播放速度
    // AnimationAction.loop = THREE.LoopOnce; //不循环播放
    // AnimationAction.clampWhenFinished=true;//暂停在最后一帧播放的状态
    AnimationAction.play();


    const clock = new THREE.Clock();

    function UpdateLoop() {
        if (mixer !== null) {
            //clock.getDelta()方法获得两帧的时间间隔
            mixer.update(clock.getDelta());
        }
        requestAnimationFrame(UpdateLoop);
    }
    UpdateLoop();

})





// 外卖盒
const L = 10;
const geometry2 = new THREE.BoxGeometry(L, L, L)
const texLoad = new THREE.TextureLoader();
const material2 = new THREE.MeshLambertMaterial({
    map: texLoad.load('./3D/外卖.jpg'),
});
const mesh = new THREE.Mesh(geometry2, material2);
mesh.rotateX(Math.PI / 2); //调整立方体姿态，以便于调整纹理图像姿态
mesh.position.y = -L / 2;
flyGroup.add(mesh);

const s = 0.7;
const center = new THREE.Vector3(20, 50, 0);
flyGroup.scale.set(s,s,s);

    // 无飞机圆周绕飞动画
    let angle = Math.PI;
    const R = 60;//绕转半径
    const v1 = new THREE.Vector3(1, 0, 0);
    function loop() {
      requestAnimationFrame(loop);
      angle += 0.01;
      // 相机y坐标不变，在XOZ平面上做圆周运动
      const x = R * Math.sin(angle) + center.x;
      const z = R * Math.cos(angle) + center.z;
      flyGroup.position.x = x;
      flyGroup.position.z = z;
      const x2 = R * Math.sin(angle + 0.001) + center.x;
      const z2 = R * Math.cos(angle + 0.001) + center.z;
      const v2 = new THREE.Vector3(x2 - x, 0, z2 - z).normalize();
      const quat = new THREE.Quaternion().setFromUnitVectors(v1, v2);
      // 随着轨迹线的变化，机头始终朝向运动方向
      flyGroup.quaternion.copy(quat);
    }
    loop();


export {
    flyGroup
};