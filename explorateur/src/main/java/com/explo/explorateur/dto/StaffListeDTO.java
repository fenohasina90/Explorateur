package com.explo.explorateur.dto;

public interface StaffListeDTO {
    // Année d'exercice
    Long getAnneeExerciceId();
    Integer getAnneeExercice();

    // Rôle
    Long getRoleId();
    String getRoleNom();

    // Instructeur
    Long getInstructeurId();
    String getInstructeurNom();
    String getInstructeurPrenom();
    String getTelephone();
    java.time.LocalDateTime getInstructeurCreatedAt();

    // Staff
    Long getStaffId();
    java.time.LocalDateTime getStaffCreatedAt();
}
