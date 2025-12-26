package com.explo.explorateur.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.explo.explorateur.entite.constant.AnneeExercice;
import com.explo.explorateur.dto.EnfantSimpleDTO;
import com.explo.explorateur.entite.membre.Instructeur;
import com.explo.explorateur.entite.membre.ParentEntite;
import com.explo.explorateur.service.AnneeExerciceService;
import com.explo.explorateur.service.EnfantService;
import com.explo.explorateur.service.InstructeurService;
import com.explo.explorateur.service.ParentService;

@RestController
@RequestMapping("/api/inscription")
public class InscriptionRestController {
    private final ParentService parentService;
    private final EnfantService enfantService;
    private final InstructeurService instructeurService;
    private final AnneeExerciceService anneeExerciceService;

    @Autowired
    public InscriptionRestController(ParentService parentService, EnfantService enfantService, InstructeurService instructeurService, AnneeExerciceService anneeExerciceService) {
        this.parentService = parentService;
        this.enfantService = enfantService;
        this.instructeurService = instructeurService;
        this.anneeExerciceService = anneeExerciceService;
    }

    @GetMapping("/annee-exercice")
    public List<AnneeExercice> getAnneeExercice() {
        return anneeExerciceService.getAllAnneeExercice();
    }
    
    @GetMapping("/parents")
    public List<ParentEntite> getParent() {
        return parentService.findAllParents();
    }
    
    @GetMapping("/enfants")
    public List<EnfantSimpleDTO> getEnfants(@RequestParam("anneeExerciceId") Integer anneeExerciceId,
                                            @RequestParam("classeId") Integer classeId) {
        return enfantService.getAllEnfantsNonInscritDto(anneeExerciceId, classeId);
    }
    
    @GetMapping("/instructeurs")
    public List<Instructeur> getInstructeurs() {
        return instructeurService.getAllInstructeurs();
    }
}
