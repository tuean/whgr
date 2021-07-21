<template>
    <div>11</div>
</template>

<script>
import { reactive, onMounted, toRefs } from 'vue'
import { post } from '/@/util/axios.js'

export default {
    name: 'goods',
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
      return {
          ...toRefs(state),
      }
    },
}
</script>