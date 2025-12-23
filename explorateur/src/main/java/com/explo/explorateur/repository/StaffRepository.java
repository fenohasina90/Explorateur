package com.explo.explorateur.repository;

import com.explo.explorateur.entite.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
