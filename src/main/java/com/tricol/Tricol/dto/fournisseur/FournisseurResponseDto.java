package com.tricol.Tricol.dto.fournisseur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurResponseDto {
    private String id = UUID.randomUUID().toString();
    private String ice;
    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String ville;
}

