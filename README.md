# whgr

Work Hard to Get Rich —— 极简 B 端脚手架：**登录 → 动态菜单 → 账号增删改查 + AI 主入口**。

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

初始账号：`admin / admin`（管理员，role=1）、`normal / normal`（普通用户，role=2）。

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
