package com.explo.explorateur.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.explo.explorateur.service.*;

@Controller
@RequestMapping("/inscription") 
public class InscriptionController {
    private final ParentService parentService;
    private final ClasseService classeService;
    private final AnneeExerciceService anneeExerciceService;
    private final InstructeurService instructeurService;
    private final RolesStaffService rolesStaffService;
    private final StaffService staffService;
    private final InscriptionService inscriptionService;

    public InscriptionController(ParentService parentService, EnfantService enfantService, 
                                ClasseService classeService, AnneeExerciceService anneeExerciceService,
                                InstructeurService instructeurService, RolesStaffService rolesStaffService,
                                StaffService staffService, InscriptionService inscriptionService) {
        this.parentService = parentService;
        this.classeService = classeService;
        this.anneeExerciceService = anneeExerciceService;
        this.instructeurService = instructeurService;
        this.rolesStaffService = rolesStaffService;
        this.staffService = staffService;
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/parent")
    public ModelAndView formulaireInscriptionParent() {
        return new ModelAndView("explo/inscription/parent");
    }

    @PostMapping("/parent")
    public ModelAndView traitementInscriptionParent(@RequestParam("nom") String nom,
                                                    @RequestParam("prenom") String prenom,
                                                    @RequestParam("adresse") String adresse,
                                                    @RequestParam("telephone") String telephone) {
        parentService.saveParent(nom, prenom, adresse, telephone);
        return new ModelAndView("redirect: /inscription/parent");
    }

    @GetMapping("/explorateur")
    public ModelAndView formulaireInscriptionEnfant() {
        ModelAndView modelAndView = new ModelAndView("explo/inscription/enfant");
        modelAndView.addObject("classes", classeService.getAllClasses());
        modelAndView.addObject("annees", anneeExerciceService.getAllAnneeExercice());
        return modelAndView;
    }

    @PostMapping("/explorateur")
    public ModelAndView traitementInscriptionEnfant(@RequestParam("nom") String nom,
                                                    @RequestParam("prenom") String prenom,
                                                    @RequestParam("adresse") String adresse,
                                                    @RequestParam("genre") String genre,
                                                    @RequestParam("dateNaissance") LocalDate dateNaissance,
                                                    @RequestParam(value = "bapteme", required = false) LocalDate bapteme,
                                                    @RequestParam("parentId") Integer parentId,
                                                    @RequestParam("classeId") Integer classeId,
                                                    @RequestParam("anneeExerciceId") Integer anneeExerciceId) {
        
        try {
            inscriptionService.saveInscriptionEnfant(nom, prenom, adresse, dateNaissance, bapteme, parentId, classeId, anneeExerciceId, genre);
            return new ModelAndView("redirect: /inscription/explorateur");
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("explo/inscription/enfant");
            modelAndView.addObject("classes", classeService.getAllClasses());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
        
    }

    @GetMapping("/classe")
    public ModelAndView formulaireInscriptionClasse() {
        ModelAndView modelAndView = new ModelAndView("explo/inscription/enfant-classe");
        modelAndView.addObject("classes", classeService.getAllClasses());
        return modelAndView;
    }

    @PostMapping("/classe")
    public ModelAndView traitementInscriptionClasse(@RequestParam("enfantId") Integer enfantId,
                                                    @RequestParam("classeId") Integer classeId,
                                                    @RequestParam("anneeExerciceId") Integer anneeExerciceId) {
        inscriptionService.saveInscription(enfantId, classeId, anneeExerciceId);
        return new ModelAndView("redirect: /inscription/classe");
    }

    @GetMapping("/instructeur")
    public ModelAndView formulaireInscriptionInstructeur() {
        return new ModelAndView("explo/inscription/instructeur");
    }

    @PostMapping("/instructeur")
    public ModelAndView traitementInscriptionInstructeur(@RequestParam("nom") String nom,
                                                        @RequestParam("prenom") String prenom,
                                                        @RequestParam("telephone") String telephone) {
        instructeurService.saveInstructeur(nom, prenom, telephone);
        return new ModelAndView("redirect: /inscription/instructeur");
    }   

    @GetMapping("/staff")
    public ModelAndView formulaireInscriptionStaff() {
        ModelAndView modelAndView = new ModelAndView("explo/inscription/staff");
        modelAndView.addObject("roles", rolesStaffService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/staff")
    public ModelAndView traitementInscriptionStaff(@RequestParam("instructeurId") Integer instructeurId,
                                                @RequestParam("anneeExerciceId") Integer anneeExerciceId,
                                                @RequestParam("roleId") Integer roleId) {
        staffService.saveStaff(instructeurId, anneeExerciceId, roleId);
        return new ModelAndView("redirect: /inscription/staff");
    }
}
