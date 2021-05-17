<template>
  <div class="w-full h-full scroll-snap-type overflow-y-scroll">

      <!-- <div class="fixed">fixed</div> -->

    <div id="p1" class="h-full scroll-snap-align-start flex">
        <el-container class="bg-fxz center bg-center w-80 mx-auto items-center justify-center">
            <div class="no-select can-click w-100 items-center h-14 font-bold text-6xl text-gray-300 text-center">
                <div>the site of our sweet life</div>
                <div>
                    <span class="text-lg">谨以此站纪念这辈子的爱情</span>
                </div>
                <div class="text-right pr-20">
                    <span class="text-lg">by 帅气阿天</span>
                </div>
            </div>
        </el-container>
        
    </div>

    <div id="p2" class="h-full scroll-snap-align-start">
        <el-container class="bg-gqh h-full">
            <div></div>
        </el-container>
    </div>

    <div id="p3" class="h-full scroll-snap-align-start">
        <el-container class="w-full bg-bq h-full">
             <el-main class="w-full p-0 m-0">
                <div class="no-select can-click w-100 h-5/6 items-center font-bold text-6xl text-gray-300 text-center flex justify-center flex-col">
                    从此以后，寒来暑往，一生相随    
                </div> 
            </el-main>
            <el-footer class="h-1/6">
                <div class="text-gray-300 text-center ">备案号：</div>
            </el-footer>
        </el-container>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    return {};
  },
};
</script>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
