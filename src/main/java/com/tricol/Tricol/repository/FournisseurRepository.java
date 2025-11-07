package com.tricol.Tricol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tricol.Tricol.model.Fournisseur;
public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
    Page<Fournisseur> findAll(Pageable pageable);
}
