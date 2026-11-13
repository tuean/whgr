# whgr

Work Hard to Get Rich —— 极简 B 端脚手架：**登录 → 动态菜单 → 账号/角色/菜单管理 + 数据权限 + AI 主入口**。

- 后端 `whgr-backend`：Spring Boot 2.7.18 + MyBatis + MySQL 8（Java 8 语法，JDK 21 运行）
- 前端 `vue3-tailwind`：Vite 2 + Vue 3 + daisyUI（暗色主题）+ Element Plus
- 附 `agent/`：独立的 Java 版命令行 coding agent（不参与后端构建）

详细结构、接口、坑见 [OVERVIEW.md](OVERVIEW.md)。

## 跑起来

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@21

docker compose up -d              # MySQL 8 → localhost:13303，首次启动自动执行 sql/init-table.sql
(cd whgr-backend && mvn spring-boot:run)    # http://localhost:7777/api
(cd vue3-tailwind && npm install && npm run dev)   # http://localhost:4000
```

初始账号：`admin / admin`（管理员，等级 1，全部数据）、`normal / normal`（普通用户，等级 2，仅本人数据）。

管理台自带：账号管理（数据权限按角色裁剪）、角色管理（等级 + 数据范围）、菜单管理（路由白名单 + 可见等级）。

菜单支持两种类型：**内部路由**（`/xxx`）和**外部页面**（`menu_type=2`，填完整 `http(s)` 地址，用 iframe 容器 `/frame/{id}` 加载）。外部页面不用写前端代码，AI 入口也能直接跳过去；种子数据里带了个 `TBS 查询` 作示例（地址换成自己的即可）。

AI 入口需要模型 key：`export DEEPSEEK_API_KEY=...`（或 `AI_API_KEY`），不配也能启动，只是问答返回「AI 未配置」。

## 目录

```
whgr-backend/     Spring Boot 后端（func/<模块>/ 下加业务）
vue3-tailwind/    Vue3 前端（views/ 下加页面）
agent/            Java 版 coding agent（独立 JDK 21 模块，java -jar）
sql/init-table.sql 建表 + 种子数据（compose 自动执行）
docker-compose.yml 只有 MySQL 一个服务
```

MIT © tuean
