<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" width="50" align="center"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="time" label="时间"> </el-table-column>
      <el-table-column label="操作" width="200">
          <template #default="scope">
              <a class="can-click" @click="show(scope.row)">查看</a>
          </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { onMounted, reactive, toRefs } from 'vue'
import { post } from '/@/util/axios.js'
export default {
    name: 'tableDemo',
  setup() {
      const state = reactive({
          loading: false,
          tableData: [],
          total: 0,
          currentPage: 1, 
          pageSize: 20,
          searchKey: '',
          options: [
              {
                  value: '',
                  label: '不计'
              }
          ]
      })
      onMounted(() => {
        console.log('mounted')
        getList()
      })
      const getList = () => {
          state.loading = true
          let req = {
              "pageNum": state.currentPage,
              "pageSize": state.pageSize
          }
          post('/test/list', req).then(res => {
              console.log("/test/list")
              console.log(res)
              state.tableData = res.list
          })
      }
      const show = row => {
          window.alert(JSON.stringify(row))
      }
      return {
          ...toRefs(state),
          show
      }
  },
};
</script>