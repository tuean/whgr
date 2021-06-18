package com.tuean.whgr.ws;

import com.tuean.whgr.ws.spring.HttpAuthHandler;
import com.tuean.whgr.ws.spring.WsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
//@Deprecated
public class WebsocketConfiguration implements WebSocketConfigurer {

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

    @Autowired
    private HttpAuthHandler httpAuthHandler;
    @Autowired
    private WsInterceptor myInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "ws")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}
