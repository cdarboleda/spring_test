package com.security.repo.impl;

import java.util.ArrayList;
import java.util.List;

import com.security.db.PersonaRol;
import com.security.repo.IPersonaRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class PersonaRepositoryCustomImpl implements IPersonaRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<PersonaRol> findPersonRoles(Integer id) {
            try {
                // String sql = "SELECT new UsuarioDocumentoDTO(u.id, u.nombres, u.apellidos, u.ciudad, u.email, u.telefono, u.fechaNacimiento, u.sexo, u.estado, u.fechaSuscripción, d) " +
                //         "FROM DocumentosUsuarios d JOIN d.usuarios u WHERE d.tipo = :tipoDoc AND u.ciudad = :ciudad";
                String sql = "SELECT pRol FROM PersonaRol pRol WHERE pRol.persona.id=:id";   
                TypedQuery<PersonaRol> myQ = this.entityManager.createQuery(sql, PersonaRol.class);
                myQ.setParameter("id", id);
                return myQ.getResultList();
    
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
    
}
