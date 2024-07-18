// 引入Three.js
import * as THREE from 'three';
import {
    model
} from './model.js';
import personLight from './personLight.js'
import person from './person.js';
import {
    FirstPersonControls
} from 'three/addons/controls/FirstPersonControls.js';
import config from './config.js';

// width和height用来设置Three.js输出Canvas画布尺寸，同时用来辅助设置相机渲染范围
const width = window.innerWidth; //窗口文档显示区的宽度
const height = window.innerHeight; //窗口文档显示区的高度
/**
 * 相机设置
 */
//  透视投影
const camera = new THREE.PerspectiveCamera(45, width / height, 0.01, 30000);
// const camera = new THREE.PerspectiveCamera(60, width / height, 0.01, 30000);
// camera.position.set(100, 100, 100);
camera.position.set(config.cx, config.cy, config.cz); //通过相机控件OrbitControls旋转相机，选择一个合适场景渲染角度
// camera.lookAt(0,0 ,0);
let x = 0,
    y = 0,
    z = 0;
camera.lookAt(x, y, z);


// 引入Three.js扩展库
import {
    OrbitControls
} from 'three/addons/controls/OrbitControls.js';




//创建控件对象  控件可以监听鼠标的变化，改变相机对象的属性
// 旋转：拖动鼠标左键
// 缩放：滚动鼠标中键
// 平移：拖动鼠标右键
import {
    renderer
} from './Renderer';


const controls = new OrbitControls(camera, renderer.domElement);

controls.target.set(x, y, z);
controls.update(); //update()函数内会执行camera.lookAt(controls.targe)
controls.addEventListener('change', function () {
    // 相机视角旋转，人的姿态视线也旋转，人的旋转跟随相机变化
    person.quaternion.copy(camera.quaternion);
})

const flyheight = 1.8; //相机高度不变，改变相机xz坐标
const nextPos = new THREE.Vector3(); //射线点击建筑对应XOZ平面坐标

const dir = new THREE.Vector3(); //飞行漫游方向，起始点构成的方向 默认值0，0，0
let flyBool = false;





// const controls = new FirstPersonControls(camera, renderer.domElement);

// controls.movementSpeed = 70;
// 				controls.lookSpeed = 0.05;
// 				controls.noFly = true;
// 				controls.lookVertical = false;

camera.personPosition = new THREE.Vector3();


// 相机对应的人的位置  personPosition用来记录，相机上次位置，方便预览模式切换
camera.personPosition.set(config.perx, config.h, config.perz + 5);




// const clock = new THREE.Clock();
function loop() {
    requestAnimationFrame(loop);
    if (flyBool) {
        const dis = camera.position.clone().sub(nextPos).length()
        // console.log('dis', dis)
        if (dis > 0.1) { // 接近nextPos位置，相机停止飞行
            //每次渲染相机沿着视线移动距离递增(xyz每个分量乘以4)
            camera.position.add(dir.clone().multiplyScalar(0.2));
            // camera.personPosition = new THREE.Vector3();
            camera.personPosition.copy(camera.position);
            person.position.copy(camera.position);

            person.position.add(dir.clone().multiplyScalar(5));

            // 还需要重写，实时计算，适应坡度变化，感觉很有用
            // 向下发射射线计算
            person.position.y = nextPos.point.y; //紧贴地面，根据高度变化

            camera.lookAt(person.position);
            controls.target.copy(person.position);
            controls.update();

            const lightPos = person.position.clone().add(personLight.dir.clone().multiplyScalar(50));
            // 光源位置保持相对人不变
            personLight.position.copy(lightPos);
            // 光源目标对象指向人的位置
            personLight.target.position.copy(person.position);

            // personLight.shadow.camera.copy(lightPos);
            // personLight.shadow.camera.lookAt(person.position);
            // personLight.shadow.camera.matrixWorldNeedsUpdate(true)

        } else {
            flyBool = false; //停止飞行飞行
            person.idle(); //停止人步行动画
        }

    }
    // const delta = clock.getDelta();
    // controls.update( delta );
    // console.log('controls.autoRotate',controls.autoRotate);
}
loop();



/**
 * 射线投射器`Raycaster`的射线拾取选中网格模型对象函数choose()
 * 选中的网格模型变为半透明效果
 */
function choose(event) {
    const Sx = event.clientX; //鼠标单击位置横坐标
    const Sy = event.clientY; //鼠标单击位置纵坐标
    //屏幕坐标转WebGL标准设备坐标
    const x = (Sx / window.innerWidth) * 2 - 1; //WebGL标准设备横坐标
    const y = -(Sy / window.innerHeight) * 2 + 1; //WebGL标准设备纵坐标
    //创建一个射线投射器`Raycaster`
    const raycaster = new THREE.Raycaster();
    //通过鼠标单击位置标准设备坐标和相机参数计算射线投射器`Raycaster`的射线属性.ray
    raycaster.setFromCamera(new THREE.Vector2(x, y), camera);
    //返回.intersectObjects()参数中射线选中的网格模型对象
    // 未选中对象返回空数组[],选中一个数组1个元素，选中两个数组两个元素
    const intersects = raycaster.intersectObject(model, true);
    // console.log("射线器返回的对象", intersects);
    // console.log("射线投射器返回的对象 点point", intersects[0].point);
    // console.log("射线投射器的对象 几何体",intersects[0].object.geometry.vertices)
    // intersects.length大于0说明，说明选中了模型
    if (intersects.length > 0) {
        // 选中模型变大
        // intersects[0].object.scale.set(1.2, 1.2, 1.2);
        //点击位置坐标
        nextPos.copy(intersects[0].point)
        nextPos.point = intersects[0].point;
        nextPos.y = flyheight;
        // 方向在XOZ平面上计算，不计算高度y方向
        dir.copy(nextPos.clone().sub(camera.position)).normalize();
        flyBool = true; //允许飞行



        //         const newnextPos = nextPos.clone();
        //         newnextPos.position.z -= 3;
        //         camera.lookAt(newnextPos);
        //         controls.target.copy(newnextPos);
        // controls.update();

        person.walk(); //播放人步行动画
    }
}


// addEventListener('mouseup', function(event){
//     if(!controls.enabled)choose(event);
// }); // 监听窗口鼠标单击事件



// 
export {
    camera,
    controls,
    dir,
    choose,
};