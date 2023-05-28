package com.ecole221.school.school_api.repository;


import com.ecole221.school.school_api.model.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PeriodeRepository extends JpaRepository<Periode, UUID> {
    Optional<Periode> findByNumero(int numero);
}
