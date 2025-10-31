package com.tricol.Tricol.dto.produit;

import lombok.Data;

import java.util.UUID;

@Data
public class ProduitResponseDto {
    private String nom;
    private String description;
    private Double prix_unitaire;
    private String categorie;
    private Integer stock_actuel;
}
