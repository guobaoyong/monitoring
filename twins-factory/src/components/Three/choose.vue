<template>
  <div ref="messageTag">
    <div v-if="chooseObj" style="width:465px;height:370px;position: absolute;color: #fff;z-index: 12;font-size: 16px;">

      <!-- top:-80px;left: 20px;相对原来位置偏移5标注 -->
      <!-- top:-280px;left: -230px; -->
      <div style="width:465px;opacity: 1.0;position:relative;top:-280px;left: -230px;">
        <!-- 标签信息背景图片 -->
        <div style="position: absolute;left: 0px;top: 0px;">
          <img src="../../assets/信息背景.png" alt="" style="width:400px;opacity: 1.0;">
        </div>

        <!-- 名称 -->
        <div style="position:absolute;left:48px;top:36px;font-size:16px;">
          <div style="font-size:20px;font-weight: 400;">
            <span>{{ deptName }}</span>
          </div>
          <div style="margin-top: 30px;">
            <span style="color: #ccc;font-weight: 300;">工人数</span><span style="font-weight: 400;margin-left: 30px;">{{ workerNumber }} 人</span>
          </div>
          <div style="margin-top: 20px;">
            <span style="color: #ccc;font-weight: 300;">负责人</span><span style="font-weight: 400;margin-left: 30px;color: #F01D1D;">{{ deptLeader }}</span>
          </div>
          <div style="margin-top: 20px;">
            <span style="color: #ccc;font-weight: 300;">联系方式</span><span style="font-weight: 400;margin-left: 10px;color: #84EC08;">{{ deptPhone }}</span>
          </div>
        </div>


        <!-- 姓名 工号 头像 -->
        <div
          style="position:absolute;left:235px;top:75px;width: 80px;height: 100px;background: url(src/assets/头像4.jpg) no-repeat;background-size: 100%;">
          <div
            style="font-weight: 300;margin-top: 85px;width: 150%;height: 43px;background-size: 100%;">
            <div style="padding:1px 2px;font-size: 14px;">
              传感器数<span style="margin-left: 5px;">{{ sensorsNumber }}</span>
            </div>
            <div style="padding:1px 2px;font-size: 14px;">
              今日异常<span style="margin-left: 5px;color: #F01D1D;">{{ nowError }}</span>
            </div>
            <div style="padding:1px 2px;font-size: 14px;">
              历史异常<span style="margin-left: 5px;color: #F01D1D;">{{ oldError }}</span>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>

</template>


<script>
// 引入three.js
import * as THREE from 'three';
import tag from './ThreeBase/messageTag.js';//HTML标签相关代码
import { raychoose } from './ThreeBase/raygroupchoose.js'

import { outlinePass } from './ThreeBase/composer.js'

import {
  createCameraTween
} from './createTween.js';

import {
  camera,
  controls,
} from './ThreeBase/Camera' //相机对象

import { model } from "./ThreeBase/model.js";
import $ from 'jquery'
import messageData from "./messageData.js";


