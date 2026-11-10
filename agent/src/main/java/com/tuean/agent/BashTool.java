package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * 跑 shell 命令（bash -lc），带超时与输出截断。
 * 权限就是当前进程的权限——和 pi 一样，不做沙箱。
 */
public class BashTool implements Tool {

    private static final int MAX_CHARS = 50_000;
    private static final ObjectNode PARAMS = params();

    @Override
    public String name() {
        return "bash";
    }

    @Override
    public String description() {
        return "Run a shell command with `bash -lc` in the working directory and return combined stdout/stderr. Default timeout 120s. Use it for builds, tests, grep, git, listing files.";
    }

    @Override
    public JsonNode parameters() {
        return PARAMS;
    }

    @Override
    public String execute(JsonNode args) throws Exception {
        String command = Args.str(args, "command");
        int timeoutSeconds = Math.max(1, Args.intOr(args, "timeoutSeconds", 120));
        Path cwd = Path.of(Args.str(args, "cwd", ".")).toAbsolutePath();

        Process process = new ProcessBuilder("bash", "-lc", command)
                .directory(cwd.toFile())
                .redirectErrorStream(true)
                .start();

        StringBuilder output = new StringBuilder();
        Thread pump = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    synchronized (output) {
                        if (output.length() < MAX_CHARS) output.append(line).append('\n');
                    }
                }
            } catch (IOException ignored) {
                // 进程被杀时流会断开，忽略
            }
        });
        pump.setDaemon(true);
        pump.start();

        boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
        if (!finished) process.destroyForcibly();
        pump.join(2000);

        String text;
        synchronized (output) {
            text = output.toString();
        }
        if (text.length() >= MAX_CHARS) text = text.substring(0, MAX_CHARS) + "…（输出已截断）\n";

        String status = finished
                ? "[exit " + process.exitValue() + "]"
                : "[超时 " + timeoutSeconds + "s，进程已杀]";
        return (text.isBlank() ? "(无输出)" : text) + status;
    }

    private static ObjectNode params() {
        ObjectNode p = Json.obj().put("type", "object");
        ObjectNode props = p.putObject("properties");
        props.putObject("command").put("type", "string").put("description", "要执行的 shell 命令");
        props.putObject("timeoutSeconds").put("type", "integer").put("description", "超时秒数，默认 120");
        props.putObject("cwd").put("type", "string").put("description", "工作目录，默认当前目录");
        p.putArray("required").add("command");
        return p;
    }
}
