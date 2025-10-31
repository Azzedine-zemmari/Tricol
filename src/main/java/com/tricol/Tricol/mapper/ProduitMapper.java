package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.dto.produit.ProduitResponseDto;
import com.tricol.Tricol.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitGetDto toDto(Produit produit);
    @Mapping(target = "id", ignore = true)
    Produit toEntity(ProduitGetDto dto); // i change it to produitResponseDto to fix the save method in service implementation

}
