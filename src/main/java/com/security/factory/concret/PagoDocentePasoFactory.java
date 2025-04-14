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

@Component
public class PagoDocentePasoFactory implements IPasoFactory {

    @Override
    @Transactional
    public List<PasoDTO> generatePasos() {
        List<PasoDTO> pasos = new ArrayList<>();

        pasos.add(this.crearPasoPrimero("documentacion_docente", "docente"));
        pasos.add(this.crearPasoDefault("revision_coordinador", 2, "coordinador"));
        pasos.add(this.crearPasoDefault("revision_secretaria", 3, "secretaria"));
        pasos.add(this.crearPasoDefault("pedido_asistencia", 4, "secretaria"));
        pasos.add(this.crearPasoDefault("validacion_asistencia_docente", 5, "docente"));
        pasos.add(this.crearPasoDefault("validacion_asistencia_coordinador", 6, "coordinador"));
        pasos.add(this.crearPasoDefault("autorizacion_director", 7, "director"));
        pasos.add(this.crearPasoDefault("aprobacion_decano", 8, "secretaria"));
        pasos.add(this.crearPasoDefault("revision_financiero", 9, "secretaria"));
        pasos.add(this.crearPasoDefault("factura_docente", 10, "docente"));
        pasos.add(this.crearPasoDefault("revision_factura_director", 11, "director"));
        pasos.add(this.crearPasoDefault("desembolso_financiero", 12, "secretaria"));
        
        return pasos;
    }

    private PasoDTO crearPasoPrimero(String nombrePaso, String rol){
        return this.crearPaso(nombrePaso, 1, Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), LocalDateTime.now(), rol);
    }

    private PasoDTO crearPasoDefault(String nombrePaso, Integer orden, String rol){
        return this.crearPaso(nombrePaso, orden, Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, rol);
    }

    private PasoDTO crearPaso(String nombre,
            Integer orden,
            Estado estado,
            String descripcionEstado,
            LocalDateTime fechaInicio,
            String rol) {

        PasoDTO paso = new PasoDTO();
        paso.setNombre(nombre);
        paso.setOrden(orden);
        paso.setDescripcionEstado(descripcionEstado);
        paso.setEstado(estado.toString());
        paso.setFechaInicio(fechaInicio);
        paso.setRol(rol);
        return paso;

    }

}
