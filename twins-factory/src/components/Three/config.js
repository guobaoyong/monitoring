// 静态不变化的CSS2标签标注数据配置
// 注意标注名字，要确保在三维模型中有相关对应名字的模型节点
const SceneTagArr =[];
SceneTagArr.push('物业', '西大门', '东大门');
for (let i = 1; i < 30; i++) {
    let name = '' + i; //注意数字转为字符串,作为楼栋号
    if (i < 10) name = '0' + name;
    SceneTagArr.push(name);
}

export {SceneTagArr};