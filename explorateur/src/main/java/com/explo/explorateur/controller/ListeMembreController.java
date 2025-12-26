package com.explo.explorateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.explo.explorateur.service.ClasseService;
import com.explo.explorateur.service.EnfantService;
import com.explo.explorateur.service.ParentService;
import com.explo.explorateur.service.RolesStaffService;
import com.explo.explorateur.service.StaffService;

@Controller
@RequestMapping("/liste")
public class ListeMembreController {

    private final EnfantService enfantService;
    private final ClasseService classeService;
    private final StaffService staffService;
    private final RolesStaffService rolesStaffService;
    private final ParentService parentService;

    @Autowired
    public ListeMembreController(EnfantService enfantService,
                                ClasseService classeService,
                                StaffService staffService,
                                RolesStaffService rolesStaffService,
                                ParentService parentService) {
        this.enfantService = enfantService;
        this.classeService = classeService;
        this.staffService = staffService;
        this.rolesStaffService = rolesStaffService;
        this.parentService = parentService;
    }

    @GetMapping("/explorateurs")
    public ModelAndView getListeMembres(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                                        @RequestParam(value = "classeId", required = false) Integer classeId) {
        ModelAndView mv = new ModelAndView("explo/liste/enfants");
        mv.addObject("classes", classeService.getAllClasses());
        mv.addObject("anneeExerciceId", anneeExerciceId);
        mv.addObject("classeId", classeId);
        mv.addObject("enfants", enfantService.getListeEnfants(anneeExerciceId, classeId));
        return mv;
    }

    @GetMapping("/staffs")
    public ModelAndView getListeStaff(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                                        @RequestParam(value = "roleId", required = false) Integer roleId) {
        ModelAndView mv = new ModelAndView("explo/liste/staff");
        mv.addObject("roles", rolesStaffService.getAllRoles());
        mv.addObject("anneeExerciceId", anneeExerciceId);
        mv.addObject("roleId", roleId);
        mv.addObject("staffs", staffService.getListeStaff(anneeExerciceId, roleId));
        return mv;
    }

    @GetMapping("/parents")
    public ModelAndView getListeParents(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                                        @RequestParam(value = "classeId",        required = false) Integer classeId) {
        ModelAndView mv = new ModelAndView("explo/liste/parents");
        mv.addObject("classes", classeService.getAllClasses());
        mv.addObject("anneeExerciceId", anneeExerciceId);
        mv.addObject("classeId", classeId);
        mv.addObject("parents", parentService.getParentsListe(anneeExerciceId, classeId));
        return mv;
    }
}
