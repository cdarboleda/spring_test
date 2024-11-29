package com.security.repo.impl;

import org.springframework.stereotype.Repository;

import com.security.repo.IPersonaRepositoryCustom;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class IPersonaRepositoryCustomImpl implements IPersonaRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;

    // @Override
    // public List<Rol> findRolesByPersonId(Integer id) {
    // try {

    // String sql = "SELECT pRol.rol FROM PersonaRol pRol WHERE
    // pRol.persona.id=:id";
    // TypedQuery<Rol> myQ = this.entityManager.createQuery(sql, Rol.class);
    // myQ.setParameter("id", id);
    // return myQ.getResultList();

    // } catch (Exception e) {
    // return new ArrayList<>();
    // }
    // }

}
