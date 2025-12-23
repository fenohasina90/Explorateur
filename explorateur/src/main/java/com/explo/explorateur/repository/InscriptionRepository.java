package com.explo.explorateur.repository;

import com.explo.explorateur.entite.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
