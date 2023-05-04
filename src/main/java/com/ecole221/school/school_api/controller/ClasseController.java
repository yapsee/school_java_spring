package com.ecole221.school.school_api.controller;

import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable UUID id) {
        Optional<Classe> classeOptional = classeService.getClasseById(id);
        return classeOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Classe> createClasse(@RequestBody Classe classe) {
        Classe createdClasse = classeService.createClasse(classe);
        return ResponseEntity.ok(createdClasse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable UUID id, @RequestBody Classe classe) {
        Classe updatedClasse = classeService.updateClasse(id, classe);
        return ResponseEntity.ok(updatedClasse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable UUID id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}






