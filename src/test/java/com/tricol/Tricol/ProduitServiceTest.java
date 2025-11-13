package com.tricol.Tricol;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.mapper.ProduitMapper;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
}
