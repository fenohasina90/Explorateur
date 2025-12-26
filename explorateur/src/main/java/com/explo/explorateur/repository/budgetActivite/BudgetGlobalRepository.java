package com.explo.explorateur.repository.budgetActivite;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.BudgetGlobal;

public interface BudgetGlobalRepository extends JpaRepository<BudgetGlobal, Long> {
}
