<template>
  <div class="h-full w-full block">
    <!-- pure page -->
    <router-view />
  </div>
</template>

<script>
// import Header from "./components/layout/Header.vue";
import { useRouter } from "vue-router";
import { reactive, getCurrentInstance, inject } from "vue";
import bus from "@/bus";
import "./index.css";
import _ from "lodash";

export default {
  components: {},
  // inject: ['dayjs'],
  setup() {
    const { proxy } = getCurrentInstance();
    const router = useRouter();
    const state = reactive({
      defaultOpen: [],
      pageType: 1,
      currentPath: "/login",
      loading: false
    });

    const afterLoading = () => {
      console.log(proxy.$dayjs().format() + 'after loading in main')
      state.loading = false;
      proxy.$my_loading(false);
    }

    // debugger
    if (!state.loading) {
      proxy.$my_loading(true)
      state.loading = true
      _.delay(afterLoading, 3000, null)
    } else {
      // router.beforeEach((to, from, next) => {
      //   // debugger
      //   if (to.name !== "404" && to.matched.length == 0) {
      //     next({ path: "/404" });
      //     return;
      //   }
      //   if (to.path == "/login") {
      //     next();
      //   } else {
      //     next();
      //   }
      //   state.pageType = 1;
      //   document.title = pathMap[to.name];
      // });
    }

    // const loginPage = ["/login"];
    return {
      state
    };
  },
  mounted() {
    bus.on("log", (t) => console.log(t));
  }
};
</script>
