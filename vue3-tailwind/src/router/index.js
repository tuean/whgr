import {createRouter, createWebHashHistory} from "vue-router"

const admins = [
    {
        path: 'pageNotFund',
        name: 'pageNotFund',
        component: () => import(/* webpackChunkName: "404" */ '../components/pages/404.vue')
    },
    {
        path: 'vuexDemo',
        name: 'vuexDemo',
        component: () => import(/* webpackChunkName: "vuexDemo" */ '../components/pages/VuexDemo.vue')
    },
]

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
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import(/* webpackChunkName: "admin" */ '../components/pages/Admin.vue'),
            children: admins
        },
        {
            path: '/404',
            name: '404',
            component: () => import(/* webpackChunkName: "404" */ '../components/pages/404.vue')
        },
        {
            path: '/apps',
            name: 'apps',
            component: () => import(/* webpackChunkName: "apps" */ '../components/pages/Apps.vue')
        },
    ]
}

const router = createRouter(routes)

export default router