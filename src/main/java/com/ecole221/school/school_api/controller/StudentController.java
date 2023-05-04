package com.ecole221.school.school_api.controller;

import com.ecole221.school.school_api.exception.ResourceNotFoundException;
import com.ecole221.school.school_api.model.Classe;
import com.ecole221.school.school_api.model.Student;
import com.ecole221.school.school_api.service.ClasseService;
import com.ecole221.school.school_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable("id") UUID classeId) {
        List<Student> students = studentService.getStudentsByClass(classeId);
        return ResponseEntity.ok().body(students);
    }


    @PostMapping("")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student createdStudent = studentService.createNewStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }



}




