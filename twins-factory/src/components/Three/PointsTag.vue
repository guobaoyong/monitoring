<template>
    <div>

    </div>
</template>

<script>
import * as THREE from 'three'
import {
    CreatePointsTag
} from './ThreeBase/PointsTag.js';
import { camera } from './ThreeBase/Camera.js';
import { raychoose } from './ThreeBase/raychoose.js';
export default {
    name: 'PointsTag',
    props: ['model'],
    data() {
        return {

        };
    },
    created() {
        const spriteArr = [];//所有热点模型集合
        // 给每个档杆添加一个模型热点
        for (let i = 0; i < 8; i++) {
            const gz = this.model.getObjectByName('GZ00' + (i + 1));
            const sprite = CreatePointsTag(gz);
            // const pos = new THREE.Vector3();
            // sprite.getWorldPosition(gz);//obj模型坐标原点对应的世界坐标
            // sprite.position.copy(pos); //光点位置设置

            // 计算档杆包围盒，通过包围盒计算出来档杆包围盒集中中心坐标
            const box3 = new THREE.Box3();
            box3.expandByObject(gz);
            const size = new THREE.Vector3();
            box3.getSize(size);
            sprite.position.x += size.x * 0.7;// 根据包围盒尺寸移动热点模型
            // sprite.position.y += 0.4;//稍微向上偏移：根据热点模型尺寸写
            sprite.position.z += 0.5;//在档杆前方适当偏移，避免旋转的时候被挡杆遮挡部分：根据热点模型尺寸写
            gz.add(sprite);
            sprite.i = i;//给热点模型编序号
            spriteArr.push(sprite);
            gz.openBool = false;//自定义一个属性用来判断档杆的状态
        }


        const _this = this;
        addEventListener('click', function (event) {
            const chooseObj = raychoose(event, spriteArr, camera);
            if (chooseObj) {
                const gz = _this.model.getObjectByName('GZ00' + (chooseObj.i + 1));
                if (gz.openBool) {
                    gz.close.start();
                    gz.close.onStart(function () {
                        gz.openBool = false;
                    })
                } else {
                    gz.open.start();
                    gz.open.onStart(function () {
                        gz.openBool = true;
                    })
                }

            }
        })
    },
    mounted() {


    },

    methods: {

    },
};
</script>

<style scoped>

</style>