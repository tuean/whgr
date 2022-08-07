<template>
  <WujieVue
    width="100%"
    height="100%"
    name="vue3"
    alive=true
    :url="vue3Url"
    :props="props"
    :attrs="attrs"
    :sync="false"
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
import { linkByAppId } from './appSpec';

export default {

    setup() {
        const router = useRouter();
        const jumpCall = name => {
            // router.push({ name });
            console.log('jump called: ' + name);
        }
        
        let vue3Url = null;
        let attrs = process.env.NODE_ENV === "production" ? { src: url } : {}
        let props = { jump: jumpCall }
        let degrade = window.localStorage.getItem("degrade") === "true"

        const init = () => {
          const appId = router.currentRoute._rawValue.params.appId
          let link = linkByAppId(appId);
          vue3Url = link;
          console.log('link: ', link)
        }

        init()

        return {
          vue3Url, attrs, props, degrade, lifecycles, fetch, jumpCall, init
        }
    }
};
</script>

<style lang="scss" scoped></style>
