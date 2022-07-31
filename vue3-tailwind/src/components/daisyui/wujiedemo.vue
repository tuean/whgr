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
// import hostMap from "../hostMap";
import fetch from "./wujie/fetch";
import lifecycles from "./wujie/lifecycle";
export default {
    
  data() {
    const url = 'https://tuean.cn';
    // const url = 'https://www.baidu.com'
    return {
      vue2Url: url,
      // 修正iframe的url，防止github pages csp报错
      attrs: process.env.NODE_ENV === "production" ? { src: url } : {},
      props: { jump: this.jump },
      fetch,
      degrade: window.localStorage.getItem("degrade") === "true",
      lifecycles,
    };
  },
  methods: {
    jump(name) {
      this.$router.push({ name });
    },
  },
};
</script>

<style lang="scss" scoped></style>