export default {
  name: 'choose',
  props: [],
  data() {
    return {
      chooseObj: null,
      deptName: "数字孪生工厂",
      workerNumber: 191,
      deptLeader: '郭保永',
      deptPhone: 15537364889,
      sensorsNumber: 9,
      nowError: 0,
      oldError: 0,
      clickBool: false,//动画期间鼠标事件不进行射线拾取
    };
  },
  created() {
    // 批量给每一个收费站窗口设置一个相机动画
    for (let i = 1; i < 30; i++) {
      let name = i + '';
      if (i < 10) name = '0' + name;
      // console.log('shoufeitag', name);
      const shoufei = model.getObjectByName(name);
      const shoufeitag = model.getObjectByName(name + '标注');
      // console.log('shoufeitag', shoufeitag.name);
      const target = new THREE.Vector3();
      shoufeitag.getWorldPosition(target);
      shoufeitag.y -= 1;
      const pos = new THREE.Vector3();
      // pos.set(target.x - 5, target.y + 3, target.z + 10)
      pos.set(target.x + 50, target.y + 50, target.z + 50)
      // pos.copy(target);
      // pos.addScalar(5);
      // 不执行相机动画
      createCameraTween(shoufei, camera, controls, pos, target);
    }
  },
  mounted() {
    const _this = this;
    //id"messageTag"对应的HTML元素作为three.js标签
    const messageTag = tag(_this.$refs.messageTag);
    model.add(messageTag);
    let chooseObj = null
    // click 单机和旋转有冲突  改成右键
    addEventListener('contextmenu', function (event) {
      if (_this.clickBool) return;//相机动画执行期间，不进行射线拾取
      //射线拾取模型对象
      if (chooseObj) {
        chooseObj = null
        outlinePass.selectedObjects = [];
      }
      const gao = model.getObjectByName('高层');
      const yang = model.getObjectByName('洋房');
      const objArr = [...gao.children, ...yang.children];
      chooseObj = raychoose(event, objArr, camera);// 判计算拾取到的对象
      _this.chooseObj = chooseObj;
      if (chooseObj) {
        outlinePass.selectedObjects = [chooseObj];
      } else {
        _this.chooseObj = null;
      }

      // 选中不同收费站窗口，HTML标签信息跟着改变
      if (chooseObj) {

        // 相机动画
        chooseObj.in.start();
        chooseObj.in.onStart(function () {
          _this.clickBool = true;//相机动画执行期间，不进行射线拾取
        })

        // 推出动画，人，用界面上UI交互按钮执行，什么时候想退在退回，退回隐藏
        chooseObj.in.onComplete(function () {



          chooseObj.out.delay(3000);//延迟
          chooseObj.out.start();
          chooseObj.out.onStart(function () {
            // 突然消失，动画，逐渐消失，类似车辆的标签，可以这样
            _this.chooseObj = false;//退出，不显示标签
            outlinePass.selectedObjects = [];//退出，无outline
          })

          chooseObj.out.onComplete(function () {
            _this.clickBool = false;


          })
        })

        // 选中收费站窗口对应的数据
        // const message = messageData['SF0'+chooseObj.name];
        // 更新vue data属性
        // _this.name = message.name;
        // _this.CarNum = message.CarNum;
        // _this.shouName = message.shouName;
        // _this.shouNameId = message.shouNameId;

        // 本质模拟随机生成数据，实际开发，请求后端或后端推送
        $.ajax({
          /* url 地址可以是 /get-json/ 的方式
          *  也可以是 http://www.qfedu.com/get-json/ 的方式
          */
          url: 'http://localhost/system/twins/query',
          type: 'GET',
          dataType: 'json',
          data: {
            ids: chooseObj.name,
          },
          success: function(res){
            // 成功处理逻辑
            _this.deptName = res.data.deptName;
            _this.workerNumber = res.data.workerNumber;
            _this.deptLeader = res.data.deptLeader;
            _this.deptPhone = res.data.deptPhone;
            _this.sensorsNumber = res.data.sensorsNumber;
            _this.nowError = res.data.nowError;
            _this.oldError = res.data.oldError;
          },
          error: function(res){
            // 错误时处理逻辑
            console.log(res)
          }
        });

        const pos = new THREE.Vector3();
        chooseObj.getObjectByName(chooseObj.name + '标注').getWorldPosition(pos); //收费窗口世界坐标    
        messageTag.position.copy(pos);//设置标签的位置

        // chooseObj.getObjectByName(chooseObj.name+'标注').add(messageTag);

        // 数字滚动动画
        const CarNumMax = _this.CarNum
        _this.CarNum = 0;
        const interval = setInterval(function () {
          if (_this.CarNum < CarNumMax) {
            _this.CarNum += Math.floor(CarNumMax / 50);
          } else {
            clearInterval(interval);//一旦达到当日通车数量，取消周期性函数interval
          }
        }, 5);

      }
    });



  },

  methods: {

  },
};
</script>

<style lang="scss" scoped>

</style>