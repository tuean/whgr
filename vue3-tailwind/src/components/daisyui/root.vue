<template>
  <div
    :data-theme="theme"
    class="w-full h-screen drawer drawer-mobile bg-base-100"
  >
    <input
      id="my-drawer-2"
      type="checkbox"
      class="appearance-none drawer-toggle"
    />
    <div class="drawer-content">
      <!-- page header -->
      <div
        class="sticky top-0 z-30 flex justify-center w-full h-16 transition-all duration-100 shadow-sm bg-opacity-90 backdrop-blur bg-base-100 text-base-content"
      >
        <nav class="w-full navbar">
          <div class="flex flex-1 md:gap-1 lg:gap-2">
            <svg
              @click="refresh"
              width="22px"
              height="22px"
              xmlns="http://www.w3.org/2000/svg"
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
              width="22px"
              height="22px"
              xmlns="http://www.w3.org/2000/svg"
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

            <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 cursor-pointer" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
              <path stroke-linecap="round" stroke-linejoin="round" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
              <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
          </div>

          <div class="flex-0">
            <!-- <div class="avatar placeholder">
              <div
                class="w-16 rounded-full bg-neutral-focus text-neutral-content"
              >
                <span class="text-xl"
                  ><a href="https://github.com/tuean">tuean</a></span
                >
              </div>
            </div> -->
            <button class="gap-2 btn">
              <div class="badge badge-secondary">
                <a href="https://github.com/tuean">tuean</a>
              </div>
            </button>
          </div>
        </nav>
      </div>
      <!-- tabs -->
      <div
        class="sticky top-0 z-30 flex justify-center w-full transition-all duration-100 shadow-sm bg-opacity-90 backdrop-blur bg-base-100 text-base-content"
      >
        <nav class="w-full tab-mine">
          <tabs />
        </nav>
      </div>

      <!-- Page content here -->
      <div class="p-3">
        <router-view />
      </div>
    </div>
    <div class="drawer-side scrollbar scrollbar-thumb-dark-900 scrollbar-track-dark-100">
      <label for="my-drawer-2" class="drawer-overlay"></label>
      <aside class="bg-base-200 w-80">
        <div
          class="sticky top-0 z-20 items-center hidden gap-2 px-4 py-2 bg-base-200 bg-opacity-90 backdrop-blur lg:flex"
        >
          <a
            href="/"
            aria-current="page"
            aria-label="Homepage"
            class="px-2 flex-0 btn btn-ghost"
          >
            <div
              class="inline-flex text-lg transition-all duration-200 font-title text-primary md:text-3xl"
            >
              <span class="lowercase">whgr</span>
              <span class="uppercase text-base-content">demo</span>
            </div>
          </a>
        </div>
        <ul class="p-4 overflow-y-auto menu w-80 bg-base-200 text-base-content">
          <!-- Sidebar content here -->
          <li v-for="(menu, index) in menus_flat" :key="menu.id">
            <a class="flex gap-4" @click="tabJump(menu)">{{ index + 1 }}. {{ menu.name }}</a>
          </li>
        </ul>
      </aside>
    </div>
  </div>
</template>

<script>
import { ref, reactive, inject, nextTick } from "vue";
import menus from "/@/conf/menu.js";
import router from "/@/router/index";
import { flatMenus } from "/@/util/index";
import screenfull from "screenfull";
import Tabs from "/@/components/tabs/Tabs.vue";
import { useRouter } from "vue-router";
import DynamicPage from '@/com/pages/daisyui/DynamicPage.vue'
// import refresh from '/@/assets/refresh.svg'
// import full from '/@/assets/full.svg'

export default {
  components: {
    Tabs
  },
  setup() {
    const router = useRouter();
    const theme = ref("dark");
    const menus_flat = flatMenus(menus);
    console.log(menus_flat);

    const state = reactive({
      rotate: true,
      full: false
    });
    const reload = inject("reload");
    const refresh = () => {
      if (!state.rotate) return;
      state.rotate = !state.rotate;
      if (reload != null) {
        reload();
      }
      setTimeout(() => {
        state.rotate = !state.rotate;
      }, 1000);
    };
    const fullscreen = () => {
      console.log((state.full ? "enter" : "cancel") + "full screen");
      if (!state.full) {
        screenfull.request();
      } else {
        screenfull.exit();
      }
      state.full = !state.full;
    };

    const tabJump = async menu => {
      console.log(menu);
      let isThird = menu.isThird || false
      if (!isThird) { // 非三方页面
        router.push(m.path);  
      } else {  // 三方页面
        let path = 'third-' + menu.id
        router.addRoute('daisyui', {
          path: path,
          name: menu.id,
          component: DynamicPage,
          meta: {
            url: menu.path
          }
        })
        await nextTick()
        router.push('/daisyui/' + path);
      }
    }

    return {
      theme,
      menus_flat,
      refresh,
      fullscreen,
      tabJump
    };
  }
};
</script>

<style scoped>
.navbar {
  display: flex;
  align-items: center;
  padding: var(--navbar-padding, 0.5rem);
  min-height: 4rem;
}
.tab-mine {
  padding-left: var(--navbar-padding, 0.5rem);
  padding-right: var(--navbar-padding, 0.5rem);
}
</style>
