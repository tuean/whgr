<template>
    <el-drawer
        title="系统设置"
        :withHeader="false"
        :modelValue="drawVisible"
        direction="rtl"
        destroy-on-close
        @close="close"
        
    >
        <div class="pl-3 pr-3">
            <el-divider>主题色</el-divider>
            <div class="flex justify-center">
                <div>
                    <div class="rounded-md w-12 h-12 bg-black border-opacity-25 ring shadow-2xl"></div>
                    <div class="text-center text-sm pt-2" @click="setTheme('black')">暗黑</div>
                </div>
                <span class="w-8"></span>
                <div>
                    <div class="rounded-md w-12 h-12 bg-white border-opacity-25 ring shadow-2xl" ></div>
                    <div class="text-center text-sm pt-2" @click="setTheme('white')">日常</div>
                </div>
            </div>
        </div>
        
    </el-drawer>
</template>

<script>
import { inject, ref, toRefs, watch, reactive } from 'vue'
export default {
    name: "globalSetting",
    props: {
        draw: Boolean
    },
    emits: ['updateVisible'],
    setup(props, context) {
        const drawVisible = ref(false)
        watch(() => {
            drawVisible.value = props.draw
        })
        const close = () => {
            drawVisible.value = false
            context.emit("updateVisible", false)
        }

        const state = reactive({
            theme: 1
        })

        const setTheme = theme => {
            
        }


        return {
            drawVisible,
            close,
            state
        }
    },
}
</script>