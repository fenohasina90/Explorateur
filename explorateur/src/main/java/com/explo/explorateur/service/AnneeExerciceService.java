package com.explo.explorateur.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.explo.explorateur.entite.constant.AnneeExercice;
import com.explo.explorateur.repository.constant.AnneeExerciceRepository;

@Service
public class AnneeExerciceService {
    private final AnneeExerciceRepository anneeExerciceRepository;

    @Autowired
    public AnneeExerciceService(AnneeExerciceRepository anneeExerciceRepository) {
        this.anneeExerciceRepository = anneeExerciceRepository;
    }

    public void saveAnneeExercice(Integer annee) {
        AnneeExercice anneeExercice = new AnneeExercice();
        anneeExercice.setAnnee(LocalDate.parse(annee + "-12-31"));
        anneeExercice.setCreatedAt(LocalDateTime.now());
        anneeExerciceRepository.save(anneeExercice);
    }

    public List<AnneeExercice> getAllAnneeExercice() {
        return anneeExerciceRepository.findAll();
    }
}
