package com.tuean.whgr;


import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.helper.ReqHelper;
import com.tuean.whgr.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class) // junit4
@SpringBootTest
@Slf4j
public class MdcTest {


    @Autowired
    @Qualifier("mineRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private AdminAccountMapper mapper;

    @Test
    public void testMdc() {
        // http request
//        String r = restTemplate.getForObject("http://tuean.cn", String.class);
//        log.info(r);

        // sql
        AdminAccount adminAccount = mapper.selectByPrimaryKey(1L);
        log.info(String.valueOf(adminAccount == null));
    }


}
