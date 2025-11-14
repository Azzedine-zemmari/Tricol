package com.tricol.Tricol;

import com.tricol.Tricol.Enums.TypeMouvement;
import com.tricol.Tricol.model.MouvementStock;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.MouvementStockRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.MouvementStockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MouvementStockTest {
    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private MouvementStockServiceImpl mouvementStockService;


    @Test
    void getMouvementStock_shouldReturnFilteredList() {

        // ----- Arrange -----
        Produit p1 = new Produit(); p1.setId("P1");
        Produit p2 = new Produit(); p2.setId("P2");

        MouvementStock m1 = new MouvementStock();
        m1.setProduit(p1);
        m1.setType(TypeMouvement.ENTREE);
        m1.setCommande(null);

        MouvementStock m2 = new MouvementStock();
        m2.setProduit(p2);
        m2.setType(TypeMouvement.SORTIE);
        m2.setCommande(null);

        when(mouvementStockRepository.findAll())
                .thenReturn(List.of(m1, m2));

        // ----- Act -----
        List<MouvementStock> result =
                mouvementStockService.getMouvementStock("P1", TypeMouvement.ENTREE, null);

        // ----- Assert -----
        assertEquals(1, result.size());
        assertEquals("P1", result.get(0).getProduit().getId());
        assertEquals(TypeMouvement.ENTREE, result.get(0).getType());

        verify(mouvementStockRepository).findAll();
    }

    @Test
    void sortieStock_shouldSaveStockMovement_whenStockIsEnough() {

        // ----- Arrange -----
        Produit produit = new Produit();
        produit.setId("P10");
        produit.setStock_actuel(50);

        when(produitRepository.findById("P10"))
                .thenReturn(Optional.of(produit));

        MouvementStock savedMovement = new MouvementStock();
        savedMovement.setQuantite(10);
        savedMovement.setProduit(produit);

        when(mouvementStockRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));


        // ----- Act -----
        MouvementStock result = mouvementStockService.sortieStock("P10", 10);

        // ----- Assert -----
        assertNotNull(result);
        assertEquals(40, produit.getStock_actuel()); // stock decreased
        assertEquals(10, result.getQuantite());
        assertEquals(TypeMouvement.SORTIE, result.getType());

        verify(produitRepository).findById("P10");
        verify(produitRepository).save(produit);
        verify(mouvementStockRepository).save(any(MouvementStock.class));
    }

    // ------------------------------------------------------
    //  TEST 3 : sortieStock stock insufficient → throw
    // ------------------------------------------------------
    @Test
    void sortieStock_shouldThrowException_whenStockInsufficient() {

        // ----- Arrange -----
        Produit produit = new Produit();
        produit.setId("PX");
        produit.setStock_actuel(5);

        when(produitRepository.findById("PX"))
                .thenReturn(Optional.of(produit));

        // ----- Act + Assert -----
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> mouvementStockService.sortieStock("PX", 10)
        );

        assertEquals("Produit stock negatif", exception.getMessage());

        verify(produitRepository).findById("PX");
        verify(produitRepository, never()).save(any());
        verify(mouvementStockRepository, never()).save(any());
    }

}
