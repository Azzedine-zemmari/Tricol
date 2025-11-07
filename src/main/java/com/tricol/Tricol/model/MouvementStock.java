package com.tricol.Tricol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tricol.Tricol.Enums.TypeMouvement;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "mouvement_stock")
public class MouvementStock {
    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "produit_id",nullable = false)
    @JsonBackReference // prevents infinite recursion when serializing
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    @JsonBackReference // prevents infinite recursion when serializing
    private Commande commande;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement;

    @Column(nullable = false)
    private int quantite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMouvement type;

}
