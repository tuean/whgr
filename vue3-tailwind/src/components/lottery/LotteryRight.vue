<template>
    <div class=" fixed h-screen w-11 right-0 text-gray-300">
        <el-button type="primary" size="small" @click="toggle">{{state.status === 'running' ? "停止" : "开始"}}</el-button>
    </div>
</template>
<script>
import { reactive, ref } from 'vue'
export default {
    setup(props) {
        const status = ref('stop')
        const state = reactive({
            status
        })
        const start = () => {
            console.log('start')
            state.status = 'running'
        }
        const stop = () => {
            console.log('end')
            state.status = 'stop'
        }
        const toggle = () => {
            if (state.status === 'running') {
                stop()
                return;
            }
            if (state.status === 'stop') {
                start()
                return;
            }
        }
        return {
            state,
            status,
            toggle,
            start,
            stop
        }
    }
}
</script>
<style lang="">
    
</style>