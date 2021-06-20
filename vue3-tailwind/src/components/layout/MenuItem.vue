<template>
    <div>
        <div class="text-gray-300 text-xl pl-8 h-10 flex items-center no-select can-click"  @click="jumpMenu(menu)">
            <span>{{menuName}}</span>
        </div>
        <div class="">
            <div v-for="c in child" :key="c.id">
                <span class="text-gray-300 text-lg pl-12 h-10 flex items-center no-select can-click" @click="jump(c)">
                    {{c.name}}
                </span>
            </div>
        </div>
    </div>
</template>
<script>
import router from '/@/router/index'
import store from '/@/store/index'

export default {
    props: {
        menu: Array|Object
    },
    setup(props) {
        console.log(props)
        const menuName = props.menu.name
        const child = props.menu.child
        
        const jump = (child) => {
            console.log(child)
            store.commit('pushTab', child)
            router.push(child.path)
        } 
        const jumpMenu = (menu) => {
            //  console.log(menu)
            store.commit('pushTab', menu)
            router.push(menu.path)
        }
        return {
            menuName,
            child,
            jump,
            jumpMenu
        }
    }
}
</script>
<style lang="scss" scoped>
    
</style>