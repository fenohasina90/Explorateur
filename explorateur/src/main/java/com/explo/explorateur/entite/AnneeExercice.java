package com.explo.explorateur.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "annee_exercice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnneeExercice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annee", nullable = false)
    private Integer annee;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
