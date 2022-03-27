<template lang="">
    <div class="inline-block tabs tabs-boxed">
        <Tab v-for="tabInfo in tabList" :tabInfo="tabInfo" :key="tabInfo.id"></Tab>
    </div>
</template>
<script>
import Tab from '/@/components/tabs/Tab.vue'
import { useStore } from 'vuex'
import { toRefs, ref, reactive } from 'vue'

export default {
    components: {
        Tab
    },
    setup(props) {
        const store = useStore()
        const { tabList } = toRefs(store.state)
        // console.log(store.state.tabList)
        console.log(tabList)
        return {
            tabList
        }
    }
}
</script>
<style lang="">
    
</style>