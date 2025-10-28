package com.tricol.Tricol.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "commande")
public class Commande {
    @Id
    String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fournisseur_id",nullable = false)
    private Fournisseur fournisseur;

    @ManyToMany
    @JoinTable(
            name = "commande-produit",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private List<Produit> produits;

    private BigDecimal montant_total;

    private String statut;

    private LocalDateTime date_command = LocalDateTime.now();
}
