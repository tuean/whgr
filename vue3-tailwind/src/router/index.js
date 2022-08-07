import {createRouter, createWebHashHistory} from "vue-router"

// const admins = [
//     {
//         path: 'pageNotFound',
//         name: 'pageNotFound',
//         component: () => import(/* webpackChunkName: "404" */ '@/com/pages/pageNotFound.vue')
//     },
//     {
//         path: 'vuexDemo',
//         name: 'vuexDemo',
//         component: () => import(/* webpackChunkName: "vuexDemo" */ '@/com/pages/VuexDemo.vue')
//     },
//     {
//         path: 'lotteryDemo',
//         name: 'lotteryDemo',
//         component: () => import(/* webpackChunkName: "Lottery" */ '@/com/pages/Lottery.vue')
//     }, 
//     {
//         path: 'tableDemo',
//         name: 'tableDemo',
//         component: () => import(/* webpackChunkName: "TableDemo" */ '@/com/pages/admin/TableDemo.vue')
//     }, 
//     {
//         path: 'goods',
//         name: 'goods',
//         component: () => import(/* webpackChunkName: "Goods" */ '@/com/pages/admin/Goods.vue')
//     }, 
//     {
//         path: 'go1',
//         name: 'go1',
//         component: () => import(/* webpackChunkName: "go1" */ '@/com/pages/admin/go.vue')
//     }, 
//     {
//         path: 'go2',
//         name: 'go2',
//         component: () => import(/* webpackChunkName: "go2" */ '@/com/pages/admin/go.vue')
//     }, 
//     {
//         path: 'micro',
//         name: 'micro',
//         component: () => import(/* webpackChunkName: "Micro" */ '../components/pages/admin/Micro.vue')
//     },
// ]



// const routes = {
//     history: createWebHashHistory(),
//     routes: [
//         {
//             path: '/',
//             redirect: '/daisyui'
//         },
//         {
//             path: '/login',
//             name: 'login',
//             component: () => import(/* webpackChunkName: "Login" */ '../components/pages/Login.vue')
//         }, 
//         { 
//             path: '/desktop',
//             name: 'desktop',
//             component: () => import(/* webpackChunkName: "desktop" */ '../components/pages/Desktop.vue')
//         },
//         {
//             path: '/admin',
//             name: 'admin',
//             component: () => import(/* webpackChunkName: "admin" */ '../components/pages/Admin.vue'),
//             children: admins
//         },
//         // {
//         //     path: '/daisyui',
//         //     name: 'daisyuiRoot',
//         //     component: () => import(/* webpackChunkName: "daisyui" */ '@/com/daisyui/root.vue'),
//         //     // children: daisyui
//         // },
//         {
//             path: '/daisyui',
//             name: 'daisyui',
//             component: () => import(/* webpackChunkName: "daisyui" */ '@/com/daisyui/root.vue'),
//             children: daisyui
//         },
//         {
//             path: '/404',
//             name: '404',
//             component: () => import(/* webpackChunkName: "404" */ '../components/pages/404.vue')
//         },
//         {
//             path: '/apps',
//             name: 'apps',
//             component: () => import(/* webpackChunkName: "apps" */ '../components/pages/Apps.vue')
//         },
//         {
//             path: '/lottery',
//             name: 'lottery',
//             component: () => import(/* webpackChunkName: "Lottery" */ '../components/pages/Lottery.vue')
//         }, 
//         {
//             path: '/shot',
//             name: 'shot',
//             component: () => import(/* webpackChunkName: "Lottery" */ '../components/screenshot/shot.vue')
//         }, 
//         {
//             path: '/gojs',
//             name: 'gojs',
//             component: () => import(/* webpackChunkName: "gojsDemo" */ '../components/go-js/gojsDemo.vue')
//         }, 
//         {
//             path: '/lineTwo',
//             name: 'lineTwo',
//             component: () => import(/* webpackChunkName: "lineTwo" */ '../components/demo/lineTwo.vue')
//         }, 
//     ]
// }

const daisyui = [
    {
        path: 'main',
        name: 'main',
        component: () => import(/* webpackChunkName: "main" */ '../components/daisyui/main.vue')
    },
    {
        path: 'wujiedemo',
        name: 'wujiedemo',
        component: () => import(/* webpackChunkName: "wujiedemo" */ '../components/daisyui/wujiedemo.vue')
    },
    {
        path: 'app/:appId',
        name: 'app',
        component: () => import(/* webpackChunkName: "Wujie-App" */ '../components/daisyui/app.vue')
    }
]

const routes  = {
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'daisyui',
            component: () => import(/* webpackChunkName: "daisyui" */ '@/com/daisyui/root.vue'),
            children: daisyui
        }
    ]
    
}




const router = createRouter(routes)

export default router