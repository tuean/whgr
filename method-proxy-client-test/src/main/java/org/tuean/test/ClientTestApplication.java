package org.tuean.test;

import com.tuean.mp.annotations.EnableMethodProxyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodProxyClient
public class ClientTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientTestApplication.class, args);
    }

}
