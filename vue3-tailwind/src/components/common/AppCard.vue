<template lang="">
    <div class="mb-3">
        <el-container class="text-gray-300 border-gray-300 border-2 ">
            <el-aside class="text-5xl flex items-center no-click justify-center w-24">
                <div class="w-26">{{appInfo.abbr}}</div>
            </el-aside>
            <el-main @click="runTo" class="can-click ml-32">
                <div class="w-full text-2xl">{{appInfo.name}}</div>
                <div class="w-full">{{appInfo.des}}</div>
            </el-main>
        </el-container>
    </div>
</template>
<script>

export default {
    props: {
        app: Object
    },
    setup(props) {
        console.log(app)
        console.log(props.app)
        const runTo = () => {
            window.location.href = appInfo.url
        }
        const appInfo = props.app
        return {
            appInfo,
            runTo
        }  
    }
}
</script>
<style lang="">
    
</style>