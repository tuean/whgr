package com.tuean.whgr.entity.es;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Document(indexName = "chat-info", shards = 1, replicas = 0, createIndex = true)
//@Mapping(mappingPath = "esmapping/chat-info.json")
public class ChatInfo {

    @Id
    @Field(type = FieldType.Keyword)
    private String msgid;

    @Field(type = FieldType.Keyword)
    private String msgType;

    @Field(type = FieldType.Keyword)
    private String from;

    @Field(type = FieldType.Keyword)
    private String to;

    @Field(type = FieldType.Auto)
    private JSONObject msgObj;

    @Field(type = FieldType.Date)
    private Long msgTime;



}
