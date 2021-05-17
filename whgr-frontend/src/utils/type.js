let input = {
    "data" : {
        "data.data[1].userid": "10001",
        "data.data[2].name": "test"
    }
}


function parse(input) {
    if (!input instanceof Object) return
    let data = input.data
    for (let d in data) {
        let index = getIndex(d)  // 1
        let key = getKey(d) // userid
        let value = data[d]
        if (index == null || key == null) continue

    }
}

function getIndex(d) {
    let regex = /\[(.+?)\]/g
    let ms = d.match(regex)
    return ms.length > 0 ? ms[0] : null
}

function getKey(d) {
    let i = d.indexOf("]")
    return i < 0 ? null : d.substring(i, i.length - 1)
}

