package com.tuean.whgr.entity.es;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Document(indexName = "test", shards = 1, replicas = 0)
public class ProductInfo {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String code;

    @Field(type = FieldType.Date)
    private Date data;
}
