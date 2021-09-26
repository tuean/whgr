<template>
    <div class="w-full flex flex-row flex-wrap">
        <div v-for = "(item, index) in data" :key="index" style="width: 50%">
            <div >{{item.text}}</div>
        </div>
    </div>
</template>

<script>
export default {
    setup() {
        let data = []
        for (let x = 0; x < 100; x++) {
            let i = {
                id: x,
                text: "axxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
            }
            data.push(i)
        }

        return {
            data
        }
    },
}
</script>

