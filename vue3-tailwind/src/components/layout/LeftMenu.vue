<template>
  <div>
    <el-menu
      default-active="1"
      :collapse="isCollapse"
      class="h-screen bg-theme"
    >
      <component
        v-for="menu in menus"
        :key="menu.index"
        :index="menu.index"
        :is="menu.isFolder ? 'el-submenu' : 'el-menu-item'"
        :class="'bg-theme'"
      >
        <template #title v-if="menu.isFolder" class="pl-8" style="padding: 0">
          <span>{{ menu.name }}</span>
        </template>
        <el-menu-item-group v-if="menu.isFolder">
          <el-menu-item class="bg-theme" v-for="c in menu.child" :key="c.index" :index="c.index" :route="{path: menu.path}">
            <template #title class="pl-8" style="padding: 0">
              <span>{{ c.name }}</span>
            </template>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item :index="menu.index" class="pl-0 pr-0 bg-theme" @click="menuClick" :route="{path: menu.path}"> 
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
    const menuClick = (i, menu) => {
      console.log(i.index);
      console.log(menu)
      let path = getUrl(i.index, menus)
      console.log("route to >>>>>>", path)
      // router.push('/admin/pageNotFund')
      router.push(path)
    }

    const getUrl = (index, menus) => {
      debugger
        if (menus == null) return null
        for (let x = 0; menus.length > x; x++) {
          let menu = menus[x]
          debugger
          if (menu.index === index) return menu.path
          if (!menu.isFolder) continue
          let url = getUrl(index, menu.child)
          if (url != null) return url
        }
        return null
    }

    return {
      menus,
      hasChild,
      isCollapse,
      menuClick,
    };
  },
};
</script>
<style lang='less'>
.bg-theme {
  background-color: var(--theme-color)
}
.el-meun {
  background-color: var(--theme-color)
}
.el-menu-item {
  background-color: var(--theme-color)
}
.el-submenu {
  background-color: var(--theme-color)
}
.el-submenu__title {
  background-color: var(--theme-color)
}
</style>