package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitResponseDto;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.serviceInterface.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {
    private final ProduitService produitService;
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
    @PostMapping("/save")
    public ResponseEntity<ProduitGetDto> save(@RequestBody ProduitResponseDto produit) {
        System.out.println("DEBUG controller received DTO: " + produit);
        ProduitGetDto produitResponseDto =  produitService.save(produit);
        return ResponseEntity.ok(produitResponseDto);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProduitGetDto>> findAll() {
        List<ProduitGetDto> produitGetDtos = produitService.findAll();
        return ResponseEntity.ok(produitGetDtos);
    }
}
