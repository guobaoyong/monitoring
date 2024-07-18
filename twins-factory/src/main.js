import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App);

import './assets/global.css';
import './assets/index.css'//大屏可视化数字字体样式设置

// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'
// app.use(ElementPlus);

app.mount('#app');
