package com.tuean.whgr.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuean.whgr.entity.es.ProductInfo;
import com.tuean.whgr.service.IEsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EsServiceImpl implements IEsService {

    private static Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Override
    public void checkTypeExist(String type) {

        esTemplate.save(ProductInfo.builder().code("ee").data(new Date()).build());
    }
}
