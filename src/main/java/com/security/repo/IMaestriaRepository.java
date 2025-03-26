package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.db.Maestria;

public interface IMaestriaRepository extends JpaRepository<Maestria, Integer> {
}
