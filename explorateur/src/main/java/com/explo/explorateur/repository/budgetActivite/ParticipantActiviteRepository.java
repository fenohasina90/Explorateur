package com.explo.explorateur.repository.budgetActivite;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.budgetActivite.ParticipantActivite;

public interface ParticipantActiviteRepository extends JpaRepository<ParticipantActivite, Long> {
}
