package com.tricol.Tricol;

import com.tricol.Tricol.dto.fournisseur.FournisseurResponseDto;
import com.tricol.Tricol.mapper.FournisseurMapper;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.service.FournisseurServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private FournisseurMapper fournisseurMapper;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @Test
    void testFindAll_ShouldReturnMappedPage() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId("test1");
        fournisseur.setAdresse("adress");
        fournisseur.setIce("ice");
        fournisseur.setSociete("societe");
        fournisseur.setContact("000000000");
        fournisseur.setEmail("email@gmail.com");
        fournisseur.setVille("safi");

        FournisseurResponseDto fournisseurResponseDto = new FournisseurResponseDto();
        fournisseurResponseDto.setId("test1");
        fournisseurResponseDto.setAdresse("adress");
        fournisseurResponseDto.setIce("ice");
        fournisseurResponseDto.setSociete("societe");
        fournisseurResponseDto.setContact("000000000");
        fournisseurResponseDto.setEmail("email@gmail.com");
        fournisseurResponseDto.setVille("safi");

        Page<Fournisseur> fournisseurPage = new PageImpl<>(List.of(fournisseur));

        when(fournisseurRepository.findAll(any(Pageable.class))).thenReturn(fournisseurPage);
        when(fournisseurMapper.toDTO(fournisseur)).thenReturn(fournisseurResponseDto);

        Page<FournisseurResponseDto> result = fournisseurService.findAll(0, 5);

        verify(fournisseurRepository).findAll(PageRequest.of(0, 5));
        verify(fournisseurMapper).toDTO(fournisseur);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getIce()).isEqualTo("ice");
    }
    @Test
    void findById_shouldReturnDto_whenEntityExists() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Fournisseur fournisseur = new Fournisseur();
        FournisseurResponseDto dto = new FournisseurResponseDto();

        when(fournisseurRepository.findById(id)).thenReturn(Optional.of(fournisseur));
        when(fournisseurMapper.toDTO(fournisseur)).thenReturn(dto);

        // Act
        Optional<FournisseurResponseDto> result = fournisseurService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
        verify(fournisseurRepository, times(1)).findById(id);
        verify(fournisseurMapper, times(1)).toDTO(fournisseur);
    }
    @Test
    void findById_shouldReturnEmpty_whenEntityDoesNotExist() {
        // Arrange
        String id = "99";
        when(fournisseurRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<FournisseurResponseDto> result = fournisseurService.findById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(fournisseurRepository, times(1)).findById(id);
        verify(fournisseurMapper, never()).toDTO(any());
    }

    @Test
    void save_shouldConvertAndReturnDto() {
        // Arrange
        FournisseurResponseDto dtoToSave = new FournisseurResponseDto();
        Fournisseur entityToSave = new Fournisseur();
        Fournisseur savedEntity = new Fournisseur();
        FournisseurResponseDto savedDto = new FournisseurResponseDto();

        // Mock behavior
        when(fournisseurMapper.toEntity(dtoToSave)).thenReturn(entityToSave);
        when(fournisseurRepository.save(entityToSave)).thenReturn(savedEntity);
        when(fournisseurMapper.toDTO(savedEntity)).thenReturn(savedDto);

        // Act
        FournisseurResponseDto result = fournisseurService.save(dtoToSave);

        // Assert
        assertNotNull(result);
        assertEquals(savedDto, result);

        // Verify interactions
        verify(fournisseurMapper, times(1)).toEntity(dtoToSave);
        verify(fournisseurRepository, times(1)).save(entityToSave);
        verify(fournisseurMapper, times(1)).toDTO(savedEntity);
    }

}
