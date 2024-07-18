import TWEEN from '@tweenjs/tween.js'
import config from './ThreeBase/config';
function createCameraChangeTween(camera,cameraPos,controlsTarget,x,y,z,tx,ty,tz) {
    const obj = {
        // 相机开始坐标
        x: cameraPos.x,
        y: cameraPos.y,
        z: cameraPos.z, 
        // 相机开始指向的目标
        tx: controlsTarget.x,
        ty: controlsTarget.y,
        tz: controlsTarget.z, 
    };
    // 杆子抬起来的动画
    const openTween = new TWEEN.Tween(obj); //创建一段tween动画      
    openTween.to({
        // 相机结束坐标
        x: x,
        y: y,
        z: z, 
        // 相机结束指向的目标
        tx: tx,
        ty: ty,
        tz: tz, 
    }, 1000); //1000：表示动画执行时间1000毫秒(ms)
    openTween.onUpdate(function () {
        // tween动画执行期间.onUpdate()重复执行
        camera.position.set(obj.x, obj.y, obj.z);
    
                camera.lookAt(obj.tx, obj.ty, obj.tz);
    });
    return openTween;
}

// 创建档杆动画打开动画
// danggan:档杆模型对象
function createGanTween(danggan) {
    //通过动画模块twwen控制模型的角度改变
    const obj = {
        angle: 0, //表示档杆旋转角度
    };
    // 杆子抬起来的动画
    const openTween = new TWEEN.Tween(obj); //创建一段tween动画      
    openTween.to({
        angle: Math.PI / 2,
    }, 2000); //2000：表示动画执行时间2000毫秒(ms)
    openTween.onUpdate(function () {
        // tween动画执行期间.onUpdate()重复执行，改变档杆姿态
        danggan.rotation.z = obj.angle;
    });
    // openTween.start(); //tween动画开始执行(你可以选择合适的时候触发执行)
    danggan.open = openTween; //档杆模型自定义一个属性绑定自己对应的动画

    //  杆子关闭动画
    const closeTween = new TWEEN.Tween(obj); //创建一段tween动画      
    closeTween.to({
        angle: 0,
    }, 2000); //2000：表示动画执行时间2000毫秒(ms)
    closeTween.onUpdate(function () {
        // tween动画执行期间.onUpdate()重复执行，改变档杆姿态
        danggan.rotation.z = obj.angle;
    });
    danggan.close = closeTween; //档杆模型自定义一个属性绑定自己对应的动画
}
// 创建车辆进站动画、出站动画，和对应标签显示动画
function createCarTween(car, TagDOM) {
    //通过动画模块twwen控制模型的车辆的z坐标改变
    const obj = {
        z: 40, //z表示车辆位置
        opacity: 0.0, //标签透明度
    };
    // 车站进站停止动画
    const stopTween = new TWEEN.Tween(obj); //创建一段tween动画
    stopTween.to({
        z: 15,
    }, 3000); //3000：表示动画执行时间3000毫秒(ms)      
    stopTween.onUpdate(function () {
        car.position.z = obj.z;
        TagDOM.style.opacity = obj.opacity;
    });
    car.stop = stopTween; //车模型自定义一个属性绑定自己对应的动画

    // 标签淡入动画和最后一点停车进站动画同步
    const showTween = new TWEEN.Tween(obj); //创建一段tween动画      
    showTween.to({
        z: 10, //停在档杆前面
        opacity: 1.0,
    }, 1000);
    // showTween.easing(TWEEN.Easing.Sinusoidal.Out); // 动画结束缓动方式(减速刹车)

    showTween.onUpdate(function () {
        car.position.z = obj.z;
        TagDOM.style.opacity = obj.opacity;
    });
    // 停车进站动画前面部分结束后，触发标签淡入和后一段进站动画
    stopTween.onComplete(function () {
        showTween.start();
    })


    //  车辆出站开走动画
    const runTween = new TWEEN.Tween(obj); //创建一段tween动画 
    runTween.to({
        z: -200,
    }, 10000);
    // runTween.easing(TWEEN.Easing.Sinusoidal.In); // 动画开始缓动方式(加速启动)
    runTween.onUpdate(function () {
        car.position.z = obj.z;
        TagDOM.style.opacity = obj.opacity;
    });
    car.run = runTween; //车模型自定义一个属性绑定自己对应的动画

    // 淡出动画
    const hideTween = new TWEEN.Tween(obj);
    hideTween.to({
        opacity: 0.0,
    }, 2000);
    hideTween.onUpdate(function () {
        TagDOM.style.opacity = obj.opacity;
    });
    // 标签淡出和出站动画同时进行
    runTween.onStart(function () {
        hideTween.start();
    })
}
// 对向车道车辆动画
function createCarTweenFan(car, TagDOM) {
    //通过动画模块twwen控制模型的车辆的z坐标改变
    const obj = {
        z: -100, //z表示车辆位置
        opacity: 0.0, //标签透明度
    };
    // 车站进站停止动画
    const stopTween = new TWEEN.Tween(obj); //创建一段tween动画
    stopTween.to({
        z: -15,
    }, 3000); //3000：表示动画执行时间3000毫秒(ms)      
    stopTween.onUpdate(function () {
        car.position.z = obj.z;
        TagDOM.style.opacity = obj.opacity;
    });
    car.stop = stopTween; //车模型自定义一个属性绑定自己对应的动画

    // 标签淡入动画和最后一点停车进站动画同步
    const showTween = new TWEEN.Tween(obj); //创建一段tween动画      
    showTween.to({
        z: -10, //停在档杆前面
        opacity: 1.0,
    }, 1000);
    // showTween.easing(TWEEN.Easing.Sinusoidal.Out); // 动画结束缓动方式

    showTween.onUpdate(function () {
        car.position.z = obj.z;
        TagDOM.style.opacity = obj.opacity;
    });
    // 停车进站动画前面部分结束后，触发标签淡入和后一段进站动画
    stopTween.onComplete(function () {
        showTween.start();
    })


    //  车辆出站开走动画
    const runTween = new TWEEN.Tween(obj); //创建一段tween动画 
    runTween.to({
        z: 40,
        // opacity: 1.0,
    }, 3000);
    // runTween.easing(TWEEN.Easing.Sinusoidal.In); // 动画开始缓动方式
    runTween.onUpdate(function () {
        car.position.z = obj.z;
        // TagDOM.style.opacity = obj.opacity;
    });
    car.run = runTween; //车模型自定义一个属性绑定自己对应的动画

    // 淡出动画
    const hideTween = new TWEEN.Tween(obj);
    hideTween.to({
        opacity: 0.0,
    }, 2000);
    hideTween.onUpdate(function () {
        TagDOM.style.opacity = obj.opacity;
    });
    // 标签淡出和出站动画同时进行
    runTween.onStart(function () {
        hideTween.start();
    })
}


