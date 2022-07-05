<template>
  <div class="loading-content">
      <!-- <div class="loading"> -->

      <!-- </div> -->
  </div>
</template>

<script>
import '../index.css'
export default {
    setup() {
        return {}
    }
}
</script>

<style scoped>
.loading-content {
    background-color: gray;
    opacity: 1;
    width: 1vh;
    height: 1vw;
}
.loading {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
}
</style>