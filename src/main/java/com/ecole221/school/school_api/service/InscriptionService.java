package com.ecole221.school.school_api.service;
import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Inscription;
import com.ecole221.school.school_api.model.Student;
import com.ecole221.school.school_api.repository.ClasseRepository;
import com.ecole221.school.school_api.repository.InscriptionRepository;
import com.ecole221.school.school_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Inscription> getAllInscriptions() {

        return inscriptionRepository.findAll();
    }


    public Inscription createInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }


    public Optional<Inscription> getInscriptionByStudentIdAndAnneeScolaire(UUID studentId, String year) {
        return inscriptionRepository.findByStudentIdAndAnneeScolaire(studentId, year);
    }


    public Inscription createReInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    public Inscription updateInscription(UUID id, Inscription inscription) {
        inscription.setId(id);
        return inscriptionRepository.save(inscription);
    }

    public void deleteInscription(UUID id) {
        inscriptionRepository.deleteById(id);
    }

    public Optional<Inscription> getInscriptionById(UUID id) {
        return inscriptionRepository.findById(id);
    }


}
