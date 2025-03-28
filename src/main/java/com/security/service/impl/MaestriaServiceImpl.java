package com.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.Maestria;
import com.security.db.MaestriaDetalle;
import com.security.repo.IMaestriaDetalleRepository;
import com.security.repo.IMaestriaRepository;
import com.security.service.IMaestriaService;
import com.security.service.dto.MaestriaDTO;

@Service
public class MaestriaServiceImpl implements IMaestriaService {

    @Autowired
    private IMaestriaRepository maestriaRepository;

    @Autowired
    private IMaestriaDetalleRepository maestriaDetalleRepository;

    @Override
    public MaestriaDTO insert(MaestriaDTO maestriaDTO) {
        Maestria maestria;

        if (maestriaDTO.getMaestriaId() == null) {
            maestria = new Maestria();
            maestria.setNombre(maestriaDTO.getNombre());
            maestria.setCodigo(maestriaDTO.getCodigo());
            maestria = maestriaRepository.save(maestria);
        } else {
            // Buscar si la maestría ya existe
            Optional<Maestria> existingMaestria = maestriaRepository.findById(maestriaDTO.getMaestriaId());

            if (existingMaestria.isPresent()) {
                maestria = existingMaestria.get(); // Si existe, usamos la existente
            } else {
                maestria = new Maestria();
                maestria.setNombre(maestriaDTO.getNombre());
                maestria.setCodigo(maestriaDTO.getCodigo());
                maestria = maestriaRepository.save(maestria); // Guardar la nueva maestría
            }
        }

        // Crear el MaestriaDetalle
        MaestriaDetalle detalle = new MaestriaDetalle();
        detalle.setCohorte(maestriaDTO.getCohorte());
        detalle.setFechaInicio(maestriaDTO.getFechaInicio());
        detalle.setFechaFin(maestriaDTO.getFechaFin());
        detalle.setEstado(maestriaDTO.getEstado());
        detalle.setMaestria(maestria); // Asociar el detalle con la maestría

        // Guardar el MaestriaDetalle
        maestriaDetalleRepository.save(detalle);

        // Actualizar el DTO con el ID del MaestriaDetalle y la Maestria
        maestriaDTO.setMaestriaId(maestria.getId());
        maestriaDTO.setMaestriaDetalleId(detalle.getId());

        return maestriaDTO;
    }

    @Override
    public List<MaestriaDTO> findAll() {
        List<Maestria> maestrias = maestriaRepository.findAll(); // Obtener todas las Maestrias
        List<MaestriaDTO> maestriaDTOs = new ArrayList<>();

        for (Maestria maestria : maestrias) {
            for (MaestriaDetalle detalle : maestria.getMaestriasDetalle()) {
                MaestriaDTO dto = new MaestriaDTO();
                dto.setMaestriaId(maestria.getId());
                dto.setNombre(maestria.getNombre());
                dto.setCodigo(maestria.getCodigo());
                dto.setCohorte(detalle.getCohorte());
                dto.setFechaInicio(detalle.getFechaInicio());
                dto.setFechaFin(detalle.getFechaFin());
                dto.setEstado(detalle.getEstado());
                dto.setMaestriaDetalleId(detalle.getId());

                maestriaDTOs.add(dto); // Añadir cada MaestriaDTO a la lista
            }
        }
        return maestriaDTOs;
    }

    @Override
    public boolean delete(MaestriaDTO maestriaDTO) {

        if (maestriaDTO.getMaestriaId() == null || maestriaDTO.getMaestriaDetalleId() == null) {
            return false;
        }

        // Obtenemos la Maestria utilizando el maestriaId del DTO
        Optional<Maestria> maestriaOpt = maestriaRepository.findById(maestriaDTO.getMaestriaId());

        if (maestriaOpt.isPresent()) {
            Maestria maestria = maestriaOpt.get();

            // Verificamos cuántos MaestriaDetalle existen para la Maestria
            if (maestria.getMaestriasDetalle().size() == 1) {
                // Si tiene solo un MaestriaDetalle, eliminamos tanto la Maestria como el
                // MaestriaDetalle
                maestriaDetalleRepository.deleteById(maestriaDTO.getMaestriaDetalleId()); // Eliminar el detalle
                maestriaRepository.deleteById(maestriaDTO.getMaestriaId()); // Eliminar la Maestria
                System.out.println("Esta eliminando solo la mestria.-------------------------------------------");
                System.out.println("Id maestria: " + maestriaDTO.getMaestriaId());
            } else {
                // Si tiene más de un MaestriaDetalle, solo eliminamos el MaestriaDetalle
                // específico
                System.out
                        .println("Esta eliminando solo la mestria detalle.-------------------------------------------");
                System.out.println("Id maestria detalle: " + maestriaDTO.getMaestriaDetalleId());
                System.out.println("Id maestria: " + maestriaDTO.getMaestriaId());

                maestriaDetalleRepository.deleteById(maestriaDTO.getMaestriaDetalleId()); // Eliminar solo el detalle
            }
            System.out.println("llego al final.-------------------------------------------");
            return true;
        } else {
            return false; // Si no se encuentra la Maestria con ese ID, no se elimina nada
        }
    }

    @Override
    public MaestriaDTO update(MaestriaDTO maestriaDTO) {
        if (maestriaDTO.getMaestriaId() == null || maestriaDTO.getMaestriaDetalleId() == null) {
            throw new IllegalArgumentException(
                    "El ID de la maestría y el ID del detalle son obligatorios para actualizar.");
        }

        Optional<Maestria> maestriaOpt = maestriaRepository.findById(maestriaDTO.getMaestriaId());
        Optional<MaestriaDetalle> detalleOpt = maestriaDetalleRepository.findById(maestriaDTO.getMaestriaDetalleId());

        if (maestriaOpt.isPresent() && detalleOpt.isPresent()) {
            Maestria maestria = maestriaOpt.get();
            MaestriaDetalle detalle = detalleOpt.get();

            // Actualizar datos de la Maestría
            maestria.setNombre(maestriaDTO.getNombre());
            maestria.setCodigo(maestriaDTO.getCodigo());
            maestriaRepository.save(maestria);

            // Actualizar datos del MaestriaDetalle
            detalle.setCohorte(maestriaDTO.getCohorte());
            detalle.setFechaInicio(maestriaDTO.getFechaInicio());
            detalle.setFechaFin(maestriaDTO.getFechaFin());
            detalle.setEstado(maestriaDTO.getEstado());
            detalle.setMaestria(maestria);
            maestriaDetalleRepository.save(detalle);

            return maestriaDTO;
        } else {
            throw new IllegalArgumentException("No se encontró la maestría o el detalle para actualizar.");
        }
    }

}
