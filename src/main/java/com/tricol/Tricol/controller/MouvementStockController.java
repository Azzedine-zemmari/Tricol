package com.tricol.Tricol.controller;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;
import com.tricol.Tricol.service.MouvementStockServiceImpl;
import com.tricol.Tricol.service.serviceInterface.MovementStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/MouvementStock")
public class MouvementStockController {
    private MovementStockService mouvementStockService;

    public MouvementStockController(MovementStockService MouvementStockService) {
        this.mouvementStockService = MouvementStockService;
    }

    @GetMapping
    public ResponseEntity<List<MouvementStock>> getMouvementStock(@RequestParam(required = false) String produitId , @RequestParam(required = false)TypeMouvement typeMouvement , @RequestParam(required = false) Integer commandeId) {
        List<MouvementStock> mouvementStockList = mouvementStockService.getMouvementStock(produitId , typeMouvement , commandeId);
        return ResponseEntity.ok(mouvementStockList);
    }
    @PostMapping("/sortieStock")
    public ResponseEntity<MouvementStock> SortieStock(@RequestParam String produitId , @RequestParam int quantity){
        MouvementStock mouvementStock = mouvementStockService.sortieStock(produitId , quantity);
        return ResponseEntity.ok(mouvementStock);
    }
}
