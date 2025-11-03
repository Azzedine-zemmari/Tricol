package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    CommandeRequestDto toDto(Commande commande);
    Commande toEntity(CommandeRequestDto commandeRequestDto);
}
