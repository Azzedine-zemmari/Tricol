package com.tricol.Tricol.dto;

import com.tricol.Tricol.Enums.TypeMouvement;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MouvementStockDto {
    private String id;
    @NotBlank(message = "le produitId ne dois pas etre vide ")
    private String produitId;
    private String commandeId;
    private LocalDateTime dateMouvement;
    private TypeMouvement typeMouvement;
}
