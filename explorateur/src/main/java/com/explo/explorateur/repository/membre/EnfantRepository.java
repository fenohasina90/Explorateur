package com.explo.explorateur.repository.membre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explo.explorateur.dto.EnfantClasseAnneeDTO;
import com.explo.explorateur.dto.EnfantListeDTO;
import com.explo.explorateur.entite.membre.Enfant;

public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    @Query(value = """
            SELECT
                c.nom AS classe,
                EXTRACT(YEAR FROM ae.annee) AS annee_exercice,
                e.prenom,
                e.nom,
                date_part('year', age(ae.annee, e.date_naissance)) AS age
            FROM enfants e
            JOIN annee_exercice ae ON ae.id = :anneeExerciceId
            JOIN classes c
                ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
            WHERE c.id = :classeId
        """, nativeQuery = true)
    List<EnfantClasseAnneeDTO> findAllEnfantsClasseAnnee(@Param("anneeExerciceId") Integer anneeExerciceId,
                                                        @Param("classeId") Integer classeId);

    @Query(value = """
        SELECT
            e.*
        FROM enfants e
        JOIN annee_exercice ae ON ae.id = :anneeExerciceId
        JOIN classes c
            ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
        WHERE c.id = :classeId AND e.id NOT IN (
            SELECT ins.enfant_id
            FROM inscriptions ins
            WHERE ins.annee_exercice_id = ae.id
        )
    """, nativeQuery = true)
    List<Enfant> findEnfantsNonInscrit(@Param("anneeExerciceId") Integer anneeExerciceId,
                                        @Param("classeId") Integer classeId);

    @Query(value = """
        SELECT
            ae.id                       AS annee_exercice_id,
            EXTRACT(YEAR FROM ae.annee) AS annee_exercice,
            c.id                       AS classe_id,
            c.nom                      AS classe_nom,
            c.age                      AS classe_age,
            e.id                       AS enfant_id,
            e.nom                      AS enfant_nom,
            e.prenom                   AS enfant_prenom,
            TO_CHAR(e.date_naissance, 'DD/MM/YYYY') AS date_naissance,
            e.adresse                  AS adresse_enfant,
            TO_CHAR(e.bapteme, 'DD/MM/YYYY') AS bapteme,
            TO_CHAR(e.created_at, 'DD/MM/YYYY') AS enfant_created_at,
            date_part('year', age(ae.annee, e.date_naissance)) AS age,
            p.id                       AS parent_id,
            p.nom                      AS parent_nom,
            p.prenom                   AS parent_prenom,
            p.adresse                  AS adresse_parent,
            p.telephone,
            i.id                       AS inscription_id,
            i.created_at               AS inscription_created_at

        FROM inscriptions i
        JOIN enfants e
            ON e.id = i.enfant_id
        LEFT JOIN parents p
            ON p.id = e.parent_id
        JOIN annee_exercice ae
            ON ae.id = i.annee_exercice_id
        JOIN classes c
            ON c.id = i.classe_id
        WHERE
            ( :anneeExerciceId IS NULL OR i.annee_exercice_id = :anneeExerciceId )
        AND ( :classeId IS NULL OR i.classe_id = :classeId )

        ORDER BY ae.annee DESC, c.age ASC, e.nom, e.prenom
    """, nativeQuery = true)
    List<EnfantListeDTO> findEnfantsListe(@Param("anneeExerciceId") Integer anneeExerciceId,
                                          @Param("classeId") Integer classeId);
}

