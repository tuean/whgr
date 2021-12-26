import { createApp } from 'vue'
// import App from './App.vue'
import Main from './Main.vue'
import router from './router/index'
import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css';
import 'element-plus/packages/theme-chalk/src/base.scss'
// import vuex from 'vuex'
import store from '/@/store'
import '/@/assets/lib/tagcanvas.js';
import mitt from 'mitt'

// const bus = {}
// const emitter = mitt()

// bus.$on = emitter.on
// bus.$off = emitter.off
// bus.$emit = emitter.emit

const app = createApp(Main)
app.config.globalProperties.$bus = mitt()

app.use(router)
app.use(store)
app.use(ElementPlus)
app.mount('#app')
