package org.tuean.test;

import com.tuean.mp.annotations.EnableMethodProxyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodProxyServer
public class ServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientTestApplication.class, args);
    }

}
