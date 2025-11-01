package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    @Mapping(target = "produits", source = "produits", qualifiedByName = "produitListToIdList")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "montant_total", source = "montant_total")
    @Mapping(target = "statut", source = "statut")
    CommandeRequestDto toDto(Commande commande);

    @Mapping(target = "fournisseur", ignore = true)
    @Mapping(target = "produits", ignore = true)
    @Mapping(target = "date_command", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "montant_total", source = "montant_total")
    @Mapping(target = "statut", source = "statut")
    Commande toEntity(CommandeRequestDto commandeRequestDto);

    @Named("produitListToIdList")
    default List<String> mapProduitListToIdList(List<Produit> produits) {
        if (produits == null) {
            return null;
        }
        return produits.stream()
                .map(Produit::getId)
                .collect(Collectors.toList());
    }
}