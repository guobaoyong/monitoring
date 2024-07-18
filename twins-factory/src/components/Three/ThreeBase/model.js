// 引入Three.js
import * as THREE from 'three';
// 引入gltf模型加载库GLTFLoader.js
import {
    GLTFLoader
} from 'three/addons/loaders/GLTFLoader.js';
// DRACOLoader解析Draco压缩过的gltf模型
import {
    DRACOLoader
} from 'three/addons/loaders/DRACOLoader.js';
// DRACOLoader解析Draco压缩过的gltf模型
const dracoLoader = new DRACOLoader();
// DRACOLoader依赖的多个js文件在public下的libs文件中
dracoLoader.setDecoderPath('./libs/dracogltf/');
// Octree八叉树
import {
    Octree
} from 'three/addons/math/Octree.js';

import {
    OctreeHelper
} from 'three/addons/helpers/OctreeHelper.js';


import person from './person.js';

import {
    flyGroup
} from './flyGroup.js';

const loader = new GLTFLoader(); //创建一个GLTF加载器
loader.setDRACOLoader(dracoLoader);

const model = new THREE.Group(); //声明一个组对象，用来添加加载成功的三维场景
model.add(person);
model.add(flyGroup);

flyGroup.position.y = 50;

// 在ThreeCanvas.vue中调用，通过_this设置组件data中的进度条进度


function loaderPer(_this) {
    loader.load("./小区2.glb", function (gltf) { //gltf加载成功后返回一个对象
        console.log('控制台查看gltf对象结构', gltf);
        model.add(gltf.scene); //三维场景添加到model组对象中
        // 加载完成隐藏进度条
        _this.percentBool = false;


        // 需要bloom后处理的模型，设置.layers.enable(1);
        const chuanghu = model.getObjectByName('窗户');
        chuanghu.layers.enable(1);
        model.traverse(function (obj) {
            if (obj.type === 'Mesh') {
                // 相同材质，统统改变layers
                if (obj.material == chuanghu.material) {
                    obj.layers.enable(1);
                    // obj.material.map=null;
                    // obj.material.color.set(0xaaaa99);
                }
            }
        })

        // 河流流动
        const waterMesh = gltf.scene.getObjectByName('river');
        waterMesh.material.transparent = true;
        waterMesh.material.opacity = 0.5;
        const map = waterMesh.material.map;

        function loop() {
            map.offset.x += 0.001;
            requestAnimationFrame(loop);
        }
        loop();





        // 切换场景或只显示一个
        // 把周围的楼隐藏，也是一种办吧
        // 单独管理一个楼房，肯定需要场景切换，
        // 然后一栋楼，也需要分层管理
        // 用包围盒计算，屏幕居中显示
        // gltf.scene.traverse(function (obj) {
        //     if (obj.type === 'Mesh') {
        //         //不同建筑共享材质了，性能急速下降，尽量共享
        //         obj.material = obj.material.clone();
        //         obj.material.transparent=true;
        //         obj.material.opacity=0.05;
        //     }
        // })
        // const 洋房 = gltf.scene.getObjectByName('扁楼');
        // 洋房.traverse(function(obj) {
        //     if (obj.type === 'Mesh') {
        //         obj.material.opacity=1.0;
        //     }
        // });
        // gltf.scene.getObjectByName('平面010').material.opacity=1.0;

        // console.log('洋房',洋房);
        // // model.remove(gltf.scene);
        // model.add(洋房);





        // const group = _this.model.getObjectByName('收费站主体');
        // console.log('收费站主体', group);
        // 递归遍历gltf.scene
        gltf.scene.traverse(function (obj) {
            if (obj.type === 'Mesh') {
                // 批量更改所有Mesh的材质
                // object.material = new THREE.MeshLambertMaterial({
                //   map: object.material.map, //获取原来材质的颜色贴图属性值
                //   color: object.material.color, //读取原来材质的颜色
                // })
                // object.material.metalness = 1.0;
                // object.material.roughness = 0.5;
                // obj.material.envMap = textureCube;
                // obj.material.envMapIntensity = 0.8;

                //玻璃给了透明，双面显示
                // obj.material.side = THREE.DoubleSide; 
                // 设置产生投影的网格模型
                obj.castShadow = true;
                // 设置接收阴影的投影面
                obj.receiveShadow = true;
            }
        })

        const treeGroup = gltf.scene.getObjectByName('树');
        // gltf.scene.remove(treeGroup);
        treeGroup.traverse(function (obj) {
            // console.log('obj.material',obj.material);
            if (obj.type === 'Mesh') {
                // 调节树的金属度、粗糙度，降低交叉mesh因反光带来的视觉瑕疵
                obj.material.metalness = 0.0;
                obj.material.roughness = 1.0;
                obj.material.reflectivity = 0.0;

                // MeshBasicMaterial MeshPhysicalMaterial
                // obj.material = new THREE.MeshBasicMaterial({
                //     map: obj.material.map,
                //     color: obj.material.color,
                //     transparent: true,
                //     side:THREE.DoubleSide,
                // })
            }

        });
        // console.log('tree', treeGroup);
        // gltf.scene.remove(treeGroup);

        // loader.load('./行道树.glb', function (gltf3) {
        //     const tree = gltf3.scene;
        //      const treeArr = treeGroup.children;
        //     for (let i = 0; i < treeArr.length; i++) {
        //         // treeArr[i].geometry.dispose();
        //         // treeArr[i].material.dispose();
        //         const newTree = tree.clone();
        //         newTree.position.copy(treeArr[i].position);
        //         model.add(newTree);
        //     }
        // });


        // const octree = new Octree(); // 八叉树
        // // 从gltf.scene获取图形节点
        // octree.fromGraphNode(gltf.scene);
        // // console.log('octree', octree);
        // // 可视化八叉树
        // const helper = new OctreeHelper(octree);
        // model.add(helper);
        // gltf.scene.visible = false; //隐藏模型，查看方便查看八叉树



    }, function (xhr) {
        // xhr.loaded / xhr.total的范围0~1 
        _this.percent = Math.floor(xhr.loaded / xhr.total * 100); //换算为百分比
        // _this.percent = Math.floor(xhr.loaded / 30000000 * 100); //2616064是该项目xhr.total的具体大小
        // console.log('_this.percent',_this.percent);
        console.log('xhr.total',xhr.total);
        // console.log('xhr',xhr);
    })
}


export {
    loaderPer,
    model
}