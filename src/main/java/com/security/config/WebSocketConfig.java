package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${frontend.url}")
    private String frontendServerUrl;

    @Autowired
    private StompChannelInterceptor stompChannelInterceptor;

    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
        System.out.println("Configurando canal inbound");
        registration.interceptors(stompChannelInterceptor);
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // Prefijo para temas de suscripción
       // config.setUserDestinationPrefix("/user/");
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para mensajes dirigidos a @MessageMapping
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Endpoint para conectarse
                .setAllowedOrigins(frontendServerUrl); // Permite conexiones desde el frontend

    }

}