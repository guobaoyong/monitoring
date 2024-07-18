// 引入dat.gui.js库
import {
    GUI
} from 'three/addons/libs/lil-gui.module.min.js';

// 光源调节

// 阴影调节

// 环境贴图控制



//引入光源对象
import { ambient, directionalLight,helper } from './scene.js'

import { scene } from './scene.js';

/**
 * 创建三维场景的控制界面
 */
//创建控件对象变量
var guiControls = {
//   envMapIntensity: 0.5,
  旋转: false,//产品模型是否旋转
  ambient: ambient.intensity,//环境光强度
  平行光强度: directionalLight.intensity,
  // 平行光坐标
  x1: directionalLight.position.x,
  y1: directionalLight.position.y,
  z1: directionalLight.position.z,
};
//关联空间数据创建交互界面
const gui = new GUI();//创建GUI对象
// var folder = gui.addFolder('菜单');//添加文件夹
//设置交互界面位置
gui.domElement.style = 'position:absolute;top:0px;right:0px;width:300px;';
// // 材质.envMapIntensity属性值变化范围0.0~1.0
// var envMapIntensityGUI = gui.add(guiControls, 'envMapIntensity', 0.0, 1.0);//添加环境贴图影响因子菜单选项
// // 事件函数.onChange：当通过UI界面envMapIntensity改变的时候，同步改变材质属性.envMapIntensity
// envMapIntensityGUI.onChange(function (value) {
//   mobilePhoneMesh.material.envMapIntensity = value;//设置环境贴图对模型表面影响程度
// });
// 控制模型旋转
gui.add(guiControls, '旋转');
// 控制环境光强度  最大强度值可以先随便给一个比较大，然后看看效果，在缩小范围调整
var ambientGUI = gui.add(guiControls, 'ambient', 0.0, 1.0);
ambientGUI.onChange(function (value) {
  ambient.intensity = value;//改变环境光强度
});
// 平行光强度
var directionalLightGUI = gui.add(guiControls, '平行光强度', 0.0, 5.0);
directionalLightGUI.onChange(function (value) {
  directionalLight.intensity = value;//改变平行光强度
});
// 平行光位置
var x1 = gui.add(guiControls, 'x1', -400, 400);
var y1 = gui.add(guiControls, 'y1', -400, 400);
var z1 = gui.add(guiControls, 'z1', -400, 400);
x1.onChange(function (value) {
  directionalLight.position.x = value;
  helper.update()
});
y1.onChange(function (value) {
  directionalLight.position.y = value;
  helper.update()
});
z1.onChange(function (value) {
  directionalLight.position.z = value;
  helper.update()
});

function loop() {

    // mesh.scale.x = controls.缩放系数; //更新缩放系数
    // material.color.setStyle(controls.颜色); //更新颜色
   if(guiControls.旋转) scene.rotateY(0.01);
    
    requestAnimationFrame(loop); //请求再次执行loop
}
loop();

// export { guiControls }

