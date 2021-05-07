<template>
    <div>header</div>

</template>

<script>
import { onMounted, reactive, toRefs } from 'vue'

export default ({
    setup() {
        const state = {}
        return {
            ...toRefs(state)
        }
    },
})
</script>
