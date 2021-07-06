import guest from "/@/conf/guest.js";
import { luckydrawHandler } from "/@/helper/algorithm";
import { reactive, ref, nextTick } from "vue";

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
};

export const fast = () => {
    window.TagCanvas.SetSpeed('rootcanvas', [5, 1]);
}

export const createCanvas = () => {
    const canvas = document.createElement("canvas");
    canvas.width = document.body.offsetWidth;
    canvas.height = document.body.offsetHeight;
    canvas.id = "rootcanvas";
    document.querySelector("#main").appendChild(canvas);
};

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
