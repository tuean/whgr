<template>
  <div class="w-screen h-screen max-h-screen">
    <div @click="shot" class="pt-10 h-32 w-full text-center">截图</div>
    <span>水水水水水水水水水水水水水水水水水水水</span>
    <span>水水水水水水水水水水水水水水水水水水水</span>
    <span>水水水水水水水水水水水水水水水水水水水</span>
    <span>水水水水水水水水水水水水水水水水水水水</span>


    <div id="echarts" class="mt-28 w-18 h-24">
        
    </div>
  </div>
</template>

<script>
import "/@/index.css";
import html2canvas from "html2canvas";
import * as echarts from "echarts";
import { nextTick } from 'vue'

export default {
  setup() {
    const init = async () => {
      await nextTick()
      var myChart = echarts.init(document.getElementById("echarts"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "ECharts 入门示例",
        },
        tooltip: {},
        legend: {
          data: ["销量"],
        },
        xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    };
    const shot = () => {
        console.log('shot')
        // 缩小页面
        document.querySelector('#app').className = 'small bg-white'

        let template = document.querySelector('#template')
        template.width = window.innerWidth
        template.height = window.innerHeight
        let source = document.querySelector('#screenshot')
        if (template.getContext) {
            console.log("start")
            let serverConfig = {
                // windowWidth: window.innerWidth,
                // windowHeight: window.innerHeight,
                // width: window.innerWidth,
                // height: window.innerHeight,
                scale: 1,
                canvas: template
            }
            html2canvas(source, serverConfig).then((canvas) => {
                // document.body.appendChild(canvas) // 把canvas放置于body下
                document.querySelector('#screen').className = 'appPrint'
                let imgBase64 = document.querySelector('#template').toDataURL('image/jpeg', 0.92)
                console.log(imgBase64)
                document.querySelector('#app').className = "bg-white"

                let div = document.createElement('div')
                div.width = '40px'
                div.height = '50px'
                div.innerHTML = "关闭"
                div.className = "btn-close"
                document.body.appendChild(div)
            })
        } 
    };
    init()
    return {
        init, 
        shot
    }
  },
};
</script>
