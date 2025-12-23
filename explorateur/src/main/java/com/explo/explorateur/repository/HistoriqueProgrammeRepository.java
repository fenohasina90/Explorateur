package com.explo.explorateur.repository;

import com.explo.explorateur.entite.HistoriqueProgramme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueProgrammeRepository extends JpaRepository<HistoriqueProgramme, Long> {
}
