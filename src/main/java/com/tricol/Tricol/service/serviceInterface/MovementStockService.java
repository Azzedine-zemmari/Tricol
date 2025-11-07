package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;

import java.util.List;

public interface MovementStockService {
    List<MouvementStock> getMouvementStock(String produitId , TypeMouvement typeMouvement , Integer commandeId);
}
