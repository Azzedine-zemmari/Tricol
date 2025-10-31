package com.tricol.Tricol.dto.produit;

import lombok.Data;
@Data
public class ProduitGetDto {
    private String id;
    private String nom;
    private String description;
    private Double prix_unitaire;
    private String categorie;
    private Integer stock_actuel;
}
