package com.explo.explorateur.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.explo.explorateur.dto.EnfantListeDTO;
import com.explo.explorateur.dto.StaffListeDTO;
import com.explo.explorateur.dto.ParentListeDTO;
import com.explo.explorateur.service.EnfantService;
import com.explo.explorateur.service.ParentService;
import com.explo.explorateur.service.StaffService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/export")
public class ExportController {

    private final EnfantService enfantService;
    private final StaffService staffService;
    private final ParentService parentService;

    @Autowired
    public ExportController(EnfantService enfantService, StaffService staffService, ParentService parentService) {
        this.enfantService = enfantService;
        this.staffService = staffService;
        this.parentService = parentService;
    }

    @GetMapping("/staff")
    public void exportStaffPdf(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                               @RequestParam(value = "roleId", required = false) Integer roleId,
                               @RequestParam(value = "cols", required = false) List<String> cols,
                               @RequestParam(value = "anneeLabel", required = false) String anneeLabel,
                               @RequestParam(value = "roleLabel", required = false) String roleLabel,
                               HttpServletResponse response) throws IOException {

        List<StaffListeDTO> staffs = staffService.getListeStaff(anneeExerciceId, roleId);

        Set<String> columns = new HashSet<>();
        if (cols == null || cols.isEmpty()) {
            columns.add("annee");
            columns.add("role");
            columns.add("nom");
            columns.add("prenom");
            columns.add("telephone");
            columns.add("dateCreation");
        } else {
            columns.addAll(cols);
        }

        String html = buildHtmlStaff(staffs, columns, anneeLabel, roleLabel);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=liste_staff.pdf");

        ConverterProperties props = new ConverterProperties();
        props.setBaseUri("http://localhost:8081/");

        HtmlConverter.convertToPdf(html, response.getOutputStream(), props);
    }

    @GetMapping("/parents")
    public void exportParentsPdf(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                                 @RequestParam(value = "classeId",        required = false) Integer classeId,
                                 @RequestParam(value = "cols",            required = false) List<String> cols,
                                 @RequestParam(value = "anneeLabel",      required = false) String anneeLabel,
                                 @RequestParam(value = "classeLabel",     required = false) String classeLabel,
                                 HttpServletResponse response) throws IOException {

        List<ParentListeDTO> parents = parentService.getParentsListe(anneeExerciceId, classeId);

        Set<String> columns = new HashSet<>();
        if (cols == null || cols.isEmpty()) {
            columns.add("nom");
            columns.add("prenom");
            columns.add("telephone");
            columns.add("adresse");
            columns.add("nbEnfants");
            columns.add("enfants");
        } else {
            columns.addAll(cols);
        }

        String html = buildHtmlParents(parents, columns, anneeLabel, classeLabel);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=liste_parents.pdf");

        ConverterProperties props = new ConverterProperties();
        props.setBaseUri("http://localhost:8081/");

        HtmlConverter.convertToPdf(html, response.getOutputStream(), props);
    }

    @GetMapping("/enfants")
    public void exportEnfantsPdf(@RequestParam(value = "anneeExerciceId", required = false) Integer anneeExerciceId,
                                 @RequestParam(value = "classeId", required = false) Integer classeId,
                                 @RequestParam(value = "cols", required = false) List<String> cols,
                                 @RequestParam(value = "anneeLabel", required = false) String anneeLabel,
                                 @RequestParam(value = "classeLabel", required = false) String classeLabel,
                                 HttpServletResponse response) throws IOException {

        List<EnfantListeDTO> enfants = enfantService.getListeEnfants(anneeExerciceId, classeId);

        Set<String> columns = new HashSet<>();
        if (cols == null || cols.isEmpty()) {
            columns.add("annee");
            columns.add("classe");
            columns.add("nom");
            columns.add("prenom");
            columns.add("age");
            columns.add("adresse");
            columns.add("parent");
        } else {
            columns.addAll(cols);
        }

        String html = buildHtml(enfants, columns, anneeLabel, classeLabel);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=liste_enfants.pdf");

        // Utilisation d'une baseUri pour que les chemins relatifs (logo) soient résolus correctement
        ConverterProperties props = new ConverterProperties();
        props.setBaseUri("http://localhost:8081/");

        HtmlConverter.convertToPdf(html, response.getOutputStream(), props);
    }

