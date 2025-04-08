package com.security.service;

import com.security.service.dto.PersonaDTO;
import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;
import com.security.service.dto.TitulacionTutorLigeroDTO;

public interface IProcesoTitulacionService {

    public void insertarNotaPasoEspecifico(Integer idProcesoTitulacion,
            TitulacionResponsableNotaLigeroDTO responsableNotaLigeroDTO);

    public void agregarTutorProcesoTitulacion(Integer idProcesoTitulacion, PersonaDTO personaTutorDTO);

    public TitulacionTutorLigeroDTO buscarTutorProcesoTitulacion(Integer idProcesoTitulacion);

}
