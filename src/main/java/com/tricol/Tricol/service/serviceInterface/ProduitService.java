package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitResponseDto;

import java.util.List;

public interface ProduitService {
    List<ProduitGetDto> findAll();
    ProduitGetDto save(ProduitResponseDto produit);
}
