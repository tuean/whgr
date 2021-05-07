import {createRouter, createWebHashHistory} from "vue-router"

const routes = {
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'login',
            component: () => import(/* webpackChunkName: "introduce" */ '../components/pages/Login.vue')
        }
    ]
}

const router = createRouter(routes)

export default router