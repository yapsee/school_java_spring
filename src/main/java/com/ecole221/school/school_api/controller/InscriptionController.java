package com.ecole221.school.school_api.controller;

import com.ecole221.school.school_api.exception.ResourceNotFoundException;
import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Inscription;
import com.ecole221.school.school_api.model.Payment;
import com.ecole221.school.school_api.model.Student;
import com.ecole221.school.school_api.service.ClasseService;
import com.ecole221.school.school_api.service.InscriptionService;
import com.ecole221.school.school_api.service.PaymentService;
import com.ecole221.school.school_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/registrations")
public class InscriptionController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClasseService classeService;
    @Autowired
    private InscriptionService inscriptionService ;
    @Autowired
    private PaymentService paymentService ;


    @GetMapping
    public List<Inscription> getAllStudents() {
        return inscriptionService.getAllInscriptions();
    }


    @PostMapping("")
    public ResponseEntity<Inscription> registerStudent(@RequestBody Inscription inscription) {
        UUID studentId = inscription.getStudent().getId();
        UUID classeId = inscription.getClasse().getId();
        Optional<Classe> classeOptional = classeService.getClasseById(classeId);
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        LocalDate dob = inscription.getStudent().getDateDeNaissance();
        String  anneeSolaire = inscription.getAnneeScolaire();
        // Check if student has minimum deposit
        Double mensualite = classeOptional.get().getMensualite();
        Double fraisInscription = classeOptional.get().getFraisInscription();
        Double autresFrais = classeOptional.get().getAutreFrais();
        Double minimumDeposit = fraisInscription + autresFrais + mensualite;

        Double initialDeposit = inscription.getInitialDeposit();
        Double annualAmount = fraisInscription + autresFrais + (9 * mensualite);


        LocalDate DateDebutInscription = classeOptional.get().getDateDebutInscription();
        LocalDate DateFinInscription = classeOptional.get().getDateFinInscription();
        LocalDate dateInscription = inscription.getDateDeInscription();
//
//        if (dateInscription.isBefore(DateDebutInscription) || dateInscription.isAfter(DateFinInscription)) {
//            throw new IllegalArgumentException("Registration date not within allowed range");
//        }
        if (initialDeposit > annualAmount) {
            throw new IllegalArgumentException("The initial deposit must not be greater than " + annualAmount);
        }

        if (initialDeposit < minimumDeposit) {
             throw new IllegalArgumentException("The amount paid must be at least " + minimumDeposit);
        }
        // Check if studentId is given on params
        if (studentId == null) {
            // Check if studentId is older than 18
            if (dob.isBefore(eighteenYearsAgo)) {
            Student newStudent = studentService.createNewStudent(inscription.getStudent());
            inscription.setStudent(newStudent);
            inscription.setClasse(classeOptional.get());
            Inscription registration = inscriptionService.createInscription(inscription);

                paymentService.launchPayments(registration, initialDeposit, minimumDeposit , mensualite);

            return ResponseEntity.status(HttpStatus.CREATED).body(registration);
            } else {
                throw new IllegalArgumentException("Student must be at least 18 years old");
            }
        }
         else {
            // Check if studentId is given exist
            Optional<Student> studentOptional = studentService.getStudentById(studentId);
            if (studentOptional.isPresent()) {

                // if student exist does it fully paid previous class before continue
                Student student = studentOptional.get();

                if (student.isFraisCompletes()) {
                    // check if student didnt already registrate this year
                    Optional<Inscription> inscriptionOptional = inscriptionService.getInscriptionByStudentIdAndAnneeScolaire(student.getId(), anneeSolaire);

                    if (inscriptionOptional.isPresent()) {
                        throw new IllegalArgumentException( "Student can't have 2 registrations in same year");
                    }
                    else {
                        inscription.setStudent(student);
                        inscription.setClasse(classeOptional.get());
                        Inscription registration = inscriptionService.createInscription(inscription);
                        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
                    }

                } else {
                    throw new IllegalArgumentException( "You have to fully pay before registering");
                }
            } else {
                throw new ResourceNotFoundException("Student", "id", studentId);
            }

        }



    }

}


