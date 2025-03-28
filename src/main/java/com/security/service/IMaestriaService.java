package com.security.service;

import java.util.List;

import com.security.service.dto.MaestriaDTO;

public interface IMaestriaService {

    public MaestriaDTO insert(MaestriaDTO maestriaDTO);

    public List<MaestriaDTO> findAll();
    
    public boolean delete(MaestriaDTO maestriaDTO);

    public MaestriaDTO update(MaestriaDTO maestriaDTO);
}
