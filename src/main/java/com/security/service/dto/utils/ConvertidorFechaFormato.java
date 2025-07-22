package com.security.service.dto.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ConvertidorFechaFormato {

    public static String formatearFecha(LocalDateTime fecha) {
        Locale locale = Locale.of("es", "ES");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy - HH:mm", locale);
        return fecha.format(formatter);
    }
}
