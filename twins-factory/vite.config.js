import {
  defineConfig
} from 'vite'
import vue from '@vitejs/plugin-vue'


import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {
  ElementPlusResolver
} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  // base: '/3D/shoufeizhan/',
  // vite.config.ts
  build: {
    // rollup 配置
    rollupOptions: {
      output: {
        manualChunks(id) {
          if (id.includes("ThreeCanvas.vue")) {
            // 单独分割ThreeCanvas.vue文件
            return 'src/components/ThreeCanvas.vue';
          }

        }
      }
    }
  }

})