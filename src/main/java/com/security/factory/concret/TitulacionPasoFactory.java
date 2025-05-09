package com.security.factory.concret;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.db.enums.PasoTitulacion;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;
import com.security.db.enums.PasoTitulacion;

import jakarta.transaction.Transactional;

//Crea pasos predefinidos para cada tipo de proceso,
//Tiene el primer paso como EN_CURSO, debe tener responsable y se asigna en el service que usa este metodo
//El resto de pasos estan en pendiente y sin responsable
@Component
public class TitulacionPasoFactory implements IPasoFactory {

        private static final String ROL_ESTUDIANTE = "estudiante";
        private static final String ROL_DOCENTE = "docente";
        // private static final String ROL_LECTOR = "LECTOR";
        private static final String ROL_SECRETARIA = "secretaria";
        private static final String ROL_DIRECTOR = "director";

        @Override
        @Transactional
        public List<PasoDTO> generatePasos() {
                List<PasoDTO> pasos = new ArrayList<>();
                pasos.add(this.crearPaso(PasoTitulacion.REGISTRO_PROYECTO.getNombre(), 1,
                                "Se registra la propuesta de proyecto",
                                Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0),
                                LocalDateTime.now(), ROL_ESTUDIANTE));
                pasos.add(this.crearPaso(PasoTitulacion.REVISION_DOCUMENTACION.getNombre(), 2,
                                "Secretaria revisa la documentacion y asigna un docente revisor",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0),
                                null, ROL_SECRETARIA));
                pasos.add(this.crearPaso(PasoTitulacion.REVISION_IDONEIDAD.getNombre(), 3,
                                "Reporte de revision del plan de titulacion",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DOCENTE));
                /*
                 * pasos.add(this.crearPaso(PasoTitulacion.CORRECCION_PLAN.getNombre(), 4,
                 * "Realizar las correcciones del plan emitidas por el revisor",
                 * Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0),
                 * null,
                 * ROL_ESTUDIANTE));
                 */
                pasos.add(this.crearPaso(PasoTitulacion.APROBACION_PLAN_TITULACION.getNombre(), 4,
                                "Consejo de posgrado revisa el reporte positivo, aprueba el plan y designa tutor oficialmente",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DIRECTOR)); // ROL CONSEJO POSGRADO sera administrado por la/el DIRECTOR
                pasos.add(this.crearPaso(PasoTitulacion.DESARROLLO_PROYECTO.getNombre(), 5,
                                "El estudiante desarrolla el proyecto y lo envia junto a documentos necesarios",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));
                pasos.add(this.crearPaso(PasoTitulacion.GENERACION_REPORTE_ANTI_PLAGIO.getNombre(), 6,
                                "Secretaria gestiona el reporte de anti plagio y recopila los documentos",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_SECRETARIA));
                pasos.add(this.crearPaso(PasoTitulacion.DESIGNACION_LECTORES.getNombre(), 7,
                                "Revisa la documentacion y designa los lectores",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DIRECTOR));

                pasos.add(this.crearPaso(PasoTitulacion.REVISION_LECTOR_1.getNombre(), 8,
                                "El Lector 1 revisa el proyecto y emiten sus observaciones",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DOCENTE));
                pasos.add(this.crearPaso(PasoTitulacion.REVISION_LECTOR_2.getNombre(), 9,
                                "El Lector 2 revisan el proyecto y emiten sus observaciones",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_DOCENTE));
                pasos.add(this.crearPaso(PasoTitulacion.CORRECCION_OBSERVACION_LECTORES.getNombre(), 10,
                                "Estudiante corrige las observaciones emitidas por los lectores",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));
                pasos.add(this.crearPaso(PasoTitulacion.DOCUMENTACION_DEFENSA.getNombre(), 11,
                                "El estudiante entrega la documentacion final para la defensa",
                                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null,
                                ROL_ESTUDIANTE));
                pasos.add(this.crearPaso(PasoTitulacion.DEFENSA.getNombre(), 12,
                                "Secretaria coordina la defensa y se asigna un tribunal",
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
