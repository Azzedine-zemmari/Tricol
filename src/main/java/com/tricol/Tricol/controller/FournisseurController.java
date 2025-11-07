package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.exception.FournisseurNotFound;
import com.tricol.Tricol.service.serviceInterface.FournisseurService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fournisseur")
public class FournisseurController {
    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @PostMapping("/save")
    @Operation(summary = "Enregistrer un fournisseur ")
    public ResponseEntity<FournisseurResponseDto> save(@Valid @RequestBody FournisseurResponseDto fournisseurDetails){
            FournisseurResponseDto fournisseurResponseDto = fournisseurService.save(fournisseurDetails);
            return ResponseEntity.ok(fournisseurResponseDto);
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les fournisseurs avec pagination")
    public ResponseEntity<Page<FournisseurResponseDto>> findAll(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size){
        if(size < 1) size = 10;
        Page<FournisseurResponseDto> fournisseurs = fournisseurService.findAll(page , size);
        if(fournisseurs.isEmpty()){
            throw new FournisseurNotFound("fournisseur not found");
        }
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un fournisseur avec son id ")
    public ResponseEntity<FournisseurResponseDto> findById(@PathVariable("id") String id){
        return fournisseurService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "Modifier les details du fournisseur ")
    public ResponseEntity<FournisseurResponseDto> update(@PathVariable("id") String id, @RequestBody FournisseurResponseDto fournisseurDetails){
        Optional<FournisseurResponseDto> OldFournisseur = fournisseurService.findById(id);
        if(OldFournisseur.isPresent()){
            FournisseurResponseDto fournisseur = OldFournisseur.get();
            fournisseur.setIce(fournisseurDetails.getIce());
            fournisseur.setSociete(fournisseurDetails.getSociete());
            fournisseur.setAdresse(fournisseurDetails.getAdresse());
            fournisseur.setEmail(fournisseurDetails.getEmail());
            fournisseur.setVille(fournisseurDetails.getVille());
            fournisseur.setContact(fournisseurDetails.getContact());

           FournisseurResponseDto updatedFournisseur =  fournisseurService.save(fournisseur);
           return ResponseEntity.ok(updatedFournisseur);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer fournisseur avec son id")
    public ResponseEntity<String> delete(@PathVariable("id") String id){
        if(fournisseurService.findById(id).isPresent()){
            fournisseurService.deleteById(id);
            return ResponseEntity.ok("Fournisseur deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur not found");
    }
}
