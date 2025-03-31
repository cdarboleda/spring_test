package com.security.db.enums;

import java.util.List;

public enum Estado {
    PENDIENTE(List.of("Pendiente", "Rechazado")),
    EN_CURSO(List.of("En curso", "Corrigiendo")),
    FINALIZADO(List.of("Completado", "Cancelado"));

    private final List<String> descripciones;

    Estado(List<String> descripciones) {
        this.descripciones = descripciones;
    }

    public String buscarDescripcionPorCriterio(String criterio) {
        return descripciones.stream()
                .filter(descripcion -> descripcion.toLowerCase().contains(criterio.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Descripción no encontrada con criterio: " + criterio));
    }

    // String descripcion =
    // Estado.EN_CURSO.buscarDescripcionPorCriterio("documentación");

    public String getDescripcionPorIndice(int indice) {
        if (indice < 0 || indice >= descripciones.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return descripciones.get(indice);
    }

    public String getDescripcionPorNombre(String nombre) {
        return descripciones.stream()
                .filter(descripcion -> descripcion.equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Descripción no encontrada: " + nombre));
    }

    // String descripcion = Estado.EN_CURSO.getDescripcionPorNombre("Corrigiendo
    // archivos");

}
