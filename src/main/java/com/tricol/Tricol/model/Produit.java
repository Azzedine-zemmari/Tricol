package com.tricol.Tricol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    private String id = UUID.randomUUID().toString();
    private String nom;
    private String description;
    private Double prix_unitaire;
    private String categorie;
    private Integer stock_actuel;
}
