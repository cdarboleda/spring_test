package com.security.db.enums;

public enum PasoTitulacion {
    REGISTRO_PROYECTO("registro_propuesta"),
    REVISION_DOCUMENTACION("revision_documentacion"),
    REVISION_IDONEIDAD("revision_idoneidad"),
    CORRECCION_PLAN("correccion_plan"),
    APROBACION_PLAN_TITULACION("aprobacion_plan_titulacion"),
    DESARROLLO_PROYECTO("desarrollo_proyecto"),
    GENERACION_REPORTE_ANTI_PLAGIO("generacion_reporte_anti_plagio"),
    DESIGNACION_LECTORES("designacion_lectores"),
    REVISION_LECTOR_1("revision_lector_1"),
    REVISION_LECTOR_2("revision_lector_2"),
    CORRECCION_OBSERVACION_LECTORES("correccion_observacion_lectores"),
    DOCUMENTACION_DEFENSA("documentacion_defensa"),
    DEFENSA("defensa");

    private final String nombre;

    PasoTitulacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static PasoTitulacion fromString(String text) {
        for (PasoTitulacion b : PasoTitulacion.values()) {
            if (b.nombre.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
