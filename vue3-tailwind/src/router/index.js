import {createRouter, createWebHashHistory} from "vue-router"

const admins = [
    {
        path: 'pageNotFound',
        name: 'pageNotFound',
        component: () => import(/* webpackChunkName: "404" */ '../components/pages/pageNotFound.vue')
    },
    {
        path: 'vuexDemo',
        name: 'vuexDemo',
        component: () => import(/* webpackChunkName: "vuexDemo" */ '../components/pages/VuexDemo.vue')
    },
    {
        path: 'lotteryDemo',
        name: 'lotteryDemo',
        component: () => import(/* webpackChunkName: "Lottery" */ '../components/pages/Lottery.vue')
    }, 
    {
        path: 'tableDemo',
        name: 'tableDemo',
        component: () => import(/* webpackChunkName: "TableDemo" */ '../components/pages/admin/TableDemo.vue')
    }, 
    {
        path: 'goods',
        name: 'goods',
        component: () => import(/* webpackChunkName: "Goods" */ '../components/pages/admin/Goods.vue')
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
        {
            path: '/lottery',
            name: 'lottery',
            component: () => import(/* webpackChunkName: "Lottery" */ '../components/pages/Lottery.vue')
        }, 
        {
            path: '/shot',
            name: 'shot',
            component: () => import(/* webpackChunkName: "Lottery" */ '../components/screenshot/shot.vue')
        }, 

    ]
}

const router = createRouter(routes)

export default router