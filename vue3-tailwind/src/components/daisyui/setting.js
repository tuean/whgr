import { reactive, toRefs, watch } from 'vue'

const settings = reactive({
    theme: ''
})

watch(
    () => settings.theme,
    (theme, preTheme) => {
        // console.log(theme, preTheme)
        console.log(theme)
        
    }
)

export default settings

