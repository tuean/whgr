<template lang="">
    <!-- <div class="inline-block pl-1 mr-2 bg-cell"> -->
        <!-- <span class="text-gray-300 "> -->
            <!-- <span v-if="tabInfo.icon" /> -->
            <!-- <span class="can-click" @click="activeTab">{{ tabInfo.name }}</span> -->
            <!-- <span class="pl-1 pr-1 can-click" @click="closeTab" v-if="tabInfo.removeable">x</span> -->
        <!-- </span> -->
    <!-- </div> -->
    <div :class="tabClass" @click="activeTab">
        <span>{{tabInfo.name}}</span>
        <span class="pl-1 pr-1 can-click" @click="closeTab" v-if="tabInfo.removeable">x</span>
    </div>
</template>
<script>
import { useStore } from 'vuex'
import { reactive, toRefs, ref } from 'vue'
import { useRouter } from 'vue-router'
import { tabEqual, getNextShow } from '/@/util/index'

export default {
    props: {
        tabInfo: Object
    },
    setup(props) {
        const tabInfo = reactive(props.tabInfo)
        console.log('init tabInfo', tabInfo)
        const store = useStore()
        const router = useRouter()
        const tabClass = ref('tab')
        if (tabInfo.active) {
            tabClass.value = 'tab active'
        } else {
            tabClass.value = 'tab'
        }
        const closeTab = () => {
            // 计算前面一个tab
            // let nextShow = tabEqual(store.state.tabList, tabInfo.id)
            let nextShow = getNextShow(store.state.tabList, tabInfo.id)
            // 展示下一个tab
            if (nextShow != null) {
                store.commit('activeTab', nextShow)
            }
            store.commit('removeTab', tabInfo.id)
            if (nextShow != null) router.push(nextShow.path)
            console.log('after close', store.state.tabList)
            tabClass.value = 'tab'
        }
        const activeTab = () => {
            store.commit('activeTab', tabInfo)
            console.log('active tab', tabInfo)
            router.push('/daisyui' + tabInfo.path)
            tabClass.value = "tab tab-active"
        }
        
        return {
            tabInfo,
            closeTab,
            activeTab,
            tabClass
        }
    }
}
</script>
<style lang="scss">
    .bg-cell {
        background-color: var(--theme-color);   
    }
</style>