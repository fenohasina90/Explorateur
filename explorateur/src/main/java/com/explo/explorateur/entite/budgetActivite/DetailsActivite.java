package com.explo.explorateur.entite.budgetActivite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "details_activites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsActivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @Column(name = "details")
    private String details;

    @Column(name = "montant")
    private double montant;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
