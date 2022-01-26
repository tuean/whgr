<template lang="">
    <div class="bg w-full h-14 p-0">
        <div class="flex h-full items-center pr-2 justify-between">
            <div class="text-gray-300 " >
                <i :class="rotate ? 'el-icon-refresh-right default can-click' : 'el-icon-refresh-right refresh can-click '" @click="refresh"/>
                <i class="el-icon-full-screen default pl-2 can-click " @click="fullscreen"/>
            </div>
            <div class=""><HeaderInfo/></div>
            
        </div>
    </div>
</template>
<script>
import { reactive, toRefs, inject } from 'vue'
import HeaderInfo from '/@/components/layout/HeaderInfo.vue'
import { useStore } from 'vuex'
import screenfull from 'screenfull'

export default {
    components: {
        HeaderInfo
    },
    setup(props) {
        const store = useStore()
        const state = reactive({
            rotate: true,
            full: false
        })
        const reload = inject('reload')
        const refresh = () => {
            if (!state.rotate) return
            state.rotate = !state.rotate
            reload()
            setTimeout(() => {
                state.rotate = !state.rotate
            }, 1000)
        }
        const fullscreen = () => {
            console.log((state.full ? "enter" : "cancel") + "full screen")
            if (!state.full) {
                screenfull.request()
            } else {
                screenfull.exit()
            }
            state.full = !state.full
        }
        return {
            ...toRefs(state),
            refresh,
            fullscreen
        }
    }
}
</script>
<style lang="scss">
    .bg{
        background-color: $header-bg;
    }
    .default {
        transition: all 0s;
    }
    .refresh {
        transform: rotate(720deg);
        transition: all 1s;
    }
</style>