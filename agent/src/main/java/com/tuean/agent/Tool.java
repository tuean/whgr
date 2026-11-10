package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 一个可被模型调用的工具：声明 JSON Schema，执行后返回给模型的文本结果。
 * 对应 pi 里每个工具自带的 name / description / parameters / execute。
 */
public interface Tool {

    /** 模型里看到的函数名，全局唯一 */
    String name();

    /** 一句话说明这个工具干什么、什么时候用，模型靠它选工具 */
    String description();

    /** JSON Schema（type=object），模型靠它构造参数 */
    JsonNode parameters();

    /** 执行，返回给模型看的文本；抛异常时由 agent 转成 ERROR 文本回给模型 */
    String execute(JsonNode args) throws Exception;
}
