package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class NotificacionController {

  @Autowired
  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public NotificacionController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

// @SendTo("/topic/procesos")
  public void notificarCambioProceso(Integer procesoId) {
    messagingTemplate.convertAndSend("/topic/procesos", "proceso obtenido:" + procesoId);
  }
}