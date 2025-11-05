package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

//    @Mapping(target = "produits", source = "produits", qualifiedByName = "produitListToIdList")
    CommandeRequestDto toDto(Commande commande);

//    @Mapping(target = "produits", ignore = true) // service will handle fetching Produit entities
    Commande toEntity(CommandeRequestDto dto);

//    @Named("produitListToIdList")
//    default List<String> produitListToIdList(List<Produit> produits) {
//        if (produits == null) return null;
//        return produits.stream()
//                .map(produit -> produit.getId().toString()) // assuming Produit has getId() as UUID
//                .toList();
//    }
}

