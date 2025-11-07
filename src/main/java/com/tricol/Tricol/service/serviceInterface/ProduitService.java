package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProduitService {
    Page<ProduitGetDto> findAll(Pageable pageable);
    ProduitGetDto save(ProduitGetDto produit);
    Optional<ProduitGetDto> findById(String id);
    void deleteById(String id);
    List<Produit> findAllByIds(List<String> ids);
}
