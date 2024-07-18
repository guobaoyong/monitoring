<template>

    <div class="con">
        <!-- 不同预览模式下切换 -->
        <div style="padding: 8px 12px;margin-top: -10px;">
            <div class="out">
                <!-- 整体预览小区全貌 -->
                <span class="in">复位</span>
                <el-switch v-model="allBool" />
            </div>
            <div class="out">
                <!-- 人在小区地面漫游 -->
                <span class="in">漫游</span>
                <el-switch v-model="personBool" />
            </div>
            <div class="out">
                <!-- 无人机在上空飞行漫游 -->
                <span class="in">飞行</span>
                <el-switch v-model="flyBool" />
            </div>
            <div class="out">
                <!-- 窗户局部发光 -->
                <span class="in">夜景</span>
                <el-switch v-model="BloomBool" />
            </div>
        </div>



    </div>
</template>

<script>
import * as THREE from 'three';
import {
    camera,
    controls,
    dir,
    choose
} from './ThreeBase/Camera' //相机对象
import { scene,mesh,texture,texture2,fog } from './ThreeBase/scene.js'
import person from './ThreeBase/person';
import config from './ThreeBase/config';
import personLight from './ThreeBase/personLight';
import {renderer}from './ThreeBase/Renderer';
import { directionalLight, ambient } from './ThreeBase/scene';
import {
    createCameraChangeTween
} from './createTween.js';
import {
  composer,
  renderTargetPass
} from './ThreeBase/composer.js'

export default {
    name: 'Control',
    data() {
        return {
            allBool: true,
            personBool: false,
            flyBool: false,
            BloomBool: false,
        };
    },
    watch: {
        // 夜晚窗户发光和白天不发光两个状态切换
        BloomBool: function (val) {
            if (val) {
                // const chuanghu =scene.getObjectByName('窗户');
                // chuanghu.layers.enable(1);
                config.BloomBool = true;//控制渲染循环bloom后处理是否执行
                composer.addPass (renderTargetPass);
                mesh.material.map =texture2;
                scene.fog=null;
                // 设置光照强度 
                directionalLight.intensity = 0;
                personLight.intensity = 0;
                ambient.intensity = 0;
                
                
            } else {
                // const chuanghu =scene.getObjectByName('窗户');
                // chuanghu.layers.enable(0);
                config.BloomBool = false;//控制渲染循环bloom后处理是否执行
                composer.removePass (renderTargetPass);
                mesh.material.map =texture;
                scene.fog = fog;

                directionalLight.intensity = 2.9;
                personLight.intensity = 2.9;
                ambient.intensity = 0.2;
                
            }
        },
        // 进入整体预览状态
        allBool: function (val) {
            if (val) {
                // tweenjs动画，控制相机从当前值逐渐变化
                // camera.position.set(173, 126, 143);
                let x = 0,
                    y = 0,
                    z = 0;
                // camera.lookAt(x, y, z);
                // 相机缓慢变化到新的状态
                const tween = createCameraChangeTween(camera, camera.position, controls.target, 173, 126, 143, x, y, z);
                tween.start();

                controls.target.set(x, y, z);
                controls.update(); //update()函数内会执行camera.lookAt(controls.targe)
                scene.add(scene.directionalLight);
                if (this.personBool) {
                    this.personBool = false;
                    scene.remove(scene.personLight);
                }
            }
        },
        personBool: function (val) {
            if (val) {

                const nPos = camera.personPosition;
                const tPos = person.position;
                const tween = createCameraChangeTween(camera, camera.position, controls.target, nPos.x, nPos.y, nPos.z, tPos.x, tPos.y, tPos.z);
                tween.start();

                controls.target.copy(person.position);
                controls.update();
                tween.onComplete(function () {
                    //人的姿态歪了，通过相机四元数重新恢复
                    person.quaternion.copy(camera.quaternion);
                })

                const personLight = scene.personLight;
                const lightPos = person.position.clone().add(personLight.dir.clone().multiplyScalar(50));
                // 光源位置保持相对人不变
                personLight.position.copy(lightPos);
                // 光源目标对象指向人的位置
                personLight.target.position.copy(person.position);



                scene.add(scene.personLight);
                if (this.allBool) {
                    this.allBool = false;
                    scene.remove(scene.directionalLight);
                }
            }
        }
    },
    created() {
    },
    mounted() {
        const _this = this;
        // 鼠标单机和拖动的单机区分开click  mouseup
        addEventListener('contextmenu', function () {
            console.log('23333333',);
            if (_this.personBool) choose(event);
        });
    },

    methods: {

    },
};
</script>

<style scoped>
.con {
    position: absolute;
    top: 250px;
    left: 20px;

}

.out {
    background: rgba(0, 0, 0, 0.4);
    width: 100px;
    height: 40px;
    line-height: 40px;
    border-radius: 20px;
    margin-top: 20px;
}

.in {
    height: 40px;
    width: 40px;
    border-radius: 20px;
    background: rgba(0, 0, 0, 0.3);
    display: inline-block;
    line-height: 40px;
    color: #fff;
    text-align: center;
    /* vertical-align:; */
}
</style>



  
