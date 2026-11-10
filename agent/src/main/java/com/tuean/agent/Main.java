package com.tuean.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * 命令行入口：
 *   java -jar whgr-agent.jar -p "把 README 里的 X 改成 Y"   # 单次执行
 *   java -jar whgr-agent.jar                              # 交互模式：/exit 退出，/new 开新会话
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && ("-h".equals(args[0]) || "--help".equals(args[0]))) {
            usage();
            return;
        }

        String apiKey = env("AGENT_API_KEY", env("DEEPSEEK_API_KEY", null));
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("缺少 API key：请先 export AGENT_API_KEY=...（DeepSeek 用户也可以直接用 DEEPSEEK_API_KEY）");
            System.exit(2);
        }

        String baseUrl = env("AGENT_BASE_URL", "https://api.deepseek.com");
        String model = env("AGENT_MODEL", "deepseek-v4-flash");
        int maxSteps = Integer.parseInt(env("AGENT_MAX_STEPS", "30"));
        Path sessionDir = Path.of(env("AGENT_SESSION_DIR", ".sessions"));
        Path cwd = Path.of("").toAbsolutePath();

        LlmClient llm = new LlmClient(baseUrl, apiKey, model);
        List<Tool> tools = List.of(new ReadTool(), new WriteTool(), new EditTool(), new BashTool());

        if (args.length >= 2 && ("-p".equals(args[0]) || "--print".equals(args[0]))) {
            Agent agent = new Agent(llm, new Session(sessionDir), tools, maxSteps, cwd);
            System.out.println(agent.run(String.join(" ", Arrays.copyOfRange(args, 1, args.length))));
            return;
        }

        Agent agent = new Agent(llm, new Session(sessionDir), tools, maxSteps, cwd);
        System.out.printf("agent %s @ %s%n会话: %s%n输入问题回车执行，/exit 退出，/new 开新会话%n",
                model, cwd, agent.session().file());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            while (true) {
                System.out.print("\n> ");
                System.out.flush();
                String line = in.readLine();
                if (line == null) break;

                line = line.strip();
                if (line.isEmpty()) continue;
                if ("/exit".equals(line) || "/quit".equals(line)) break;
                if ("/new".equals(line)) {
                    agent = new Agent(llm, new Session(sessionDir), tools, maxSteps, cwd);
                    System.out.println("新会话: " + agent.session().file());
                    continue;
                }
                try {
                    System.out.println("\n" + agent.run(line));
                } catch (Exception e) {
                    System.err.println("出错: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("读取输入失败: " + e.getMessage());
        }
    }

    private static String env(String name, String def) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? def : value;
    }

    private static void usage() {
        System.out.println("""
                Java 版极简大模型 agent（read / write / edit / bash）

                用法:
                  java -jar whgr-agent.jar -p "你的任务"    单次执行
                  java -jar whgr-agent.jar                 交互模式

                环境变量:
                  AGENT_API_KEY       API key（回退到 DEEPSEEK_API_KEY）
                  AGENT_BASE_URL      OpenAI 兼容网关，默认 https://api.deepseek.com
                  AGENT_MODEL         模型名，默认 deepseek-v4-flash
                  AGENT_MAX_STEPS     单轮最多几步工具调用，默认 30
                  AGENT_SESSION_DIR   会话 JSONL 目录，默认 .sessions
                """);
    }
}
