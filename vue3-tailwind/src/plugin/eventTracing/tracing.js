import { reactive } from "vue"

export default {
    install: (app, options) => {
        let url = options.url || '';
        
        let data = {"data": "frist"}

        const outfn = param => {
            window.navigator.sendBeacon(url, JSON.stringify(param))
            console.log('send to url ', url);
        }

        window.addEventListener('beforeunload', (e) => {
            console.log('unload sendBeaconing')
            outfn(data)
        })

        const set_param = param => {
            data = param
        }
        app.config.globalProperties.$my_out = set_param
    }
}