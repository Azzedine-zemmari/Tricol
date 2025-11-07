package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface FournisseurService {
    Page<FournisseurResponseDto> findAll(int page , int size);
    Optional<FournisseurResponseDto> findById(String id);
    FournisseurResponseDto save(FournisseurResponseDto fournisseurResponseDto);
    void deleteById(String id);
}
