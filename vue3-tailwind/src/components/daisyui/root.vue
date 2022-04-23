<template>
  <div
    :data-theme="theme"
    class="h-screen drawer drawer-mobile w-full bg-base-100"
  >
    <input
      id="my-drawer-2"
      type="checkbox"
      class="drawer-toggle appearance-none"
    />
    <div class="drawer-content">
      <!-- page header -->
      <div class="sticky top-0 z-30 flex h-16 w-full justify-center bg-opacity-90 backdrop-blur transition-all duration-100 bg-base-100 text-base-content shadow-sm">
        <nav class="navbar w-full">
            <div class="flex flex-1 md:gap-1 lg:gap-2">
                tools:
            </div> 

            <div class="flex-0">
                avatar
            </div>
        </nav>
      </div>

      <!-- Page content here -->
    </div>
    <div class="drawer-side">
      <label for="my-drawer-2" class="drawer-overlay"></label>
      <aside class="bg-base-200 w-80">
        <div
          class="
            z-20
            bg-base-200 bg-opacity-90
            backdrop-blur
            sticky
            top-0
            items-center
            gap-2
            px-4
            py-2
            hidden
            lg:flex
          "
        >
          <a
            href="/"
            aria-current="page"
            aria-label="Homepage"
            class="flex-0 btn btn-ghost px-2"
          >
            <div
              class="
                font-title
                text-primary
                inline-flex
                text-lg
                transition-all
                duration-200
                md:text-3xl
              "
            >
              <span class="lowercase">whgr</span>
              <span class="text-base-content uppercase">demo</span>
            </div>
          </a>
        </div>
         <ul class="menu p-4 overflow-y-auto w-80 bg-base-200 text-base-content">
            <!-- Sidebar content here -->
            <li v-for="(menu, index) in menus_flat" :key="menu.id">
                <a class="flex gap-4">{{index+1}}. {{ menu.name }}</a>
            </li>

        </ul>
      </aside>
     
    </div>
  </div>
</template>


<script>
import { ref, reactive } from "vue";
import menus from "/@/conf/menu.js";
import router from "/@/router/index";
import { flatMenus } from '/@/util/index'

export default {
  setup() {
    const theme = ref("dark");
    const menus_flat = flatMenus(menus)
    console.log(menus_flat)

    return {
      theme,
      menus_flat,
    };
  },
};
</script>


<style scoped>
.navbar { ·
    display: flex;

}
</style>