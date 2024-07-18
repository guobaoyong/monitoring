
import * as THREE from 'three';
// 通用射线拾取函数，对group和mesh的选择都是有效的
import config from './config';
// event：鼠标事件
// chooseObjArr：需要射线拾取的模型对象构成的数组
function raychoose(event,chooseObjArr,camera) {
    
    const Sx = event.clientX; //鼠标单击位置横坐标
    const Sy = event.clientY; //鼠标单击位置纵坐标
    //屏幕坐标转WebGL标准设备坐标
    const x = (Sx / config.cWidth) * 2 - 1; //WebGL标准设备横坐标
    const y = -(Sy / config.cHeight) * 2 + 1; //WebGL标准设备纵坐标
    //创建一个射线投射器`Raycaster`
    const raycaster = new THREE.Raycaster();
    //通过鼠标单击位置标准设备坐标和相机参数计算射线投射器`Raycaster`的射线属性.ray
    raycaster.setFromCamera(new THREE.Vector2(x, y), camera);
    //返回.intersectObjects()参数中射线选中的网格模型对象
    // 未选中对象返回空数组[],选中一个数组1个元素，选中两个数组两个元素 
    const intersects = raycaster.intersectObjects(chooseObjArr);
    // console.log('intersects,',intersects);
    // console.log("射线器返回的对象", intersects);
    // intersects.length大于0说明，说明选中了模型
    let choose =null;
    if (intersects.length > 0) {
        choose=intersects[0].object;
    }
    return choose;
}

export{raychoose}