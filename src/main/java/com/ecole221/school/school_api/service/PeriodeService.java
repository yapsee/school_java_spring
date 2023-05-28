package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Periode;
import com.ecole221.school.school_api.repository.ClasseRepository;
import com.ecole221.school.school_api.repository.PeriodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class PeriodeService {

    @Autowired
    private PeriodeRepository periodeRepository ;

    public List<Periode> getAllPeriodes() {

        return periodeRepository.findAll();
    }

    public Optional<Periode> getPeriodeById(UUID id) {
        return periodeRepository.findById(id);
    }

    public Optional<Periode> getPeriodeByNumero(int  id) {
        return periodeRepository.findByNumero(id);
    }


    public Periode createPeriode(Periode periode) {

        return periodeRepository.save(periode);
    }
}
