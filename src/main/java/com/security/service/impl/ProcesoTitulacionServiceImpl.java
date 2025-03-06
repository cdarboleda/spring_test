package com.security.service.impl;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.security.service.IProcesoTitulacionService;
import com.security.service.dto.ProcesoCompletoDTO;

@Service
@Transactional
public class ProcesoTitulacionServiceImpl implements IProcesoTitulacionService {

    @Override
    public void inscribirse(ProcesoCompletoDTO procesoCompletoDTO) {
        
    }

}
