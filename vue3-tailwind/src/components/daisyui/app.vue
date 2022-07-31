<template>
  <WujieVue
    width="100%"
    height="100%"
    name="vue2"
    :url="vue2Url"
    :props="props"
    :attrs="attrs"
    :sync="true"
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
export default {

    setup() {
        const jump = name => {
            this.$router.push({ name });
        }

        let vue2Url = url
        let attrs = process.env.NODE_ENV === "production" ? { src: url } : {}
        let props = { jump: this.jump }
        let degrade = window.localStorage.getItem("degrade") === "true"
        return {
            vue2Url, attrs, props, degrade, lifecycles, fetch, jump
        }
    }
};
</script>

<style lang="scss" scoped></style>
