package com.security.db.enums;

public enum Estado {
    PENDIENTE, EN_CURSO, FINALIZADO;

    public static boolean isValid(String value) {
        try {
            Estado.valueOf((value.toUpperCase()));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
