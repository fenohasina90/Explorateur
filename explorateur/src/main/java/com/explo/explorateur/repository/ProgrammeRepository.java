package com.explo.explorateur.repository;

import com.explo.explorateur.entite.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
}
