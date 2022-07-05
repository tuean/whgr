<template>
  <el-drawer
    title="系统设置"
    :withHeader="false"
    :modelValue="drawVisible"
    direction="rtl"
    destroy-on-close
    @close="close"
  >
    <div class="pl-3 pr-3 globalSetting" :data-theme="$store.state.theme">
      <el-divider>主题色</el-divider>
      <div class="flex justify-center">
        <div>
          <div
            @click="setTheme('dark')"
            class="
              rounded-md
              w-12
              h-12
              bg-black
              border-opacity-25
              ring
              shadow-2xl
              can-click
            "
          ></div>
          <div class="text-center text-sm pt-2">暗黑</div>
        </div>
        <span class="w-8"></span>
        <div>
          <div
            @click="setTheme('light')"
            class="
              rounded-md
              w-12
              h-12
              bg-white
              border-opacity-25
              ring
              shadow-2xl
              can-click
            "
          ></div>
          <div class="text-center text-sm pt-2">日常</div>
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { inject, ref, toRefs, watch, reactive } from "vue";
import { useStore } from "vuex"
import settings from '/@/store/settings'

export default {
  name: "globalSetting",
  props: {
    draw: Boolean,
  },
  emits: ["updateVisible"],
  setup(props, context) {
    const store = useStore();
    const drawVisible = ref(false);

    watch(() => {
      drawVisible.value = props.draw;
    });
    const close = () => {
      drawVisible.value = false;
      context.emit("updateVisible", false);
    };

    const setTheme = (t) => {
      // console.log(t);
      // store.commit("setTheme", theme);
      settings.theme = t
    };

    // setTheme('dark')

    return {
      drawVisible,
      close,
      setTheme,
    };
  },
};
</script>

<style lang="scss" scoped>


</style>