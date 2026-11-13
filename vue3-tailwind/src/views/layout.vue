<template>
  <!-- 结构与样式沿用 components/daisyui/root.vue 的抽屉式布局 -->
  <div data-theme="dark" class="w-full h-screen drawer drawer-mobile bg-base-100 text-base-content">
    <input id="my-drawer" type="checkbox" class="appearance-none drawer-toggle" />

    <div class="drawer-content flex flex-col min-w-0">
      <div
        class="sticky top-0 z-30 flex justify-center w-full h-16 transition-all duration-100 shadow-sm bg-opacity-90 backdrop-blur bg-base-100"
      >
        <nav class="w-full navbar">
          <div class="flex flex-1 items-center gap-2">
            <label for="my-drawer" class="btn btn-square btn-ghost btn-sm lg:hidden">
              <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </label>
            <svg
              @click="refresh"
              class="w-6 h-6 cursor-pointer"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              stroke-width="2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
              />
            </svg>
            <svg
              @click="fullscreen"
              class="w-6 h-6 cursor-pointer"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              stroke-width="2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M4 8V4m0 0h4M4 4l5 5m11-1V4m0 0h-4m4 0l-5 5M4 16v4m0 0h4m-4 0l5-5m11 5l-5-5m5 5v-4m0 4h-4"
              />
            </svg>
            <span class="ml-2 text-sm text-base-content/60 hidden md:inline">{{ route.meta.title }}</span>
          </div>

          <div class="flex-0 flex items-center gap-2">
            <button class="btn btn-sm btn-primary gap-1" @click="router.push('/ai')">
              <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                />
              </svg>
              AI
            </button>
            <div class="badge badge-secondary">{{ user.name || user.account || 'guest' }}</div>
            <button class="btn btn-ghost btn-sm" @click="onLogout">退出</button>
          </div>
        </nav>
      </div>

      <div class="flex-1 overflow-auto p-3 bg-base-200">
        <router-view />
      </div>
    </div>

    <div class="drawer-side scrollbar scrollbar-thumb-dark-900 scrollbar-track-dark-100">
      <label for="my-drawer" class="drawer-overlay"></label>
      <aside class="bg-base-200 w-80">
        <div
          class="sticky top-0 z-20 items-center hidden gap-2 px-4 py-2 bg-base-200 bg-opacity-90 backdrop-blur lg:flex"
        >
          <a href="#/" aria-current="page" aria-label="Homepage" class="px-2 flex-0 btn btn-ghost">
            <div class="inline-flex text-lg transition-all duration-200 font-title text-primary md:text-3xl">
              <span class="lowercase">whgr</span>&nbsp;
              <span class="uppercase text-base-content">admin</span>
            </div>
          </a>
        </div>
        <ul class="p-4 overflow-y-auto menu w-80 bg-base-200 text-base-content">
          <li v-for="item in menus" :key="item.id">
            <router-link
              :to="item.path"
              class="flex gap-4"
              :class="{ active: route.path === item.path }"
            >
              {{ item.menuName }}
            </router-link>
          </li>
        </ul>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import screenfull from 'screenfull'
import { menuList, profile, logout } from '/@/api'
import { clearToken } from '/@/api/request'

const route = useRoute()
const router = useRouter()
const menus = ref([])
const user = reactive({})

onMounted(async () => {
  const [list, me] = await Promise.all([menuList(), profile()])
  menus.value = list || []
  // 前端跳转前用它兜一层权限：只允许跳到当前账号可见的菜单
  // path 由后端算好：外部页面是 /frame/{id}，和 AI 白名单、路由守卫共用同一套
    sessionStorage.setItem('menuPaths', JSON.stringify(menus.value.map(m => m.path)))
  Object.assign(user, me || {})
})

const refresh = () => location.reload()

const fullscreen = () => (screenfull.isFullscreen ? screenfull.exit() : screenfull.request())

const onLogout = async () => {
  try {
    await logout()
  } finally {
    clearToken()
    // 别把上一个账号的 AI 对话和菜单权限留给下一个登录的人
    sessionStorage.removeItem('aiTurns')
    sessionStorage.removeItem('menuPaths')
    router.push('/login')
  }
}
</script>
