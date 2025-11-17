package com.example.mypet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // 구독용 prefix
        registry.setApplicationDestinationPrefixes("/app");     // 전송용 prefix
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws") // ws 엔드포인트 추가
                .setAllowedOriginPatterns("*")  // cors 허용
                .withSockJS();
    }

}
