package com.ecole221.school.school_api.controller;

import com.ecole221.school.school_api.model.Periode;
import com.ecole221.school.school_api.service.PeriodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/periodes")
public class PeriodeController {

    @Autowired
    private PeriodeService periodeService;

    @GetMapping
    public List<Periode> getAllPeriodes() {
        return periodeService.getAllPeriodes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Periode> getPeriodeById(@PathVariable UUID id) {
        Optional<Periode> periodeOptional = periodeService.getPeriodeById(id);
        return periodeOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Periode> createPeriode(@RequestBody Periode periode) {
        Periode createdPeriode = periodeService.createPeriode(periode);
        return ResponseEntity.ok(createdPeriode);
    }
}
