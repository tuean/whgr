<template lang="">
    <div class="pl-1 mr-1 bg-cell inline-block">
        <span class="text-gray-300 ">
            <!-- <span v-if="tabInfo.icon" /> -->
            <span class="can-click" @click="activeTab">{{ tabInfo.name }}</span>
            <span class="pl-1 pr-1 can-click" @click="closeTab" v-if="!tabInfo.removeable">x</span>
        </span>
    </div>
</template>
<script>
import { useStore } from 'vuex'
import { reactive, toRefs } from 'vue'
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
        }
        const activeTab = () => {
            store.commit('activeTab', tabInfo)
            router.push(tabInfo.path)
        }
        return {
            tabInfo,
            closeTab,
            activeTab
        }
    }
}
</script>
<style lang="scss">
    .bg-cell {
        background-color: $end-bg;   
    }
</style>