package com.ecole221.school.school_api.repository;

import com.ecole221.school.school_api.model.Filiere;
import com.ecole221.school.school_api.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, UUID> {

    Optional<Inscription> findByStudentIdAndAnneeScolaire(UUID studentId, String anneeScolaire);
    List<Inscription> findByClasseId(UUID classeId);


}
