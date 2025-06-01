package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Maestria;
import com.security.exception.CustomException;
import com.security.repo.IMaestriaRepository;
import com.security.service.IMaestriaService;
import com.security.service.dto.MaestriaDTO;
import com.security.service.dto.utils.ConvertidorMaestria;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MaestriaServiceImpl implements IMaestriaService {

    @Autowired
    private IMaestriaRepository maestriaRepository;

    @Autowired
    private ConvertidorMaestria convertidorMaestria;

    @Override
    public MaestriaDTO insert(MaestriaDTO maestriaDTO) {
        // si ex que ya existe una con esos tres mismos campos pues no deja insertar
        this.existeMaestriaPorCodigoNombreCohorte(maestriaDTO.getCodigo(), maestriaDTO.getNombre(),
                maestriaDTO.getCohorte());

        return this.convertidorMaestria
                .convertirADTO(this.maestriaRepository.save(this.convertidorMaestria.convertirAEntidad(maestriaDTO)));
    }

    @Override
    public List<MaestriaDTO> findAll() {
        List<Maestria> maestrias = this.maestriaRepository.findAll(); // Obtener todas las Maestrias
        return maestrias.stream().map(convertidorMaestria::convertirADTO).toList();
    }

    @Override
    public boolean delete(MaestriaDTO maestriaDTO) {
        if (maestriaDTO.getId() == null)
            return false;
        this.maestriaRepository.deleteById(maestriaDTO.getId());
        return true;
    }

    @Override
    public Maestria findById(Integer id) {
        return this.maestriaRepository.findById(id).orElseThrow();
    }

    @Override
    public MaestriaDTO update(MaestriaDTO maestriaDTO) {
        Maestria maestria = this.findById(maestriaDTO.getId());
        maestria.setId(maestriaDTO.getId());
        maestria.setNombre(maestriaDTO.getNombre());
        maestria.setCodigo(maestriaDTO.getCodigo());
        maestria.setCohorte(maestriaDTO.getCohorte());
        maestria.setFechaInicio(maestriaDTO.getFechaInicio());
        maestria.setFechaFin(maestriaDTO.getFechaFin());
        maestria.setEstado(maestriaDTO.getEstado());

        return this.convertidorMaestria.convertirADTO((maestria));
    }

    @Override
    public void existeMaestriaPorCodigoNombreCohorte(String codigo, String nombre, Integer cohorte) {
        Maestria maestria = this.maestriaRepository.findMaestriaPorCodigoNombreCohorte(codigo, nombre, cohorte);
        if (maestria != null) {
            throw new CustomException(
                    "La maestria ya existe con esos campos: ", HttpStatus.CONFLICT);
        }
    }

}
