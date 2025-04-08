package com.security.service;

import com.security.service.dto.TitulacionResponsableNotaLigeroDTO;

public interface IGestorProcesoTitulacionService {

    public TitulacionResponsableNotaLigeroDTO buscarResponsableNotaPaso(Integer idProcesoTitulacion, String nombrePaso);

}
