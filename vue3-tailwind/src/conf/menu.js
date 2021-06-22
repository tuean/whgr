const menus = [
    {
        id: "1",
        name: "首页",
        path: "/admin",
        keepAlive: true,
        isFolder: false,
        removeable: true,
    }, 
    {
        id: "2",
        name: "404",
        path: "/admin/pageNotFund",
        keepAlive: true,
        isFolder: false,
        removeable: false,
    },
    {
        id: "3",
        name: "table",
        path: "/admin/tableDemo",
        keepAlive: false,
        isFolder: false,
        removeable: false,
    },
    {
        id: "4",
        name: "demo",
        path: "/demo",
        keepAlive: false,
        isFolder: true,
        removeable: false,
        child: [
            {
                id: "41",
                name: "demoChild",
                path: "/demoChild",
                keepAlive: false,
                isFolder: false,
                removeable: false,
            },
        ]
    },
    {
        id: "5",
        name: "vuexDemo",
        path: "/admin/vuexDemo",
        keepAlive: false,
        isFolder: false,
        removeable: false,
    },
]

export default menus