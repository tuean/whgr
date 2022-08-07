<template lang="">
    <div :class="tabClass">
        <span class="text-xl" @click="activeTab">{{tabInfo.name}}</span>
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
            let nextShow = getNextShow(store.state.tabList, tabInfo.id)
            // 展示下一个tab
            if (nextShow != null) {
                store.commit('activeTab', nextShow)
            } else {
                router.push('/')
            }
            store.commit('removeTab', tabInfo.id)
            if (nextShow != null) {
              console.log('nextShow', nextShow.path)
              router.push(nextShow.path)
            }
            console.log('after close', store.state.tabList)
            tabClass.value = 'tab'
        }
        const activeTab = () => {
            store.commit('activeTab', tabInfo)
            console.log('active tab', tabInfo)
            router.push(tabInfo.path)
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