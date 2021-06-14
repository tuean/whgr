<template>
  <div class="w-full h-full scroll-snap-type overflow-y-scroll">
    <!-- <div class="fixed">fixed</div> -->

    <div id="p1" class="h-full scroll-snap-align-start flex">
      <!-- <el-container
        class="bg-fxz center bg-center w-80 mx-auto items-center justify-center"
      >
        <div
          class="
            no-select
            w-100
            items-center
            h-14
            font-bold
            text-6xl text-gray-300 text-center
          "
        >
          <div>the site of our sweet life</div>
          <div>
            <span class="text-lg">此站献给夫人</span>
          </div>
          <div class="text-right pr-20" @click="blog">
            <span class="text-lg can-click">by 帅气阿天</span>
          </div>
        </div>
      </el-container> -->
      <el-container
        class="bg-fxz h-full text-center justify-center items-center text-gray-300 w-40"
      >
        <div >
          <span class="text-6xl">the site of our sweet life</span>
          <div class="pt-2">
            <span class="text-lg">此站献给夫人</span>
          </div>
          <div class="text-right pr-20" @click="blog">
            <span class="text-lg can-click">by 帅气阿天</span>         
          </div>
        </div>
        
        <el-footer> </el-footer>
      </el-container>
    </div>

    <div id="p2" class="h-full scroll-snap-align-start">
      <el-container
        class="bg-gqh h-full text-center justify-center items-center"
      >
        <div class="text-gray-300 text-2xl">
          本站作为夫人和我一起学习web前后端知识的结果，将会集成一系列的功能或作品；
          <br />
          <br />
          <span>希望能实现早日躺平的目标；</span>
          <br />
          <br />
          <span>也希望我和夫人的感情能顺畅且长久；</span>
          <br />
          <br />
          <span>余生就请你指教了o( =•ω•= )m</span>
        </div>
        <el-footer> </el-footer>
      </el-container>
    </div>

    <div id="p3" class="h-full scroll-snap-align-start">
      <el-container class="w-full bg-bq h-full">
        <el-main class="w-full p-0 m-0">
          <div
            class="
              no-select
              w-100
              h-5/6
              items-center
              font-bold
              text-6xl text-gray-300 text-center
              flex
              justify-center
              flex-col
            "
          >
            <span class="can-click" @click="apps">
              移步作品列表 --->
            </span>
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
import router from '../../router/index'

export default {
  setup() {
    const beian = () => {
      window.location.href = "http://beian.miit.gov.cn";
    };
    const blog = () => {
      window.location.href = "https://tuean.cn";
    };
    const apps = () => {
     router.push('/apps')
    }
    const loveData = [
      "我家夫人喜欢可爱的小狗狗",
      "我家夫人喜欢吃耗油生菜+番茄蛋汤+红烧鸡翅",
      "我家夫人喜欢舒服的回家躺下",
      "我家夫人喜欢第一杯果汁的畅快",
      "我家夫人喜欢臭烘烘的螺蛳粉",
      "我家夫人喜欢",
    ];
    return {
      beian,
      blog,
      apps
    };
  },
};
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
.middle {
  height: 80px;
}
</style>
