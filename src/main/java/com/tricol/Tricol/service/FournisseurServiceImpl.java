package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.mapper.FournisseurMapper;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.serviceInterface.FournisseurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class FournisseurServiceImpl implements FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    public FournisseurServiceImpl(FournisseurRepository repository , FournisseurMapper mapper) {
            this.fournisseurMapper = mapper;
            this.fournisseurRepository = repository;
    }
    @Override
    public Page<FournisseurResponseDto> findAll(int page , int size) {
        if(size < 1) size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<Fournisseur> fournisseurs = fournisseurRepository.findAll(pageable);
        return fournisseurs.map(fournisseurMapper::toDTO);
    }
    @Override
    public Optional<FournisseurResponseDto> findById(String id) {
         return fournisseurRepository.findById(id).map(fournisseurMapper::toDTO);
    }
    @Override
    public FournisseurResponseDto save(FournisseurResponseDto fournisseur) {
        return fournisseurMapper.toDTO(fournisseurRepository.save(fournisseurMapper.toEntity(fournisseur)));
    }
    @Override
    public void deleteById(String id) {
        fournisseurRepository.deleteById(id);
    }
}
