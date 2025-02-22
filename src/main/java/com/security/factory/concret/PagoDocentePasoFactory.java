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

        pasos.add(this.crearPaso("documentacion_docente", 1, "Aqui se sube la documentación",
                Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), LocalDateTime.now(),
                "docente"));
        pasos.add(this.crearPaso("revision_coordinador", 2, "Coordinarción me revisa la documentación",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "coordinador"));
        pasos.add(this.crearPaso("revision_secretaria", 3, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "secretaria"));
        pasos.add(this.crearPaso("autorizacion_director", 4, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "director"));
        pasos.add(this.crearPaso("aprobacion_decano", 5, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "secretaria"));
        pasos.add(this.crearPaso("factura_docente", 6, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "docente"));
        pasos.add(this.crearPaso("revision_factura_financiero", 7, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "secretaria"));
        pasos.add(this.crearPaso("revision_factura_director", 8, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "director"));
        pasos.add(this.crearPaso("desembolso_financiero", 9, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "secretaria"));
        pasos.add(this.crearPaso("finalizacion_tramite", 10, "Se ha realizao el pago",
                Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, "secretaria"));
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
