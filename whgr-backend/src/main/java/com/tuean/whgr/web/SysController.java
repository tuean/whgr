package com.tuean.whgr.web;

import com.tuean.whgr.feign.FeignDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SysController {


    @RequestMapping("/out")
    public void sendBeacon(String body) {
        log.info("body: {}", body);
    }

    @Autowired
    FeignDemo feignDemo;

    @RequestMapping(value = "/search/bing", method = RequestMethod.GET, produces = "application/json")
    public String search(@RequestParam("key") String key) {
        return feignDemo.search(key);
    }

}
