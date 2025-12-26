package com.explo.explorateur.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.explo.explorateur.dto.BudgetGlobalListeDTO;
import com.explo.explorateur.service.BudgetProgrammeService;

@Controller()
@RequestMapping("/budget-programme")
public class BudgetProgrammeController {

    private final BudgetProgrammeService budgetProgrammeService;

    public BudgetProgrammeController(BudgetProgrammeService budgetProgrammeService) {
        this.budgetProgrammeService = budgetProgrammeService;
    }

    @GetMapping("/prevision")
    public ModelAndView formulaireBudgetProgramme(){
        return new ModelAndView("budgetProgramme/prevision/budget");
    }

    @PostMapping("/prevision")
    public ModelAndView enregistrerBudgetPrevision(@ModelAttribute BudgetPrevisionForm form) {
        budgetProgrammeService.creerBudgetPrevision(form.getAnneeExerciceId(), form.getMontant(), form.getActivites());
        return new ModelAndView("redirect:/budget-programme/prevision");
    }

    @GetMapping("/liste")
    public ModelAndView listeBudgets(@RequestParam(value = "anneeExerciceId", required = false) Long anneeExerciceId) {
        ModelAndView mv = new ModelAndView("budgetProgramme/liste/budgets");
        var budgets = budgetProgrammeService.getBudgetsParAnnee(anneeExerciceId);
        mv.addObject("budgets", budgets);

        Integer anneeLabel = null;
        if (!budgets.isEmpty()) {
            anneeLabel = budgets.get(0).getAnnee();
        }
        mv.addObject("selectedAnneeId", anneeExerciceId);
        mv.addObject("selectedAnneeLabel", anneeLabel);
        return mv;
    }

    public static class BudgetPrevisionForm {
        private Long anneeExerciceId;
        private BigDecimal montant;
        private List<ActiviteForm> activites = new ArrayList<>();

        public Long getAnneeExerciceId() {
            return anneeExerciceId;
        }

        public void setAnneeExerciceId(Long anneeExerciceId) {
            this.anneeExerciceId = anneeExerciceId;
        }

        public BigDecimal getMontant() {
            return montant;
        }

        public void setMontant(BigDecimal montant) {
            this.montant = montant;
        }

        public List<ActiviteForm> getActivites() {
            return activites;
        }

        public void setActivites(List<ActiviteForm> activites) {
            this.activites = activites;
        }

        public static class ActiviteForm {
            private String nom;
            private String description;
            private LocalDate dateActivite;
            private BigDecimal cout;
            private List<DetailForm> details = new ArrayList<>();

            public String getNom() {
                return nom;
            }

            public void setNom(String nom) {
                this.nom = nom;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public LocalDate getDateActivite() {
                return dateActivite;
            }

            public void setDateActivite(LocalDate dateActivite) {
                this.dateActivite = dateActivite;
            }

            public BigDecimal getCout() {
                return cout;
            }

            public void setCout(BigDecimal cout) {
                this.cout = cout;
            }

            public List<DetailForm> getDetails() {
                return details;
            }

            public void setDetails(List<DetailForm> details) {
                this.details = details;
            }

            public static class DetailForm {
                private String details;
                private BigDecimal montant;

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }

                public BigDecimal getMontant() {
                    return montant;
                }

                public void setMontant(BigDecimal montant) {
                    this.montant = montant;
                }
            }
        }
    }
}
