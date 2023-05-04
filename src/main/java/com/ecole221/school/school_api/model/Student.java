package com.ecole221.school.school_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
    private String photo;
    private LocalDate dateDeNaissance;
    private boolean fraisCompletes;
    private double solde;

}
