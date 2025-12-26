package com.explo.explorateur.repository.membre;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.membre.Inscription;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
