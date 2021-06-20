<template lang="">
    <div class="inline-block">
        <Tab v-for="tabInfo in tabList" :tabInfo="tabInfo"></Tab>
    </div>
</template>
<script>
import Tab from '/@/components/tabs/Tab.vue'
import { useStore } from 'vuex'

export default {
    components: {
        Tab
    },
    setup(props) {
        const store = useStore()
        const tabList = store.state.tabList
        return {
            tabList
        }
    }
}
</script>
<style lang="">
    
</style>