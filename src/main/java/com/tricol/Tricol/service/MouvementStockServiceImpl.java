package com.tricol.Tricol.service;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;
import com.tricol.Tricol.repository.MouvementStockRepository;
import com.tricol.Tricol.service.serviceInterface.MovementStockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MouvementStockServiceImpl implements MovementStockService {
    private final MouvementStockRepository mouvementStockRepository;

    public MouvementStockServiceImpl(MouvementStockRepository mouvementStockRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
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
}
