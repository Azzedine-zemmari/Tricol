package com.tricol.Tricol.repository;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Integer> {
}
