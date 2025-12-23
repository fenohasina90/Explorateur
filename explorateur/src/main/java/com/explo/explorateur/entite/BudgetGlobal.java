package com.explo.explorateur.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_global")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGlobal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_exercice_id")
    private AnneeExercice anneeExercice;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
