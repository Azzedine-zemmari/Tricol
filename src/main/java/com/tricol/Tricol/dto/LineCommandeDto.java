package com.tricol.Tricol.dto;

import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Produit;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class LineCommandeDto {
    private int id;
    private Produit produit;
    private int quantite;
}
