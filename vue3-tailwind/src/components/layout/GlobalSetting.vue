<template>
    <el-drawer
        title="系统设置"
        :modelValue="drawVisible"
        direction="rtl"
        destroy-on-close
        @close="close"
    >
        <span>setting</span>
    </el-drawer>
</template>

<script>
import { inject, ref, toRefs, watch } from 'vue'
export default {
    name: "globalSetting",
    props: {
        draw: Boolean
    },
    emits: ['updateVisible'],
    setup(props, context) {
        // let { draw } = toRefs(props.draw)
        const drawVisible = ref(false)
        watch(() => {
            drawVisible.value = props.draw
            console.log("watched", drawVisible.value)
        })
        const close = () => {
            drawVisible.value = false
            context.emit("updateVisible", false)
        }
        return {
            drawVisible,
            close
        }
    },
}
</script>