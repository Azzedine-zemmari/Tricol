package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;

import java.util.List;


public interface FournisseurService {
    List<FournisseurResponseDto> findAll();
}
