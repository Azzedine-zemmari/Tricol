package com.tricol.Tricol.repository;

import com.tricol.Tricol.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Produit, String> {
}
