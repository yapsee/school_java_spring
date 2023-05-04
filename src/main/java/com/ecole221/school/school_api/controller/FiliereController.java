package com.ecole221.school.school_api.controller;
import com.ecole221.school.school_api.model.Filiere;
import com.ecole221.school.school_api.model.Student;
import com.ecole221.school.school_api.service.FiliereService;
import com.ecole221.school.school_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/filieres")

public class FiliereController {


    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public List<Filiere> getAllFilieres() {
        return filiereService.getAllFilieres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filiere> getFiliereById(@PathVariable UUID id) {
        Optional<Filiere> filiereOptional = filiereService.getFiliereById(id);
        return filiereOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        Filiere createdFiliere = filiereService.createFiliere(filiere);
        return ResponseEntity.ok(createdFiliere);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable UUID id, @RequestBody Filiere filiere) {
        Filiere updatedFiliere = filiereService.updateFiliere(id, filiere);
        return ResponseEntity.ok(updatedFiliere);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable UUID id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.noContent().build();
    }

}
