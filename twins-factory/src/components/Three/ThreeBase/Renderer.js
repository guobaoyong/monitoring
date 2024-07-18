import * as THREE from 'three';
import config from './config';
/**
 * 创建渲染器对象
 */
const renderer = new THREE.WebGLRenderer({
    antialias: true, //开启锯齿
    // 对数深度缓冲区解决深度冲突问题
    logarithmicDepthBuffer: true
});
renderer.setPixelRatio(window.devicePixelRatio); //设置设备像素比率,防止Canvas画布输出模糊。
renderer.setSize(config.cWidth, config.cHeight); //设置渲染区域尺寸

renderer.physicallyCorrectLights = true;//物理校正光照计算
// THREE.NoToneMapping
// THREE.LinearToneMapping
// THREE.ReinhardToneMapping
// THREE.CineonToneMapping
// THREE.ACESFilmicToneMapping//明暗对比更明显
// renderer.toneMapping = THREE.ACESFilmicToneMapping;
// renderer.toneMappingExposure = 1;

renderer.shadowMap.enabled = true; // 设置渲染器，允许场景中使用阴影贴图
renderer.shadowMap.type = THREE.VSMShadowMap; // 以免模型表面产生条纹影响渲染效果

// renderer.domElement表示Three.js渲染结果,也就是一个HTML元素(Canvas画布)
// document.body.appendChild(renderer.domElement); //body元素中插入canvas对象

// 设置three.js背景颜色 和雾化颜色相配   
// renderer.setClearColor(0x005577, 1);
// 使用了后处理compouser，renderer.outputEncoding的设置无效，使用伽马校正GammaCorrectionShader后处理解决
// renderer.outputEncoding = THREE.sRGBEncoding; //解决加载gltf格式模型纹理贴图和原图不一样问题

export {
    renderer
}