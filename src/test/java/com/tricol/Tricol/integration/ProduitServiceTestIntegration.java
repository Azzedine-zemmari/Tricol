package com.tricol.Tricol.integration;

import com.tricol.Tricol.dto.produit.ProduitGetDto;
import com.tricol.Tricol.mapper.ProduitMapper;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.ProduitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;


@Testcontainers
@SpringBootTest
@Transactional
class ProduitServiceTestIntegration {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.1")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private ProduitServiceImpl produitService;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    Produit produit1;
    Produit produit2;

    @BeforeEach
    void setUp() {
        produitRepository.deleteAll();

        produit1 = new Produit();
        produit1.setId("P1");
        produit1.setNom("Laptop");
        produit1.setPrix_unitaire(1000.0);
        produit1.setStock_actuel(10);
        produit1.setDescription("High-end laptop");

        produit2 = new Produit();
        produit2.setId("P2");
        produit2.setNom("Mouse");
        produit2.setPrix_unitaire(50.0);
        produit2.setStock_actuel(100);
        produit2.setDescription("Wireless mouse");

        produitRepository.save(produit1);
        produitRepository.save(produit2);
    }

    // ----------------------------------------------------
    // 1. TEST SAVE
    // ----------------------------------------------------
    @Test
    void save_shouldPersistProduitSuccessfully() {
        ProduitGetDto dto = new ProduitGetDto();
        dto.setId("P3");
        dto.setNom("Keyboard");
        dto.setPrix_unitaire(120.0);
        dto.setStock_actuel(20);

        ProduitGetDto savedDto = produitService.save(dto);

        assertNotNull(savedDto);
        assertEquals("P3", savedDto.getId());

        Produit saved = produitRepository.findById("P3").orElse(null);
        assertNotNull(saved);
        assertEquals("Keyboard", saved.getNom());
    }

    // ----------------------------------------------------
    // 2. TEST FIND ALL PAGINATED
    // ----------------------------------------------------
    @Test
    void findAll_shouldReturnPaginatedResults() {
        PageRequest page = PageRequest.of(0, 10);

        Page<ProduitGetDto> result = produitService.findAll(page);

        assertEquals(2, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getNom());
    }

    // ----------------------------------------------------
    // 3. TEST FIND BY ID
    // ----------------------------------------------------
    @Test
    void findById_shouldReturnProduit_whenExists() {
        Optional<ProduitGetDto> result = produitService.findById("P1");

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getNom());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<ProduitGetDto> result = produitService.findById("UNKNOWN");

        assertFalse(result.isPresent());
    }

    // ----------------------------------------------------
    // 4. TEST DELETE
    // ----------------------------------------------------
    @Test
    void deleteById_shouldDeleteProduit() {
        produitService.deleteById("P1");

        assertFalse(produitRepository.findById("P1").isPresent());
    }

    // ----------------------------------------------------
    // 5. TEST FIND ALL BY IDS
    // ----------------------------------------------------
    @Test
    void findAllByIds_shouldReturnMatchingProduits() {
        List<Produit> produits = produitService.findAllByIds(List.of("P1", "P2"));

        assertEquals(2, produits.size());
        assertTrue(produits.stream().anyMatch(p -> p.getId().equals("P1")));
        assertTrue(produits.stream().anyMatch(p -> p.getId().equals("P2")));
    }
}
