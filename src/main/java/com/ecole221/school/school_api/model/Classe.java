package com.ecole221.school.school_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    private String libelle;
    private double fraisInscription;
    private double mensualite;
    private double autreFrais;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
}
