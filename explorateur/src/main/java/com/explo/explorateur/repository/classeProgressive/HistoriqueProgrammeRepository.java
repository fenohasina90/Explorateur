package com.explo.explorateur.repository.classeProgressive;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.classeProgressive.HistoriqueProgramme;

public interface HistoriqueProgrammeRepository extends JpaRepository<HistoriqueProgramme, Long> {
}
