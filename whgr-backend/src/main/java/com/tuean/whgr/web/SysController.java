package com.tuean.whgr.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SysController {


    @RequestMapping("/out")
    public void sendBeacon(String body) {
        log.info("body: {}", body);
    }

}
