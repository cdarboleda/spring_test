package com.security.factory.concret;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;

import jakarta.transaction.Transactional;

//Crea pasos predefinidos para cada tipo de proceso,
//Tiene el primer paso como EN_CURSO, debe tener responsable y se asigna en el service que usa este metodo
//El resto de pasos estan en pendiente y sin responsable
@Component
public class TitulacionPasoFactory implements IPasoFactory {

        private static final String ROL_ESTUDIANTE = "ESTUDIANTE";
        private static final String ROL_DOCENTE = "DOCENTE";
        private static final String ROL_LECTOR = "LECTOR";
        private static final String ROL_SECRETARIA = "SECRETARIA";
        private static final String ROL_DIRECTOR = "DIRECTOR";
        private static final String ROL_CONSEJO_POSGRADO = "CONSEJO_POSGRADO";

        @Override
        @Transactional
        public List<PasoDTO> generatePasos() {
                List<PasoDTO> pasos = new ArrayList<>();
                pasos.add(this.crearPaso("registro_propuesta", 1, "Se registra la propuesta de proyecto",
                                Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0),
                                LocalDateTime.now(), "ESTUDIANTE"));
                pasos.add(this.crearPaso("revision_documentacion", 2,
                                "Secretaria revisa la documentacion y asigna un docente revisor",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0),
                                LocalDateTime.now(), ROL_SECRETARIA));
                pasos.add(this.crearPaso("revision_idoneidad", 3,
                                "Reporte de revision del plan de titulacion",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DOCENTE));
                pasos.add(this.crearPaso("revision_reporte", 4,
                                "Secretaria revisa el reporte del docente revisor",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));
                pasos.add(this.crearPaso("aprobacion_plan_titulacion", 5,
                                "Consejo de posgrado revisa el reporte positivo, aprueba el plan y designa tutor oficialmente",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_CONSEJO_POSGRADO));

                pasos.add(this.crearPaso("notificacion_aprobacion_plan", 6,
                                "Secretaria notifica al estudiante que el proceso de titulacion a iniciado formalmente",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));

                pasos.add(this.crearPaso("desarrollo_proyecto", 7,
                                "El estudiante desarrolla el proyecto y lo envia junto a documentos necesarios",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));

                pasos.add(this.crearPaso("envio_proyecto_documentacion", 8,
                                "El estudiante desarrolla el proyecto y lo envia junto a documentos necesarios",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));
                pasos.add(this.crearPaso("generacion_reporte_anti_plagio", 9,
                                "Secretaria gestiona el reporte de anti plagio y recopila los documentos",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));
                pasos.add(this.crearPaso("asignacion_lectores", 10,
                                "revisa la documentacion y asigna lectores",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DIRECTOR));
                pasos.add(this.crearPaso("revision_lectores", 11,
                                "Lectores revisan el proyecto y emiten sus observaciones",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_LECTOR));
                pasos.add(this.crearPaso("habilitacion_defensa", 12,
                                "Se reciben y revisan informes positivos finales de los lectores se habilita la defensa",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DIRECTOR));
                pasos.add(this.crearPaso("documentacion_defensa", 13,
                                "El estudiante entrega la documentacion final para la defensa",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));
                pasos.add(this.crearPaso("defensa", 14,
                                "Secretaria coordina la defensa y se asigna un tribunal",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));
                pasos.add(this.crearPaso("finalizacion_defensa", 15,
                                "El tribunal emite el resultado de la defensa",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));
                return pasos;
        }

        private PasoDTO crearPaso(String nombre,
                        Integer orden,
                        String descripcionPaso,
                        Estado estado,
                        String descripcionEstado,
                        LocalDateTime fechaInicio,
                        String rol) {

                PasoDTO paso = new PasoDTO();
                paso.setNombre(nombre);
                paso.setOrden(orden);
                paso.setDescripcionPaso(descripcionPaso);
                paso.setDescripcionEstado(descripcionEstado);
                paso.setEstado(estado.toString());
                paso.setFechaInicio(fechaInicio);
                paso.setRol(rol);
                return paso;

        }

}
