package com.explo.explorateur.repository.membre;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.membre.Instructeur;

public interface InstructeurRepository extends JpaRepository<Instructeur, Long> {
}
