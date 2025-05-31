package com.security.config;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import com.security.db.Rol;
import com.security.repo.IRolRepository;
 
import jakarta.annotation.PostConstruct;
 
@Component
public class DataInitializer {
 
    @Autowired
    private IRolRepository rolRepository;
 
    //@PostConstruct
    public void initData() {
        if (rolRepository.count() == 0) {
            List<Rol> roles = List.of(
                new Rol("Docente maestria", "docente"),
                new Rol("Coordinador maestria", "coordinador"),
                new Rol("Estudiante maestria", "estudiante"),
                new Rol("Secretaria maestria", "secretaria"),
                new Rol("Diseñador maestria", "disenador"),
                new Rol("Director maestria", "director")
            );
            rolRepository.saveAll(roles);
            System.out.println("Roles inicializados correctamente");
        } else {
            System.out.println("Roles ya estaban inicializados");
        }
    }
   
}