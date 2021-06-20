<template>
  <div class="h-full w-full">
    <el-container class="h-full w-full">
      <el-aside width="200px">
        <!-- left menu -->
        <!-- <left-menu :menus="menus" /> -->
        <left-menu-spec />
      </el-aside>

      <!-- right -->
      <el-container>
        
        <el-header class="" style="height: 88px">
          <!-- header -->
          <top-header></top-header>
          <div class="bg-tabs w-full p-0">
            <tabs />
          </div>
        </el-header>

        <el-main style="padding: 0"> 
            <!-- content --> 
            <div class="bg-white h-full">
              <router-view />
            </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Loading from "/@/components/Loading.vue";
// import LeftMenu from "/@/components/common/LeftMenu.vue";
import LeftMenuSpec from "/@/components/layout/LeftMenuSpec.vue";
import TopHeader from '/@/components/layout/TopHeader.vue';
import Tabs from '/@/components/tabs/Tabs.vue';
import "../../index.css";

export default {
  components: {
    Loading,
    // LeftMenu,
    TopHeader,
    LeftMenuSpec,
    Tabs
  },

  setup() {
    
    return {

    };
  },
};
</script>

<style scoped lang="scss">
.el-header {
    padding: 0;
}

.bg-tabs {
  background-color: $tabs-bg;
}
</style>
