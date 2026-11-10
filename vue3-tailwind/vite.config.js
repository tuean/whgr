import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
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
      "@/config": pathResolve("public/config")
    }
  },
  plugins: [
    vue()
  ],
  build: {
    target: 'modules',
    // outDir: 'dist', //指定输出路径
    // assetsDir: 'assets', // 指定生成静态资源的存放路径
    minify: 'terser' // 混淆器，terser构建后文件体积更小
  }
})
