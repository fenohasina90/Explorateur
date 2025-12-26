package com.explo.explorateur.repository.budgetActivite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.Activite;
import com.explo.explorateur.entite.budgetActivite.BudgetGlobal;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    List<Activite> findByBudget(BudgetGlobal budget);
}
