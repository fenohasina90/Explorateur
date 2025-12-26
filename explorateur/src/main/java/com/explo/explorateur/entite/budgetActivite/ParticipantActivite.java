package com.explo.explorateur.entite.budgetActivite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.explo.explorateur.entite.membre.Inscription;
import com.explo.explorateur.entite.membre.Staff;

@Entity
@Table(name = "participants_activites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantActivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfant_id")
    private Inscription inscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
