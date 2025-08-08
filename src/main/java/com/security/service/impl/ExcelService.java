package com.security.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.security.db.Rol;
import com.security.exception.CustomException;
import com.security.service.dto.PersonaDTO;

import jakarta.transaction.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExcelService {

    @Autowired
    private RolServiceImpl rolServiceImpl;

    public List<PersonaDTO> leerPersonasDesdeExcel(MultipartFile archivo) {
        List<PersonaDTO> personas = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet hoja = workbook.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();

            boolean esPrimeraFila = true;

            while (filas.hasNext()) {
                Row fila = filas.next();

                if (esPrimeraFila) {
                    esPrimeraFila = false; // Saltar encabezado
                    continue;
                }
                // Validar que la fila no esté completamente vacía
                boolean filaVacia = true;
                for (int i = 0; i <= 6; i++) { // hasta columna roles
                    Cell celda = fila.getCell(i);
                    if (celda != null && celda.getCellType() != CellType.BLANK
                            && !getCellValue(celda).trim().isEmpty()) {
                        filaVacia = false;
                        break;
                    }
                }
                if (filaVacia) {
                    continue; // saltar fila vacía
                }

                PersonaDTO persona = new PersonaDTO();
                persona.setNombre(getCellValue(fila.getCell(0)));
                persona.setApellido(getCellValue(fila.getCell(1)));
                String valorCelda = getCellValue(fila.getCell(2)).trim();
                boolean activo = "true".equalsIgnoreCase(valorCelda); // "true" (sin importar mayúsculas)
                persona.setActivo(activo);
                persona.setCorreo(getCellValue(fila.getCell(3)));
                persona.setCedula(getCellValue(fila.getCell(4)));
                persona.setTelefono(getCellValue(fila.getCell(5)));
                List<Rol> rolesDB = this.rolServiceImpl.findAll();
                String rolesStr = getCellValue(fila.getCell(6)); // Ej: "ESTUDIANTE,DOCENTE"

                if (rolesStr != null && !rolesStr.isBlank()) {
                    List<String> rolesAsignar = Arrays.stream(rolesStr.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    Boolean roleExists = rolesDB.stream().anyMatch(rol -> rolesAsignar.contains(rol.getNombre()));

                    if (!roleExists) {
                        throw new CustomException(
                                "Los roles del usuario con cedula " + "¨" + persona.getCedula() + "¨"
                                        + " son incorrectos",
                                HttpStatus.BAD_REQUEST);
                    }

                    // validar si los roles de esa personas pueden ser asignados segun la regla de
                    // negocio
                    boolean rolesValidos = this.validarConjuntoRoles(rolesAsignar);
                    if (!rolesValidos) {
                        throw new CustomException(
                                "Los roles " + rolesAsignar + " del usuario " + persona.getCedula()
                                        + " no se pueden asignar simultaneamente",
                                HttpStatus.CONFLICT);
                    }

                    persona.setRoles(rolesAsignar);
                } else {
                    persona.setRoles(Collections.emptyList()); // Si no se proporcionan roles, se establece una lista
                                                               // vacia
                    System.out.println("Roles no proporcionados");
                }
                personas.add(persona);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo Excel: " + e.getMessage(), e);
        }

        return personas;
    }

    private String getCellValue(Cell celda) {
        if (celda == null)
            return "";

        return switch (celda.getCellType()) {
            case STRING -> celda.getStringCellValue();
            case NUMERIC -> String.valueOf((long) celda.getNumericCellValue());
            case BOOLEAN -> String.valueOf(celda.getBooleanCellValue());
            default -> "";
        };
    }

    private boolean validarConjuntoRoles(List<String> rolesAsignar) {

        // Validaciones específicas de negocio:
        boolean tieneEstudiante = rolesAsignar.contains("estudiante");
        boolean tieneDocente = rolesAsignar.contains("docente");
        boolean tieneDirector = rolesAsignar.contains("director");
        boolean tieneSecretaria = rolesAsignar.contains("secretaria");

        // Si tiene estudiante, solo puede tener estudiante (ningún otro)
        if (tieneEstudiante)
            return rolesAsignar.size() == 1;
        // Si tiene secretaria, solo puede ser secretaria
        if (tieneSecretaria)
            return rolesAsignar.size() == 1;
        // Si tiene docente, puede tener docente y opcionalmente director (y viceversa)
        if (tieneDocente || tieneDirector) {
            // Solo puede tener docente y/o director, nada más
            for (String rol : rolesAsignar) {
                if (!rol.equals("docente") && !rol.equals("director")) {
                    return false;
                }
            }
            return true;
        }
        // Si no cumple con ninguna regla, no válido
        return false;
    }
}