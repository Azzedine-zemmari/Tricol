package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.mapper.ProduitMapper;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.serviceInterface.ProduitService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    public ProduitServiceImpl(ProduitRepository p, ProduitMapper pm) {
        this.produitRepository = p;
        this.produitMapper = pm;
    }
    @Override
    public ProduitGetDto save(ProduitGetDto produit) {
        Produit entity = produitMapper.toEntity(produit);
        System.out.println("DEBUG mapped entity before save: " + entity);
        Produit saved = produitRepository.save(entity);
        return produitMapper.toDto(saved);
    }
    @Override
    public Page<ProduitGetDto> findAll(int page, int size) {
        if (size < 1) size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Produit> produits = produitRepository.findAll(pageable);
        return produits.map(produitMapper::toDto);
    }


    @Override
    public Optional<ProduitGetDto> findById(String id) {
        return produitRepository.findById(id).map(produitMapper::toDto);
    }
    @Override
    public void deleteById(String id) {
        produitRepository.deleteById(id);
    }
    @Override
    public List<Produit> findAllByIds(List<String> ids) {
        return produitRepository.findAllById(ids);
    }
}
