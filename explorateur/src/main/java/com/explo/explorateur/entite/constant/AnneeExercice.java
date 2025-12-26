package com.explo.explorateur.entite.constant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private LocalDate annee;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
