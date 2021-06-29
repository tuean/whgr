const menus = [
    {
        id: "1",
        name: "首页",
        path: "/admin",
        keepAlive: true,
        isFolder: false,
        removeable: true,
        child: [],
        icon: "el-icon-s-home",
    }, 
    {
        id: "2",
        name: "状态页面",
        path: "/admin/status",
        keepAlive: true,
        isFolder: false,
        removeable: false,
        icon: "el-icon-warning",
        child: [
            {
                id: "21",
                name: "404",
                path: "/admin/pageNotFound",
                keepAlive: false,
                isFolder: false,
                removeable: false,
                icon: ""
            },
        ]
    },
    {
        id: "3",
        name: "tableDemo",
        path: "/admin/tableDemo",
        keepAlive: false,
        isFolder: false,
        removeable: false,
        child: []
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
        child: []
    },
]

export default menus