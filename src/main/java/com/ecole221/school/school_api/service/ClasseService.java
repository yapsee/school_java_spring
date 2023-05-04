package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service

public class ClasseService {


    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> getAllClasses() {

        return classeRepository.findAll();
    }

    public Optional<Classe> getClasseById(UUID id) {
        return classeRepository.findById(id);
    }

    public Classe createClasse(Classe classe) {

        return classeRepository.save(classe);
    }

    public Classe updateClasse(UUID id, Classe classe) {
        classe.setId(id);
        return classeRepository.save(classe);
    }

    public void deleteClasse(UUID id) {
        classeRepository.deleteById(id);
    }

}






