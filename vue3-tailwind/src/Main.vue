<template>
  <div class="h-full w-full block">
    <!-- pure page -->
    <div class="relative bg-no-repeat bg-cover lg:bg-center bg-top-right" v-if="state.pageType === 1">
      <router-view />
    </div>

    <!-- left menu -->
    <el-container v-else-if="state.pageType === 2">
      <el-aside class="aside">
        <div class="head">
          <div>
            <span>tailwind</span>
          </div>
        </div>
        <div class="line" />
        <el-menu
            :default-openeds="state.defaultOpen"
          background-color="#222832"
          text-color="#fff"
          :router="true"
          :default-active='state.currentPath'
        >
            <el-submenu index="1">
            <template #title>
              <span>Dashboard</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/introduce"><i class="el-icon-data-line" />系统介绍</el-menu-item>
              <el-menu-item index="/dashboard"><i class="el-icon-odometer" />Dashboard</el-menu-item>
              <el-menu-item index="/add"><i class="el-icon-plus" />添加商品</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
    </el-container>

    <el-container v-else>
        <span>404</span>
    </el-container>
  </div>
</template>

<script>
import Header from "./components/layout/Header.vue";
import { useRouter } from 'vue-router'
import { reactive } from 'vue'
import './index.css'


export default {
  components: { Header },
  setup() {
    const router = useRouter();
    const state = reactive({
      defaultOpen: [],
      pageType: 1,
      currentPath: "/login",
    });

    router.beforeEach((to, from, next) => {
      // debugger
      if (to.name !== '404' && to.matched.length == 0) {
        next({path: '/404'})
        return
      }
      if (to.path == '/login') {
        next()
      } else {
        next()
      }
      state.pageType = 1
      document.title = pathMap[to.name]
    })

    const loginPage = ["/login"];

    return {
        state
    }
  },
};
</script>
