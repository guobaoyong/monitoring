// 场景总文件
// 引入Three.js
import * as THREE from 'three';
import {
    model
} from './model.js';
import personLight from './personLight.js'
/**
 * 创建场景对象Scene
 */
const scene = new THREE.Scene();
scene.add(model); //model添加到场景中
// scene.add(personLight);
const target = new THREE.Object3D();
scene.add(target);
personLight.target = target ;
scene.personLight =  personLight;
/**
 * 光源设置
 */
// 平行光
const directionalLight = new THREE.DirectionalLight(0xffffff, 2.9);
directionalLight.position.set(-125, 150, 75);
scene.add(directionalLight);
scene.directionalLight =  directionalLight;
// 光源绕y轴旋转动画，阴影跟着变化
// let angle = 0;
// function loop() {
//     angle+=0.01;
//     directionalLight.position.x = 200*Math.sin(angle);
//     directionalLight.position.z = 200*Math.cos(angle);
//     requestAnimationFrame(loop);
// }
// loop();




// 设置用于计算阴影的光源对象
directionalLight.castShadow = true;

// 设置计算阴影的区域，最好刚好紧密包围在对象周围
// 计算阴影的区域过大：模糊  过小：看不到或显示不完整
directionalLight.shadow.camera.near = 0.5;
directionalLight.shadow.camera.far = 3000;
directionalLight.shadow.camera.left = -500;
directionalLight.shadow.camera.right = 500;
directionalLight.shadow.camera.top = 500;
directionalLight.shadow.camera.bottom = -500;
// 为了清晰度，提升阴影贴图的尺寸
directionalLight.shadow.mapSize.width = 1024 *4;
directionalLight.shadow.mapSize.height = 1024 * 4;

directionalLight.shadow.radius = 3;
// directionalLight.shadow.bias = -0.00006;
// directionalLight.shadow.bias = -0.00001;

// 可视化平行光阴影对应的正投影相机对象
const cameraHelper = new THREE.CameraHelper(directionalLight.shadow.camera);
// scene.add(cameraHelper);


// DirectionalLightHelper：可视化平行光
var helper = new THREE.DirectionalLightHelper(directionalLight, 5, 0xff0000);
// scene.add(helper);

// // 平行光2
// const directionalLight2 = new THREE.DirectionalLight(0xffffff, 1.9);
// directionalLight2.position.set(-400, -200, -300);
// scene.add(directionalLight2);
//环境光
const ambient = new THREE.AmbientLight(0xffffff, 0.2);
scene.add(ambient);

// Three.js三维坐标轴 三个坐标轴颜色RGB分别对应xyz轴
const axesHelper = new THREE.AxesHelper(2500);
// scene.add(axesHelper);

// import {
//     EXRLoader
// } from 'three/addons/loaders/EXRLoader.js';
// new EXRLoader().load('./环境全景图.exr', function (texture) {
//     // scene.background = texture;
//     // 全景图作为球体Mesh颜色纹理贴图
//     const geometry = new THREE.SphereGeometry(500, 50, 50);
//     // const geometry = new THREE.CylinderGeometry( 200, 200, 200, 32 );
//     const material = new THREE.MeshBasicMaterial({
//         map: texture,
//         side: THREE.BackSide, //默认前面可见，设置为背面可见即可
//     });
//     const mesh = new THREE.Mesh(geometry, material);
//     mesh.position.y+=20;
//     mesh.rotateY(Math.PI);
//     scene.add(mesh);
// })
// 全景图作为球体Mesh颜色纹理贴图
const texture = new THREE.TextureLoader().load("./全景图.jpg");
const texture2 = new THREE.TextureLoader().load("./全景图晚上.jpg");
const geometry = new THREE.SphereGeometry(1200, 50, 50);
// const geometry = new THREE.CylinderGeometry( 200, 200, 200, 32 );
// MeshBasicMaterial  MeshStandardMaterial   
const material = new THREE.MeshBasicMaterial({
    map: texture,
    side: THREE.BackSide, //默认前面可见，设置为背面可见即可
});
const mesh = new THREE.Mesh(geometry, material);
// mesh.position.y = -10;
// mesh.rotateY(Math.PI);
scene.add(mesh);
//设置纹理贴图编码方式和WebGL渲染器或composer后期伽马修正一致
material.map.encoding = THREE.sRGBEncoding;



// 设置雾化效果，雾的颜色和背景颜色相近，这样远处网格线和背景颜色融为一体
// scene.fog = new THREE.Fog(0xffffff, 400, 2000);
// mesh.scale.y *= 0.2; //天空球变成椭球，这样远处雾化，近处不雾化
const fog = new THREE.Fog(0x73a5c8, 900, 1600);
scene.fog = fog; //根据天空盒天空的颜色雾化


// 加载环境贴图
const textureCube = new THREE.CubeTextureLoader()
    .setPath('./环境贴图/环境贴图1/')
    .load(['px.jpg', 'nx.jpg', 'py.jpg', 'ny.jpg', 'pz.jpg', 'nz.jpg']);

textureCube.encoding = THREE.sRGBEncoding; //改变默认编码方式
// 设置影响所有模型的环境贴图
scene.environment = textureCube;
export {
    scene,
    ambient,
    directionalLight,
    helper,
    mesh,
    fog,
    texture,
texture2
};