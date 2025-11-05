package com.tricol.Tricol.repository;

import com.tricol.Tricol.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitRepository extends JpaRepository<Produit, String> {
    Page<Produit> findAll(Pageable pageable); // Correct Pageable import!
}
