package com.security.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class PasoWebSocketController {

    @MessageMapping("/procesos/{procesoId}/notificaciones")
    @SendTo("/topic/procesos/{procesoId}/notificaciones")
    public Map<String, Object> handleNotificacion(
            @DestinationVariable String procesoId,
            @Payload Map<String, Object> notificacion) {

        notificacion.put("procesoId", procesoId);
        return notificacion;
    }
}