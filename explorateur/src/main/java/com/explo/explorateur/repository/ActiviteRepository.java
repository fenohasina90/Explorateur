package com.explo.explorateur.repository;

import com.explo.explorateur.entite.Activite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
}
