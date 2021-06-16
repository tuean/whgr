<template>
  <div class="h-full w-full">
    <el-container class="h-full w-full">
      <el-aside width="200px">
        <!-- left menu -->
        <left-menu :menus="menus" />
      </el-aside>

      <!-- right -->
      <el-container>
        <el-header class="">
          <!-- header -->
          <top-header></top-header>
        </el-header>
        <el-main style="padding: 0"> 
            <!-- content --> 
            
            <div class="bg-black h-full">
              <router-view />
            </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Loading from "/@/components/Loading.vue";
import LeftMenu from "/@/components/common/LeftMenu.vue";
import TopHeader from '/@/components/common/TopHeader.vue';

import "../../index.css";

export default {
  components: {
    Loading,
    LeftMenu,
    TopHeader
  },

  setup() {
    return {

    };
  },
};
</script>

<style scoped>
.el-header {
    padding: 0;
}
</style>
