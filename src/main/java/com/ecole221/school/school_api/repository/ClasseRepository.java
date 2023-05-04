package com.ecole221.school.school_api.repository;


import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, UUID> {
    List<Classe> findByFiliere(Filiere filiere);
}
