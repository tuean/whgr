<template lang="">
    <div>
        {{$store.state.count}}
        <el-button type="primary" @click="$store.commit('increment')">+1</el-button>
    </div>
</template>
<script>
export default {
    setup(props) {
        // console.log($store.state)
        return {}
    }
}
</script>
<style lang="">
    
</style>