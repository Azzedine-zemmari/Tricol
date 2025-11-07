package com.tricol.Tricol.dto.fournisseur;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurResponseDto {
    private String id = UUID.randomUUID().toString();
    @NotBlank(message = "le ice ne doit pas etre vide")
    private String ice;
    @NotBlank(message = "le societe ne doit pas etre vide")
    private String societe;
    @NotBlank(message = "le adresse ne doit pas etre vide")
    private String adresse;
    @NotBlank(message = "le contact ne doit pas etre vide")
    private String contact;
    @NotBlank(message = "le email ne doit pas etre vide")
    private String email;
    @NotBlank(message = "le ville ne doit pas etre vide")
    private String ville;
}

