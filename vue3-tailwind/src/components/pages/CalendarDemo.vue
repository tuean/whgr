<template>
  <div class="mx-auto container bg">
    <el-calendar> </el-calendar>
  </div>
</template>

<script>
export default {
  setup() {},
};
</script>

<style scoped lang="less">
.bg {
  background-image: url(https://i.loli.net/2019/10/23/rGVfgibcAOs49EM.jpg);
}
/deep/ tr td {
  border-top-style: none;
  border-bottom-style: none;
  border-left-style: none;
  border-right-style: none;
}
/deep/ .el-calendar-table tr td:first-child {
  border-left-width: 0px;
}
/deep/ .el-calendar-table tr:first-child td {
  border-top-width: 0px;
}
/deep/ tbody td tr {
  border-top-style: none;
  border-bottom-style: none;
  border-left-style: none;
  border-right-style: none;
}

table {
  border: none;
}
</style>
