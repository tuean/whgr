const links = [
    {
        appId: "zoey-email",
        link: "https://zoey.plus/e-s/email" 
    },
    {
        appId: "zoey",
        link: "https://zoey.plus"
    },
    {
        appId: "tuean",
        link: "https://tuean.cn"
    }
]

export const add = (data) => {
    links.push(data)
}


export const linkByAppId = appId => {
    for (let i = 0; links.length > i; i++) {
        if (links[i].appId === appId) {
            return links[i].link
        }
    }
    return null;
}