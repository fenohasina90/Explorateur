package com.explo.explorateur.repository.constant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.constant.ProgrammeStatus;

public interface ProgrammeStatusRepository extends JpaRepository<ProgrammeStatus, Long> {
}
