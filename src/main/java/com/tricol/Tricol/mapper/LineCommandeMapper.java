package com.tricol.Tricol.mapper;

import com.tricol.Tricol.dto.LineCommandeDto;
import com.tricol.Tricol.model.LineCommande;
import com.tricol.Tricol.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LineCommandeMapper {
    LineCommande ToEntity(LineCommandeDto lineCommandeDto);
    LineCommandeDto ToDto(LineCommande lineCommande);

}
