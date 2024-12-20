package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.owner.OwnerCreateDTO;
import ma.youcode.surveyit.dto.request.owner.OwnerUpdateDTO;
import ma.youcode.surveyit.dto.response.owner.OwnerResponseDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyEmbeddedDTO;
import ma.youcode.surveyit.entity.Owner;
import jakarta.persistence.EntityNotFoundException;
import ma.youcode.surveyit.mapper.OwnerMapper;
import ma.youcode.surveyit.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceImpTest {

    @Mock
    private OwnerRepository repository;

    @Mock
    private OwnerMapper mapper;

    @InjectMocks
    private OwnerServiceImp ownerService;

    private OwnerCreateDTO ownerCreateDTO;
    private OwnerUpdateDTO ownerUpdateDTO;
    private OwnerResponseDTO ownerResponseDTO;
    private Owner owner;
    private final Long ownerId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        ownerCreateDTO = new OwnerCreateDTO("John Doe");
        ownerUpdateDTO = new OwnerUpdateDTO("Jane Doe");

        owner = new Owner();
        owner.setId(ownerId);
        owner.setName("John Doe");
        List<SurveyEmbeddedDTO> surveys = new ArrayList<>();
        ownerResponseDTO = new OwnerResponseDTO(ownerId, "John Doe", surveys);
    }

    @Test
    void shouldCreateOwnerSuccessfully() {
        // Arrange
        when(mapper.toOwner(ownerCreateDTO)).thenReturn(owner);
        when(repository.save(owner)).thenReturn(owner);
        when(mapper.toResponseDTO(owner)).thenReturn(ownerResponseDTO);

        // Act
        OwnerResponseDTO result = ownerService.createOwner(ownerCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(ownerResponseDTO, result);
        verify(repository).save(owner);
        verify(mapper).toResponseDTO(owner);
    }

    @Test
    void shouldEditOwnerSuccessfully() {
        // Arrange
        when(mapper.toOwner(ownerUpdateDTO)).thenReturn(owner);
        when(repository.save(owner)).thenReturn(owner);
        when(mapper.toResponseDTO(owner)).thenReturn(ownerResponseDTO);

        // Act
        OwnerResponseDTO result = ownerService.editOwner(ownerUpdateDTO, ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(ownerResponseDTO, result);
        verify(repository).save(owner);
        verify(mapper).toResponseDTO(owner);
    }

    @Test
    void shouldDeleteOwnerSuccessfully() {
        // Arrange
        doNothing().when(repository).deleteById(ownerId);

        // Act
        ownerService.deleteOwner(ownerId);

        // Assert
        verify(repository).deleteById(ownerId);
    }

    @Test
    void shouldReturnPageOfOwnersWhenGetAllOwnersCalled() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Owner> ownerPage = new PageImpl<>(java.util.Arrays.asList(owner, owner));
        when(repository.findAll(pageable)).thenReturn(ownerPage);
        when(mapper.toResponseDTO(any(Owner.class))).thenReturn(ownerResponseDTO);

        // Act
        Page<OwnerResponseDTO> result = ownerService.getAllOwners(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(repository).findAll(pageable);
    }

    @Test
    void shouldReturnOwnerResponseDTOWhenGetOwnerById() {
        // Arrange
        when(repository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(mapper.toResponseDTO(owner)).thenReturn(ownerResponseDTO);

        // Act
        OwnerResponseDTO result = ownerService.getOwner(ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(ownerResponseDTO, result);
        verify(repository).findById(ownerId);
        verify(mapper).toResponseDTO(owner);
    }

    @Test
    void shouldReturnOwnerEntityWhenFGetOwnerEntity() {
        // Arrange
        when(repository.findById(ownerId)).thenReturn(Optional.of(owner));

        // Act
        Owner result = ownerService.getOwnerEntity(ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(owner, result);
        verify(repository).findById(ownerId);
    }

    @Test
    void shouldReturnOwnerEntityWhenFGetOwnerEntityIsFails() {
        // Arrange
        when(repository.findById(ownerId)).thenReturn(Optional.empty());

        // Act
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> ownerService.getOwnerEntity(ownerId));

        // Assert
        assertEquals(e.getMessage() , "Owner not found.");
        verify(repository).findById(ownerId);
    }
}
