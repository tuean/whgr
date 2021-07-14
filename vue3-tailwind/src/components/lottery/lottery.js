import guest from "/@/conf/guest.js";
import { luckydrawHandler, randomNum } from "/@/helper/algorithm";
import { reactive, ref, nextTick } from "vue";
import { getLastNum } from '/@/util/index'

const KEY_LOCAL_STORAGE_RESULT = 'lotteryResult'
const KEY_LOCAL_STORAGE_ACTIVE = 'active'


export default {}
export const getData = () => {
    const nums = guest.length;
    const configNum = nums > 1500 ? Math.floor(nums / 3) : nums;
    const randomShowNums = luckydrawHandler(configNum, [], nums);
    // debugger
    let tags = guest.map((item, index) => {
        return {
            key: item + index,
            name: item,
            photo: null,
        };
    });
    console.log(tags)
    return tags;
};

export const datas = getData()

export const speed = () => {
    return [0.1 * Math.random() + 0.01, -(0.1 * Math.random() + 0.01)];
}

export const fast = () => {
    window.TagCanvas.SetSpeed('rootcanvas', [5, 1])
}

export const createCanvas = () => {
    const canvas = document.createElement("canvas");
    canvas.width = document.body.offsetWidth;
    canvas.height = document.body.offsetHeight;
    canvas.id = "rootcanvas";
    document.querySelector("#main").appendChild(canvas);
}

export const startTagCanvas = () => {
    createCanvas();

    window.TagCanvas.Start("rootcanvas", "tags", {
        textColour: null,
        initial: speed(),
        dragControl: 1,
        textHeight: 20,
        noSelect: true,
        lock: "xy",
    });

    setActive("一等奖")
};
export const reloadTagCanvas = () => {
    window.TagCanvas.Reload("rootcanvas");
};
export const start = async () => {
    await nextTick();
    console.log("init after dom updated");
    startTagCanvas();
};

export const reportWindowSize = () => {
    let exist = document.querySelector('#rootcanvas')
    if (exist.parentElement) {
        exist.parentElement.removeChild(exist)
    }
    start()
}

export const reload = () => {
    window.TagCanvas.Reload('rootcanvas');
}

const status = ref("stop");
const showFlag = ref(false);
const result = reactive({});
export const state = reactive({
    status,
    showFlag,
    result
})

export const resultStart = () => {
    console.log("start");
    state.status = "running";
    window.TagCanvas.SetSpeed("rootcanvas", fast());
    hidden();
}

export const resultStop = () => {
    console.log("end");
    state.status = "stop";
    window.TagCanvas.SetSpeed("rootcanvas", speed());
    show();
    let active = getActive()
    let data = unluckyData()
    let luckyPeople = lucky(data, 1)
    setResult(active, luckyPeople)
    console.log(result)
}

export const show = () => {
    state.showFlag = true;
}

export const hidden = () => {
    state.showFlag = false;
}

export const setResult = (k, v) => {
    state.result = v
    let old = getResult()
    old[k] = v
    localStorage.setItem(KEY_LOCAL_STORAGE_RESULT, JSON.stringify(result))
}

export const getResult = () => {
    let v = localStorage.getItem(KEY_LOCAL_STORAGE_RESULT)
    if (v == null) return {}
    return JSON.parse(v)
}

export const unluckyData = () => {
    let d = guest
    // todo filter lucky data
    return d
}

export const setActive = name => {
    localStorage.setItem(KEY_LOCAL_STORAGE_ACTIVE, name)
}

export const getActive = () => {
    return localStorage.getItem(KEY_LOCAL_STORAGE_ACTIVE)
}

export const lucky = (data, n) => {
    let r = new Set()
    while (r.size < n) {
        let index = randomNum(0, data.length)
        if (data[index] != null) r.add(data[index])
        
    }
    return Array.from(r)
}