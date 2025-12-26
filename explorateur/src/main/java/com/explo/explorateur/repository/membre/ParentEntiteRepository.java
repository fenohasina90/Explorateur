package com.explo.explorateur.repository.membre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explo.explorateur.dto.ParentEnfantFlatDTO;
import com.explo.explorateur.entite.membre.ParentEntite;

public interface ParentEntiteRepository extends JpaRepository<ParentEntite, Long> {

    @Query(value = """
            SELECT
                p.id                         AS parent_id,
                p.nom                        AS parent_nom,
                p.prenom                     AS parent_prenom,
                p.telephone,
                p.adresse                    AS adresse_parent,
                e.id                         AS enfant_id,
                e.nom                        AS enfant_nom,
                e.prenom                     AS enfant_prenom,
                e.genre,
                TO_CHAR(e.date_naissance, 'DD/MM/YYYY') AS date_naissance,
                CASE
                    WHEN ae.id IS NOT NULL
                    THEN date_part('year', age(ae.annee, e.date_naissance))
                    ELSE NULL
                END AS age_enfant,
                c.id                         AS classe_id,
                c.nom                        AS classe_nom,
                EXTRACT(YEAR FROM ae.annee)  AS annee_exercice
            FROM parents p
            JOIN enfants e
                ON e.parent_id = p.id
            JOIN inscriptions i
                ON i.enfant_id = e.id
            JOIN classes c
                ON c.id = i.classe_id
            JOIN annee_exercice ae
                ON ae.id = i.annee_exercice_id

            WHERE
                ( :anneeExerciceId IS NULL OR i.annee_exercice_id = :anneeExerciceId )
            AND ( :classeId        IS NULL OR i.classe_id          = :classeId )

            ORDER BY
                p.nom, p.prenom,
                e.nom, e.prenom
            """, nativeQuery = true)
    List<ParentEnfantFlatDTO> findParentsEnfantsListe(@Param("anneeExerciceId") Integer anneeExerciceId,
                                                    @Param("classeId") Integer classeId);
}
