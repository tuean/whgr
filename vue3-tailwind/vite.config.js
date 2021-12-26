import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import styleImport from 'vite-plugin-style-import'
import { resolve } from 'path'

function pathResolve(dir) {
  return resolve(__dirname, ".", dir)
}

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 4000
  },
  publicDir: 'src',
  resolve: {
    alias: {
      "/@": pathResolve("src"),
      // "/config": pathResolve("public/config"),
      // "/com": pathResolve("src/components"),
      "@/config": pathResolve("public/config"),
      "@/com": pathResolve("src/components"),
      "@/bus": pathResolve("src/bus"),
      // "/util": pathResolve("src/util")
    }
  },
  plugins: [
    vue(),
    styleImport({
      libs: [{
        libraryName: 'element-plus',
        esModule: true,
        ensureStyleFile: true,
        resolveStyle: (name) => {
          return `element-plus/lib/theme-chalk/${name}.css`;
        },
        resolveComponent: (name) => {
          return `element-plus/lib/${name}`;
        },
      }]
    })
  ],
  build: {
    target: 'modules',
    // outDir: 'dist', //指定输出路径
    // assetsDir: 'assets', // 指定生成静态资源的存放路径
    minify: 'terser' // 混淆器，terser构建后文件体积更小
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "./src/scss/theme.scss";`
      }
    }
  }
})
