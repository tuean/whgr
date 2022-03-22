<template>
  <div class="h-full w-full">
    <el-container class="h-full w-full">
      <el-aside width="200px">
        <!-- left menu -->
        <!-- <left-menu :menus="menus" /> -->
        <left-menu-spec />
      </el-aside>

      <!-- right -->
      <el-container class="h-screen">
        
        <el-header class="" style="height: auto">
          <!-- header -->
          <top-header></top-header>
          <div class="bg-tabs w-full p-0 h-13">
            <tabs />
          </div>
        </el-header>

        <el-main style="padding: 0"> 
            <!-- content --> 
            <div class="bg-main">
              <router-view v-slot="{ Component }" :key="$route.fullPath">
                <transition name="router-fade" mode="out-in">
                  <keep-alive v-if="isRouterAlive">
                    <component :is="Component" />
                  </keep-alive>
                </transition>
              </router-view>
            </div>
        </el-main>
      </el-container>
    </el-container>

    <GlobalSetting :draw="draw" @updateVisible="updateVisible"/>
  </div>
</template>

<script>
import { ref, nextTick, provide, reactive } from 'vue'
import Loading from "/@/components/Loading.vue";
// import LeftMenu from "/@/components/common/LeftMenu.vue";
import LeftMenuSpec from "/@/components/layout/LeftMenuSpec.vue";
import TopHeader from '/@/components/layout/TopHeader.vue';
import Tabs from '/@/components/tabs/Tabs.vue';
import GlobalSetting from '/@/components/layout/GlobalSetting.vue'
import { useStore } from 'vuex'


export default {
  components: {
    Loading,
    // LeftMenu,
    TopHeader,
    LeftMenuSpec,
    Tabs,
    GlobalSetting
  },

  setup() {
    const store = useStore()
    store.commit('initTheme')
    const isRouterAlive = ref(true)
    const draw = ref(false)
    const reload = () => {
      console.log("reload admin")
      isRouterAlive.value = false;
      nextTick(() => {
        isRouterAlive.value = true;
      });
    };
    const openGlobalSetting = () => {
      draw.value = true
      console.log('open global setting')
      console.log(draw)
    }
    const updateVisible = () => {
      draw.value = false
    }
    provide("reload", reload);
    provide("openGlobalSetting", openGlobalSetting)
           

    return {
        isRouterAlive,
        reload,
        draw,
        updateVisible
    };
  },
};
</script>

<style scoped lang="scss">
.el-header {
    padding: 0;
}

.bg-tabs {
  background-color: var(--tabs-bg)
}

.bg-main {
  background-color: var(--end-bg);
  // height: 100% - 56px;
  overflow-y: scroll;
  overflow-x: hidden;
}
</style>
