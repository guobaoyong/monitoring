<template>
    <div>
        <!-- 如果某个标签需要鼠标事件，注意pointer-event不能为none; -->
        <!-- style="pointer-events: auto;"  -->
        <div class="SceneTag" ref="tag" v-on:click="choose()">{{ name }}</div>
    </div>
</template>

<script>
import tag from './ThreeBase/messageTag.js'
import { model } from './ThreeBase/model.js'
export default {
    name: 'XiaoquSceneTag',
    props: ['name'],
    data() {
        return {
            SceneTagArr: [],
        };
    },

    mounted() {
    
        const SceneTag = tag(this.$refs.tag);// 获取标签对应HTML元素
        const obj = model.getObjectByName(this.name+'标注');
        obj.add(SceneTag);// 标签作为标注空对象的子对象，添加到场景中

    },

    methods: {
       choose:function(){

       }
    }
};
</script>

<style scoped>
.SceneTag {
    /* border:solid #009999 1px; */
    /* background: linear-gradient(#00ffff, #00ffff) left top,
        linear-gradient(#00ffff, #00ffff) left top,
        linear-gradient(#00ffff, #00ffff) right bottom,
        linear-gradient(#00ffff, #00ffff) right bottom;
    background-repeat: no-repeat;
    background-size: 1px 6px, 6px 1px; */
    /* background-color: rgba(0, 0, 0, 0.4); */
    /* color: #ffffff; */
    color: #00dddd;
    font-size: 18px;
    padding: 4px 16px;
    padding-bottom: 8px;
    /* border-radius: 25px; */
    background-image: url(../../assets/SceneTag.png);
    background-repeat: no-repeat;
    background-size: 100% 100%;
}
</style>