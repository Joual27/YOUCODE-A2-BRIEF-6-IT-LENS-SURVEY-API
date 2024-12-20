package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.edition.EditionCreateDTO;
import ma.youcode.surveyit.dto.request.edition.EditionUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.edition.EditionEmbeddedDTO;
import ma.youcode.surveyit.dto.response.edition.EditionResponseDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyCustomDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyEmbeddedDTO;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Survey;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.EditionMapper;
import ma.youcode.surveyit.repository.EditionRepository;
import ma.youcode.surveyit.service.interfaces.EditionService;
import ma.youcode.surveyit.service.interfaces.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditionServiceImpTest {

    @Mock
    private EditionRepository repository;

    @Mock
    private EditionMapper mapper;

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private EditionServiceImp editionService;

    private EditionCreateDTO editionCreateDTO;
    private EditionUpdateDTO editionUpdateDTO;
    private EditionResponseDTO editionResponseDTO;
    private Edition edition;
    private Survey survey;
    private final Long editionId = 1L;
    private final Long surveyId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Setting up test data
        survey = new Survey();
        survey.setId(surveyId);
        survey.setTitle("Sample Survey");

        editionCreateDTO = new EditionCreateDTO(LocalDateTime.now(), LocalDateTime.now(), 2024, surveyId);
        editionUpdateDTO = new EditionUpdateDTO(LocalDateTime.now(), LocalDateTime.now(), 2024, surveyId);

        edition = new Edition();
        edition.setId(editionId);
        edition.setSurvey(survey);
        List<ChapterEmbeddedDTO> chapters = new ArrayList<>();
        SurveyCustomDTO surveyCustomDTO = new SurveyCustomDTO(surveyId, "simple survey", "description");
        editionResponseDTO = new EditionResponseDTO(editionId, LocalDateTime.now(), LocalDateTime.now(), surveyCustomDTO, chapters);
    }

    @Test
    void shouldCreateEditionSuccessfully() {
        // Arrange
        when(surveyService.getSurveyEntity(editionCreateDTO.surveyId())).thenReturn(survey);
        when(mapper.toEdition(editionCreateDTO)).thenReturn(edition);
        when(repository.save(edition)).thenReturn(edition);
        when(mapper.toResponseDTO(edition)).thenReturn(editionResponseDTO);

        // Act
        EditionResponseDTO result = editionService.createEdition(editionCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(editionResponseDTO, result);
        verify(surveyService).getSurveyEntity(editionCreateDTO.surveyId());
        verify(repository).save(edition);
        verify(mapper).toResponseDTO(edition);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenSurveyNotFoundDuringEditionCreation() {
        // Arrange
        when(surveyService.getSurveyEntity(editionCreateDTO.surveyId())).thenThrow(EntityNotFoundException.class);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> editionService.createEdition(editionCreateDTO));
        verify(surveyService).getSurveyEntity(editionCreateDTO.surveyId());
    }

    @Test
    void shouldEditEditionSuccessfully() {
        // Arrange
        when(mapper.toEdition(editionUpdateDTO)).thenReturn(edition);
        when(repository.save(edition)).thenReturn(edition);
        when(mapper.toResponseDTO(edition)).thenReturn(editionResponseDTO);

        // Act
        EditionResponseDTO result = editionService.editEdition(editionUpdateDTO, editionId);

        // Assert
        assertNotNull(result);
        assertEquals(editionResponseDTO, result);
        verify(repository).save(edition);
        verify(mapper).toResponseDTO(edition);
    }


    @Test
    void shouldDeleteEditionSuccessfully() {
        // Arrange
        doNothing().when(repository).deleteById(editionId);

        // Act
        editionService.deleteEdition(editionId);

        // Assert
        verify(repository).deleteById(editionId);
    }

    @Test
    void shouldReturnPageOfEditionsWhenGetAllEditionsCalled() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Edition> editionsPage = new PageImpl<>(Arrays.asList(edition, edition));
        when(repository.findAll(pageable)).thenReturn(editionsPage);
        Page<EditionResponseDTO> responseDTOPage = mock(Page.class);
        when(mapper.toResponseDTO(any(Edition.class))).thenReturn(editionResponseDTO);

        // Act
        Page<EditionResponseDTO> result = editionService.getAllEditions(0, 10);

        // Assert
        assertNotNull(result);
        verify(repository).findAll(pageable);
    }

    @Test
    void shouldReturnEditionResponseDTOWhenGetEditionById() {
        // Arrange
        when(repository.findById(editionId)).thenReturn(Optional.of(edition));
        when(mapper.toResponseDTO(edition)).thenReturn(editionResponseDTO);

        // Act
        EditionResponseDTO result = editionService.getEdition(editionId);

        // Assert
        assertNotNull(result);
        assertEquals(editionResponseDTO, result);
        verify(repository).findById(editionId);
        verify(mapper).toResponseDTO(edition);
    }


    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetEditionEntityFails() {
        // Arrange
        when(repository.findById(editionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> editionService.getEditionEntity(editionId));

        verify(repository).findById(editionId);
    }
}
