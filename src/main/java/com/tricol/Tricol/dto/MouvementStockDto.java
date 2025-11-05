package com.tricol.Tricol.dto;

import com.tricol.Tricol.Enums.TypeMouvement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MouvementStockDto {
    private String id;
    private String produitId;
    private String commandeId;
    private LocalDateTime dateMouvement;
    private TypeMouvement typeMouvement;
}
