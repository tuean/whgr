package com.tuean.whgr.impl;

import com.alibaba.fastjson.JSON;
import com.tuean.whgr.entity.es.ProductInfo;
import com.tuean.whgr.service.IEsService;
import com.tuean.whgr.util.MathUtil;
import com.tuean.whgr.util.StringUtil;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
public class EsServiceImpl implements IEsService {

    private static Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    @Override
    public void mockData(String type) {

        ExecutorService executor = Executors.newFixedThreadPool(16);
        for (int x = 0; x < 16; x++) {
            executor.submit(() -> {
                int start = 0, round = 500000;
                while (start < round) {
                    String code = StringUtil.randomFullString(36);
                    esTemplate.save(ProductInfo.builder()
                            .code(code)
                            .data(new Date())
                            .type(start / 100)
                            .build(), IndexCoordinates.of(type));
                    if (start % 100 == 0) logger.info("{}", start);
                    start++;
                }
            });
        }


    }

    @Override
    public void getData() {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        for (int x = 0; x < 16; x++) {
            executor.submit(() -> {
                while (true) {
                    Integer type = MathUtil.random(0, 6000);
                    //构建分页
                    Pageable pageable= PageRequest.of(0,100);
                    NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
                    NativeSearchQuery query=builder.withQuery(QueryBuilders.termQuery("type", type))
                            .withPageable(pageable)
                            .build();
                    SearchHits<ProductInfo> search = esTemplate.search(query, ProductInfo.class);
                    Stream<SearchHit<ProductInfo>> searchHitStream = search.get();
                    List<ProductInfo> result = new ArrayList<>();
                    searchHitStream.forEach(searchHit->{
                        result.add(searchHit.getContent());
                    });
                    logger.info(JSON.toJSONString(result));
//                    return searchHitStream;
                }
            });
        }
    }
}
