package com.explo.explorateur.repository.constant;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explo.explorateur.entite.constant.AnneeExercice;

public interface AnneeExerciceRepository extends JpaRepository<com.explo.explorateur.entite.constant.AnneeExercice, Long> {
    @Query(value = """
        SELECT
        date_part(
            'year',
            age(ae.annee, :dateNaissance)
        ) AS age
    FROM annee_exercice ae
    WHERE ae.id = :anneeExerciceId
    """, nativeQuery = true)
    Integer getAgeEnfant(@Param("anneeExerciceId") Integer anneeExerciceId, @Param("dateNaissance") LocalDate dateNaissance);
}
