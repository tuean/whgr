<template>
  <div>
    <el-menu
      default-active="1"
      :collapse="isCollapse"
      class="h-screen bg-yhh el-menu-vertical-demo"
    >
      <component
        v-for="menu in menus"
        :key="menu.index"
        :index="menu.index"
        :is="menu.isFolder ? 'el-submenu' : 'el-menu-item'"
      >
        <template #title v-if="menu.isFolder" class="pl-8" style="padding: 0">
          <span>{{ menu.name }}</span>
        </template>
        <el-menu-item-group v-if="menu.isFolder">
          <el-menu-item v-for="c in menu.child" :key="c.index" :index="c.index">
            <template #title class="pl-8" style="padding: 0">
              <span>{{ c.name }}</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item :index="menu.index" class="pl-0 pr-0" @click="menuClick">
          <template #title class="pl-8" style="padding: 0">
            <span>{{ menu.name }}</span>
          </template>
        </el-menu-item>
      </component>
    </el-menu>
  </div>
</template>
<script>
import menus from "/@/conf/menu.js";
import router from '/@/router/index'


export default {
  name: "LeftMenu",
  props: {
    // menus: Object | Array,
  },
  setup(props) {
    // console.log(menus);
    const hasChild = (menu) => {
      return menu.child != null && menu.child.length > 0;
    };
    let isCollapse = false;
    const menuClick = (menu, index) => {
      console.log(menu, index);
      router.push('/admin/pageNotFund')
    };
    return {
      menus,
      hasChild,
      isCollapse,
      menuClick,
    };
  },
};
</script>
<style scoped>
/* .el-submenu .el-menu-item { */
/* padding-left: 20px !important; */
/* } */
/* .el-menu {
    background-color: #f1939c;
}
.el-menu-item:hover {
    background-color: #b598a1;
}
.el-menu-item:focus {
    background-color: #f1939c;
}
..el-submenu__title {
    background-color: #f1939c;
}
.el-submenu__title:hover {
    background-color: #b598a1;
}
.el-submenu__title:focus {
    background-color: #b598a1;
} */
</style>