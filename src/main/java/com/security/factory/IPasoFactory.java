package com.security.factory;

import java.util.List;

import com.security.db.Paso;
import com.security.db.enums.Estado;
import com.security.service.dto.PasoDTO;

public interface IPasoFactory {

    public List<PasoDTO> generatePasos();

    
}
