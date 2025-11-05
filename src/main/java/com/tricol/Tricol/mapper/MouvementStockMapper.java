package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.MouvementStockDto;
import com.tricol.Tricol.model.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {
    @Mapping(source = "produit.id" , target = "produitId")
    @Mapping(source = "commande.id", target = "commandeId")
    MouvementStockDto toDto(MouvementStock mouvementStock);
    @Mapping(target = "produit.id" , source = "produitId")
    @Mapping(target = "commande.id", source = "commandeId")
    MouvementStock toEntity(MouvementStockDto mouvementStockDto);
}
