package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;

import java.util.List;
import java.util.Optional;


public interface FournisseurService {
    List<FournisseurResponseDto> findAll();
    Optional<FournisseurResponseDto> findById(String id);
}
