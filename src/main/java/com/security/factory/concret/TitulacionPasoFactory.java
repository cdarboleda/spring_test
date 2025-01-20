package com.security.factory.concret;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.db.enums.Estado;
import com.security.factory.IPasoFactory;
import com.security.service.dto.PasoDTO;

import jakarta.transaction.Transactional;

@Component
public class TitulacionPasoFactory implements IPasoFactory {

    @Override
    @Transactional
    public List<PasoDTO> generatePasos() {
        List<PasoDTO> pasos = new ArrayList<>();
        pasos.add(this.crearPaso("Subir documentación", 1, null, Estado.EN_CURSO, "Aqui se sube la documentación",
                "Enviando", LocalDateTime.now(), null));
                pasos.add(this.crearPaso("Revisión documentación", 2, null, Estado.PENDIENTE, "Coordinarción me revisa la documentación",
                "En espera", null, null));
                pasos.add(this.crearPaso("Finalzaucion", 2, null, Estado.PENDIENTE, "Se ha realizao el pago",
                "En espera", null, null));
        return pasos;
    }

    private PasoDTO crearPaso(String nombre, Integer orden, Integer procesoId, Estado estado, String descripcionPaso,
            String descripcionEstado,
            LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        PasoDTO paso = new PasoDTO();
        paso.setNombre(nombre);
        paso.setOrden(orden);
        paso.setIdProceso(procesoId);
        paso.setDescripcionPaso(nombre);
        paso.setDescripcionEstado(nombre);
        paso.setEstado(estado.name().toLowerCase());
        paso.setFechaInicio(null);
        paso.setFechaFin(null);
        return paso;
    }


}
