package com.explo.explorateur.repository.constant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explo.explorateur.entite.constant.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    @Query(value = """
        SELECT * FROM classes WHERE age = :age
        """, nativeQuery = true)
    Classe findByAge(@Param("age") Integer age);
}
