package com.explo.explorateur.dto;

public interface ParentEnfantFlatDTO {
    // Parent
    Long getParentId();
    String getParentNom();
    String getParentPrenom();
    String getTelephone();
    String getAdresseParent();

    // Enfant
    Long getEnfantId();
    String getEnfantNom();
    String getEnfantPrenom();
    String getGenre();
    String getDateNaissance();
    Integer getAgeEnfant();

    // Classe
    Long getClasseId();
    String getClasseNom();

    // Annee
    Integer getAnneeExercice();
}
