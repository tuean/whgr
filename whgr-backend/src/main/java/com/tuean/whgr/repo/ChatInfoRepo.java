package com.tuean.whgr.repo;

import com.tuean.whgr.entity.es.ChatInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChatInfoRepo extends ElasticsearchRepository<ChatInfo, String> {


}
