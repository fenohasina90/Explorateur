package com.explo.explorateur.service;

import org.springframework.stereotype.Service;

import com.explo.explorateur.entite.membre.Instructeur;
import com.explo.explorateur.repository.membre.InstructeurRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class InstructeurService {
    private final InstructeurRepository instructeurRepository;
    
    @Autowired
    public InstructeurService(InstructeurRepository instructeurRepository) {
        this.instructeurRepository = instructeurRepository;
    }
    
    
    public void saveInstructeur(String nom, String prenom, String telephone) {
        Instructeur instructeur = new Instructeur();
        instructeur.setNom(nom.toUpperCase());
        instructeur.setPrenom(prenom);
        instructeur.setTelephone(telephone);
        instructeur.setCreatedAt(LocalDateTime.now());
        instructeurRepository.save(instructeur);
    }
    
    public List<Instructeur> getAllInstructeurs() {
        return instructeurRepository.findAll();
    }
}
