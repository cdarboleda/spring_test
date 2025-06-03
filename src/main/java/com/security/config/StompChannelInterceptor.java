package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class StompChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            System.out.println("Interceptando conexión WebSocket");
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Jwt jwt = jwtDecoder.decode(token);
                    // Reutilizar el convertidor
                    Authentication authentication = jwtAuthenticationConverter.convert(jwt);
                    // System.out.println("authentication del jwt: "+ authentication);
                    System.out.println("authentication.getName(): " + authentication.getName());
                    accessor.setUser(authentication);
                } catch (JwtException e) {
                    System.out.println("Token inválido");
                    throw new IllegalArgumentException("Token inválido");
                }
            } else {
                System.out.println("No se encontró el Authorization header");
                throw new IllegalArgumentException("No se encontró el Authorization header");
            }
        }
        return message;
    }
}

// @Component
// public class StompChannelInterceptor implements ChannelInterceptor {

//     @Autowired
//     private JwtDecoder jwtDecoder;

//     @Override
//     public Message<?> preSend(Message<?> message, MessageChannel channel) {
//         StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

//         if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//             System.out.println("Interceptando conexión WebSocket");
//             String authHeader = accessor.getFirstNativeHeader("Authorization");
//             // System.out.println("Authorization: " + authHeader);
//             if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                 String token = authHeader.substring(7);
//                 try {
//                     Jwt jwt = jwtDecoder.decode(token);
//                     Authentication auth = new JwtAuthenticationToken(jwt, List.of(), jwt.getSubject());
//                     accessor.setUser(auth);
//                 } catch (JwtException e) {
//                     System.out.println("Token inválido");
//                     throw new IllegalArgumentException("Token inválido");
//                 }
//             } else {
//                                     System.out.println("No se encontró el Authorization header");
//                 throw new IllegalArgumentException("No se encontró el Authorization header");
//             }
//         }
//         return message;
//     }
// }