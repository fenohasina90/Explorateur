package com.explo.explorateur.repository;

import com.explo.explorateur.entite.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
