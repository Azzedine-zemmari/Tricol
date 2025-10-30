package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.mapper.FournisseurMapper;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.serviceInterface.FournisseurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FournisseurServiceImpl implements FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    public FournisseurServiceImpl(FournisseurRepository repository , FournisseurMapper mapper) {
            this.fournisseurMapper = mapper;
            this.fournisseurRepository = repository;
    }
    @Override
    public List<FournisseurResponseDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<FournisseurResponseDto> findById(String id) {
         return fournisseurRepository.findById(id).map(fournisseurMapper::toDTO);
    }
    @Override
    public FournisseurResponseDto save(FournisseurResponseDto fournisseur) {
        return fournisseurMapper.toDTO(fournisseurRepository.save(fournisseurMapper.toEntity(fournisseur)));
    }
}
