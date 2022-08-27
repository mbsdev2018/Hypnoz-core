package net.hypnoz.core.service;

import net.hypnoz.core.dto.UsersDto;
import net.hypnoz.core.mapper.UsersMapper;
import net.hypnoz.core.models.Users;
import net.hypnoz.core.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository mockRepository;
    @Mock
    private UsersMapper mockUsersMapper;

    private UsersService usersServiceUnderTest;

    @BeforeEach
    void setUp() {
        usersServiceUnderTest = new UsersService(mockRepository, mockUsersMapper);
    }

    @Test
    void testSave() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();
        final UsersDto expectedResult = UsersDto.builder().build();
        when(mockUsersMapper.toEntity(UsersDto.builder().build())).thenReturn(Users.builder().build());
        when(mockRepository.save(Users.builder().build())).thenReturn(Users.builder().build());
        when(mockUsersMapper.toDto(Users.builder().build())).thenReturn(UsersDto.builder().build());

        // Run the test
        final UsersDto result = usersServiceUnderTest.save(usersDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDeleteById() {
        // Setup
        // Run the test
        usersServiceUnderTest.deleteById(0L);

        // Verify the results
        verify(mockRepository).deleteById(0L);
    }

    @Test
    void testFindById() {
        // Setup
        final UsersDto expectedResult = UsersDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Users.builder().build()));
        when(mockUsersMapper.toDto(Users.builder().build())).thenReturn(UsersDto.builder().build());

        // Run the test
        final UsersDto result = usersServiceUnderTest.findById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_UsersRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> usersServiceUnderTest.findById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testFindByCondition() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();

        // Configure UsersRepository.findAll(...).
        final Page<Users> users = new PageImpl<>(List.of(Users.builder().build()));
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(users);

        when(mockUsersMapper.toDto(List.of(Users.builder().build()))).thenReturn(List.of(UsersDto.builder().build()));

        // Run the test
        final Page<UsersDto> result = usersServiceUnderTest.findByCondition(usersDto, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testFindByCondition_UsersRepositoryReturnsNoItems() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));
        when(mockUsersMapper.toDto(List.of(Users.builder().build()))).thenReturn(List.of(UsersDto.builder().build()));

        // Run the test
        final Page<UsersDto> result = usersServiceUnderTest.findByCondition(usersDto, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testFindByCondition_UsersMapperReturnsNoItems() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();

        // Configure UsersRepository.findAll(...).
        final Page<Users> users = new PageImpl<>(List.of(Users.builder().build()));
        when(mockRepository.findAll(any(Pageable.class))).thenReturn(users);

        when(mockUsersMapper.toDto(List.of(Users.builder().build()))).thenReturn(Collections.emptyList());

        // Run the test
        final Page<UsersDto> result = usersServiceUnderTest.findByCondition(usersDto, PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();
        final UsersDto expectedResult = UsersDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.of(Users.builder().build()));
        when(mockUsersMapper.toDto(Users.builder().build())).thenReturn(UsersDto.builder().build());
        when(mockUsersMapper.toEntity(UsersDto.builder().build())).thenReturn(Users.builder().build());
        when(mockRepository.save(Users.builder().build())).thenReturn(Users.builder().build());

        // Run the test
        final UsersDto result = usersServiceUnderTest.update(usersDto, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdate_UsersRepositoryFindByIdReturnsAbsent() {
        // Setup
        final UsersDto usersDto = UsersDto.builder().build();
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> usersServiceUnderTest.update(usersDto, 0L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
