package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitResponseDto;
import com.tricol.Tricol.exception.ProduitNotFound;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.serviceInterface.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/produit")
public class ProduitController {
    private final ProduitService produitService;
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
    @PostMapping("/save")
    public ResponseEntity<ProduitGetDto> save(@RequestBody ProduitGetDto produit) {
        System.out.println("DEBUG controller received DTO: " + produit);
        ProduitGetDto produitResponseDto =  produitService.save(produit);
        return ResponseEntity.ok(produitResponseDto);
    }
    @GetMapping("/")
    public ResponseEntity<Page<ProduitGetDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (size < 1) size = 10;
        Page<ProduitGetDto> produits = produitService.findAll(page, size);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitGetDto> findById(@PathVariable String id) {
        return produitService.findById(id).map(ResponseEntity::ok).orElseThrow(()-> new ProduitNotFound("produit not found"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProduitGetDto> update(@PathVariable("id") String id, @RequestBody ProduitGetDto produit) {
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
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        if(produitService.findById(id).isPresent()){
            produitService.deleteById(id);
            return ResponseEntity.ok("Produit est supprime");
        }else{
            return ResponseEntity.ok("produit n est pas existe");
        }
    }
}
