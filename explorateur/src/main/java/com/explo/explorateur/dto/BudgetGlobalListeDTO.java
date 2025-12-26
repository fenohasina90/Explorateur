package com.explo.explorateur.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetGlobalListeDTO {
    private Long budgetId;
    private Long anneeExerciceId;
    private Integer annee;
    private double montant;
    private LocalDate anneeDate;
    private List<BudgetActiviteDTO> activites = new ArrayList<>();

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getAnneeExerciceId() {
        return anneeExerciceId;
    }

    public void setAnneeExerciceId(Long anneeExerciceId) {
        this.anneeExerciceId = anneeExerciceId;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getAnneeDate() {
        return anneeDate;
    }

    public void setAnneeDate(LocalDate anneeDate) {
        this.anneeDate = anneeDate;
    }

    public List<BudgetActiviteDTO> getActivites() {
        return activites;
    }

    public void setActivites(List<BudgetActiviteDTO> activites) {
        this.activites = activites;
    }
}
