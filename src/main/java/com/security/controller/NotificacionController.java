package com.security.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.security.db.enums.TipoEventoNotificacion;
import com.security.db.enums.TipoObjetoNotificacion;
import com.security.service.dto.notificacionDTO;
import com.security.service.impl.ProcesoResponsablesCache;

@Controller
@CrossOrigin
public class NotificacionController {

  // @Autowired
  // private final SimpMessagingTemplate messagingTemplate;

  // @Autowired
  // private ProcesoResponsablesCache procesoResponsablesCache;

  // @Autowired
  // public NotificacionController(SimpMessagingTemplate messagingTemplate) {
  //   this.messagingTemplate = messagingTemplate;
  // }

  // // @SendTo("/topic/procesos")
  // public void notificarCambio(String idKeycloak, Object mensaje) {
  //   // System.out.println("Enviando mensaje a usuario: " + idKeycloak);
  //   messagingTemplate.convertAndSendToUser(idKeycloak, "/queue/notificaciones", mensaje);
  //   // messagingTemplate.convertAndSend("/topic/public", "Mensaje prueba");
  // }

  // public void notificarUsuarios(Set<String> ids, Object mensaje) {
  //   ids.stream().forEach(id -> {
  //     System.out.println("Notificar a: " + id + ", mensaje: " + mensaje.toString());
  //     this.notificarCambio(id, mensaje);
  //   });
  // }

  // public void notificarProcesoEliminacion(Integer procesoId, Set<String> ids) {
  //   notificarUsuarios(ids,
  //       new notificacionDTO(TipoObjetoNotificacion.PROCESO, procesoId,
  //           "El proceso: " + procesoId + " ha sido eliminado",
  //           TipoEventoNotificacion.ELIMINACION));
  // }

  // public void notificarProcesoCreacion(Integer procesoId) {
  //   notificarUsuarios(procesoResponsablesCache.getResponsables(procesoId),
  //       new notificacionDTO(TipoObjetoNotificacion.PROCESO, procesoId,
  //           "El proceso: " + procesoId + " ha sido creado",
  //           TipoEventoNotificacion.CREACION));
  // }

  // public void notificarProcesoActualizacion(Integer procesoId) {
  //   notificarUsuarios(procesoResponsablesCache.getResponsables(procesoId),
  //       new notificacionDTO(TipoObjetoNotificacion.PROCESO, procesoId,
  //           "El proceso: " + procesoId + " ha sido actualizado",
  //           TipoEventoNotificacion.ACTUALIZACION));
  // }

  // public void notificarPasoActualizacion(Integer procesoId, List<Integer> pasosIds) {
  //   notificarUsuarios(procesoResponsablesCache.getResponsables(procesoId),
  //       new notificacionDTO(TipoObjetoNotificacion.PASO, procesoId,
  //           "El proceso: " + procesoId + " ha actualizado sus pasos",
  //           TipoEventoNotificacion.ACTUALIZACION, pasosIds));
  // }

  // @MessageMapping("/notificar/proceso")
  // public void notificarProceso(notificacionDTO actualizacion) {
  //   System.out.println("Llegue al notificar del controller");
  //   Set<String> usuarios = procesoResponsablesCache.getResponsables(actualizacion.getProcesoId());
  //   notificarUsuarios(usuarios, actualizacion);

  // }
}