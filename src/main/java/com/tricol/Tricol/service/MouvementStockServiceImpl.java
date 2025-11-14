package com.tricol.Tricol.service;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.MouvementStockRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.serviceInterface.MovementStockService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MouvementStockServiceImpl implements MovementStockService {
    private final MouvementStockRepository mouvementStockRepository;
    private final ProduitRepository produitRepository;

    public MouvementStockServiceImpl(MouvementStockRepository mouvementStockRepository , ProduitRepository produitRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public List<MouvementStock> getMouvementStock(String produitId , TypeMouvement typeMouvement , Integer commandeId) {
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();

        return mouvementStockList.stream()
                .filter(m->produitId == null || m.getProduit().getId().equals(produitId))
                .filter(m -> typeMouvement == null || m.getType().equals(typeMouvement) )
                .filter(m -> commandeId == null || m.getCommande().getId().equals(commandeId))
                .toList();
    }
    @Transactional
    @Override
    public MouvementStock sortieStock(String produitId, int quantity){
        Produit produit = produitRepository.findById(produitId).get();
        int produitStock = produit.getStock_actuel();

        if(produitStock < quantity){
            throw new RuntimeException("Produit stock negatif");
        }
        produit.setStock_actuel(produitStock - quantity);
        produitRepository.save(produit);

        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setProduit(produit);
        mouvementStock.setQuantite(quantity);
        mouvementStock.setDateMouvement(LocalDateTime.now());
        mouvementStock.setType(TypeMouvement.SORTIE);
        return mouvementStockRepository.save(mouvementStock);
    }
}
