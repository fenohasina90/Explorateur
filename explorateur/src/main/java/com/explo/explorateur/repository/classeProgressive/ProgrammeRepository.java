package com.explo.explorateur.repository.classeProgressive;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.classeProgressive.Programme;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
}
