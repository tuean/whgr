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
import guest from "/@/conf/guest.js";
import { luckydrawHandler } from "/@/helper/algorithm";
import LotteryHeader from '/@/components/lottery/LotteryHeader.vue'
import LotteryRight from '/@/components/lottery/LotteryRight.vue'

export default {
  components: {
    LotteryHeader, LotteryRight
  },
  setup(props) {
    

    const showRes = ref(false);
    let list = guest,
      photos = guest;

    const getData = () => {
      const nums = guest.length;
      const configNum = nums > 1500 ? Math.floor(nums / 3) : nums;
      const randomShowNums = luckydrawHandler(configNum, [], nums);
      // debugger
      let tags = guest.map((item, index) => {
        return {
          key: item + index,
          name: item,
          photo: null,
        };
      });
      console.log(tags)
      return tags;
    };

    const datas = getData()

    const speed = () => {
      return [0.1 * Math.random() + 0.01, -(0.1 * Math.random() + 0.01)];
    };

    const slow = () => {
      window.TagCanvas.SetSpeed('rootcanvas', [5, 1]);
    }

    const createCanvas = () => {
      const canvas = document.createElement("canvas");
      canvas.width = document.body.offsetWidth;
      canvas.height = document.body.offsetHeight;
      canvas.id = "rootcanvas";
      document.querySelector("#main").appendChild(canvas);
    };
    const startTagCanvas = () => {
      createCanvas();

      window.TagCanvas.Start("rootcanvas", "tags", {
        textColour: null,
        initial: speed(),
        dragControl: 1,
        textHeight: 20,
        noSelect: true,
        lock: "xy",
      });
    };
    const reloadTagCanvas = () => {
      window.TagCanvas.Reload("rootcanvas");
    };

    const start = async () => {
      await nextTick();
      console.log("init after dom updated");
      startTagCanvas();
    };

    const reportWindowSize = () => {
      let exist = document.querySelector('#rootcanvas')
      if (exist.parentElement) {
        exist.parentElement.removeChild(exist)
      }
      start()
    }

    const reload = () => {
      window.TagCanvas.Reload('rootcanvas');
    }



    window.addEventListener('resize', reportWindowSize);

    start();

    return {
      showRes,
      speed,
      createCanvas,
      startTagCanvas,
      reloadTagCanvas,
      start,
      datas,
    };
  },
};
</script>
<style lang="">
</style>