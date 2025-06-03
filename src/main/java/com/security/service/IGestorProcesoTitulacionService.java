package com.security.service;

import com.security.service.dto.TitulacionPasoResponsableLigeroDTO;

public interface IGestorProcesoTitulacionService {

    public TitulacionPasoResponsableLigeroDTO buscarResponsablePaso(Integer idProcesoTitulacion, String nombrePaso);

}
