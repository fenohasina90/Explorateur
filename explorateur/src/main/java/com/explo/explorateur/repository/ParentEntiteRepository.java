package com.explo.explorateur.repository;

import com.explo.explorateur.entite.ParentEntite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentEntiteRepository extends JpaRepository<ParentEntite, Long> {
}
