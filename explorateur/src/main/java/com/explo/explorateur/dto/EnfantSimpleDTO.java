package com.explo.explorateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnfantSimpleDTO {
    private Long id;
    private String nom;
    private String prenom;
}
