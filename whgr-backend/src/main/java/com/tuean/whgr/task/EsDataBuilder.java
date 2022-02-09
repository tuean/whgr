package com.tuean.whgr.task;

import com.tuean.whgr.service.IEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EsDataBuilder {

    @Autowired
    private IEsService esService;

    @PostConstruct
    public void run() {
//        esService.mockData("test");
//        esService.getData();
    }

}
