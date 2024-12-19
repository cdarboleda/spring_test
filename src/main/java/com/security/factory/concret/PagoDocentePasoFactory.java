package com.security.factory.concret;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.db.Estado;
import com.security.db.Paso;
import com.security.db.Persona;
import com.security.db.Proceso;
import com.security.factory.IPasoFactory;
import com.security.repo.IEstadoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Component
public class PagoDocentePasoFactory implements IPasoFactory {

    @Autowired
    private IEstadoRepository estadoRepository;

    @Override
    @Transactional
    public List<Paso> generatePasos() {
        List<Paso> pasos = new ArrayList<>();
        pasos.add(this.crearPaso("paso 1.1", 1, null, null, this.obtenerEstado("en-curso")));
        pasos.add(this.crearPaso("Paso 1.2", 2, null, null,this.obtenerEstado("pendiente")));
        return pasos;
    }

    private Paso crearPaso(String nombre, Integer orden, Proceso proceso, Persona responsable, Estado estado) {
        Paso paso = new Paso();
        paso.setNombre(nombre);
        paso.setOrden(orden);
        paso.setProceso(proceso);
        paso.setResponsable(responsable);
        paso.setEstado(estado);
        return paso;
    }

    @Override
    public Estado obtenerEstado(String nombreEstado) {

        return estadoRepository.findByNombre(nombreEstado).orElseThrow(() -> new EntityNotFoundException(
                "El estado " + nombreEstado + " no fue encontrado para agregarlo al paso."));

    }

}
