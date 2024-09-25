package com.alness.quickmail.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtils {

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean esFechaVigente(String fechaVigenciaStr) {
        try {
            LocalDate fechaVigencia = stringDateToLocalDate(fechaVigenciaStr);
            LocalDate fechaActual = LocalDate.now();
            return !fechaVigencia.isBefore(fechaActual);
        } catch (DateTimeParseException e) {
            log.error("Error: Formato de fecha no válido. Use 'yyyy-MM-dd'.");
            log.error("Especifico: {}",e.getMessage());
            return false;
        }
    }
    
    public static LocalDate stringDateToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
    
    public static String stringDateNestToDate(String date){
        Instant instant = Instant.parse(date);
        LocalDate fecha = instant.atZone(ZoneId.of("UTC")).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fecha.format(formatter);
    }
}
