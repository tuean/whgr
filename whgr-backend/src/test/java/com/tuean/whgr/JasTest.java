package com.tuean.whgr;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class) // junit4
@SpringBootTest
@Slf4j
public class JasTest {


    @Resource
    private StringEncryptor encryptor;

    @Test
    public void makeENC() {
        System.out.println(encryptor.encrypt("111"));
    }

}
