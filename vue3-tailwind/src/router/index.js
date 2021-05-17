import {createRouter, createWebHashHistory} from "vue-router"

const routes = {
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            redirect: '/desktop'
        },
        {
            path: '/login',
            name: 'login',
            component: () => import(/* webpackChunkName: "Login" */ '../components/pages/Login.vue')
        }, 
        {
            path: '/desktop',
            name: 'desktop',
            component: () => import(/* webpackChunkName: "desktop" */ '../components/pages/Desktop.vue')
        }
    ]
}

const router = createRouter(routes)

export default router