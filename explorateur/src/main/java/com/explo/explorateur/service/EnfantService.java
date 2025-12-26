package com.explo.explorateur.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explo.explorateur.dto.EnfantSimpleDTO;
import com.explo.explorateur.entite.membre.Enfant;
import com.explo.explorateur.dto.EnfantListeDTO;
import com.explo.explorateur.repository.membre.EnfantRepository;
import com.explo.explorateur.repository.membre.ParentEntiteRepository;

@Service
public class EnfantService {
    private final EnfantRepository enfantRepository;
    private final ParentEntiteRepository parentEntiteRepository;

    @Autowired
    public EnfantService(EnfantRepository enfantRepository, ParentEntiteRepository parentEntiteRepository) {
        this.enfantRepository = enfantRepository;
        this.parentEntiteRepository = parentEntiteRepository;
    }

    public Enfant saveEnfant(String nom, String prenom, String adresse, LocalDate dateNaissance, LocalDate bapteme, Integer parent_id, String genre) {
        Enfant enfant = new Enfant();
        enfant.setAdresse(adresse);
        enfant.setBapteme(bapteme);
        enfant.setDateNaissance(dateNaissance);
        enfant.setNom(nom.toUpperCase());
        enfant.setPrenom(prenom);
        enfant.setGenre(genre);
        enfant.setCreatedAt(LocalDateTime.now());
        enfant.setParent(parentEntiteRepository.findById(Long.valueOf(parent_id)).orElse(null));
        return enfantRepository.save(enfant);
    }

    public List<Enfant> getAllEnfantsNonInscrit(Integer anneeExerciceId, Integer classeId) {
        return enfantRepository.findEnfantsNonInscrit(anneeExerciceId, classeId);
    }

    public List<EnfantSimpleDTO> getAllEnfantsNonInscritDto(Integer anneeExerciceId, Integer classeId) {
        return enfantRepository.findEnfantsNonInscrit(anneeExerciceId, classeId)
                .stream()
                .map(e -> new EnfantSimpleDTO(e.getId(), e.getNom(), e.getPrenom()))
                .collect(Collectors.toList());
    }

    public List<EnfantListeDTO> getListeEnfants(Integer anneeExerciceId, Integer classeId) {
        return enfantRepository.findEnfantsListe(anneeExerciceId, classeId);
    }
}
