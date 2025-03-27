package com.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.db.Maestria;
import com.security.db.MaestriaDetalle;
import com.security.db.Materia;
import com.security.exception.CustomException;
import com.security.repo.IMaestriaDetalleRepository;
import com.security.repo.IMaestriaRepository;
import com.security.repo.IMateriaRepository;
import com.security.service.IMateriaService;
import com.security.service.dto.MateriaDTO;
import com.security.service.dto.MateriaTablaDTO;
import com.security.service.dto.utils.ConvertidorMateria;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private IMateriaRepository materiaRepository;

    @Autowired
    private ConvertidorMateria convertidorMateria;

    @Autowired
    private IMaestriaDetalleRepository maestriaDetalleRepository;

    @Override
    public List<MateriaTablaDTO> findAll() {
        // return this.materiaRepository.findAll();
        return this.materiaRepository.findMateriasTabla();
    }

    @Override
    public MateriaTablaDTO insert(MateriaDTO materiaDTO) {
        Materia materia = this.convertidorMateria.convertirAEntidad(materiaDTO);
        MaestriaDetalle maestriaDetalle = this.maestriaDetalleRepository
                .findById(materiaDTO.getMaestriaId())
                .orElseThrow(() -> new CustomException(
                        "La maestria detalle: " + materiaDTO.getMaestriaId() + " no encontrada", HttpStatus.NOT_FOUND));

        materia.setMaestriaDetalle(maestriaDetalle);
        Materia materiaAux = this.materiaRepository.save(materia);
        return this.convertidorMateria.convertirEntidadATablaDTO(materiaAux);
    }

    @Override
    public MateriaTablaDTO update(MateriaDTO materiaDTO) {
        Materia materia = this.findById(materiaDTO.getId());
        materia.setCodigo(materiaDTO.getCodigo());
        materia.setNombre(materiaDTO.getNombre());
        materia.setPeriodo(materiaDTO.getPeriodo());
        MaestriaDetalle maestriaDetalle = this.maestriaDetalleRepository
                .findById(materiaDTO.getMaestriaId())
                .orElseThrow(() -> new CustomException(
                        "La maestria detalle: " + materiaDTO.getMaestriaId() + " no encontrada", HttpStatus.NOT_FOUND));
        materia.setMaestriaDetalle(maestriaDetalle);
        System.out.println(materia);
        return this.convertidorMateria.convertirEntidadATablaDTO(materia);
    }

    @Override
    public Materia findById(Integer id) {
        return this.materiaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Materia: " + id + " no encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteById(Integer id) {
        this.materiaRepository.deleteById(id);
    }

}
