<template>
  <div class="w-full h-full scroll-snap-type overflow-y-scroll">
    <!-- <div class="fixed">fixed</div> -->

    <div id="p1" class="h-full scroll-snap-align-start flex">
      <el-container
        class="bg-fxz center bg-center w-80 mx-auto items-center justify-center"
      >
        <div
          class="no-select can-click w-100 items-center h-14 font-bold text-6xl text-gray-300 text-center"
        >
          <div>
            <span>the site of our sweet life</span>

            <div>
              <span class="text-lg">谨以此站记录快乐的一生</span>
            </div>
            <div class="text-right pr-20" @click="blog">
              <span class="text-lg">by 帅气阿天</span>
            </div>
          </div>
        </div>
      </el-container>
    </div>

    <div id="p2" class="h-full scroll-snap-align-start">
      <el-container class="bg-gqh h-full text-center">
        <el-main class="">
            <div>我家夫人喜欢狗缺不喜欢养狗</div>
            <div>我家夫人喜欢吃菜讨厌吃肉</div>
            <div>我家夫人</div>
        </el-main>
        <el-footer>

        </el-footer>
      </el-container>
    </div>

    <div id="p3" class="h-full scroll-snap-align-start">
      <el-container class="w-full bg-bq h-full">
        <el-main class="w-full p-0 m-0">
          <div
            class="no-select can-click w-100 h-5/6 items-center font-bold text-6xl text-gray-300 text-center flex justify-center flex-col"
          >
            余生就请你指教了
          </div>
        </el-main>
        <el-footer class="h-1/6">
          <div class="text-gray-300 text-center" @click="beian">
            <span class="no-select can-click"
              >备案号：沪ICP备2021014470号-1</span
            >
          </div>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    const beian = () => {
      window.location.href = "http://beian.miit.gov.cn";
    };
    const blog = () => {
        window.location.href = "https://tuean.cn"
    }
    return {
      beian,
      blog
    };
  },
};
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
