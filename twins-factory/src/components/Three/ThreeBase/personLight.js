 import * as THREE from 'three';
 // 平行光
 const directionalLight = new THREE.DirectionalLight(0xffffff, 2.9);
 directionalLight.position.set(-12.5, 15.0, 7.5);

//  光源方向计算
 directionalLight.dir =  directionalLight.position.clone().normalize();

 // 设置用于计算阴影的光源对象
 directionalLight.castShadow = true;
 
 // 设置计算阴影的区域，最好刚好紧密包围在对象周围
 // 计算阴影的区域过大：模糊  过小：看不到或显示不完整
 directionalLight.shadow.camera.near = 0.5;
 directionalLight.shadow.camera.far = 300;
 directionalLight.shadow.camera.left = -50;
 directionalLight.shadow.camera.right = 50;
 directionalLight.shadow.camera.top = 50;
 directionalLight.shadow.camera.bottom = -50;
 // 为了清晰度，提升阴影贴图的尺寸
 directionalLight.shadow.mapSize.width = 1024*2;
 directionalLight.shadow.mapSize.height = 1024*2;
 
 directionalLight.shadow.radius = 2;
 // directionalLight.shadow.bias = -0.00006;




 export default directionalLight;