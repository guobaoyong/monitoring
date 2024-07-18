
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
    const intersects = raycaster.intersectObjects(chooseObjArr, true);//参数2设置true，拾取所有后代mesh
    // console.log('intersects,',intersects);
    // console.log("射线器返回的对象", intersects);
    // intersects.length大于0说明，说明选中了模型
    let choose =null;
    // 射线拾取组group或Object3D方法：
    // 1. intersectObject或intersectObjects的参数2，设置true，计算组的所有后代mesh
    // 2.1.如果要拾取的对象所有后代只有1个层级，可以通过拾取的mesh的父对象名字，判断那个组对象倍选中了
    // 2.2.如果要拾取的对象所有后代只有1个以上层级，可以给对象所有子孙后代mesh，设置一个祖先father属性指向祖先    
    for (let i = 0; i < chooseObjArr.length; i++) {
        const group = chooseObjArr[i];
        //递归遍历chooseObj，并给chooseObj的所有子孙后代设置一个ancestors属性指向自己
        group.traverse(function(obj){
            obj.ancestors = group;
        })
    }   
    if (intersects.length > 0) {
        choose=intersects[0].object.ancestors;
    }
    return choose;
}

export{raychoose}