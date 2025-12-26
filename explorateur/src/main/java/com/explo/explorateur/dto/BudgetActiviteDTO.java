package com.explo.explorateur.dto;

import java.util.ArrayList;
import java.util.List;

public class BudgetActiviteDTO {
    private String nom;
    private String description;
    // Date déjà formatée (jj/MM/yyyy) pour l'affichage
    private String dateActivite;
    private double cout;
    private String status;
    private List<BudgetActiviteDetailDTO> details = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateActivite() {
        return dateActivite;
    }

    public void setDateActivite(String dateActivite) {
        this.dateActivite = dateActivite;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BudgetActiviteDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<BudgetActiviteDetailDTO> details) {
        this.details = details;
    }
}
