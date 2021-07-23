<template lang="">
    <div>
        <span class="text-gray-300 pr-2">tuean: {{$store.state.count}}</span>
        <span class="">
            <i class="el-icon-setting text-gray-300 can-click" @click="openGlobalSetting"/>
        </span>
    </div>
</template>
<script>
import { inject } from 'vue'
export default {
    setup(props) {
        const openGlobalSetting = inject('openGlobalSetting')
        return {
            openGlobalSetting
        }
    }
}
</script>
<style lang="scss" scoped>
    
</style>