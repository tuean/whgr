<template lang="">
    <div class="bg w-full h-14 p-0">
        <div class="flex h-full items-center pr-2 justify-between">
            <div class="can-click text-gray-300" @click="refresh">
                <i :class="rotate ? 'el-icon-refresh-right default' : 'el-icon-refresh-right refresh'" />
            </div>
            <div class=""><HeaderInfo/></div>
            
        </div>
    </div>
</template>
<script>
import { reactive, toRefs, inject } from 'vue'
import HeaderInfo from '/@/components/layout/HeaderInfo.vue'
import { useStore } from 'vuex'

export default {
    components: {
        HeaderInfo
    },
    setup(props) {
        const store = useStore()
        const state = reactive({
            rotate: true
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
        return {
            ...toRefs(state),
            refresh
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