package com.security.service.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotEmpty;
// import org.hibernate.validator.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "tipoProceso",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ProcesoPagoDocenteDTO.class, name = "PAGO_DOCENTE"),
    @JsonSubTypes.Type(value = ProcesoTitulacionDTO.class, name = "TITULACION")
})
public class ProcesoDTO {
    private Integer id;
    @NotEmpty //para strings
    @Pattern(
        regexp = "PAGO_DOCENTE|TITULACION|DISENIO_MAESTRIA",
        message = "El tipo de proceso no es válido. Debe ser uno de: PAGO_DOCENTE, TITULACION, DISENIO_MAESTRIA"
    )
    private String tipoProceso;
    @NotNull
    @Min(value = 1)
    private Integer requirienteId;
    private String descripcion;
    private Instant fechaInicio;
    private Instant fechaFin;//Solo serviria cuando vamos a actualizar el final
    private Boolean finalizado;//Solo serviria cuando vamos a actualizar el final
    private Boolean cancelado;
    // public ProcesoDTO() {} // Constructor vacío necesario
}
