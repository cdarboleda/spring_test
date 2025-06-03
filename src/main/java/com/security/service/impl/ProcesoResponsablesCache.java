package com.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.repo.IProcesoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProcesoResponsablesCache {

    @Autowired
    private IProcesoRepository procesoRepository;

    // Mapa: procesoId -> lista de usuarios
    private final Map<Integer, Set<String>> responsablesPorProceso = new ConcurrentHashMap<>();

    @Transactional
    public Set<String> getResponsables(Integer procesoId) {

        List<String> responsablesIdsList = this.procesoRepository.responsablesDeUnProceso(procesoId);
        Set<String> responsablesIds = new HashSet<>(responsablesIdsList);
        this.setResponsables(procesoId, responsablesIds);
        System.out.println("Responsables para " + procesoId + ", cargando desde la base de datos");
        responsablesIds.forEach(r -> System.out.println(r + ", "));
        return responsablesIds;
    }

    public void setResponsables(Integer procesoId, Set<String> responsables) {
        responsablesPorProceso.put(procesoId, responsables);
    }

    public void addResponsableAProceso(Integer procesoId, String responsableId) {
        responsablesPorProceso
                .computeIfAbsent(procesoId, k -> ConcurrentHashMap.newKeySet())
                .add(responsableId);
    }

    public void removeResponsables(Integer procesoId) {
        responsablesPorProceso.remove(procesoId);
    }
}