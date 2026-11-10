import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Main from './Main.vue'
import router from './router/index'
import '/@/assets/font/lxgw.css'
import './index.css'

const app = createApp(Main)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
