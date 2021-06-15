<template>
    <div class="bg-default h-full w-full">
       <!-- left menu -->

       <!-- right -->
       <!-- header -->

       <!-- content -->
    </div>
</template>

<script>
import Loading from '/@/components/Loading.vue'
import { menus } from '/@/conf/menu.js' 
import '../../index.css'

export default {
    components: { 
        Loading 
    },
    setup() {

        return {
            menus
        }
    }
}
</script>

<style>

</style>
