import { CSS3DRenderer } from 'three/addons/renderers/CSS3DRenderer.js';
import config from './config';

// 创建一个CSS3渲染器CSS3DRenderer
const CSS3LabelRenderer = new CSS3DRenderer();
CSS3LabelRenderer.setSize(config.cWidth, config.cHeight);
CSS3LabelRenderer.domElement.style.position = 'absolute';
// 相对标签原位置位置偏移大小
CSS3LabelRenderer.domElement.style.top = '0px';
CSS3LabelRenderer.domElement.style.left = '0px';
// //设置.pointerEvents=none，以免模型标签HTML元素遮挡鼠标选择场景模型
CSS3LabelRenderer.domElement.style.pointerEvents = 'none';
document.body.appendChild(CSS3LabelRenderer.domElement);

export {CSS3LabelRenderer}