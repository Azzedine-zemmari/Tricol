package com.tricol.Tricol.repository;

import com.tricol.Tricol.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Commande, String> {
}
