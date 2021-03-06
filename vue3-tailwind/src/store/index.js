import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import { tabEqual } from '/@/util/index'
import { toRefs, toRef, reactive, ref, inject } from 'vue'
import { useRouter } from 'vue-router'
// import { reload } from '/@/components/pages/Admin.vue'

const store = createStore({
    plugins: [createPersistedState({storage: window.sessionStorage})],
    state() {
        return {
            count: 0,
            tabList: [],
            userInfo: {},
            win: {},
            lotteryResultShow: false,
            theme: "",
            fullFlag: false
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
            let newList = reactive([])
            for (let x = 0; state.tabList.length > x; x++) {
                if (state.tabList[x].id != id) newList.push(state.tabList[x])
            }
            state.tabList = newList
            console.log('tabList:', state.tabList)
        },
        setResult(key, list) {
            win[key] = list
        },
        
        initTheme(state) {
            let theme = localStorage.getItem("theme")
            if (theme !== null && theme !== '') {
                state.theme = theme
            }
        },
        setTheme(state, theme) {
            localStorage.setItem("theme", theme)
            state.theme = theme
        },

        getWins() {
            return win
        },

        showResult() {
            state.lotteryResultShow = true
        },

        hiddenResult() {
            state.lotteryResultShow = false
        },
    }
})

export default store