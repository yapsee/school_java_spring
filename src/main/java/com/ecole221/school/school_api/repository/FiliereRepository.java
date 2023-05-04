package com.ecole221.school.school_api.repository;


import com.ecole221.school.school_api.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface FiliereRepository extends JpaRepository<Filiere, UUID> {
}
