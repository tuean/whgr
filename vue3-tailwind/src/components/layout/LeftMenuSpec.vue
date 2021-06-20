<template>
    <div class="h-screen bg-menu">
        <MenuItem v-for="menu in menus" :menu="menu" :key="menu.index"/>
    </div>
</template>
<script>
import MenuItem from '/@/components/layout/MenuItem.vue'
import menus from "/@/conf/menu.js";
import store from '/@/store/index'


export default {
    components: {
        MenuItem,
    },
    props: {
        // menus: Array
    },
    setup(props) {
        store.commit('pushTab', menus[0])
        store.commit('activeTab', menus[0].index)

        return {
            menus
        }
    }
}
</script>
<style lang="scss">
    .bg-menu {
        background-color: $default-bg;
    }
</style>