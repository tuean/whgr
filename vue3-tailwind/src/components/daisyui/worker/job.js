try {
    importScripts('robust-websocket.js');
} catch (e) {
    console.log("Failed to import robust websocket script");
    close()
}

var config = {
    job_interval: 10000,

}

var context = {
    job: null,

}

function heartbeat() {
    console.log('job started');
}

function init() {
    if (context.job == null) {
        context.job = setInterval(heartbeat, 1000)
        console.log('heartbeat', context.job)
    }
}


onmessage = function(event) {
    switch (event.data.type) {
        case 'init':
            init();
            break;
        default:
            console.log("Unknown message type")
    }
}





