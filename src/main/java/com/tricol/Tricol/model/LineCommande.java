package com.tricol.Tricol.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "line_commande")
public class LineCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    private double prix_unitaire;
    private int quantite;
}
