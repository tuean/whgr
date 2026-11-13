import { createRouter, createWebHashHistory } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getToken } from '/@/api/request'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('/@/views/login.vue'),
    meta: { public: true }
  },
  {
    path: '/',
    component: () => import('/@/views/layout.vue'),
    // AI 入口是主入口，登录后直接落到这
    redirect: '/ai',
    children: [
      {
        path: 'ai',
        name: 'ai',
        component: () => import('/@/views/ai.vue'),
        meta: { title: 'AI 入口' }
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('/@/views/dashboard.vue'),
        meta: { title: '工作台' }
      },
      {
        path: 'account',
        name: 'account',
        component: () => import('/@/views/account.vue'),
        meta: { title: '账号管理' }
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('/@/views/role.vue'),
        meta: { title: '角色管理' }
      },
      {
        path: 'menu',
        name: 'menu',
        component: () => import('/@/views/menu.vue'),
        meta: { title: '菜单管理' }
      },
      {
        // 外部页面（menu_type=2）统一在这层 iframe 容器里打开，AI 跳转的 path 也是 /frame/{id}
        path: 'frame/:id',
        name: 'frame',
        component: () => import('/@/views/frame.vue'),
        meta: { title: '外部页面' }
      }
    ]
  },
  { path: '/:pathMatch(.*)*', redirect: '/ai' }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// layout 拉到菜单后会写一份 menuPaths，这里用它兜权限：手敲 URL 也进不去没权限的页面
function allowedPaths() {
  try {
    return JSON.parse(sessionStorage.getItem('menuPaths') || '[]')
  } catch (e) {
    return []
  }
}

router.beforeEach((to, from, next) => {
  const token = getToken()
  if (!to.meta.public && !token) return next('/login')
  if (to.path === '/login' && token) return next('/')

  const allowed = allowedPaths()
  // /frame/{id} 是外部页面容器：可见性由后端 /menu/frame/{id} 校验（越权 403），
  // 前端不再拦——否则菜单刚加时旧 sessionStorage 会把人挡在外面，看起来就是「报错」
  const framePage = to.path.startsWith('/frame/')
  if (!to.meta.public && !framePage && allowed.length && !allowed.includes(to.path)) {
    ElMessage.error('没有权限访问该页面')
    return next('/ai')
  }
  next()
})

export default router
