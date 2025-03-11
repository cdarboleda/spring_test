package com.security.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.db.ProcesoLog;
import com.security.repo.IProcesoLogRepository;
import com.security.service.IProcesoLogService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProcesoLogServiceImpl implements IProcesoLogService {

    @Autowired
    private IProcesoLogRepository procesoLogRepository;

    @Override
    public ProcesoLog insert(ProcesoLog procesoLog) {
        procesoLog.setFechaCambio(LocalDateTime.now());
        return this.procesoLogRepository.save(procesoLog);
    }

    @Override
    public List<ProcesoLog> findAll() {
        return this.procesoLogRepository.findAll();
    }

    @Override
    public List<ProcesoLog> findByProcesoId(Integer id) {
        return this.procesoLogRepository.findByProcesoId(id);
    }

    // @Override
    // public List<ProcesoLog> findByIdProcesoAndIdPaso(Integer idProceso, Integer
    // idPaso) {
    // return this.procesoLogRepository.findByIdProcesoAndIdPaso(idProceso, idPaso);
    // }

}
