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

    @Mapping(source = "fournisseur.id" , target = "fournisseurId")
    CommandeRequestDto toDto(Commande commande);
    @Mapping(target = "fournisseur.id" , source = "fournisseurId")
    Commande toEntity(CommandeRequestDto dto);

}

