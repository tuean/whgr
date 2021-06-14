<template>
    <div class="bg-tlz mx-auto w-full h-full">
        <el-container>
            <el-header class="text-gray-300 pt-4 text-3xl flex items-center">
                项目列表
            </el-header>

            <el-main>
                <app-card v-for="app in apps" :key="app.id" :app="app" />
            </el-main>
        </el-container>
    </div>
</template>

<script>
import apps from '/@/conf/app'
// import apps from '../../conf/app'
// import {firstLetter} from '/@/util/index'
import AppCard from '/@/components/common/AppCard.vue'

export default {
    components: {
        AppCard
    },
    setup() {
        console.log("instant apps:", apps)
       
        return {
            apps
        }
    },
}
</script>