// 引入Three.js
import * as THREE from 'three'

function CreatePointsTag(obj) {
  // 精灵模型+背景透明png贴图实现光点效果
  const spriteMaterial = new THREE.SpriteMaterial({
    // map: new THREE.TextureLoader().load("../../../src/assets/3D/光点.png"), //设置精灵纹理贴图
    map: new THREE.TextureLoader().load("./3D/光点.png"), //设置精灵纹理贴图
    transparent: true,
    color:0x00ffff,
  });
  const sprite = new THREE.Sprite(spriteMaterial);
  sprite.scale.set(1.0, 1.0, 1); //大小设置

  // 设置标注精灵Sprite波动，提示用户点击
  const s = 0.0;
  const Smax = 0.6;
  function waveAnimation() { //设置产品模型旋转动画
    s += 0.01;
    if (s < 0.5) {
      sprite.scale.x = Smax * (1 + s);
      sprite.scale.y = Smax * (1 + s);
    } else if (s >= 0.5 && s < 1.0) {
      sprite.scale.x = Smax * (2 - s);
      sprite.scale.y = Smax * (2 - s);
    } else {
      s = 0.0;
    }

    requestAnimationFrame(waveAnimation); //请求再次执行函数waveAnimation
  }
  waveAnimation();

  return sprite
}
export {
  CreatePointsTag
}