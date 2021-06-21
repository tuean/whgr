<template lang="">
    <div>
        {{$store.state.count}}
        <el-button type="primary" @click="$store.commit('increment')">+1</el-button>
        <el-input type="number" v-model="n"></el-input>
    </div>
</template>
<script>
import {reactive, ref} from 'vue'
export default {
    setup(props) {
        // console.log($store.state)
        const n = ref(0)
        return {
            n
        }
    }
}
</script>
<style lang="">
    
</style>