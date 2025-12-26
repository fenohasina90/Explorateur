package com.explo.explorateur.dto;

public interface EnfantListeDTO {
    // Année d'exercice
    Long getAnneeExerciceId();
    Integer getAnneeExercice();

    // Classe
    Long getClasseId();
    String getClasseNom();
    Integer getClasseAge();

    // Enfant
    Long getEnfantId();
    String getEnfantNom();
    String getEnfantPrenom();
    String getDateNaissance();
    String getAdresseEnfant();
    String getBapteme();
    String getEnfantCreatedAt();
    Integer getAge();

    // Parent
    Long getParentId();
    String getParentNom();
    String getParentPrenom();
    String getAdresseParent();
    String getTelephone();

    // Inscription
    Long getInscriptionId();
    java.time.LocalDateTime getInscriptionCreatedAt();
}
