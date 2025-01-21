package com.security.service.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
//Para cambios con el front
public class ProcesoDTO {
    private Integer id;
    private String nombre;
    @NotBlank
    @NotNull
    private Integer requirienteId;
    private Integer responsablePrimerPaso;
    private String descripcion;

    @NotBlank
    @NotNull
    @Pattern(
        regexp = "PAGO_DOCENTE|TITULACION|DISENIO_MAESTRIA",
        message = "El tipo de proceso no es válido. Debe ser uno de: PAGO_DOCENTE, TITULACION, DISENIO_MAESTRIA"
    )
    private String tipoProceso;
    private LocalDateTime fechaFinal;//Solo serviria cuando vamos a actualizar el final
    private Boolean finalizado;//Solo serviria cuando vamos a actualizar el final

    private Boolean modalidadVirtual;
    

}
