package com.tricol.Tricol.dto;

import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Produit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LineCommandeDto {
    private int id;
    @NotBlank(message = "le produitId ne dois pas etre vide ")
    private String produitId;
    @PositiveOrZero(message = "la quantite doit etre positive")
    private int quantite;

    public LineCommandeDto(String produitId, int quantite) {
        this.produitId = produitId;
        this.quantite = quantite;
    }
}
