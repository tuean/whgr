<template>
  <div class="w-screen h-screen bg-gray-800">
    <LotteryHeader />
    <LotteryRight />
    <div id="main" :class="{ mask: showRes }"></div>
    <div id="tags">
      <ul v-for="item in datas" :key="item.key">
        <li>
          <a
            href="javascript:void(0);"
            :style="{
              color: '#fff',
            }"
          >
            {{ item.name ? item.name : item.key }}
            <img v-if="item.photo" :src="item.photo" :width="50" :height="50" />
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import { reactive, ref, nextTick } from "vue";
// import guest from "/@/conf/guest.js";
// import { luckydrawHandler } from "/@/helper/algorithm";
import LotteryHeader from "/@/components/lottery/LotteryHeader.vue";
import LotteryRight from "/@/components/lottery/LotteryRight.vue";
import { start, getData, reportWindowSize  } from "/@/components/lottery/lottery.js";

export default {
  components: {
    LotteryHeader,
    LotteryRight,
  },
  setup(props) {
    const showRes = ref(false);
    const datas = getData()

    window.addEventListener("resize", reportWindowSize);

    start();

    return {
      showRes,
      start,
      datas,
    };
  },
};
</script>
<style lang="">
</style>