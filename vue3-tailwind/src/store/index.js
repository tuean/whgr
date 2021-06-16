import { createStore } from 'vuex'


// const state = {
//     count: 0, 
//     showName: null
// }

// const mutation = {
//     increment: state => state.count++,
//     storeName: (state, v) => {
//         state.showName = v
//     }
// }

const store = createStore({
    state() {
        return {
            count: 0
        }
    },
    mutations: {
        increment(state) {
            state.count++
        }
    }
})

export default store