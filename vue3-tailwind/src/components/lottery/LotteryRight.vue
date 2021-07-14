<template>
  <div class="fixed h-screen w-11 right-0 text-gray-300">
    <el-button type="primary" size="small" @click="toggle">{{
      state.status === "running" ? "停止" : "开始"
    }}</el-button>
    <el-dialog
      title="抽奖结果"
      v-model="state.showFlag"
      width="40%"
      :before-close="handleClose"
    >
      <div class="dialog-title" slot="title">
        <!-- <span :style="{ fontSize: '18px' }"> 名单如下 </span> -->
        <div v-for="i in state.result"  :key="i">
            {{i}}
        </div>
        <span :style="{ fontSize: '14px', color: '#999', marginLeft: '10px' }">
          (点击号码可以删除)
        </span>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { reactive, ref, toRefs } from "vue";
import {
  speed,
  fast,
  hidden,
  state,
  resultStop,
  resultStart
} from "/@/components/lottery/lottery.js";
import LotteryResult from "/@/components/lottery/LotteryResult.vue";
import { ElMessageBox } from "element-plus";

export default {
  components: {
    LotteryResult,
  },
  setup(props) {
    // const result = reactive({});
    const handleClose = (done) => {
      hidden();
    };

    const toggle = () => {
      if (state.status === "running") {
        resultStop();
        return;
      }
      if (state.status === "stop") {
        resultStart();
        return;
      }
    };

    return {
      state,
      toggle,
      handleClose,
    };
  },
};
</script>
<style lang="">
</style>