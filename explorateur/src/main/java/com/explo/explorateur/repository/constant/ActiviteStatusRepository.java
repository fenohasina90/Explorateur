package com.explo.explorateur.repository.constant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.explo.explorateur.entite.constant.ActiviteStatus;

public interface ActiviteStatusRepository extends JpaRepository<ActiviteStatus, Long> {
}
