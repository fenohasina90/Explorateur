package com.explo.explorateur.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_programmes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueProgramme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_id")
    private Programme programme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_progressive_id")
    private ClasseProgressive classeProgressive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ProgrammeStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
