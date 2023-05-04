package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.Filiere;
import com.ecole221.school.school_api.repository.FiliereRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FiliereService {
    @Autowired
    private FiliereRepository filiereRepository;

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Optional<Filiere> getFiliereById(UUID id) {
        return filiereRepository.findById(id);
    }

    public Filiere createFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public Filiere updateFiliere(UUID id, Filiere filiere) {
        Filiere existingFiliere = getFiliereById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filiere with id " + id + " not found"));
        existingFiliere.setLibelle(filiere.getLibelle());
        return filiereRepository.save(existingFiliere);
    }

    public void deleteFiliere(UUID id) {
        filiereRepository.deleteById(id);
    }
}
