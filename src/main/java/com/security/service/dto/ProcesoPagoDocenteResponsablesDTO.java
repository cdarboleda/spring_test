package com.security.service.dto;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcesoPagoDocenteResponsablesDTO extends ProcesoPagoDocenteDTO{
    private Map<String, Integer> mapaRolResponsable;

}
