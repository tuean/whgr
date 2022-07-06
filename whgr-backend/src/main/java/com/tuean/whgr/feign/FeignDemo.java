package com.tuean.whgr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "feign-demo",
        url = "${feign.demo.url}"
)
public interface FeignDemo {

    @GetMapping("/search")
    String search(@RequestParam("q") String search);

}
