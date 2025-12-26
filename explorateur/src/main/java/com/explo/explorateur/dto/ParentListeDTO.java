package com.explo.explorateur.dto;

import java.util.ArrayList;
import java.util.List;

public class ParentListeDTO {
    private Long parentId;
    private String parentNom;
    private String parentPrenom;
    private String telephone;
    private String adresseParent;

    private List<String> enfantsLignes = new ArrayList<>();

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getParentNom() { return parentNom; }
    public void setParentNom(String parentNom) { this.parentNom = parentNom; }

    public String getParentPrenom() { return parentPrenom; }
    public void setParentPrenom(String parentPrenom) { this.parentPrenom = parentPrenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresseParent() { return adresseParent; }
    public void setAdresseParent(String adresseParent) { this.adresseParent = adresseParent; }

    public List<String> getEnfantsLignes() { return enfantsLignes; }
    public void setEnfantsLignes(List<String> enfantsLignes) { this.enfantsLignes = enfantsLignes; }

    public void addEnfantLigne(String ligne) {
        this.enfantsLignes.add(ligne);
    }

    public int getNbEnfants() {
        return enfantsLignes.size();
    }

    public String getEnfantsResume() {
        return String.join("\n", enfantsLignes);
    }
}
