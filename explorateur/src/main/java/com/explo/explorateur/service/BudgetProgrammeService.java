package com.explo.explorateur.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.explo.explorateur.controller.BudgetProgrammeController.BudgetPrevisionForm.ActiviteForm;
import com.explo.explorateur.controller.BudgetProgrammeController.BudgetPrevisionForm.ActiviteForm.DetailForm;
import com.explo.explorateur.dto.BudgetActiviteDTO;
import com.explo.explorateur.dto.BudgetActiviteDetailDTO;
import com.explo.explorateur.dto.BudgetGlobalListeDTO;
import com.explo.explorateur.entite.budgetActivite.Activite;
import com.explo.explorateur.entite.budgetActivite.BudgetGlobal;
import com.explo.explorateur.entite.budgetActivite.DetailsActivite;
import com.explo.explorateur.entite.constant.ActiviteStatus;
import com.explo.explorateur.entite.constant.AnneeExercice;
import com.explo.explorateur.repository.budgetActivite.ActiviteRepository;
import com.explo.explorateur.repository.budgetActivite.BudgetGlobalRepository;
import com.explo.explorateur.repository.budgetActivite.DetailsActiviteRepository;
import com.explo.explorateur.repository.constant.ActiviteStatusRepository;
import com.explo.explorateur.repository.constant.AnneeExerciceRepository;

@Service
public class BudgetProgrammeService {

    private final BudgetGlobalRepository budgetGlobalRepository;
    private final ActiviteRepository activiteRepository;
    private final DetailsActiviteRepository detailsActiviteRepository;
    private final ActiviteStatusRepository activiteStatusRepository;
    private final AnneeExerciceRepository anneeExerciceRepository;

    public BudgetProgrammeService(BudgetGlobalRepository budgetGlobalRepository,
                                  ActiviteRepository activiteRepository,
                                  DetailsActiviteRepository detailsActiviteRepository,
                                  ActiviteStatusRepository activiteStatusRepository,
                                  AnneeExerciceRepository anneeExerciceRepository) {
        this.budgetGlobalRepository = budgetGlobalRepository;
        this.activiteRepository = activiteRepository;
        this.detailsActiviteRepository = detailsActiviteRepository;
        this.activiteStatusRepository = activiteStatusRepository;
        this.anneeExerciceRepository = anneeExerciceRepository;
    }

