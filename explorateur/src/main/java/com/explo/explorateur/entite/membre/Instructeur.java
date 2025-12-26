package com.explo.explorateur.entite.membre;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "instructeur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "telephone", length = 15)
    private String telephone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
