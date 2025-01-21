package com.security.factory;

import java.util.List;

import com.security.service.dto.PasoDTO;

public interface IPasoFactory {

    public List<PasoDTO> generatePasos();

    
}
