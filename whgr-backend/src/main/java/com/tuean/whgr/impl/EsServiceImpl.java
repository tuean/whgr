package com.tuean.whgr.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuean.whgr.entity.es.ProductInfo;
import com.tuean.whgr.service.IEsService;
import com.tuean.whgr.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EsServiceImpl implements IEsService {

    private static Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Override
    public void mockData(String type) {
        int start = 0, round = 4000000; // 4百万
        while (start < round) {
            String code = StringUtil.randomFullString(36);
            esTemplate.save(ProductInfo.builder()
                    .code(code)
                    .data(new Date())
                    .type(start / 100)
                    .build(), IndexCoordinates.of(type));
            if (start % 100 == 0) System.out.println(start);
            start++;
        }

    }
}
