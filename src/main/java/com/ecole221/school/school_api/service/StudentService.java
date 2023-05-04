package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.exception.InvalidRequestException;
import com.ecole221.school.school_api.exception.ResourceNotFoundException;
import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Inscription;
import com.ecole221.school.school_api.model.Student;
import com.ecole221.school.school_api.repository.ClasseRepository;
import com.ecole221.school.school_api.repository.InscriptionRepository;
import com.ecole221.school.school_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private InscriptionRepository inscriptionRepository;



    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Student createNewStudent(Student student) {
        student.setId(UUID.randomUUID());
        student.setFraisCompletes(false);
        student.setSolde(0);
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }


    public List<Student> getStudentsByClass(UUID classeId) {
        List<Student> students = new ArrayList<>();
        List<Inscription> inscriptions = inscriptionRepository.findByClasseId(classeId);
        for (Inscription inscription : inscriptions) {
            students.add(inscription.getStudent());
        }
        return students;
    }




}
