package com.tricol.Tricol;

import com.tricol.Tricol.dto.LineCommandeDto;
import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.mapper.CommandeMapper;
import com.tricol.Tricol.model.*;
import com.tricol.Tricol.repository.*;
import com.tricol.Tricol.service.CommandeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceTest {
    @Mock
    private CommandRepository commandRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private CommandeServiceImpl commandeService;

    @Mock
    private LineCommandRepository lineCommandRepository;

    @Mock
    private MouvementStockRepository mouvementStockRepository;


    @Test
    void createCommande_shouldCreateCommandeSuccessfully() {
        // ----- Arrange -----
        // 1. Mock input DTO
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId("Test");
        dto.setStatut("EN_ATTENTE");

        LineCommandeDto line1 = new LineCommandeDto("10P", 2); // produitId=10, quantite=2
        LineCommandeDto line2 = new LineCommandeDto("20P", 3); // produitId=20, quantite=3

        dto.setProduits(List.of(line1, line2));

        // 2. Mock fournisseur
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId("Test");

        when(fournisseurRepository.findById("Test"))
                .thenReturn(Optional.of(fournisseur));

        // 3. Mock produits
        Produit p1 = new Produit();
        p1.setId("10P");
        p1.setPrix_unitaire(100.1);  // prix 100

        Produit p2 = new Produit();
        p2.setId("20P");
        p2.setPrix_unitaire(50.2); // prix 50

        when(produitRepository.findById("10P")).thenReturn(Optional.of(p1));
        when(produitRepository.findById("20P")).thenReturn(Optional.of(p2));

        // 4. Mock Commande save
        Commande savedCommande = new Commande();
        savedCommande.setId(9);
        savedCommande.setMontant_total(BigDecimal.valueOf(100 * 2 + 50 * 3)); // 200 + 150 = 350

        when(commandRepository.save(any(Commande.class))).thenReturn(savedCommande);

        // ----- Act -----
        Commande result = commandeService.createCommande(dto);

        // ----- Assert -----
        assertNotNull(result);
        assertEquals(9, result.getId());
        assertEquals(BigDecimal.valueOf(350), result.getMontant_total());

        verify(fournisseurRepository).findById("Test");
        verify(produitRepository).findById("10P");
        verify(produitRepository).findById("20P");
        verify(commandRepository).save(any(Commande.class));
    }
    @Test
    void getCommandeById_shouldReturnCommandeDto() {
        // Arrange
        Commande commande = new Commande();
        commande.setId(5);

        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId("F1");

        when(commandRepository.findById(5)).thenReturn(Optional.of(commande));
        when(commandeMapper.toDto(commande)).thenReturn(dto);

        // Act
        Optional<CommandeRequestDto> result = commandeService.getCommandeById(5);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("F1", result.get().getFournisseurId());
        verify(commandRepository).findById(5);
        verify(commandeMapper).toDto(commande);
    }

    @Test
    void updateStatus_shouldReturnNull_whenCommandeNotFound() {
        when(commandRepository.findById(99)).thenReturn(Optional.empty());

        CommandeRequestDto result = commandeService.updateStatus(99, "EN_ATTENTE");

        assertNull(result);
        verify(commandRepository).findById(99);
    }

    @Test
    void updateStatus_shouldUpdateStatusWithoutStock_whenStatutNotLivree() {
        // Arrange
        Commande commande = new Commande();
        commande.setId(1);
        commande.setStatut("EN_ATTENTE");

        Commande updated = new Commande();
        updated.setId(1);
        updated.setStatut("VALIDÉE");

        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setStatut("VALIDÉE");

        when(commandRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandRepository.save(any())).thenReturn(updated);
        when(commandeMapper.toDto(updated)).thenReturn(dto);

        // Act
        CommandeRequestDto result = commandeService.updateStatus(1, "VALIDÉE");

        // Assert
        assertNotNull(result);
        assertEquals("VALIDÉE", result.getStatut());
        verify(lineCommandRepository, never()).findByCommandeId(anyInt());
        verify(produitRepository, never()).save(any());
        verify(mouvementStockRepository, never()).save(any());
    }

    @Test
    void updateStatus_shouldUpdateCUMPAndStock_whenLivree() {
        // ----- Arrange -----
        Commande commande = new Commande();
        commande.setId(1);
        commande.setStatut("EN_ATTENTE");

        Commande updated = new Commande();
        updated.setId(1);
        updated.setStatut("LIVRÉE");

        Produit produit = new Produit();
        produit.setId("P10");
        produit.setStock_actuel(10);
        produit.setCout_moyen_pondere(100.0);

        LineCommande line = new LineCommande();
        line.setProduit(produit);
        line.setQuantite(5);
        line.setPrix_unitaire(80.0);

        when(commandRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandRepository.save(any())).thenReturn(updated);
        when(lineCommandRepository.findByCommandeId(1)).thenReturn(List.of(line));
        when(produitRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setStatut("LIVRÉE");
        when(commandeMapper.toDto(updated)).thenReturn(dto);

        // ----- Act -----
        CommandeRequestDto result = commandeService.updateStatus(1, "LIVRÉE");

        // ----- Assert -----
        assertNotNull(result);
        assertEquals("LIVRÉE", result.getStatut());

        // New CUMP
        double expectedCump = ((10 * 100.0) + (5 * 80.0)) / (10 + 5);
        assertEquals(expectedCump, produit.getCout_moyen_pondere());

        // New stock
        assertEquals(15, produit.getStock_actuel());

        verify(mouvementStockRepository).save(any(MouvementStock.class));
    }

    @Test
    void getAllCommandes_shouldReturnPageOfDtos() {
        // Arrange
        Commande c1 = new Commande();
        c1.setId(1);

        CommandeRequestDto dto1 = new CommandeRequestDto();
        dto1.setFournisseurId("F1");

        Page<Commande> page = new PageImpl<>(List.of(c1));

        when(commandRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(commandeMapper.toDto(c1)).thenReturn(dto1);

        // Act
        Page<CommandeRequestDto> result = commandeService.getAllCommandes(0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("F1", result.getContent().get(0).getFournisseurId());
    }
    @Test
    void findCommandeByID_shouldReturnCommande() {
        Commande commande = new Commande();
        commande.setId(1);

        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId("F1");

        when(commandRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandeMapper.toDto(commande)).thenReturn(dto);

        CommandeRequestDto result = commandeService.findCommandeByID(1);


        assertNotNull(result);
        assertEquals("F1", result.getFournisseurId());
    }


}