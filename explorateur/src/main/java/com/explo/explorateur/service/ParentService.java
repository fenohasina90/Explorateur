package com.explo.explorateur.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explo.explorateur.dto.ParentEnfantFlatDTO;
import com.explo.explorateur.dto.ParentListeDTO;
import com.explo.explorateur.entite.membre.ParentEntite;
import com.explo.explorateur.repository.membre.ParentEntiteRepository;

@Service
public class ParentService {
    private final ParentEntiteRepository parentEntiteRepository;

    @Autowired
    public ParentService(ParentEntiteRepository parentEntiteRepository) {
        this.parentEntiteRepository = parentEntiteRepository;
    }

    public void saveParent(String nom, String prenom, String adresse, String telephone) {
        ParentEntite parent = new ParentEntite();
        parent.setNom(nom.toUpperCase());
        parent.setPrenom(prenom);
        parent.setAdresse(adresse);
        parent.setTelephone(telephone);
        parentEntiteRepository.save(parent);
    }

    public List<ParentEntite> findAllParents() {
        return parentEntiteRepository.findAll();
    }

    public List<ParentListeDTO> getParentsListe(Integer anneeExerciceId, Integer classeId) {
        List<ParentEnfantFlatDTO> flats = parentEntiteRepository.findParentsEnfantsListe(anneeExerciceId, classeId);

        Map<Long, ParentListeDTO> map = new LinkedHashMap<>();

        for (ParentEnfantFlatDTO row : flats) {
            Long parentId = row.getParentId();
            ParentListeDTO parent = map.get(parentId);
            if (parent == null) {
                parent = new ParentListeDTO();
                parent.setParentId(parentId);
                parent.setParentNom(row.getParentNom());
                parent.setParentPrenom(row.getParentPrenom());
                parent.setTelephone(row.getTelephone());
                parent.setAdresseParent(row.getAdresseParent());
                map.put(parentId, parent);
            }

            // Construire la ligne enfant au format "Nom Prenom: 10 ans, JUNIOR 2024"
            StringBuilder enfantLine = new StringBuilder();
            if (row.getEnfantNom() != null) {
                enfantLine.append(row.getEnfantNom());
            }
            if (row.getEnfantPrenom() != null) {
                enfantLine.append(" ").append(row.getEnfantPrenom());
            }
            enfantLine.append(": ");

            boolean hasPrevious = false;
            if (row.getAgeEnfant() != null) {
                enfantLine.append(row.getAgeEnfant()).append(" ans");
                hasPrevious = true;
            }
            if (row.getClasseNom() != null) {
                if (hasPrevious) {
                    enfantLine.append(", ");
                }
                enfantLine.append(row.getClasseNom());
                hasPrevious = true;
            }
            if (row.getAnneeExercice() != null) {
                if (hasPrevious) {
                    enfantLine.append(" ");
                }
                enfantLine.append(row.getAnneeExercice());
            }

            parent.addEnfantLigne(enfantLine.toString());
        }

        return List.copyOf(map.values());
    }
}
