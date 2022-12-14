package net.hypnoz.core.service;

import net.hypnoz.core.dto.GroupesDto;
import net.hypnoz.core.mapper.GroupesMapper;
import net.hypnoz.core.models.Groupes;
import net.hypnoz.core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupesServiceTest {

    @Mock
    private GroupesRepository mockRepository;
    @Mock
    private GroupesMapper mockGroupesMapper;
    @Mock
    private GroupesApplicationsRepository groupesApplicationsRepository;
    @Mock
    private StructuresRepository structuresRepository;
    @Mock
    private ModulesRepository modulesRepository;
    @Mock
    private ApplicationsRepository applicationsRepository;
    @Mock
    private FonctionsRepository fonctionsRepository;
    @Mock
    private ModulesStructureRepository modulesStructureRepository;
    @Mock
    private GroupesModulesRepository groupesmodulesRepository;
    @Mock
    private GroupesFonctionsRepository groupesFonctionsRepository;
    @Mock
    private UsersService usersService;
    private GroupesService groupesServiceUnderTest;

    @BeforeEach
    void setUp() {
        groupesServiceUnderTest = new GroupesService(mockRepository,
                mockGroupesMapper, structuresRepository, applicationsRepository, fonctionsRepository, modulesStructureRepository,
                groupesmodulesRepository, groupesApplicationsRepository, groupesFonctionsRepository, usersService
        );
    }

    @Test
    void testSave() {
        // Setup
        final GroupesDto groupesDto = GroupesDto.builder().build();
        final GroupesDto expectedResult = GroupesDto.builder().build();
        when(mockGroupesMapper.toEntity(GroupesDto.builder().build())).thenReturn(Groupes.builder().build());
        when(mockRepository.save(Groupes.builder().build())).thenReturn(Groupes.builder().build());
        when(mockGroupesMapper.toDto(Groupes.builder().build())).thenReturn(GroupesDto.builder().build());

        // Run the test
        final GroupesDto result = groupesServiceUnderTest.save(groupesDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteById() {
        // Setup
        // Run the test
        groupesServiceUnderTest.deleteById(0L);

        // Verify the results
        verify(mockRepository).deleteById(0L);
    }

    @Test
    void testFindById() {
        // Setup
        final GroupesDto expectedResult = GroupesDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Groupes.builder().build()));
        when(mockGroupesMapper.toDto(Groupes.builder().build())).thenReturn(GroupesDto.builder().build());

        // Run the test
        final GroupesDto result = groupesServiceUnderTest.findById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_GroupesRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> groupesServiceUnderTest.findById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testUpdate() {
        // Setup
        final GroupesDto groupesDto = GroupesDto.builder().build();
        final GroupesDto expectedResult = GroupesDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Groupes.builder().build()));
        when(mockGroupesMapper.toDto(Groupes.builder().build())).thenReturn(GroupesDto.builder().build());
        when(mockGroupesMapper.toEntity(GroupesDto.builder().build())).thenReturn(Groupes.builder().build());
        when(mockRepository.save(Groupes.builder().build())).thenReturn(Groupes.builder().build());

        // Run the test
        final GroupesDto result = groupesServiceUnderTest.update(groupesDto, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdate_GroupesRepositoryFindByIdReturnsAbsent() {
        // Setup
        final GroupesDto groupesDto = GroupesDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> groupesServiceUnderTest.update(groupesDto, 0L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
