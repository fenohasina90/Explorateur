package com.explo.explorateur.repository.membre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.explo.explorateur.dto.StaffListeDTO;
import com.explo.explorateur.entite.membre.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query(value = """
            SELECT
                ae.id                       AS annee_exercice_id,
                EXTRACT(YEAR FROM ae.annee) AS annee_exercice,

                r.id                        AS role_id,
                r.role_name                 AS role_nom,

                i.id                        AS instructeur_id,
                i.nom                       AS instructeur_nom,
                i.prenom                    AS instructeur_prenom,
                i.telephone,
                i.created_at                AS instructeur_created_at,

                s.id                        AS staff_id,
                s.created_at                AS staff_created_at

            FROM staff s
            JOIN instructeur i
                ON i.id = s.id_instructeur
            JOIN roles_staff r
                ON r.id = s.role_id
            JOIN annee_exercice ae
                ON ae.id = s.annee_exercice_id

            WHERE
                ( :anneeExerciceId IS NULL OR s.annee_exercice_id = :anneeExerciceId )
            AND ( :roleId IS NULL OR s.role_id = :roleId )

            ORDER BY ae.annee DESC, r.role_name, i.nom, i.prenom
            """, nativeQuery = true)
    List<StaffListeDTO> findStaffListe(@Param("anneeExerciceId") Integer anneeExerciceId,
                                       @Param("roleId") Integer roleId);
}
