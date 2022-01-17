package com.tuean.whgr;

import com.tuean.whgr.service.IEsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) // junit4
@SpringBootTest
@Slf4j
public class WhgrBackendApplicationTests {

    @Autowired
    private IEsService esService;

    @Test
    public void contextLoads() {
//        esService.getData();
       try {
           esService.mockChatData();
       } catch (Exception var) {
           log.error("", var);
       }
    }

}
