<template>
  <WujieVue
    width="100%"
    height="100%"
    name="vue3"
    :url="vue3Url"
    :props="props"
    :attrs="attrs"
    :sync="false"
    :fetch="fetch"
    :degrade="degrade"
    :beforeLoad="lifecycles.beforeLoad"
    :beforeMount="lifecycles.beforeMount"
    :afterMount="lifecycles.afterMount"
    :beforeUnmount="lifecycles.beforeUnmount"
    :afterUnmount="lifecycles.afterUnmount"
    :activated="lifecycles.activated"
    :deactivated="lifecycles.deactivated"
    :loadError="lifecycles.loadError"
  ></WujieVue>
</template>

<script>
import fetch from "./wujie/fetch";
import lifecycles from "./wujie/lifecycle";
import { useRouter} from "vue-router";

export default {

    setup() {
      const router = useRouter();
        const jumpCall = name => {
          // router.push({ name });
          console.log('jump called: ' + name);
        }
        console.log('router', router)
        let vue3Url = "http://tuean.cn"
        let attrs = process.env.NODE_ENV === "production" ? { src: url } : {}
        let props = { jump: jumpCall }
        let degrade = window.localStorage.getItem("degrade") === "true"
        return {
          vue3Url, attrs, props, degrade, lifecycles, fetch, jumpCall
        }
    }
};
</script>

<style lang="scss" scoped></style>
