// 引入Three.js
import * as THREE from 'three';
import {
    camera
} from './Camera.js'
import {
  renderer
} from './Renderer.js' //渲染器对象
import {
    scene
} from './scene.js'

import {
    EffectComposer
} from 'three/addons/postprocessing/EffectComposer.js';
import {
    UnrealBloomPass
} from "./UnrealBloomPass.js";
import {
    RenderPass
} from 'three/addons/postprocessing/RenderPass.js';
import {
    ShaderPass
} from 'three/addons/postprocessing/ShaderPass.js';
// import { CopyShader } from 'three/addons/postprocessing/CopyShader.js';
import {
    OutlinePass
} from 'three/addons/postprocessing/OutlinePass.js';
// 伽马校正
import {
    GammaCorrectionShader
} from 'three/addons/shaders/GammaCorrectionShader.js';

import config from './config';

// 特定模型bloom专用后处理
const bloomComposer = new EffectComposer(renderer);

// 创建一个渲染器通道，场景和相机作为参数
const renderPass = new RenderPass(scene, camera);
// 创建OutlinePass通道,显示外轮廓边框
const outlinePass = new OutlinePass(new THREE.Vector2(config.cWidth, config.cHeight), scene,
    camera);


//设置要显示边框的网格模型
//交互的时候可以设置一个鼠标事件，点击选中了某个模型，就直接把某个模型作为值的元素
outlinePass.selectedObjects = [];


//outlinePass相关属性设置
outlinePass.visibleEdgeColor.set(0x00ffff); //模型描边颜色，默认白色          
outlinePass.edgeThickness = 4.0; //轮廓边缘描边厚度
outlinePass.edgeStrength = 6.0; //边缘发光强度,数值大，更亮一些
outlinePass.pulsePeriod = 2;//模型闪烁频率控制，默认0不闪烁
// outlinePass.hiddenEdgeColor.set(0x220101);//模型被遮挡部分描边颜色控制        
// outlinePass.edgeGlow = 0.0;//边缘发光，默认0.0
// outlinePass.downSampleRatio = 2;//下采样比,默认2
// outlinePass.usePatternTexture = false;//纹理,默认false

// 创建后处理对象EffectComposer，WebGL渲染器作为参数
const composer = new EffectComposer(renderer);
// 设置renderPass通道
composer.addPass(renderPass);
// 设置OutlinePass通道
composer.addPass(outlinePass);



const vertexShader= `varying vec2 vUv;`+
                     `void main() {`+
                     `    vUv = uv;`+
                     `    gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );`+
                     `}`;
const fragmentShader=`uniform sampler2D baseTexture;`+
                     `uniform sampler2D bloomTexture;`+
                     `varying vec2 vUv;`+
                     `void main() {`+
                     `    gl_FragColor = ( texture2D( baseTexture, vUv ) + vec4( 1.0 ) * texture2D( bloomTexture, vUv ) );`+
                     `}`;                     
const renderTargetPass = new ShaderPass(
    new THREE.ShaderMaterial({
        uniforms: {
            baseTexture: {
                value: null
            },
            bloomTexture: {
                // 获取bloomComposer的渲染结果：从帧缓冲区中读取
                value: bloomComposer.renderTarget2.texture
            },
        },
        vertexShader: vertexShader,
        fragmentShader: fragmentShader,
        defines: {},
    }),
    "baseTexture"
);
// composer.addPass(renderTargetPass);//可以通过UI交互界面控制是否添加该通道


// 景深
// import {
//     BokehPass
// } from 'three/addons/postprocessing/BokehPass.js';

// const bokehPass = new BokehPass( scene, camera, {
//     focus: 1000.0,
//     aperture: 0.025,
//     maxblur: 1000.01,

//     width: window.innerWidth,
//     height: window.innerHeight
// } );
// composer.addPass( bokehPass );



// FXAA锯齿
import { FXAAShader } from 'three/addons/shaders/FXAAShader.js';
const FXAA = new ShaderPass( FXAAShader );
FXAA.uniforms[ 'resolution' ].value.set( 1 / window.innerWidth, 1 / window.innerHeight );
composer.addPass( FXAA );

// SSAA锯齿  没成功
// import {
//     SSAARenderPass
// } from 'three/addons/postprocessing/SSAARenderPass.js';
// const SSAA =  new SSAARenderPass( scene, camera );
// composer.addPass(SSAA);
// SMAA锯齿
// import {
//     SMAAPass
// } from 'three/addons/postprocessing/SMAAPass.js';
// const SMAA = new SMAAPass(window.innerWidth * renderer.getPixelRatio(), window.innerHeight * renderer.getPixelRatio());
// composer.addPass(SMAA);

const gammaCorrection = new ShaderPass(GammaCorrectionShader);
composer.addPass(gammaCorrection);






// bloom后处理设置
bloomComposer.renderToScreen = false;//需要设置：离屏渲染
bloomComposer.addPass(renderPass);//和compouser一样设置
const bloomPass = new UnrealBloomPass(new THREE.Vector2(window.innerWidth, window.innerHeight));
bloomPass.strength = 3.0;//bloom强度，默认1.0。
bloomPass.radius = 0;
bloomComposer.addPass(bloomPass);
bloomComposer.addPass(FXAA);




export {
    composer,
    outlinePass,
    FXAA,
    bloomComposer,
    renderTargetPass
}