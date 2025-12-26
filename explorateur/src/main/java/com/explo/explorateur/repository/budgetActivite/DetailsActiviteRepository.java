package com.explo.explorateur.repository.budgetActivite;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.DetailsActivite;

public interface DetailsActiviteRepository extends JpaRepository<DetailsActivite, Long> {
}
