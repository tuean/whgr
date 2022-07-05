<template>
  <!-- <div class="h-screen bg-menu">
        <MenuItem v-for="menu in menus" :menu="menu" :key="menu.index"/>
    </div> -->
  <div class="h-screen bg-menu">
    <el-menu
      :uniqueOpened="true"
      default-active="1"
      class="el-menu-vertial"
      @open="open"
      @close="close"
    >
      <template v-for="menu in menus" :key="menu.id">
        <!-- 二级 -->
        <template v-if="menu.child != null && menu.child.length > 0">
          <el-submenu :index="menu.id">
            <template #title>
              <i :class="menu.icon"></i>
              <span>{{ menu.name }}</span>
            </template>

            <el-menu-item
              v-for="subMenu in menu.child"
              :key="subMenu.id"
              :index="subMenu.id"
              :route="subMenu.path"
              @click="menuClick(subMenu)"
            >
              <i :class="subMenu.icon"></i>
              <span slot="title">{{ subMenu.name }}</span>
            </el-menu-item>
          </el-submenu>
        </template>
        <!-- 一级 -->
        <template v-if="menu.child == null || menu.child.length < 1">
          <el-menu-item
            :key="menu.id"
            :index="menu.id"
            :route="menu.path"
            @click="menuClick(menu)"
          >
            <i :class="menu.icon"></i>
            <span slot="title">{{ menu.name }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
<script>
import MenuItem from "/@/components/layout/MenuItem.vue";
import menus from "/@/conf/menu.js";
import store from "/@/store/index";
import { useRouter } from "vue-router";
import { computed } from "vue";
import settings from '/@/store/settings' 

export default {
  components: {
    MenuItem,
  },
  props: {
    // menus: Array
  },
  setup(props) {
    const router = useRouter();
    store.commit("pushTab", menus[0]);
    store.commit("activeTab", menus[0]);
    const open = (i, m) => {
      console.log(m);
    };

    const close = (i, m) => {
      console.log(m);
    };

    const menuClick = (m) => {
      console.log(m);
      let isThird = m.isThird || false
      store.commit("pushTab", m);
      store.commit("activeTab", m);
      if (isThird) {
        router.push({path: '/admin/micro', params: {url: m.path}})
      } else {
        router.push(m.path);
      }
    };

    return {
      menus,
      open,
      close,
      menuClick,
    };
  },
};
</script>
<style lang="less">
.bg-menu {
  background-color: var(--theme-color) !important
}
.el-menu-vertial {
  border-right: 0;
  background-color: var(--theme-color)
}
.el-meun {
  background-color: var(--theme-color);
  color: var(--theme-text);
}
.el-menu-item {
  background-color: var(--theme-color);
  color: var(--theme-text);
}
.el-menu-item:focus, .el-menu-item:hover {
    outline: 0;
    background-color: var(--theme-color)
}
.el-submenu {
  background-color: var(--theme-color);
  color: var(--theme-text);
}
.el-submenu__title {
  color: var(--theme-text);
}
.el-submenu__title:focus, .el-submenu__title:hover {
  background-color: var(--theme-color)
}

</style>