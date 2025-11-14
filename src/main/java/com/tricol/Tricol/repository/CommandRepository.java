package com.tricol.Tricol.repository;

import com.tricol.Tricol.model.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Commande, Integer> {
    Page<Commande> findAll(Pageable pageable);
}
