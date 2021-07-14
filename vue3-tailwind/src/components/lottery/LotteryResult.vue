<template>
  <div>
    <el-dialog
      title="抽奖结果"
      width="600px"
      class="c-Result"
      :append-to-body="false"
      v-model="visible"
    >
      <div class="dialog-title" slot="title">
        <span :style="{ fontSize: '18px' }"> 抽奖结果 </span>
        <span :style="{ fontSize: '14px', color: '#999', marginLeft: '10px' }">
          (点击号码可以删除)
        </span>
      </div>
      <!-- <div
        v-for="(item, index) in list"
        :key="index"
        class="listrow"
        @click="
          (event) => {
            deleteRes(event, item);
          }
        "
      >
        <span class="name">
          {{ item.name }}
        </span>
        <span class="value">
          <span v-if="item.value && item.value.length === 0"> 暂未抽奖 </span>
          <span
            class="card"
            v-for="(data, j) in item.value"
            :key="j"
            :data-res="data"
          >
            {{ data }}
          </span>
        </span>
      </div> -->
    </el-dialog>
  </div>
</template>
<script>
import { conversionCategoryName, getDomData } from "/@/helper/index";
// import { store } from "/@/store/index";
import { useStore } from "vuex";
import { watch, ref, reactive } from "vue";

export default {
  props: {
    result: Object,
    visible: Boolean,
  },
  setup(props) {
    const result = reactive(props.result);
    const visible = ref(props.visible);
    console.log("visible:", visible);
    console.log("result", result)
    watch(visible, (oldVal, newVal) => {
      console.log(oldVal);
      console.log(newVal);
    });
    let list = [];
    for (const key in result) {
      if (result.hasOwnProperty(key)) {
        const element = result[key];
        let name = conversionCategoryName(key);
        list.push({
          label: key,
          name,
          value: element,
        });
      }
    }
    return {
      list,
      visible,
    };
  },
};
</script>
<style lang="scss">
.c-Result {
  .el-dialog__body {
    max-height: 500px;
    overflow-y: auto;
  }
  .listrow {
    display: flex;
    line-height: 30px;
    .name {
      width: 80px;
      font-weight: bold;
    }
    .value {
      flex: 1;
    }
    .card {
      display: inline-block;
      // width: 40px;
      padding: 0 5px;
      line-height: 30px;
      text-align: center;
      font-size: 18px;
      font-weight: bold;
      border-radius: 4px;
      border: 1px solid #ccc;
      background-color: #f2f2f2;
      margin-left: 5px;
      margin-bottom: 5px;
      position: relative;
      cursor: pointer;
      &:hover {
        &::before {
          content: "删除";
          width: 100%;
          height: 100%;
          background-color: #ccc;
          position: absolute;
          left: 0;
          top: 0;
          color: red;
        }
      }
    }
  }
}
</style>