-- calculer l’âge de tous les enfants par rapport à une année d’exercice et classe donnée
SELECT
    c.nom AS classe,
    EXTRACT(YEAR FROM ae.annee) AS annee_exercice,
    e.prenom,
    e.nom,
    date_part('year', age(ae.annee, e.date_naissance)) AS age
FROM enfants e
JOIN annee_exercice ae ON ae.id = 1
JOIN classes c
    ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
WHERE c.id = 3;

-- calculer l’âge d'un enfant par rapport à une année d’exercice et date de naissance donnée
SELECT
    date_part(
        'year',
        age(ae.annee, '2015-02-21')
    ) AS age
FROM annee_exercice ae
WHERE ae.id = 1;


-- calculer l’âge de tous les enfants par rapport à une année d’exercice donnée
SELECT
    e.id,
    e.prenom,
    e.nom,
    EXTRACT(YEAR FROM ae.annee) AS annee_exercice,
    date_part('year', age(ae.annee, e.date_naissance)) AS age
FROM enfants e
JOIN annee_exercice ae
    ON ae.id = 1
ORDER BY e.nom, e.prenom;


-- tous les enfants classés par classe pour une année donnée
SELECT
    c.nom AS classe,
    EXTRACT(YEAR FROM ae.annee) AS annee_exercice,
    e.prenom,
    e.nom,
    date_part('year', age(ae.annee, e.date_naissance)) AS age
FROM enfants e
JOIN annee_exercice ae ON ae.id = 1
JOIN classes c
    ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
ORDER BY c.age, e.nom;

-- recuperer annee d'exercice
SELECT 
    id,
    EXTRACT(YEAR FROM annee) AS annee_exercice
FROM annee_exercice;


-- recuperer les enfants non inscrits dans une classe donnée pour une année d’exercice donnée
SELECT
    e.*
FROM enfants e
JOIN annee_exercice ae ON ae.id = 1
JOIN classes c
    ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
WHERE c.id = 2 AND e.id NOT IN (
    SELECT ins.enfant_id
    FROM inscriptions ins
    WHERE ins.annee_exercice_id = ae.id
);


-- calculer l’âge de tous les enfants par rapport à une année d’exercice et classe donnée (info complet)
SELECT
    -- Classe & année
    c.id           AS classe_id,
    c.nom          AS classe_nom,
    c.age          AS classe_age,
    EXTRACT(YEAR FROM ae.annee) AS annee_exercice,

    -- Enfant (toutes les infos)
    e.id           AS enfant_id,
    e.nom          AS enfant_nom,
    e.prenom       AS enfant_prenom,
    e.date_naissance,
    e.adresse      AS adresse_enfant,
    e.bapteme,

    -- Âge calculé
    date_part('year', age(ae.annee, e.date_naissance)) AS age,

    -- Parent (toutes les infos)
    p.id           AS parent_id,
    p.nom          AS parent_nom,
    p.prenom       AS parent_prenom,
    p.adresse      AS adresse_parent,
    p.telephone

FROM enfants e
JOIN parents p
    ON p.id = e.parent_id
JOIN annee_exercice ae
    ON ae.id = 1
JOIN classes c
    ON date_part('year', age(ae.annee, e.date_naissance)) = c.age
WHERE c.id = 3
ORDER BY e.nom, e.prenom;





SELECT
    -- Année
    ae.id                       AS annee_exercice_id,
    EXTRACT(YEAR FROM ae.annee) AS annee_exercice,

    -- Classe
    c.id                       AS classe_id,
    c.nom                      AS classe_nom,
    c.age                      AS classe_age,

    -- Enfant
    e.id                       AS enfant_id,
    e.nom                      AS enfant_nom,
    e.prenom                   AS enfant_prenom,
    e.date_naissance,
    e.adresse                  AS adresse_enfant,
    e.bapteme,

    -- Âge (informatif)
    date_part('year', age(ae.annee, e.date_naissance)) AS age,

    -- Parent
    p.id                       AS parent_id,
    p.nom                      AS parent_nom,
    p.prenom                   AS parent_prenom,
    p.telephone,

    -- Inscription
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
    ( :annee_exercice_id IS NULL OR i.annee_exercice_id = :annee_exercice_id )
AND ( :classe_id IS NULL OR i.classe_id = :classe_id )

ORDER BY ae.annee DESC, c.age ASC, e.nom, e.prenom;


