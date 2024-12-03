package com.security.factory.concret;

import java.util.ArrayList;
import java.util.List;

import com.security.db.Estado;
import com.security.db.Proceso;
import com.security.db.ProcesoPaso;
import com.security.factory.IProcesoPasoFactory;

public class PagoDocentePasoFactory implements IProcesoPasoFactory {

    @Override
    public List<ProcesoPaso> generatePasos(Proceso proceso) {
        List<ProcesoPaso> pasos = new ArrayList<>();
        pasos.add(crearPaso("Paso 1.1", "Descripción del Paso 1.1", 1, proceso, obtenerEstado("Activo")));
        pasos.add(crearPaso("Paso 1.2", "Descripción del Paso 1.2", 2, proceso, obtenerEstado("Pendiente")));
        return pasos;
    }

    private ProcesoPaso crearPaso(String nombre, String descripcion, Integer orden, Proceso proceso, Estado estado) {
        ProcesoPaso paso = new ProcesoPaso();
        paso.setNombre(nombre);
        paso.setDescripcion(descripcion);
        paso.setOrden(orden);
        paso.setProceso(proceso);
        paso.setEstado(estado);
        return paso;
    }

    private Estado obtenerEstado(String nombreEstado) {
        Estado estado = new Estado();
        estado.setNombre(nombreEstado);
        return estado;
    }

}
