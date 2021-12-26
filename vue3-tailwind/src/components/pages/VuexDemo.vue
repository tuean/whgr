<template lang="">
    <div>
        {{$store.state.count}}
        <el-button type="primary" @click="$store.commit('increment')">+1</el-button>
        <el-button @click="mitt">mitt</el-button>
        <el-input type="number" v-model="n"></el-input>
    </div>
</template>
<script>
import {reactive, ref} from 'vue'
import bus from '@/bus'

export default {
    setup(props) {
        // console.log($store.state)
        const mitt = () => {
            bus.emit("log", {a:"1"})
        }
        const n = ref(0)
        return {
            n, 
            mitt
        }
    }
}
</script>
<style lang="">
    
</style>