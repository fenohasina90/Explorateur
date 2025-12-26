package com.explo.explorateur.entite.constant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "programme_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
}
