package com.tricol.Tricol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "fournisseur")
public class Fournisseur {
    @Id
    private String id = UUID.randomUUID().toString();
    private String ice;
    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String ville;
}
