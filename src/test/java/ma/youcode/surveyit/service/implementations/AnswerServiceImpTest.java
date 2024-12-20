package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.answer.AnswerCreateDTO;
import ma.youcode.surveyit.dto.request.answer.AnswerUpdateDTO;
import ma.youcode.surveyit.dto.response.answer.AnswerResponseDTO;
import ma.youcode.surveyit.dto.response.question.QuestionEmbeddedDTO;
import ma.youcode.surveyit.entity.Answer;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.enums.QuestionType;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.AnswerMapper;
import ma.youcode.surveyit.repository.AnswerRepository;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerServiceImpTest {

    @Mock
    private AnswerRepository repository;

    @Mock
    private AnswerMapper mapper;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private AnswerServiceImp answerService;

    private AnswerCreateDTO answerCreateDTO;
    private AnswerUpdateDTO answerUpdateDTO;
    private AnswerResponseDTO answerResponseDTO;
    private Answer answer;
    private Question question;
    private final Long answerId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        question = new Question();
        question.setId(1L);
        question.setText("Sample Question");

        answerCreateDTO = new AnswerCreateDTO("Sample Answer",0, 1L);
        answerUpdateDTO = new AnswerUpdateDTO("Updated Answer" , 1 , 1L);

        answer = new Answer();
        answer.setId(answerId);
        answer.setText("Sample Answer");
        answer.setQuestion(question);
        QuestionEmbeddedDTO questionEmbeddedDTO = new QuestionEmbeddedDTO(1L , "new Question", QuestionType.SINGLE_CHOICE ,0);
        answerResponseDTO = new AnswerResponseDTO(answerId, "Sample Answer", 0 , questionEmbeddedDTO);
    }

    @Test
    void shouldCreateAnswerSuccessfully() {
        // Arrange
        when(questionService.findQuestionById(answerCreateDTO.questionId())).thenReturn(question);
        when(mapper.toAnswer(answerCreateDTO)).thenReturn(answer);
        when(repository.save(answer)).thenReturn(answer);
        when(mapper.toResponseDTO(answer)).thenReturn(answerResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.createAnswer(answerCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(answerResponseDTO, result);
        verify(questionService).findQuestionById(answerCreateDTO.questionId());
        verify(repository).save(answer);
        verify(mapper).toResponseDTO(answer);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCreateAnswerFails() {
        // Arrange
        when(questionService.findQuestionById(answerCreateDTO.questionId())).thenThrow(EntityNotFoundException.class);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> answerService.createAnswer(answerCreateDTO));
        verify(questionService).findQuestionById(answerCreateDTO.questionId());
    }

    @Test
    void shouldReturnAnswerResponseDTOWhenGetAnswerById() {
        // Arrange
        when(repository.findById(answerId)).thenReturn(Optional.of(answer));
        when(mapper.toResponseDTO(answer)).thenReturn(answerResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.getAnswer(answerId);

        // Assert
        assertNotNull(result);
        assertEquals(answerResponseDTO, result);
        verify(repository).findById(answerId);
        verify(mapper).toResponseDTO(answer);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetAnswerByIdFails() {
        // Arrange
        when(repository.findById(answerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> answerService.findAnswerById(answerId));

        verify(repository).findById(answerId);
    }

    @Test
    void shouldReturnAnswerEntityWhenGetAnswerByIdFails() {
        // Arrange
        when(repository.findById(answerId)).thenReturn(Optional.of(answer));

        // Act & Assert
        Answer result = answerService.findAnswerById(answerId);
        assertNotNull(result);
        verify(repository).findById(answerId);
    }

    @Test
    void shouldDeleteAnswerSuccessfully() {
        // Arrange
        doNothing().when(repository).deleteById(answerId);

        // Act
        answerService.deleteAnswer(answerId);

        // Assert
        verify(repository).deleteById(answerId);
    }

    @Test
    void shouldReturnPageOfAnswersWhenGetAllAnswersCalled() {

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Answer> answersPage = new PageImpl<>(Arrays.asList(new Answer(), new Answer()));
        when(repository.findAll(pageable)).thenReturn(answersPage);

        AnswerResponseDTO mockResponseDTO = new AnswerResponseDTO(1L , "simple answer" , 0 , new QuestionEmbeddedDTO(1L , "simple question" , QuestionType.SINGLE_CHOICE ,0));
        when(mapper.toResponseDTO(any(Answer.class))).thenReturn(mockResponseDTO);

        Page<AnswerResponseDTO> result = answerService.getAllAnswers(0, 10);
        assertNotNull(result);
        verify(repository).findAll(pageable);
    }

    @Test
    void shouldReturnUpdatedAnswerResponseDTOWhenEditAnswerSuccessfully() {
        // Arrange
        when(mapper.toAnswer(answerUpdateDTO)).thenReturn(answer);
        when(repository.save(answer)).thenReturn(answer);
        when(mapper.toResponseDTO(answer)).thenReturn(answerResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.editAnswer(answerUpdateDTO, answerId);

        // Assert
        assertNotNull(result);
        assertEquals(answerResponseDTO, result);
        verify(repository).save(answer);
        verify(mapper).toResponseDTO(answer);
    }

}