package net.hypnoz.core.service;

import net.hypnoz.core.dto.FonctionsDto;
import net.hypnoz.core.mapper.FonctionsMapper;
import net.hypnoz.core.models.Fonctions;
import net.hypnoz.core.repository.ApplicationsRepository;
import net.hypnoz.core.repository.FonctionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FonctionsServiceTest {

    @Mock
    private FonctionsRepository mockRepository;
    @Mock
    private FonctionsMapper mockFonctionsMapper;
    @Mock
    private ApplicationsRepository applicationsRepository;
    private FonctionsService fonctionsServiceUnderTest;

    @BeforeEach
    void setUp() {
        fonctionsServiceUnderTest = new FonctionsService(mockRepository, mockFonctionsMapper, applicationsRepository);
    }

    @Test
    void testSave() {
        // Setup
        final FonctionsDto fonctionsDto = FonctionsDto.builder().build();
        final FonctionsDto expectedResult = FonctionsDto.builder().build();
        when(mockFonctionsMapper.toEntity(FonctionsDto.builder().build())).thenReturn(Fonctions.builder().build());
        when(mockRepository.save(any(Fonctions.class))).thenReturn(Fonctions.builder().build());
        when(mockFonctionsMapper.toDto(any(Fonctions.class))).thenReturn(FonctionsDto.builder().build());

        // Run the test
        final FonctionsDto result = fonctionsServiceUnderTest.save(fonctionsDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteById() {
        // Setup
        // Run the test
        fonctionsServiceUnderTest.deleteById(0L);

        // Verify the results
        verify(mockRepository).deleteById(0L);
    }

    @Test
    void testFindById() {
        // Setup
        final FonctionsDto expectedResult = FonctionsDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Fonctions.builder().build()));
        when(mockFonctionsMapper.toDto(any(Fonctions.class))).thenReturn(FonctionsDto.builder().build());

        // Run the test
        final FonctionsDto result = fonctionsServiceUnderTest.findById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_FonctionsRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> fonctionsServiceUnderTest.findById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void testUpdate() {
        // Setup
        final FonctionsDto fonctionsDto = FonctionsDto.builder().build();
        final FonctionsDto expectedResult = FonctionsDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Fonctions.builder().build()));
        when(mockFonctionsMapper.toDto(any(Fonctions.class))).thenReturn(FonctionsDto.builder().build());
        when(mockFonctionsMapper.toEntity(FonctionsDto.builder().build())).thenReturn(Fonctions.builder().build());
        when(mockRepository.save(any(Fonctions.class))).thenReturn(Fonctions.builder().build());

        // Run the test
        final FonctionsDto result = fonctionsServiceUnderTest.update(fonctionsDto, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdate_FonctionsRepositoryFindByIdReturnsAbsent() {
        // Setup
        final FonctionsDto fonctionsDto = FonctionsDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> fonctionsServiceUnderTest.update(fonctionsDto, 0L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
