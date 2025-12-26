package com.explo.explorateur.repository.budgetActivite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.Activite;
import com.explo.explorateur.entite.budgetActivite.DetailsActivite;

public interface DetailsActiviteRepository extends JpaRepository<DetailsActivite, Long> {
    List<DetailsActivite> findByActivite(Activite activite);
}
