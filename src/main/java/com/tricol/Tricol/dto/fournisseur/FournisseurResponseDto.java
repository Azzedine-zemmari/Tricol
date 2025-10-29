package com.tricol.Tricol.dto.fournisseur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurResponseDto {
    private String id;
    private String ice;
    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String ville;
}