    @Transactional
    public void creerBudgetPrevision(Long anneeExerciceId, BigDecimal montantBudget, List<ActiviteForm> activitesForm) {
        if (anneeExerciceId == null) {
            throw new IllegalArgumentException("anneeExerciceId manquant");
        }

        AnneeExercice anneeExercice = anneeExerciceRepository.findById(anneeExerciceId)
                .orElseThrow(() -> new IllegalArgumentException("AnneeExercice introuvable: " + anneeExerciceId));

        if (activitesForm == null || activitesForm.isEmpty()) {
            throw new IllegalArgumentException("Au moins une activite est requise");
        }

        double montantEffectif = montantBudget != null ? montantBudget.doubleValue() : 0.0;
        if (montantBudget == null) {
            montantEffectif = activitesForm.stream()
                    .mapToDouble(a -> a.getCout() != null ? a.getCout().doubleValue() : 0.0)
                    .sum();
        }

        BudgetGlobal budgetGlobal = new BudgetGlobal();
        budgetGlobal.setAnneeExercice(anneeExercice);
        budgetGlobal.setMontant(montantEffectif);
        budgetGlobal.setCreatedAt(LocalDateTime.now());
        budgetGlobal = budgetGlobalRepository.save(budgetGlobal);

        ActiviteStatus statutDefaut = activiteStatusRepository.findByStatus("en attente").orElse(null);

        for (ActiviteForm activiteForm : activitesForm) {
            if (activiteForm == null) {
                continue;
            }

            Activite activite = new Activite();
            activite.setNom(activiteForm.getNom());
            activite.setDescription(activiteForm.getDescription());
            LocalDate date = activiteForm.getDateActivite();
            activite.setDateActivite(date);

            double cout = 0.0;
            if (activiteForm.getCout() != null) {
                cout = activiteForm.getCout().doubleValue();
            } else if (activiteForm.getDetails() != null) {
                cout = activiteForm.getDetails().stream()
                        .mapToDouble(d -> d.getMontant() != null ? d.getMontant().doubleValue() : 0.0)
                        .sum();
            }
            activite.setCout(cout);
            activite.setBudget(budgetGlobal);
            activite.setStatus(statutDefaut);
            activite.setCreatedAt(LocalDateTime.now());
            activite.setUpdatedAt(LocalDateTime.now());
            activite = activiteRepository.save(activite);

            if (activiteForm.getDetails() != null) {
                for (DetailForm detailForm : activiteForm.getDetails()) {
                    if (detailForm == null) {
                        continue;
                    }
                    DetailsActivite detailsActivite = new DetailsActivite();
                    detailsActivite.setActivite(activite);
                    detailsActivite.setDetails(detailForm.getDetails());
                    detailsActivite.setMontant(detailForm.getMontant() != null ? detailForm.getMontant().doubleValue() : 0.0);
                    detailsActivite.setCreatedAt(LocalDateTime.now());
                    detailsActiviteRepository.save(detailsActivite);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<BudgetGlobalListeDTO> getBudgetsParAnnee(Long anneeExerciceId) {
        List<BudgetGlobal> tousBudgets = budgetGlobalRepository.findAll();
        if (tousBudgets.isEmpty()) {
            return List.of();
        }

        // Déterminer l'année par défaut si non fournie : dernière année connue
        Long cibleAnneeId = anneeExerciceId;
        if (cibleAnneeId == null) {
            cibleAnneeId = tousBudgets.stream()
                    .map(BudgetGlobal::getAnneeExercice)
                    .filter(ae -> ae != null && ae.getId() != null)
                    .max((a1, a2) -> a1.getAnnee().compareTo(a2.getAnnee()))
                    .map(AnneeExercice::getId)
                    .orElse(tousBudgets.get(0).getAnneeExercice().getId());
        }

        Long finalCibleAnneeId = cibleAnneeId;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return tousBudgets.stream()
                .filter(bg -> bg.getAnneeExercice() != null
                        && bg.getAnneeExercice().getId() != null
                        && bg.getAnneeExercice().getId().equals(finalCibleAnneeId))
                .map(bg -> {
                    BudgetGlobalListeDTO dto = new BudgetGlobalListeDTO();
                    dto.setBudgetId(bg.getId());
                    dto.setAnneeExerciceId(bg.getAnneeExercice().getId());
                    if (bg.getAnneeExercice().getAnnee() != null) {
                        dto.setAnneeDate(bg.getAnneeExercice().getAnnee());
                        dto.setAnnee(bg.getAnneeExercice().getAnnee().getYear());
                    }
                    dto.setMontant(bg.getMontant());

                    List<Activite> activites = activiteRepository.findByBudget(bg);
                    for (Activite act : activites) {
                        BudgetActiviteDTO aDto = new BudgetActiviteDTO();
                        aDto.setNom(act.getNom());
                        aDto.setDescription(act.getDescription());
                        if (act.getDateActivite() != null) {
                            aDto.setDateActivite(act.getDateActivite().format(dateFormatter));
                        }
                        aDto.setCout(act.getCout());
                        if (act.getStatus() != null) {
                            aDto.setStatus(act.getStatus().getStatus());
                        }

                        List<DetailsActivite> details = detailsActiviteRepository.findByActivite(act);
                        for (DetailsActivite d : details) {
                            BudgetActiviteDetailDTO dDto = new BudgetActiviteDetailDTO();
                            dDto.setDetails(d.getDetails());
                            dDto.setMontant(d.getMontant());
                            aDto.getDetails().add(dDto);
                        }

                        dto.getActivites().add(aDto);
                    }

                    return dto;
                })
                .toList();
    }
}
