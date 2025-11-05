package com.tricol.Tricol.dto.command;

import com.tricol.Tricol.dto.LineCommandeDto;
import com.tricol.Tricol.model.Produit;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CommandeRequestDto {
    private String fournisseurId;
    private List<LineCommandeDto> produits;
    private BigDecimal montant_total;
    private String statut;

}
