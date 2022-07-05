const navbarEn = require('../configs/navbar/en')
const navbarZh = require('../configs/navbar/zh')

let config = {
    // 站点配置
    lang: 'en-US',
    title: 'whgr',
    description: 'work hard and get rich',
    base: "/",

    head: [['link', { rel: 'icon', href: 'https://image-1256217908.cos.ap-shanghai.myqcloud.com/20220122162647.png' }]],
    locales: {
        // 键名是该语言所属的子路径
        // 作为特例，默认语言可以使用 '/' 作为其路径。
        '/': {
            lang: 'en-US',
            title: 'whgr',
            description: 'work hard and get rich',
        },
        '/zh/': {
            lang: 'zh-CN',
            title: 'whgr',
            description: '努力工作，致富人生',
        },
    },

    // 主题和它的配置
    theme: '@vuepress/theme-default',
    themeConfig: {
        // logo: 'https://image-1256217908.cos.ap-shanghai.myqcloud.com/20220122162647.png',
        logo: null,
        repo: 'tuean/whgr',
        repoLabel: 'github',
        docsDir: './docs',
        sidebar: 'auto',
        locales: {
            '/': {
                selectLanguageName: 'English',
                navbar: navbarEn
            },
            '/zh/': {
                selectLanguageName: '简体中文',
                navbar: navbarZh
            },
        },

    },

    // 插件
    plugins: [
        [
            '@vuepress/plugin-prismjs',
            {
                preloadLanguages: ['markdown', 'jsdoc', 'yaml', 'java', 'javascript']
            }
            // '@vuepress/plugin-search',
            // {
            //     locales: {
            //         '/': {
            //             placeholder: 'Search',
            //         },
            //         '/zh/': {
            //             placeholder: '搜索',
            //         },
            //     },
            // },
        ],
    ],
}

module.exports = config
