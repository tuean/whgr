import { createStore } from 'vuex'
import { tabEqual } from '/@/util/index'
import { toRefs, toRef, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'



const store = createStore({
    state() {
        return {
            count: 0,
            tabList: [],
            userInfo: {}
        }
    },
    mutations: {
        increment(state) {
            state.count++
        },
        activeTab(state, tabInfo) {
            for (let x = 0; state.tabList.length > x; x++) {
                state.tabList[x].active = state.tabList[x].id === tabInfo.id
            }
        },
        pushTab(state, tab) {
            if (tab == null) return
            let exist = false
            for (let x = 0; state.tabList.length > x; x++) {
                if (state.tabList[x].id === tab.id) exist = true
            }
            console.log('exists', exist)
            if (exist) {
                for (let x = 0; state.tabList.length > x; x++) {
                    state.tabList[x].active = state.tabList[x].id === tab.id
                }
                console.log('switch end')
                return
            }
            state.tabList.push(tab)
            console.log("tabs:", state.tabList)
        },
        removeTab(state, id) {
            // let i = tabEqual(state.tabList, id)
            // if (i < 0) {
            //     console.log('id not exist', id)
            //     return
            // }
            // state.tabList.splice(i, 1)
            // console.log('tabList:', state.tabList)
            let newList = reactive([])
            for (let x = 0; state.tabList.length > x; x++) {
                if (state.tabList[x].id != id) newList.push(state.tabList[x])
            }
            state.tabList = newList
            console.log('tabList:', state.tabList)
        },
    }
})

export default store