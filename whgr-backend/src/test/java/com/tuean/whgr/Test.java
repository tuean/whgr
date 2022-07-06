package com.tuean.whgr;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class) // junit4
@SpringBootTest
@Slf4j
public class Test {

    public static void main(String[] args) {
        String a = "1,2,4";
        List<String> r = Arrays.asList(a.split(","));
        System.out.println(r);

        String test = "{\\\\\\\"a\": \" b\"}";
//        test = test.replace("\\", "");
        test.replace("\\", "");
        System.out.println(test);
    }

    @org.junit.Test
    public void classPath() {
        String path = "classpath:/mapper/main/*.xml";
        Resource resource = new ClassPathResource(path);
        System.out.println(resource);
    }
}
