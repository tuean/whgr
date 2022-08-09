var worker = new Worker('job.js')

worker.onmessage = function (event) {
    var data = event.data
    if (data.type === 'logout') {
        console.log(data.message)
        worker.terminate()
    }
}

worker.onerror = function (event) {
    console.log(event)
    worker.terminate()
}

worker.postMessage({
    "type": "init"
})
