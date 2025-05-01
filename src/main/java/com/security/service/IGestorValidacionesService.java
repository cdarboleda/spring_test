package com.security.service;

import com.security.service.dto.PasoValidacionDTO;

public interface IGestorValidacionesService {

    public PasoValidacionDTO validarAccionPaso(Integer pasoId, Integer personaId);
}
