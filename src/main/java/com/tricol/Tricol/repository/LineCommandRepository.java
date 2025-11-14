package com.tricol.Tricol.repository;

import com.tricol.Tricol.model.LineCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineCommandRepository extends JpaRepository<LineCommande , Integer> {
    List<LineCommande> findByCommandeId(int id);
}
