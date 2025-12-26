package com.explo.explorateur.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.explo.explorateur.entite.constant.Classe;
import com.explo.explorateur.entite.membre.Enfant;
import com.explo.explorateur.entite.membre.Inscription;
import com.explo.explorateur.repository.constant.AnneeExerciceRepository;
import com.explo.explorateur.repository.constant.ClasseRepository;
import com.explo.explorateur.repository.membre.EnfantRepository;
import com.explo.explorateur.repository.membre.InscriptionRepository;

@Service
public class InscriptionService {
    private final AnneeExerciceRepository anneeExerciceRepository;
    private final ClasseRepository classeRepository;
    private final EnfantService enfantService;
    private final EnfantRepository enfantRepository;
    private final InscriptionRepository inscriptionRepository;

    public InscriptionService(AnneeExerciceRepository anneeExerciceRepository, ClasseRepository classeRepository, EnfantService enfantService, InscriptionRepository inscriptionRepository, EnfantRepository enfantRepository) {
        this.anneeExerciceRepository = anneeExerciceRepository;
        this.classeRepository = classeRepository;
        this.enfantService = enfantService;
        this.enfantRepository = enfantRepository;
        this.inscriptionRepository = inscriptionRepository;
    }
    
    public void saveInscription(Integer enfantId, Integer classeId, Integer anneeExerciceId) {
        Inscription inscription = new Inscription();
        inscription.setEnfant(enfantRepository.findById(enfantId.longValue()).orElse(null));
        inscription.setClasse(classeRepository.findById(classeId.longValue()).orElse(null));
        inscription.setAnneeExercice(anneeExerciceRepository.findById(anneeExerciceId.longValue()).orElse(null));
        inscription.setCreatedAt(LocalDateTime.now());
        inscriptionRepository.save(inscription);
    }

    public void saveInscriptionEnfant(String nom, String prenom, String adresse, LocalDate dateNaissance, LocalDate bapteme, Integer parent_id, Integer classeId, Integer anneeExerciceId, String genre) {
        Inscription inscription = new Inscription();
        verifieAge(dateNaissance, classeId, anneeExerciceId);

        Enfant enfant = enfantService.saveEnfant(nom, prenom, adresse, dateNaissance, bapteme, parent_id, genre);
        inscription.setEnfant(enfant);
        inscription.setClasse(classeRepository.findById(classeId.longValue()).orElse(null));
        inscription.setAnneeExercice(anneeExerciceRepository.findById(anneeExerciceId.longValue()).orElse(null));
        inscription.setCreatedAt(LocalDateTime.now());
        inscriptionRepository.save(inscription);
    }

    private void verifieAge(LocalDate dateNaissance, Integer classeId, Integer anneeExerciceId) {
        Integer age = anneeExerciceRepository.getAgeEnfant(anneeExerciceId, dateNaissance);
        System.out.println("Age : " + age);

        if (age < 10) {
            throw new RuntimeException("L'enfant doit etre dans les <b>Aventuriers</b>");
        }

        if (age > 15 && age < 22) {
            throw new RuntimeException("Cette personne doit etre dans les <b>Ambassadeurs</b>");
        }

        if (age > 21) {
            throw new RuntimeException("Cette personne doit etre dans les <b>Jeunes adultes</b>");
        }
        Classe classe = classeRepository.findById(classeId.longValue()).orElse(null);
        Classe classeIdeal = classeRepository.findByAge(age);
        String classeIdealName = classeIdeal.getNom();

        System.out.println("Classe : " + classe.getNom());
        System.out.println("Classe Ideal : " + classeIdealName);

        if (age < classe.getAge()) {
            throw new RuntimeException("L'enfant est trop jeune pour cette classe, son classe ideal est : <b>" + classeIdealName+"</b>");
        } 
        
        if (age > classe.getAge()) {
            throw new RuntimeException("L'enfant est trop âgé pour cette classe, son classe ideal est : <b>" + classeIdealName + "</b>");
        }
    }
}
