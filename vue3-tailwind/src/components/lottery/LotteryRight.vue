<template>
    <div class=" fixed h-screen w-11 right-0 text-gray-300">
        <el-button type="primary" size="small" @click="toggle">{{state.status === 'running' ? "停止" : "开始"}}</el-button>
        <LotteryResult :visible="showFlag" :result="result" />
    </div>
    
</template>
<script>
import { reactive, ref } from 'vue'
import { speed, fast } from "/@/components/lottery/lottery.js";
import LotteryResult from '/@/components/lottery/LotteryResult.vue'

export default {
    components: {
        LotteryResult
    },
    setup(props) {
        const status = ref('stop')
        let showFlag = ref(false)
        const result = reactive({})
        const state = reactive({
            status
        })
        const start = () => {
            console.log('start')
            state.status = 'running'
            window.TagCanvas.SetSpeed('rootcanvas', fast());
            hidden()
        }
        const stop = () => {
            console.log('end')
            state.status = 'stop'
            window.TagCanvas.SetSpeed('rootcanvas', speed());
            show()
            setResult("1", "22")
            console.log(showFlag)
            console.log(result)
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

        const setResult = (k, v) => {
            result[k] = v
        }

        const show = () => {
            showFlag = true
        }

        const hidden = () => {
            showFlag = false
        }
        return {
            state,
            status,
            toggle,
            start,
            stop,
            showFlag,
            result
        }
    }
}
</script>
<style lang="">
    
</style>