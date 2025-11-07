package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.exception.ProduitNotFound;
import com.tricol.Tricol.service.serviceInterface.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {
    private final ProduitService produitService;
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
    @PostMapping("/save")
    @Operation(summary = "Enregistrer un produit")
    public ResponseEntity<ProduitGetDto> save(@Valid @RequestBody ProduitGetDto produit) {
        System.out.println("DEBUG controller received DTO: " + produit);
        ProduitGetDto produitResponseDto =  produitService.save(produit);
        return ResponseEntity.ok(produitResponseDto);
    }
    @GetMapping
    @Operation(summary = "Récupérer tous les produits avec pagination et tri")
    public ResponseEntity<Page<ProduitGetDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "prix_unitaire") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Page<ProduitGetDto> produits = produitService.findAll(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer produit par id")
    public ResponseEntity<ProduitGetDto> findById(@PathVariable String id) {
        return produitService.findById(id).map(ResponseEntity::ok).orElseThrow(()-> new ProduitNotFound("produit not found"));
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "Modifier les details du produit")
    public ResponseEntity<ProduitGetDto> update(@Valid @PathVariable("id") String id, @RequestBody ProduitGetDto produit) {
        Optional<ProduitGetDto> produitGetDto = produitService.findById(id);
        if (produitGetDto.isPresent()) {
            ProduitGetDto produitDto = produitGetDto.get();
            produitDto.setNom(produit.getNom());
            produitDto.setCategorie(    produit.getCategorie());
            produitDto.setDescription(produit.getDescription());
            produitDto.setStock_actuel(produit.getStock_actuel());
            produitDto.setPrix_unitaire(produit.getPrix_unitaire());

            ProduitGetDto updateProduit = produitService.save(produitDto);
            return ResponseEntity.ok(updateProduit);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "supprimer produit par son id")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        if(produitService.findById(id).isPresent()){
            produitService.deleteById(id);
            return ResponseEntity.ok("Produit est supprime");
        }else{
            return ResponseEntity.ok("produit n est pas existe");
        }
    }
}
