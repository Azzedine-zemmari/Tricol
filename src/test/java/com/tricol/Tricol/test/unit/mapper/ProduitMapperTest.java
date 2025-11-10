package com.tricol.Tricol.test.unit.mapper;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.mapper.ProduitMapper;
import com.tricol.Tricol.model.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProduitMapperTest {
    @Autowired
    private ProduitMapper mapper;

    @Test
    void shoudlMapEntityToDto(){
        Produit produit = new Produit();
        produit.setId("test");
        produit.setNom("testNom");
        produit.setStock_actuel(22);
        produit.setCategorie("testCategorie");
        produit.setDescription("test test test");
        produit.setPrix_unitaire(12.12);
        produit.setCout_moyen_pondere(1212.12);

        ProduitGetDto dto = mapper.toDto(produit);

        assertEquals("testNom",dto.getNom());
        assertEquals("test",dto.getId());

    }
    @Test
    void shouldMapDtoToEntity() {
        // Arrange: create a DTO
        ProduitGetDto dto = new ProduitGetDto();
        dto.setNom("testNom");
        dto.setStock_actuel(22);
        dto.setCategorie("testCategorie");
        dto.setDescription("test test test");
        dto.setPrix_unitaire(12.12);

        // Act: map DTO to Entity
        Produit produit = mapper.toEntity(dto);

        // Assert: check all fields
        assertNotNull(produit.getId());
        assertEquals("testNom", produit.getNom());
        assertEquals(22, produit.getStock_actuel());
        assertEquals("testCategorie", produit.getCategorie());
        assertEquals("test test test", produit.getDescription());
        assertEquals(12.12, produit.getPrix_unitaire());
    }
}
