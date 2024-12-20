package ma.youcode.surveyit.service.implementations;

import jakarta.persistence.EntityExistsException;
import ma.youcode.surveyit.dto.request.survey.SurveyCreateDTO;
import ma.youcode.surveyit.dto.request.survey.SurveyUpdateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResponseDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResultDTO;
import ma.youcode.surveyit.entity.*;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.SurveyMapper;
import ma.youcode.surveyit.repository.SurveyRepository;
import ma.youcode.surveyit.service.implementations.SurveyServiceImp;
import ma.youcode.surveyit.service.interfaces.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.*;

public class SurveyServiceImpTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private SurveyServiceImp surveyService;

    private Survey survey;
    private SurveyCreateDTO surveyCreateDTO;
    private SurveyUpdateDTO surveyUpdateDTO;
    private SurveyResponseDTO surveyResponseDTO;
    private Owner owner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup Survey, SurveyCreateDTO, SurveyUpdateDTO, etc.
        owner = new Owner();
        owner.setId(1L);
        Question question = new Question();
        Chapter subchapter = new Chapter();
        subchapter.setQuestions(Arrays.asList(question , question));
        Chapter chapter = new Chapter();
        chapter.setTitle("chapter");
        Edition edition = new Edition();
        edition.setChapters(Arrays.asList(chapter ,chapter));
        survey = new Survey();
        survey.setId(1L);
        survey.setTitle("Survey 1");
        survey.setEditions(Arrays.asList(edition , edition));

        surveyCreateDTO = new SurveyCreateDTO("Survey 1", "description" , 1L);
        surveyUpdateDTO = new SurveyUpdateDTO("Survey 1", "description" , 1L );
        surveyResponseDTO = new SurveyResponseDTO(1L, "Survey 1", "Description" , null , null);
    }

    @Test
    void shouldCreateSurvey() {
        // Arrange
        when(ownerService.getOwnerEntity(anyLong())).thenReturn(owner);
        when(surveyMapper.toSurvey(surveyCreateDTO)).thenReturn(survey);
        when(surveyRepository.save(survey)).thenReturn(survey);
        when(surveyMapper.toResponseDTO(survey)).thenReturn(surveyResponseDTO);

        // Act
        SurveyResponseDTO result = surveyService.createSurvey(surveyCreateDTO);

        // Assert
        assertEquals(surveyResponseDTO, result);
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    void shouldEditSurvey() {
        // Arrange
        when(surveyRepository.existsByTitleNotId(surveyUpdateDTO.title(), 1L)).thenReturn(false);
        when(surveyMapper.toSurvey(surveyUpdateDTO)).thenReturn(survey);
        when(surveyRepository.save(survey)).thenReturn(survey);
        when(surveyMapper.toResponseDTO(survey)).thenReturn(surveyResponseDTO);

        // Act
        SurveyResponseDTO result = surveyService.editSurvey(surveyUpdateDTO, 1L);

        // Assert
        assertEquals(surveyResponseDTO, result);
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    void shouldThrowEntityExistsExceptionWhenTitleIsTaken() {
        // Arrange
        when(surveyRepository.existsByTitleNotId(surveyUpdateDTO.title(), 1L)).thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            surveyService.editSurvey(surveyUpdateDTO, 1L);
        });
    }

    @Test
    void shouldDeleteSurvey() {
        // Arrange
        Long surveyId = 1L;
        doNothing().when(surveyRepository).deleteById(surveyId);

        // Act
        surveyService.deleteSurvey(surveyId);

        // Assert
        verify(surveyRepository, times(1)).deleteById(surveyId);
    }

//    @Test
//    void shouldGetSurveyResults() {
//        // Arrange
//        Question question = new Question();
//        Chapter subchapter = new Chapter();
//        subchapter.setQuestions(Arrays.asList(question , question));
//        Chapter chapter = new Chapter();
//        Edition edition = new Edition();
//        edition.setChapters(Arrays.asList(chapter ,chapter));
//        survey = new Survey();
//        survey.setId(1L);
//        survey.setTitle("Survey 1");
//        survey.setEditions(Arrays.asList(edition , edition));
//        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));
//
//        SurveyResultDTO surveyResultDTO = new SurveyResultDTO("Survey 1", null); // Assume no chapters
//
//
//        // Act
//        SurveyResultDTO result = surveyService.getSurveyResults(1L);
//
//        // Assert
//        assertEquals(surveyResultDTO, result);
//    }
@Test
void shouldGetSurveyResults() {
    // Arrange
    Survey survey = new Survey();
    survey.setTitle("Survey 1");

    // Create mock chapters and subchapters
    Chapter chapter = new Chapter();
    Chapter subchapter = new Chapter();
    Question question = new Question();
//    Answer answer = new Answer();

    question.setAnswers(List.of());
    subchapter.setQuestions(List.of(question));
    chapter.setSubchapters(List.of(subchapter));
    Edition edition = new Edition();
    edition.setChapters(List.of(chapter));
    survey.setEditions(List.of(edition));

    when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));

    // Expected result
    SurveyResultDTO expectedSurveyResult = new SurveyResultDTO(
            "Survey 1",
            List.of(
                    new SurveyResultDTO.Chapter(
                            null,
                            List.of(
                                    new SurveyResultDTO.Subchapter(
                                            null,
                                            List.of(
                                                    new SurveyResultDTO.Question(
                                                            null,
                                                            Map.of(),0
                                                    )
                                            ),
                                            null
                                    )
                            )
                    )
            )
    );

    // Act
    SurveyResultDTO result = surveyService.getSurveyResults(1L);

    // Assert
    assertEquals(expectedSurveyResult, result);
}


}
