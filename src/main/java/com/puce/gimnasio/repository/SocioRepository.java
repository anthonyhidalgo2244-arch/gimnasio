package com.puce.gimnasio.repository;

import com.puce.gimnasio.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer> {
    List<Socio> findByPlanIgnoreCase(String plan);
}
