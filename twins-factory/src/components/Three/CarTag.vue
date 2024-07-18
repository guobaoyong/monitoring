<template>
    <!-- css3渲染器会把该div元素默认渲染为绝对定位，默认HTML标签的几何中心与标注点位置世界坐标重合 -->
    <div ref="cartag" class="tag">
        <div class="container">
            <div>车牌号</div>
            <div>{{car.num}}</div>
        </div>
        <div class="container">
            <div>司机</div>
            <div>{{car.driverName}}</div>

        </div>
        <div class="container">
            <div>车型</div>
            <div>{{car.carType}}</div>

        </div>
        <div class="container">
            <div>状态</div>
            <div v-if="car.state=='正常'" style="color: #00ff00;">{{car.state}}</div>
            <div v-if="car.state=='异常'" style="color: #ffff00;">{{car.state}}</div>
        </div>


    </div>
</template>

<script>

import * as THREE from 'three';
import { CSS3DSprite } from "three/addons/renderers/CSS3DRenderer.js";


import {
    createCarTween, createCarTweenFan
} from './createTween.js'; //车辆和标签对应动画
export default {
    name: "CarTag",
    props: ['model', 'car'],
    data: function () {
        return {

        };
    },
    created() {
    },
    mounted: function () {
        const _this = this;
        let label = tag(5.0); //创建标签对象
        if (_this.car.order > 4) {
            label = tag(6.5);
        }

        this.car.add(label); //标签对象添加到三维场景
        const road = this.model.getObjectByName('CD0' + this.car.order);//车道中间线上的一个模型对象
        const pos = new THREE.Vector3();
        road.getWorldPosition(pos); 
        this.car.position.x = pos.x; // 把车辆放置在车道中线
        this.car.position.z = 40;
        this.model.add(this.car);


        // 计算档杆包围盒，通过包围盒计算出来档杆包围盒集中中心坐标
        const box3 = new THREE.Box3();
        box3.expandByObject(this.car);
        const size = new THREE.Vector3();
        box3.getSize(size);
        // console.log('size.y', size.y)

        if (_this.car.order <= 4) {
            //调整标签相对车的坐标
            // size.y：坐标原点在车底，需要根据车高度调节
            // +2：标签有一定尺寸，也要考虑
            label.position.y += size.y + 2;
        } else {
            label.position.y += size.y + 3;
        }



        // 创建一个HTML标签
        function tag(size) {
            // 获取div元素(作为标签)
            const div = _this.$refs.cartag;
            div.style.display = "block"; //HTML标签代码中设置了display:none;，这里改为'block'
            // const label = new CSS3DObject(div);//HTML标签对象 类似矩形平面Mesh
            const label = new CSS3DSprite(div); //HTML标签对象 类似Sprite
            // CSS3标签HTML元素渲染大小由自身像素尺寸和scale属性决定
            const w = div.offsetWidth; //获取标签HTML元素宽度
            // console.log("w", w);
            label.scale.set(size / w, size / w, size / w); //缩放CSS3DObject模型对象
            // 设置HTML元素标签位置
            // label.position.set(x, y, z);
            div.style.pointerEvents = "none"; //避免HTML标签遮挡三维场景的鼠标事件
            return label; //返回CSS3模型标签
        }


        // 标签对应的HTML元素淡入淡出动画
        _this.$refs.cartag.style.opacity = 0.0;
        if (_this.car.order <= 4) {
            //车绑定进站出站动画和对应标签淡入淡出动画
            createCarTween(_this.car, _this.$refs.cartag);
        } else {
            _this.car.rotateY(Math.PI);//对向车道车辆绕着y轴旋转180度调头
            createCarTweenFan(_this.car, _this.$refs.cartag);
        }


        // 定时器测试车辆进进站动画
        setTimeout(function () {
            _this.car.stop.start();
        }, 1000)

        // 出站动画
        setTimeout(function () {
            const danggan = _this.model.getObjectByName('GZ00' + _this.car.order);
            danggan.open.start();//杆打开
            // 杆打开完毕，车启动开走
            danggan.open.onComplete(function () {

            })
            setTimeout(function () {
                _this.car.run.start();//车开走
            }, 2000);

            setTimeout(function () {
                // 车开走一定距离，关闭档杆
                danggan.close.start();
            }, 6000);

            // 车离开动画介绍
            _this.car.run.onComplete(function () {
                // 车开走，不渲染标签
                // label.visible = false;
                // 车辆从场景中移除
                _this.model.remove(_this.car);
            })
        }, 6000)
    },
};
</script>

<style scoped>


.tag {
    opacity: 0.0;

    background-image: url(../../assets/车信息背景3.png);
    background-repeat: no-repeat;
    background-size: 100% 100%;

    /* width: 684px; */
    /* height: 616px; */
    color: #fff;
    font-size: 16px;

    z-index: 20;
    padding: 20px 30px;
    padding-bottom: 80px;
    /* width: 171px;
    height:154px ; */
}

.container {
    width: 200px;
    display: flex;
    /* row是flex-direction默认值,可以不设置，表示主轴为水平方向，从左向右排列*/
    flex-direction: row;
    /*space-between表示*/
    justify-content: space-between;
}

.container div {
    padding:6px 0px ;
    width: 120px;
}
</style>
