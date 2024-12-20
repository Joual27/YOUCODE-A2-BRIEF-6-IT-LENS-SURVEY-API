package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.question.QuestionCreateDTO;
import ma.youcode.surveyit.dto.request.question.QuestionUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterCustomDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.enums.QuestionType;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.QuestionMapper;
import ma.youcode.surveyit.repository.QuestionRepository;
import ma.youcode.surveyit.service.interfaces.ChapterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuestionServiceImpTest {

    @Mock
    private QuestionRepository repository;

    @Mock
    private QuestionMapper mapper;

    @Mock
    private ChapterService chapterService;

    @InjectMocks
    private QuestionServiceImp questionService;

    private QuestionCreateDTO questionCreateDTO;
    private Question question;
    private Chapter subchapter;
    private QuestionResponseDTO questionResponseDTO;
    private QuestionUpdateDTO questionUpdateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        question = new Question(1L, "Test Question", QuestionType.SINGLE_CHOICE , 0 , null , null );
        subchapter = new Chapter();
        subchapter.setId(4L);
        subchapter.setParentId(1L);
        ChapterCustomDTO chapterCustomDTO = new ChapterCustomDTO(1L , "subchapter" , new ArrayList<>());
        questionResponseDTO = new QuestionResponseDTO(1L, "Test Question", QuestionType.SINGLE_CHOICE , 0 , chapterCustomDTO , new ArrayList<>());
        questionCreateDTO = new QuestionCreateDTO("Test Question", QuestionType.SINGLE_CHOICE , 0 ,subchapter.getId() );
        questionUpdateDTO = new QuestionUpdateDTO("Updated Question", QuestionType.SINGLE_CHOICE , 0 ,subchapter.getId() );

        // Mocking the ChapterService and QuestionRepository behavior
        when(chapterService.findChapterById(1L)).thenReturn(subchapter);
        when(repository.save(any(Question.class))).thenReturn(question);
        when(mapper.toResponseDTO(any(Question.class))).thenReturn(questionResponseDTO);
    }

    @Test
    void shouldCreateQuestionSuccessfully() {
        // Arrange
        when(mapper.toQuestion(questionCreateDTO)).thenReturn(question);
        when(chapterService.findChapterById(1L)).thenReturn(subchapter);

        // Act
        QuestionResponseDTO result = questionService.createQuestion(questionCreateDTO, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Question", result.text());
        verify(repository, times(1)).save(any(Question.class));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSubchapterIsInvalid() {
        // Arrange
        subchapter.setSubchapters(List.of(new Chapter()));
        when(chapterService.findChapterById(1L)).thenReturn(subchapter);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.createQuestion(questionCreateDTO, 1L);
        });
        assertEquals("cannot added question because is not subchapter", exception.getMessage());
    }

    @Test
    void shouldEditQuestion() {
        // Arrange
        when(repository.findById(question.getId())).thenReturn(Optional.of(question));
        question.setText("Updated Question");
        when(repository.save(question)).thenReturn(question);
        when(mapper.toResponseDTO(question)).thenReturn(questionResponseDTO);
        // Act
        QuestionResponseDTO result = questionService.editQuestion(questionUpdateDTO, 1L);

        // Assert
        assertEquals(result , questionResponseDTO);
        verify(repository, times(1)).save(any(Question.class));
        assertEquals("Updated Question", question.getText());
    }

    @Test
    void shouldDeleteQuestionSuccessfully() {
        // Act
        questionService.deleteQuestion(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetAllQuestions() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Question> questionPage = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(questionPage);
        when(questionPage.map(any())).thenReturn(mock(Page.class));

        // Act
        Page<QuestionResponseDTO> result = questionService.getAllQuestions(0, 10);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void shouldGetQuestionById() {
        // Arrange
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(question));

        // Act
        QuestionResponseDTO result = questionService.getQuestion(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Question", result.text());
    }

    @Test
    void shouldReturnQuestionEntityWhenFindQuestionByIdIsCalled() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(question));

        // Act & Assert
        Question result = questionService.findQuestionById(question.getId());
        assertEquals(result , question);
        verify(repository).findById(question.getId());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindQuestionByIdIsCalled() {
        // Arrange
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            questionService.findQuestionById(1L);
        });
        assertEquals("Question not found.", exception.getMessage());
    }

    @Test
    void shouldEditAnswerCount() {
        // Act
        questionService.editAnswerCount(question);

        // Assert
        verify(repository, times(1)).save(question);
    }
}
