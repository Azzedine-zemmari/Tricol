package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.service.serviceInterface.FournisseurService;
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

    @GetMapping
    public ResponseEntity<List<FournisseurResponseDto>> findAll(){
        List<FournisseurResponseDto> fournisseurs = fournisseurService.findAll();
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurResponseDto> findById(@PathVariable("id") String id){
        return fournisseurService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
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
}
