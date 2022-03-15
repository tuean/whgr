<template>
    <!-- <div>micro</div> -->
    <micro-app name='my-app' :url="path"></micro-app>
</template>

<script>
import { useRouter } from 'vue-router'
import store from "/@/store/index";
import microApp from '@micro-zoe/micro-app'


export default {
    name: "Micro",
    setup() {
        const route = useRouter()
        console.log(store.state.tabList)
        let activeTabs = store.state.tabList.filter(n => n.active)
        let tab = activeTabs[0]
        let path = tab.path
        // let redirectUrl = route.param.redirect
        // console.log(redirectUrl)
        return {
            path
        }
    }
}
</script>
