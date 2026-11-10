# whgr-agent

Java 版极简大模型 agent，参考本机 `pi-coding-agent` 的实现思路，只用 JDK 21 + Jackson，没有 Spring、没有 TUI。

能做的事：给一句任务，模型自己决定读哪个文件、怎么改、跑什么命令，多轮循环直到收尾。

```bash
mvn -q package                       # 出 target/whgr-agent.jar（含依赖）
export AGENT_API_KEY=$DEEPSEEK_API_KEY

java -jar target/whgr-agent.jar -p "读 demo.txt，把 price 从 100 改成 200，改完念一遍"
# [step 1] read {"path":"/private/tmp/agent-demo/demo.txt"}
# [step 1]   ← 1→hello agent 2→price = 100
# [step 2] edit {"path":"...demo.txt","edits":[{"oldText":"price = 100","newText":"price = 200"}]}
# [step 2]   ← 已修改 …/demo.txt（1 处）
# [step 3] read ...
# 已把 demo.txt 里的 `price = 100` 改成 `price = 200`。

java -jar target/whgr-agent.jar      # 交互模式：/exit 退出，/new 开新会话
```

## 和 pi 的对应关系

| pi | 这里 | 说明 |
|---|---|---|
| agent loop（`how-pi-works.md`） | `Agent.run` | 组装 system + 历史 + 工具 → 拿 assistant 消息 → 有 `tool_calls` 就执行并回填 → 再请求，直到没有工具调用 |
| `read` / `write` / `edit` / `bash` 工具 | `ReadTool` `WriteTool` `EditTool` `BashTool` | edit 沿用 pi 的语义：`oldText` 逐字符匹配、必须唯一、多处 edits 不许重叠、全部按**原文**匹配后一次性落盘 |
| session JSONL | `Session` | 每条消息一行 JSONL 落 `.sessions/session-<ts>.jsonl`；历史里存模型返回的**原始**消息 JSON，回传时原样带回去（DeepSeek 的 `reasoning_content` 不会丢） |
| provider 配置 | `LlmClient` | OpenAI 兼容 `/chat/completions`，DeepSeek 默认；429/5xx 退避重试 3 次，4xx 直接报错 |
| 系统提示词 | `Agent.buildSystemPrompt` | 只留编码 agent 必须的规则（先读后改、改动最小、看 bash 结果、ERROR 自己修） |
| 输出渲染 | `Main` | 每步打印 `[step N] 工具 参数` 和结果摘要，最后打印回答 |

## 环境变量

| 变量 | 默认值 | 作用 |
|---|---|---|
| `AGENT_API_KEY` | 回退 `DEEPSEEK_API_KEY` | 必填 |
| `AGENT_BASE_URL` | `https://api.deepseek.com` | 任何 OpenAI 兼容网关 |
| `AGENT_MODEL` | `deepseek-v4-flash` | 模型名 |
| `AGENT_MAX_STEPS` | `30` | 单轮最多几步工具调用 |
| `AGENT_SESSION_DIR` | `.sessions` | 会话 JSONL 目录 |

## 目录

```
agent/
├── pom.xml                                     # JDK 21 + jackson-databind + junit5，shade 打成可执行 jar
└── src/
    ├── main/java/com/tuean/agent/
    │   ├── Main.java                           # CLI：-p 单次 / 交互 REPL
    │   ├── Agent.java                          # 主循环 + 系统提示词
    │   ├── LlmClient.java                      # OpenAI 兼容客户端（重试/报错）
    │   ├── Session.java                        # 历史 + JSONL
    │   ├── Tool.java  Args.java  Json.java      # 工具契约、入参校验、Jackson 小工具
    │   ├── ReadTool.java  WriteTool.java  EditTool.java  BashTool.java
    └── test/java/com/tuean/agent/
        ├── AgentLoopTest.java                  # 假 LlmClient 验证循环与 tool 结果回填
        └── EditToolTest.java                   # 唯一匹配 / 多处 edits / 重叠 / 删除
```

`mvn test` 8 个用例；`mvn -q package` 出 `target/whgr-agent.jar`。

## 工具契约（给模型看的）

| 工具 | 参数 | 行为 |
|---|---|---|
| `read` | `path`, `offset`=1, `limit`=2000 | 带行号（`N→内容`），单次最多 2000 行 / 50KB，超 5MB 拒绝 |
| `write` | `path`, `content` | 覆盖或新建，自动建父目录 |
| `edit` | `path`, `edits[{oldText,newText}]` | 精确替换，`newText=""` 即删除；不唯一/重叠/未匹配都会返回 ERROR |
| `bash` | `command`, `timeoutSeconds`=120, `cwd` | `bash -lc`，合并 stdout/stderr，输出 50KB 截断，末尾带 `[exit N]` 或超时标记 |

工具抛异常不会中断循环，会被转成 `ERROR: ...` 文本回给模型，让它自己改参数重试。

## 故意没做的（和 pi 的差距）

- **流式输出**：一次拿完整响应，长任务期间只有每步一行进度。
- **上下文压缩 / 会话恢复**：没有 compaction、没有 `--resume`、没有分支树；历史会一直长下去，超长时 DeepSeek 直接报错。
- **多模态、扩展、技能、`/` 命令、TUI、权限确认**：都没有。`bash` 的权限就是当前进程权限，不沙箱、不确认。

想补哪个：流式改 `LlmClient` 的 `stream:true` + SSE 解析（注意 `tool_calls` 的增量拼接）；压缩在 `Agent.messagesForModel` 里按 token 数裁掉最老的消息并插一条摘要。
