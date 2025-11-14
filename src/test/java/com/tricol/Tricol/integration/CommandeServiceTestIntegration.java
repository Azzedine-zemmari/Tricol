package com.tricol.Tricol.integration;

import com.tricol.Tricol.dto.LineCommandeDto;
import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.mapper.CommandeMapper;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.CommandRepository;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.CommandeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class CommandeServiceTestIntegration {

    @Container
    static MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:8.1")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    private CommandeServiceImpl commandeService;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeMapper commandeMapper;

    private Fournisseur fournisseur;
    private Produit produit1;
    private Produit produit2;

    @BeforeEach
    void setUp() {
        commandRepository.deleteAll();
        produitRepository.deleteAll();
        fournisseurRepository.deleteAll();

        fournisseur = new Fournisseur();
        fournisseur.setSociete("ABC SARL");
        fournisseur.setIce("123456789");
        fournisseur.setAdresse("Rue 1");
        fournisseur.setContact("0123456789");
        fournisseur.setEmail("abc@example.com");
        fournisseur.setVille("Casablanca");
        fournisseurRepository.save(fournisseur);

        produit1 = new Produit();
        produit1.setId("P1");
        produit1.setNom("Laptop");
        produit1.setPrix_unitaire(1000.0);
        produit1.setStock_actuel(10);
        produitRepository.save(produit1);

        produit2 = new Produit();
        produit2.setId("P2");
        produit2.setNom("Mouse");
        produit2.setPrix_unitaire(50.0);
        produit2.setStock_actuel(100);
        produitRepository.save(produit2);
    }

    @Test
    void createCommande_shouldPersistCommandeSuccessfully() {
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId(fournisseur.getId());
        dto.setStatut("EN_COURS");
        dto.setProduits(List.of(
                new LineCommandeDto("P1", 2),
                new LineCommandeDto("P2", 5)
        ));

        Commande savedCommande = commandeService.createCommande(dto);

        assertNotNull(savedCommande);
        assertNotNull(savedCommande.getId());
        assertEquals(fournisseur.getId(), savedCommande.getFournisseur().getId());
        assertEquals(2, savedCommande.getLignesCommande().size());

        // Verify total amount
        double expectedTotal = 2 * produit1.getPrix_unitaire() + 5 * produit2.getPrix_unitaire();
        assertEquals(expectedTotal, savedCommande.getMontant_total().doubleValue());
    }

    @Test
    void getCommandeById_shouldReturnCommande() {
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId(fournisseur.getId());
        dto.setStatut("EN_COURS");
        dto.setProduits(List.of(new LineCommandeDto("P1", 1)));

        Commande savedCommande = commandeService.createCommande(dto);

        Optional<CommandeRequestDto> fetched = commandeService.getCommandeById(savedCommande.getId());
        assertTrue(fetched.isPresent());
        assertEquals(savedCommande.getMontant_total(), fetched.get().getMontant_total());
    }

    @Test
    void updateStatus_shouldUpdateCommandeStatus() {
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setFournisseurId(fournisseur.getId());
        dto.setStatut("EN_COURS");
        dto.setProduits(List.of(new LineCommandeDto("P1", 1)));

        Commande savedCommande = commandeService.createCommande(dto);

        // Update status to LIVRÉE
        commandeService.updateStatus(savedCommande.getId(), "LIVRÉE");

        CommandeRequestDto updated = commandeService.getCommandeById(savedCommande.getId()).get();
        assertEquals("LIVRÉE", updated.getStatut());

        // Check stock update for produit1
        Produit updatedProduit = produitRepository.findById("P1").get();
        assertEquals(11, updatedProduit.getStock_actuel());
    }
}
