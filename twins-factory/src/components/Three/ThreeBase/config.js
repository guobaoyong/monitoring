export default{
    // cWidth、cHeight：threejs渲染输出的canvas画布宽度、高度尺寸
    cWidth:window.innerWidth,
    cHeight:window.innerHeight,
    // // 相机position初始化位置
    cx:173,
    cy:126,
    cz:143,
    // // 相机初始化目标观察点
    // tx:-0.29,
    // ty:2.38,
    // tz:-0.39,
    // 人(相机)初始定位坐标  相机稍微在人后面一点
    perx:-52.8,
    pery:0,
    perz:160,//三维场景中测量，z对应bldner的-y
    h:1.8, //相机和人眼睛高度相似
    BloomBool:false,//渲染循环中通过该属性判断是否执行bloom后处理，vue中可以通过按钮改变
}