import { createStore } from 'vuex'
import { tabEqual } from '/@/util/index'
import { toRefs, toRef, reactive } from 'vue'
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
                state.tabList[x].active = state.tabList[x].index === tabInfo.index
            }
        },
        pushTab(state, tab) {
            if (tab == null) return
            let exist = false
            for (let x = 0; state.tabList.length > x; x++) {
                if (state.tabList[x].index === tab.index) exist = true
            }
            console.log('exists', exist)
            if (exist) {
                for (let x = 0; state.tabList.length > x; x++) {
                    state.tabList[x].active = state.tabList[x].index === tab.index
                }
                console.log('switch end')
                return
            }
            state.tabList.push(tab)
            console.log("tabs:", state.tabList)
        },
        removeTab(state, index) {
            let i = tabEqual(state.tabList, index)
            if (i < 0) {
                console.log('index not exist', index)
                return
            }
            state.tabList.splice(i, 1)
            state.tabList = state.tabList == null ? [] : state.tabList
            console.log('tabList:', state.tabList)
        },
        
    }
})

export default store