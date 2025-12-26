package com.explo.explorateur.entite.classeProgressive;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "classe_progressive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasseProgressive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_cp", nullable = false)
    private LocalDate dateCp;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @Column(name = "niveau")
    private Integer niveau;
}
