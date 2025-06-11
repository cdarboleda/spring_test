package com.security.factory.concret;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.security.db.enums.Estado;
import com.security.db.enums.EstadoHelper;
import com.security.db.enums.Rol;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;

import jakarta.transaction.Transactional;

@Component
public class PagoDocentePasoFactory implements IPasoFactory {

    @Override
    @Transactional
    public List<PasoDTO> generatePasos() {
        List<PasoDTO> pasos = new ArrayList<>();

        pasos.add(this.crearPasoPrimero("documentacion_docente", Rol.DOCENTE));
        pasos.add(this.crearPasoDefault("revision_coordinador", 2, Rol.COORDINADOR));
        pasos.add(this.crearPasoDefault("revision_pedido_asistencia", 3, Rol.SECRETARIA));
        pasos.add(this.crearPasoDefault("validacion_asistencia_docente", 4, Rol.DOCENTE));
        pasos.add(this.crearPasoDefault("validacion_asistencia_coordinador", 5, Rol.COORDINADOR));
        pasos.add(this.crearPasoDefault("autorizacion_director", 6, Rol.DIRECTOR));
        pasos.add(this.crearPasoDefault("aprobacion_decano", 7, Rol.SECRETARIA));
        pasos.add(this.crearPasoDefault("factura_docente", 8, Rol.DOCENTE));
        pasos.add(this.crearPasoDefault("revision_financiero", 9, Rol.SECRETARIA));
        pasos.add(this.crearPasoDefault("revision_factura_director", 10, Rol.DIRECTOR));
        pasos.add(this.crearPasoDefault("desembolso_financiero", 11, Rol.SECRETARIA));
        
        return pasos;
    }

    private PasoDTO crearPasoPrimero(String nombrePaso, Rol rol){
        return this.crearPaso(nombrePaso, 1, Estado.EN_CURSO, EstadoHelper.getDescripcionPorIndice(Estado.EN_CURSO, 0), Instant.now(), rol.getNombre());
    }

    private PasoDTO crearPasoDefault(String nombrePaso, Integer orden, Rol rol){
        return this.crearPaso(nombrePaso, orden, Estado.PENDIENTE, EstadoHelper.getDescripcionPorIndice(Estado.PENDIENTE, 0), null, rol.getNombre());
    }

    private PasoDTO crearPaso(String nombre,
            Integer orden,
            Estado estado,
            String descripcionEstado,
            Instant fechaInicio,
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
