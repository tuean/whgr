<template>
  <div class="h-full w-full block">
    <!-- pure page -->
    <router-view />
  </div>
</template>

<script>
// import Header from "./components/layout/Header.vue";
import { useRouter } from 'vue-router'
import { reactive } from 'vue'
import bus from '@/bus'
import './index.css'


export default {
  components: {  },
  setup() {
    const router = useRouter();
    const state = reactive({
      defaultOpen: [],
      pageType: 1,
      currentPath: "/login",
    });

    router.beforeEach((to, from, next) => {
      // debugger
      if (to.name !== '404' && to.matched.length == 0) {
        next({path: '/404'})
        return
      }
      if (to.path == '/login') {
        next()
      } else {
        next()
      }
      state.pageType = 1
      document.title = pathMap[to.name]
    })

    const loginPage = ["/login"];
    return {
        state
    }
  },
  mounted() {
    bus.on("log", t => console.log(t));
  },
};
</script>
