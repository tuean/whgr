<template lang="">
    <span>
        <span class="text-gray-300 border-gray-50 border-2 pl-1">
            <!-- <span v-if="tabInfo.icon" /> -->
            <span class="can-click" @click="activeTab">{{ tabInfo.name }}</span>
            <span class="pl-1 pr-1 can-click" @click="closeTab">x</span>
        </span>
    </span>
</template>
<script>
import { useStore } from 'vuex'
import { toRefs } from 'vue'
import { useRouter } from 'vue-router'

export default {
    props: {
        tabInfo: Object
    },
    setup(props) {
        const tabInfo = props.tabInfo
        const store = useStore()
        const router = useRouter()
        const closeTab = () => {
            store.commit('removeTab', tabInfo.index)
        }
        const activeTab = () => {
            store.commit('activeTab', tabInfo.index)
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
<style lang="">
    
</style>