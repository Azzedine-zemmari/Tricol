package com.tricol.Tricol;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.mapper.ProduitMapper;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.ProduitServiceImpl;
import com.tricol.Tricol.service.serviceInterface.ProduitService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProduitServiceTest {
    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @InjectMocks
    private ProduitServiceImpl produitService;

    @Test
    void testfindAll_ShouldReturnMappedPage(){
        Produit produit1 = new Produit();
        produit1.setId("testProduit");
        produit1.setStock_actuel(10);
        produit1.setPrix_unitaire(12.2);
        produit1.setCout_moyen_pondere(0.0);
        produit1.setDescription("testProduit");
        produit1.setCategorie("testProduit");
        produit1.setNom("testProduit");

        ProduitGetDto produitGetDto = new ProduitGetDto();
        produitGetDto.setId("testProduit");
        produitGetDto.setStock_actuel(10);
        produitGetDto.setPrix_unitaire(12.2);
        produitGetDto.setDescription("testProduit");
        produitGetDto.setCategorie("testProduit");
        produitGetDto.setNom("testProduit");


        Page<Produit> produitPage = new PageImpl<>(List.of(produit1));

        when(produitRepository.findAll(any(Pageable.class))).thenReturn(produitPage);
        when(produitMapper.toDto(produit1)).thenReturn(produitGetDto);

        Page<ProduitGetDto> result = produitService.findAll(Pageable.unpaged());

        verify(produitRepository).findAll(any(Pageable.class));
        verify(produitMapper).toDto(produit1);

        Assertions.assertThat(result.getContent()).hasSize(1);
        Assertions.assertThat(result.getContent().get(0).getCategorie()).isEqualTo("testProduit");

    }

    @Test
    void findById_shouldReturnDto_whenEntityExists() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Produit produit = new Produit();
        ProduitGetDto dto = new ProduitGetDto();

        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));
        when(produitMapper.toDto(produit)).thenReturn(dto);

        // Act
        Optional<ProduitGetDto> result = produitService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
        verify(produitRepository, times(1)).findById(id);
        verify(produitMapper, times(1)).toDto(produit);
    }
    @Test
    void findById_shouldReturnEmpty_whenEntityDoesNotExist() {
        // Arrange
        String id = "99";
        when(produitRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<ProduitGetDto> result = produitService.findById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(produitRepository, times(1)).findById(id);
        verify(produitMapper, never()).toDto(any());
    }
    @Test
    void save_shouldReturnDto_whenEntityExists() {
        ProduitGetDto dto = new ProduitGetDto();
        Produit entitySave = new Produit();
        ProduitGetDto produitGetDto = new ProduitGetDto();

        when(produitMapper.toEntity(dto)).thenReturn(entitySave);
        when(produitRepository.save(entitySave)).thenReturn(entitySave);
        when(produitMapper.toDto(entitySave)).thenReturn(produitGetDto);

        ProduitGetDto result = produitService.save(dto);

        assertNotNull(result);
        assertEquals(produitGetDto, result);

        verify(produitMapper).toEntity(dto);
        verify(produitRepository).save(entitySave);
        verify(produitMapper).toDto(entitySave);
    }


    @Test
    void deleteById_shouldCallRepositoryDelete(){
        String id = UUID.randomUUID().toString();

        produitService.deleteById(id);

        verify(produitRepository, times(1)).deleteById(id);
    }
}
