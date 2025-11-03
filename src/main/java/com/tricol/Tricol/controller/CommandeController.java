package com.tricol.Tricol.controller;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.service.serviceInterface.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/Commande")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping("/save")
    public Commande addCommande(@RequestBody CommandeRequestDto commande) {
        System.out.println("dto : " + commande);
        return commandeService.createCommande(commande);
    }
    @PutMapping("/update/{id}")
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
}
