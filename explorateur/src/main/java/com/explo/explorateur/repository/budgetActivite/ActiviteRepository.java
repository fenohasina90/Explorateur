package com.explo.explorateur.repository.budgetActivite;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.Activite;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
}
