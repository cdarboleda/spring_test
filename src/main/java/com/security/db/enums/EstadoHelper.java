package com.security.db.enums;

public class EstadoHelper {

    public static String getDescripcionPorIndice(Estado estado, int indice) {
        return estado.getDescripcionPorIndice(indice);
    }

    public static String buscarDescripcion(Estado estado, String criterio) {
        return estado.buscarDescripcionPorCriterio(criterio);
    }
    
}
