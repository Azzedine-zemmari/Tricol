package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.model.Fournisseur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {

    FournisseurResponseDto toDTO(Fournisseur fournisseur);
    Fournisseur toEntity(FournisseurResponseDto fournisseurResponseDto);
}
