package com.explo.explorateur.repository;

import com.explo.explorateur.entite.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfantRepository extends JpaRepository<Enfant, Long> {
}
