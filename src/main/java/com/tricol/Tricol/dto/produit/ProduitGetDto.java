package com.tricol.Tricol.dto.produit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
@Data
public class ProduitGetDto {
    private String id;
    @NotBlank(message = "le nom ne dois pas etre vide")
    private String nom;
    @NotBlank(message = "la description ne dois pas etre vide")
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private Double prix_unitaire;
    @NotBlank(message = "le categorie ne dois pas etre vide")
    private String categorie;
    @PositiveOrZero(message = "le stock actuel doit etre positive")
    private Integer stock_actuel;
}
