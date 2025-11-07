package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.service.serviceInterface.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Commande")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping("/save")
    @Operation(summary = "Enregistrer une commande")
    public Commande addCommande(@Valid @RequestBody CommandeRequestDto commande) {
        System.out.println("dto : " + commande);
        return commandeService.createCommande(commande);
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "Modifier les details du commande")
    public ResponseEntity<CommandeRequestDto> updateStatus(
            @PathVariable int id,
            @RequestBody Map<String, String> request) {

        String nouveauStatut = request.get("statut");

        CommandeRequestDto updated = commandeService.updateStatus(id, nouveauStatut);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    @GetMapping("/all")
    @Operation(summary = "Récupérer tous les commandes avec pagination")
    public ResponseEntity<Page<CommandeRequestDto>> getAllCommandes(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size) {
        Page<CommandeRequestDto> commandes = commandeService.getAllCommandes(page,size);
        return ResponseEntity.ok(commandes);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer commande avec son id")
    public ResponseEntity<CommandeRequestDto> getCommandeById(@PathVariable int id) {
        Optional<CommandeRequestDto> commande = commandeService.getCommandeById(id);
        if(commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        }
            return ResponseEntity.notFound().build();
    }
}
