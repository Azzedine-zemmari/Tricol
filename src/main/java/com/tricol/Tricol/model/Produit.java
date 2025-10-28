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
    String id = UUID.randomUUID().toString();
    String nom;
    String description;
    Double prix_unitaire;
    String categorie;
    Integer stock_actuel;
}
