package com.security.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class PersonaTitulacionLigeroDTO {

    private Integer id;
    private Integer procesoId;
    List<String> roles;

}
