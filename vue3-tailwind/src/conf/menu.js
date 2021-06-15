const menus = [
    {
        index: "1",
        name: "首页",
        path: "/admin",
        keepAlive: true,
        isFolder: false,
    }, 
    {
        index: "2",
        name: "404",
        path: "/404",
        keepAlive: true,
        isFolder: false,
    },
    {
        index: "3",
        name: "table",
        path: "/tableDemo",
        keepAlive: false,
        isFolder: false,
    },
    {
        index: "4",
        name: "demo",
        path: "/demo",
        keepAlive: false,
        isFolder: true,
        child: [
            {
                index: "41",
                name: "demoChild",
                path: "/demoChild",
                keepAlive: false,
                isFolder: false,
            },
        ]
    }
]

export default menus