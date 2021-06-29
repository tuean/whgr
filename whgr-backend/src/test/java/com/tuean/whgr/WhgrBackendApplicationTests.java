package com.tuean.whgr;

import com.tuean.whgr.service.IEsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class) // junit4
@SpringBootTest
@Slf4j
class WhgrBackendApplicationTests {

    @Autowired
    private IEsService esService;

    @Test
    void contextLoads() {
        esService.getData();
    }

}
