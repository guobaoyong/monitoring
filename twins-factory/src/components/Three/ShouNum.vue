<template>
    <div id="num">
        <!-- 给每个收费窗口标注一个CSS3标签：标注收费窗口序号 -->
        <!-- 也可以美术建模用用贴图标注 -->        
        <div  class="numTag" v-for="(num, i) in numArr" :key="i" :id="'CD'+i">0{{num}}</div>
    </div>
</template>

<script>

import { CSS3DObject } from 'three/addons/renderers/CSS3DRenderer';

import * as  THREE from 'three';
export default {
    name: 'ShouNum',
    props: ['model'],
    data() {
        return {
            numArr: [1,2,3,4,5,6,7,8]
        };
    },

    mounted() {
            // 车道中间标注
            // const chedao = this.model.getObjectByName('车道中间标注');
            for (let i = 0; i < 8; i++) {
                // const obj = chedao.children[i];//写法不严谨，如果chedao的子元素children顺序解析的时候不能保持
                const obj = this.model.getObjectByName('CD0'+(i+1));
                const label = new CSS3DObject(document.getElementById('CD'+i));
                const pos = new THREE.Vector3();
                obj.getWorldPosition(pos);
                label.position.copy(pos);
                this.model.add(label);
                label.position.y+=0.6;
                label.scale.set(0.03,0.03,0.03);
            }
    },

    methods: {

    },
};
</script>

<style  scoped>

.numTag {
    /* border:solid #009999 1px; */
    /* background: linear-gradient(#00ffff, #00ffff) left top,
        linear-gradient(#00ffff, #00ffff) left top,
        linear-gradient(#00ffff, #00ffff) right bottom,
        linear-gradient(#00ffff, #00ffff) right bottom;
    background-repeat: no-repeat;
    background-size: 1px 6px, 6px 1px; */
    /* background-color: rgba(0, 0, 0, 0.4); */
    color: #ffffff;
    /* color: #00ffff; */
    font-size: 18px;
    padding: 4px 16px;
    padding-bottom:8px ;
    /* border-radius: 25px; */
    background-image: url(../../assets/车道序号.png);
   background-repeat: no-repeat;
   background-size:100% 100%;
}
/* .numTag div {

} */
</style>