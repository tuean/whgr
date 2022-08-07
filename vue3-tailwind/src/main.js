import { createApp, provide } from 'vue'
import Main from './Main.vue'
import router from './router/index'
import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css';
import 'element-plus/packages/theme-chalk/src/base.scss'
import store from '/@/store'
import '/@/assets/lib/tagcanvas.js';
import mitt from 'mitt'
import loading from './plugin/loading/loading';
import dayjs from 'dayjs'
import '/@/theme/theme.less'
import '/@/assets/font/lxgw.css'
import tracing from './plugin/eventTracing/tracing';
// import microApp from '@micro-zoe/micro-app'
import "./index.css";
import WujieVue from "wujie-vue3";

const { bus, preloadApp, destroyApp } = WujieVue;

// microApp.start()

const app = createApp(Main)
app.config.globalProperties.$bus = mitt()
app.config.globalProperties.$dayjs = dayjs
// provide('dayjs', dayjs)

app.use(loading)
app.use(tracing, {url: "http://localhost:7777/api/out"})
app.use(router)
app.use(store)
app.use(ElementPlus)
app.use(WujieVue);
app.mount('#app')

preloadApp({ name: "tuean.cn", url: "http://tuean.cn", exec: true });
preloadApp({ name: "zoey-plus", url: "http://zoey.plus/e-s/email", exec: true });