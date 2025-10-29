package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.fournisseur.FournisseurCreateDto;
import com.tricol.Tricol.mapper.FournisseurMapper;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.repository.FournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;
    public FournisseurService(FournisseurRepository repository , FournisseurMapper mapper) {
            this.fournisseurMapper = mapper;
            this.fournisseurRepository = repository;
    }
    public List<FournisseurCreateDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }
}
