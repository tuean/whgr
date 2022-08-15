try {
    importScripts('robust-websocket.js');
} catch (e) {
    console.log("Failed to import robust websocket script");
    close()
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

function initJob() {
    if (context.job == null) {
        context.job = setInterval(function() {
            if (context.ws === null) {
                console.log("ws not initialized")
                return
            }
            if (context.ws.readyState === WebSocket.OPEN) {
                context.ws.send('')
            } else if (context.ws.readyState === WebSocket.CONNECTING) {
                console.log("connecting")
                context.ws_connection_wait_time++
                if (context.ws_connection_wait_time > config.ws_connection_wait_time_max) {
                    console.log("Connection timed out after " + config.ws_connection_wait_time_max)
                    context.ws.close()
                }
            } else if (context.ws.readyState === WebSocket.CLOSED) {
                console.log('ws closed')
                context.ws.dispatchEvent(new CustomEvent('close'))
            } else if (context.ws.readyState === WebSocket.CLOSING) {
                console.log('ws is closing')
            }
        }, 1000)
        console.log('heartbeat', context.job)
    }
}

function init() {
    let url = ''
    context.ws = new WebSocket(url)

    context.ws.onopen = function(event) {
        console.log('ws opened')
        context.ws.send('')
        initJob()
    }

    context.ws.onmessage = function(event) {
        let message = event.data
        console.log('msg:', message)
    }

    context.ws.onerror = function(event) {
        console.log('ws heartbeat err')
        heartbeatError()
    }

    context.ws.onclose = function(event) {
        console.log('ws closed')
        wsCloseError()
        init()
    }
}

function heartbeatError() {
    context.err_times++;
    if (context.err_times > config.max_ws_err) {
        logout()
    }

    if (context.ws != null) {
        try {
            context.ws.close()
        } catch (e) {
            console.log('close ws err', e)
        }
        context.ws = null
    }
}

function wsCloseError() {
    // same with heartbeatError
}

function logout () {
    postMessage({type: "closeByJob"})
}

const config = {
    job_interval: 10000,
    max_ws_err: 20,
    ws_connection_wait_time_max: 4,
}



const context = {
    job: null,
    ws: null,
    err_times: 0,
    close_times: 0,
    ws_connection_wait_time: 0,
}

function heartbeat() {
    console.log('job started');
}










