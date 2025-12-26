package com.explo.explorateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.explo.explorateur.service.AnneeExerciceService;

@Controller
@RequestMapping("/annee-exercice")
public class AnneeExerciceController {
    private final AnneeExerciceService anneeExerciceService;

    @Autowired
    public AnneeExerciceController(AnneeExerciceService anneeExerciceService) {
        this.anneeExerciceService = anneeExerciceService;
    }

    @GetMapping()
    public ModelAndView formulaireAnneeExercice() {
        return new ModelAndView("explo/annee/exercice");
    }

    @PostMapping()
    public String ajouterAnneeExercice(@RequestParam("annee") Integer annee) {
        anneeExerciceService.saveAnneeExercice(annee);
        return "redirect:/annee-exercice";
    }
}
