package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProduitService {
    List<ProduitGetDto> findAll();
    ProduitGetDto save(ProduitGetDto produit);
    Optional<ProduitGetDto> findById(String id);
    void deleteById(String id);
}
