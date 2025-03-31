package com.security.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.service.dto.ProcesoCompletoDTO;
import com.security.service.dto.TitulacionPropuestaLigeroDTO;
import com.security.service.dto.PersonaTitulacionLigeroDTO;

import jakarta.transaction.Transactional;

public interface IProcesoTitulacionService {

    public void AgregarTutorRevisor(PersonaTitulacionLigeroDTO tutorRevisorDTO);

    public TitulacionPropuestaLigeroDTO buscarRevisorYNota(Integer idProcesoTitulacion);

}