// // 创建车辆进站动画、出站动画
// function createCarTween(car) {
//     //通过动画模块twwen控制模型的车辆的z坐标改变
//     const obj = {
//         z: 30, //z表示车辆位置
//     };
//     // 车站进站停止动画
//     const stopTween = new TWEEN.Tween(obj); //创建一段tween动画      
//     stopTween.to({
//         z: 10,//停在档杆前面
//     }, 4000); //4000：表示动画执行时间4000毫秒(ms)
//     stopTween.onUpdate(function () {
//         // tween动画执行期间.onUpdate()重复执行，改变档杆姿态
//         car.position.z = obj.z;
//     });
//     car.stop = stopTween; //车模型自定义一个属性绑定自己对应的动画

//     //  车辆出站开走动画
//     const runTween = new TWEEN.Tween(obj); //创建一段tween动画      
//     runTween.to({
//         z: -150,
//     }, 10000); //4000：表示动画执行时间4000毫秒(ms)
//     runTween.onUpdate(function () {
//         // tween动画执行期间.onUpdate()重复执行，改变档杆姿态
//         car.position.z = obj.z;
//     });
//     car.run = runTween; //车模型自定义一个属性绑定自己对应的动画
// }


// 创建标签淡入淡出动画   用于车辆，用于收费窗口等标签淡入淡出
function createTagTween(TagDOM) {
    const obj = {
        opacity: 0.0, //标签透明度
    };
    // 淡入动画
    const showTween = new TWEEN.Tween(obj); //创建一段tween动画      
    showTween.to({
        opacity: 1.0,
    }, 1000);
    showTween.onUpdate(function () {
        TagDOM.style.opacity = obj.opacity;
    });
    TagDOM.show = showTween;

    // 淡出动画
    const hideTween = new TWEEN.Tween(obj);
    hideTween.to({
        opacity: 0.0,
    }, 2000);
    hideTween.onUpdate(function () {
        TagDOM.style.opacity = obj.opacity;
    });
    TagDOM.hide = hideTween;
}

function createCameraTween(shoufei, camera, controls,pos,target) {
    const obj = {
        // 相机整体观察位置
        x: config.cx,
        y: config.cy,
        z: config.cz,
        // 相机整体预览对应目标观察点
        tx: 0,
        ty: 0,
        tz: 0,
    };
    const inTween = new TWEEN.Tween(obj);
    inTween.to({
        // 相机局部观察位置
        x: pos.x,
        y: pos.y,
        z: pos.z,
        // 相机局部预览对应目标观察点
        tx: target.x,
        ty: target.y,
        tz: target.z,
    }, 2000);
    inTween.onUpdate(function () {
        camera.position.set(obj.x, obj.y, obj.z);
        camera.lookAt(obj.tx, obj.ty, obj.tz);
        controls.target.set(obj.tx, obj.ty, obj.tz);
        controls.update(); //update()函数内会执行camera.lookAt(controls.targe)

    });
    shoufei.in = inTween;
    const outTween = new TWEEN.Tween(obj);
    outTween.to({
        // 相机整体观察位置
        x: config.cx,
        y: config.cy,
        z: config.cz,
        // 相机整体预览对应目标观察点
        tx: 0,
        ty: 0,
        tz: 0,
    }, 2000);
    outTween.onUpdate(function () {
        camera.position.set(obj.x, obj.y, obj.z);
        camera.lookAt(obj.tx, obj.ty, obj.tz);
        controls.target.set(obj.tx, obj.ty, obj.tz);
        controls.update();
    });
    shoufei.out = outTween;
}

export {
    createGanTween,
    createCarTween,
    createCarTweenFan,
    createTagTween,
    createCameraTween,
    createCameraChangeTween,
}