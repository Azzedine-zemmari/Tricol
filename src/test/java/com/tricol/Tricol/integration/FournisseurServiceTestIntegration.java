package com.tricol.Tricol.integration;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.mapper.FournisseurMapper;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.FournisseurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class FournisseurServiceTestIntegration {

    @Container
    private static final MySQLContainer<?> mysqlContainer =
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
    private FournisseurServiceImpl fournisseurService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurMapper fournisseurMapper;

    Fournisseur fournisseur1;
    Fournisseur fournisseur2;

    @BeforeEach
    void setUp() {
        fournisseurRepository.deleteAll();

        fournisseur1 = new Fournisseur();
        fournisseur1.setSociete("ABC SARL");
        fournisseur1.setIce("123456789");
        fournisseur1.setAdresse("Rue 1");
        fournisseur1.setContact("0123456789");
        fournisseur1.setEmail("abc@example.com");
        fournisseur1.setVille("Casablanca");

        fournisseur2 = new Fournisseur();
        fournisseur2.setSociete("XYZ SARL");
        fournisseur2.setIce("987654321");
        fournisseur2.setAdresse("Rue 2");
        fournisseur2.setContact("0987654321");
        fournisseur2.setEmail("xyz@example.com");
        fournisseur2.setVille("Rabat");

        fournisseurRepository.save(fournisseur1);
        fournisseurRepository.save(fournisseur2);
    }

    @Test
    void save_shouldPersistFournisseurSuccessfully() {
        FournisseurResponseDto dto = new FournisseurResponseDto();
        dto.setSociete("LMN SARL");
        dto.setIce("555555555");
        dto.setAdresse("Rue 3");
        dto.setContact("0555555555");
        dto.setEmail("lmn@example.com");
        dto.setVille("Marrakech");

        FournisseurResponseDto savedDto = fournisseurService.save(dto);

        assertNotNull(savedDto);
        assertNotNull(savedDto.getId());
        assertEquals("LMN SARL", savedDto.getSociete());

        Fournisseur saved = fournisseurRepository.findById(savedDto.getId()).orElse(null);
        assertNotNull(saved);
        assertEquals("LMN SARL", saved.getSociete());
    }

    @Test
    void findAll_shouldReturnPaginatedResults() {
        Page<FournisseurResponseDto> result = fournisseurService.findAll(0, 10);

        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().stream().anyMatch(f -> f.getSociete().equals("ABC SARL")));
        assertTrue(result.getContent().stream().anyMatch(f -> f.getSociete().equals("XYZ SARL")));
    }

    @Test
    void findById_shouldReturnFournisseur_whenExists() {
        Optional<FournisseurResponseDto> result = fournisseurService.findById(fournisseur1.getId());
        assertTrue(result.isPresent());
        assertEquals("ABC SARL", result.get().getSociete());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<FournisseurResponseDto> result = fournisseurService.findById("UNKNOWN");
        assertFalse(result.isPresent());
    }

    @Test
    void deleteById_shouldDeleteFournisseur() {
        fournisseurService.deleteById(fournisseur1.getId());
        assertFalse(fournisseurRepository.findById(fournisseur1.getId()).isPresent());
    }

    @Test
    void findAllByIds_shouldReturnMatchingFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findAllById(List.of(fournisseur1.getId(), fournisseur2.getId()));
        assertEquals(2, fournisseurs.size());
        assertTrue(fournisseurs.stream().anyMatch(f -> f.getId().equals(fournisseur1.getId())));
        assertTrue(fournisseurs.stream().anyMatch(f -> f.getId().equals(fournisseur2.getId())));
    }
}