    private String buildHtml(List<EnfantListeDTO> enfants, Set<String> columns, String anneeLabel, String classeLabel) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<meta charset='UTF-8'>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; font-size: 11px; }");
        sb.append("h1 { margin: 0; font-size: 18px; }");
        sb.append("h2 { margin: 4px 0 0 0; font-size: 12px; color: #555; }");
        sb.append(".header { display: flex; align-items: center; margin-bottom: 10px; }");
        sb.append(".header-logo { margin-right: 10px; }");
        sb.append(".header-logo img { height: 40px; }");
        sb.append(".header-text { flex: 1; text-align: center; }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        sb.append("th, td { border: 1px solid #ccc; padding: 4px 6px; }");
        sb.append("th { background-color: #f2f2f2; }");
        sb.append(".footer { margin-top: 15px; font-size: 10px; text-align: right; color: #555; }");
        sb.append("</style>");
        sb.append("</head><body>");

        // En-tête avec logo + titre dynamique
        sb.append("<div class='header'>");
        // chemin relatif, résolu via baseUri
        sb.append("<div class='header-logo'><img src='app-assets/images/logo/logo.png' alt='Logo'></div>");
        sb.append("<div class='header-text'>");
        sb.append("<h1>Liste des enfants inscrits</h1>");
        // Sous-titre dynamique en fonction des filtres
        String ann = (anneeLabel == null ? "" : anneeLabel.trim());
        String cls = (classeLabel == null ? "" : classeLabel.trim());

        String subtitleText;
        if (ann.isEmpty() && cls.isEmpty()) {
            subtitleText = "Tous les enfants inscrits (toutes années, toutes classes)";
        } else if (!ann.isEmpty() && cls.isEmpty()) {
            subtitleText = "Année : " + escapeHtml(ann) + " (toutes classes)";
        } else if (ann.isEmpty() && !cls.isEmpty()) {
            subtitleText = "Classe : " + escapeHtml(cls) + " (toutes années)";
        } else {
            subtitleText = "Année : " + escapeHtml(ann) + " &nbsp; | &nbsp; Classe : " + escapeHtml(cls);
        }

        if (subtitleText != null && !subtitleText.isEmpty()) {
            sb.append("<h2>").append(subtitleText).append("</h2>");
        }

        sb.append("</div></div>");

        sb.append("<table><thead><tr>");
        if (columns.contains("annee")) {
            sb.append("<th>Année</th>");
        }
        if (columns.contains("classe")) {
            sb.append("<th>Classe</th>");
        }
        if (columns.contains("nom")) {
            sb.append("<th>Nom</th>");
        }
        if (columns.contains("prenom")) {
            sb.append("<th>Prénom</th>");
        }
        if (columns.contains("age")) {
            sb.append("<th>Âge</th>");
        }
        if (columns.contains("adresse")) {
            sb.append("<th>Adresse</th>");
        }
        if (columns.contains("parent")) {
            sb.append("<th>Parent</th>");
        }
        sb.append("</tr></thead><tbody>");

        for (EnfantListeDTO e : enfants) {
            sb.append("<tr>");
            if (columns.contains("annee")) {
                sb.append("<td>").append(nullSafe(e.getAnneeExercice())).append("</td>");
            }
            if (columns.contains("classe")) {
                sb.append("<td>").append(nullSafe(e.getClasseNom())).append("</td>");
            }
            if (columns.contains("nom")) {
                sb.append("<td>").append(nullSafe(e.getEnfantNom())).append("</td>");
            }
            if (columns.contains("prenom")) {
                sb.append("<td>").append(nullSafe(e.getEnfantPrenom())).append("</td>");
            }
            if (columns.contains("age")) {
                sb.append("<td>").append(nullSafe(e.getAge())).append("</td>");
            }
            if (columns.contains("adresse")) {
                sb.append("<td>").append(nullSafe(e.getAdresseEnfant())).append("</td>");
            }
            if (columns.contains("parent")) {
                String parent = (nullSafe(e.getParentNom()) + " " + nullSafe(e.getParentPrenom())).trim();
                sb.append("<td>").append(parent).append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</tbody></table>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String exportDate = LocalDateTime.now().format(formatter);
        sb.append("<div class='footer'>Date d'export : ").append(exportDate).append("</div>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private String buildHtmlStaff(List<StaffListeDTO> staffs, Set<String> columns, String anneeLabel, String roleLabel) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<meta charset='UTF-8'>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; font-size: 11px; }");
        sb.append("h1 { margin: 0; font-size: 18px; }");
        sb.append("h2 { margin: 4px 0 0 0; font-size: 12px; color: #555; }");
        sb.append(".header { display: flex; align-items: center; margin-bottom: 10px; }");
        sb.append(".header-logo { margin-right: 10px; }");
        sb.append(".header-logo img { height: 40px; }");
        sb.append(".header-text { flex: 1; text-align: center; }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        sb.append("th, td { border: 1px solid #ccc; padding: 4px 6px; }");
        sb.append("th { background-color: #f2f2f2; }");
        sb.append(".footer { margin-top: 15px; font-size: 10px; text-align: right; color: #555; }");
        sb.append("</style>");
        sb.append("</head><body>");

        sb.append("<div class='header'>");
        sb.append("<div class='header-logo'><img src='app-assets/images/logo/logo.png' alt='Logo'></div>");
        sb.append("<div class='header-text'>");
        sb.append("<h1>Liste des staffs</h1>");

        String ann = (anneeLabel == null ? "" : anneeLabel.trim());
        String role = (roleLabel == null ? "" : roleLabel.trim());

        String subtitleText;
        if (ann.isEmpty() && role.isEmpty()) {
            subtitleText = "Tous les staffs (toutes années, tous rôles)";
        } else if (!ann.isEmpty() && role.isEmpty()) {
            subtitleText = "Année : " + escapeHtml(ann) + " (tous rôles)";
        } else if (ann.isEmpty() && !role.isEmpty()) {
            subtitleText = "Rôle : " + escapeHtml(role) + " (toutes années)";
        } else {
            subtitleText = "Année : " + escapeHtml(ann) + " &nbsp; | &nbsp; Rôle : " + escapeHtml(role);
        }

        if (subtitleText != null && !subtitleText.isEmpty()) {
            sb.append("<h2>").append(subtitleText).append("</h2>");
        }

        sb.append("</div></div>");

        sb.append("<table><thead><tr>");
        if (columns.contains("annee")) {
            sb.append("<th>Année</th>");
        }
        if (columns.contains("role")) {
            sb.append("<th>Rôle</th>");
        }
        if (columns.contains("nom")) {
            sb.append("<th>Nom</th>");
        }
        if (columns.contains("prenom")) {
            sb.append("<th>Prénom</th>");
        }
        if (columns.contains("telephone")) {
            sb.append("<th>Téléphone</th>");
        }
        if (columns.contains("dateCreation")) {
            sb.append("<th>Date création staff</th>");
        }
        sb.append("</tr></thead><tbody>");

        for (StaffListeDTO s : staffs) {
            sb.append("<tr>");
            if (columns.contains("annee")) {
                sb.append("<td>").append(nullSafe(s.getAnneeExercice())).append("</td>");
            }
            if (columns.contains("role")) {
                sb.append("<td>").append(nullSafe(s.getRoleNom())).append("</td>");
            }
            if (columns.contains("nom")) {
                sb.append("<td>").append(nullSafe(s.getInstructeurNom())).append("</td>");
            }
            if (columns.contains("prenom")) {
                sb.append("<td>").append(nullSafe(s.getInstructeurPrenom())).append("</td>");
            }
            if (columns.contains("telephone")) {
                sb.append("<td>").append(nullSafe(s.getTelephone())).append("</td>");
            }
            if (columns.contains("dateCreation")) {
                sb.append("<td>").append(nullSafe(s.getStaffCreatedAt())).append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</tbody></table>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String exportDate = LocalDateTime.now().format(formatter);
        sb.append("<div class='footer'>Date d'export : ").append(exportDate).append("</div>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private String buildHtmlParents(List<ParentListeDTO> parents, Set<String> columns, String anneeLabel, String classeLabel) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>");
        sb.append("<meta charset='UTF-8'>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; font-size: 11px; }");
        sb.append("h1 { margin: 0; font-size: 18px; }");
        sb.append("h2 { margin: 4px 0 0 0; font-size: 12px; color: #555; }");
        sb.append(".header { display: flex; align-items: center; margin-bottom: 10px; }");
        sb.append(".header-logo { margin-right: 10px; }");
        sb.append(".header-logo img { height: 40px; }");
        sb.append(".header-text { flex: 1; text-align: center; }");
        sb.append("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        sb.append("th, td { border: 1px solid #ccc; padding: 4px 6px; vertical-align: top; }");
        sb.append("th { background-color: #f2f2f2; }");
        sb.append(".footer { margin-top: 15px; font-size: 10px; text-align: right; color: #555; }");
        sb.append("</style>");
        sb.append("</head><body>");

        sb.append("<div class='header'>");
        sb.append("<div class='header-logo'><img src='app-assets/images/logo/logo.png' alt='Logo'></div>");
        sb.append("<div class='header-text'>");
        sb.append("<h1>Liste des parents</h1>");

        String ann = (anneeLabel == null ? "" : anneeLabel.trim());
        String cls = (classeLabel == null ? "" : classeLabel.trim());

        String subtitleText;
        if (ann.isEmpty() && cls.isEmpty()) {
            subtitleText = "Tous les parents (toutes années, toutes classes)";
        } else if (!ann.isEmpty() && cls.isEmpty()) {
            subtitleText = "Année : " + escapeHtml(ann) + " (toutes classes)";
        } else if (ann.isEmpty() && !cls.isEmpty()) {
            subtitleText = "Classe : " + escapeHtml(cls) + " (toutes années)";
        } else {
            subtitleText = "Année : " + escapeHtml(ann) + " &nbsp; | &nbsp; Classe : " + escapeHtml(cls);
        }

        if (subtitleText != null && !subtitleText.isEmpty()) {
            sb.append("<h2>").append(subtitleText).append("</h2>");
        }

        sb.append("</div></div>");

        sb.append("<table><thead><tr>");
        if (columns.contains("nom")) {
            sb.append("<th>Nom</th>");
        }
        if (columns.contains("prenom")) {
            sb.append("<th>Prénom</th>");
        }
        if (columns.contains("telephone")) {
            sb.append("<th>Téléphone</th>");
        }
        if (columns.contains("adresse")) {
            sb.append("<th>Adresse</th>");
        }
        if (columns.contains("nbEnfants")) {
            sb.append("<th>Nb enfants</th>");
        }
        if (columns.contains("enfants")) {
            sb.append("<th>Enfants</th>");
        }
        sb.append("</tr></thead><tbody>");

        for (ParentListeDTO p : parents) {
            sb.append("<tr>");
            if (columns.contains("nom")) {
                sb.append("<td>").append(escapeHtml(p.getParentNom())).append("</td>");
            }
            if (columns.contains("prenom")) {
                sb.append("<td>").append(escapeHtml(p.getParentPrenom())).append("</td>");
            }
            if (columns.contains("telephone")) {
                sb.append("<td>").append(escapeHtml(p.getTelephone())).append("</td>");
            }
            if (columns.contains("adresse")) {
                sb.append("<td>").append(escapeHtml(p.getAdresseParent())).append("</td>");
            }
            if (columns.contains("nbEnfants")) {
                sb.append("<td>").append(p.getNbEnfants()).append("</td>");
            }
            if (columns.contains("enfants")) {
                sb.append("<td>");
                for (String ligne : p.getEnfantsLignes()) {
                    sb.append(escapeHtml(ligne)).append("<br/>");
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</tbody></table>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String exportDate = LocalDateTime.now().format(formatter);
        sb.append("<div class='footer'>Date d'export : ").append(exportDate).append("</div>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private String nullSafe(Object value) {
        return value == null ? "" : value.toString();
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
