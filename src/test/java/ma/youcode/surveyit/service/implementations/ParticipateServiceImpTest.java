package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.participate.ParticipateDTO;
import ma.youcode.surveyit.entity.Answer;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.enums.QuestionType;
import ma.youcode.surveyit.service.interfaces.AnswerService;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipateServiceImpTest {

    @Mock
    private AnswerService answerService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ParticipateServiceImp participateService;

    private ParticipateDTO.Response singleChoiceResponse;
    private ParticipateDTO.Response multiChoiceResponse;
    private Question singleChoiceQuestion;
    private Question multiChoiceQuestion;
    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Prepare test data
        singleChoiceQuestion = new Question();
        singleChoiceQuestion.setId(1L);
        singleChoiceQuestion.setType(QuestionType.SINGLE_CHOICE);
        singleChoiceQuestion.setAnswerCount(0);

        multiChoiceQuestion = new Question();
        multiChoiceQuestion.setId(2L);
        multiChoiceQuestion.setType(QuestionType.MULTI_CHOICE);
        multiChoiceQuestion.setAnswerCount(0);

        answer1 = new Answer();
        answer1.setId(1L);
        answer1.setSelectionCount(0);

        answer2 = new Answer();
        answer2.setId(2L);
        answer2.setSelectionCount(0);

        // Single choice response
        singleChoiceResponse = new ParticipateDTO.Response(1L, 1L, List.of());

        // Multi choice response
        multiChoiceResponse = new ParticipateDTO.Response(2L, null, List.of(
                new ParticipateDTO.Answer(1L),
                new ParticipateDTO.Answer(2L)
        ));
    }

    @Test
    void shouldHandleSingleChoiceResponseSuccessfully() {
        // Arrange
        when(questionService.findQuestionById(singleChoiceResponse.questionId())).thenReturn(singleChoiceQuestion);
        when(answerService.findAnswerById(singleChoiceResponse.answerId())).thenReturn(answer1);

        // Mocking the void methods
        doNothing().when(questionService).editAnswerCount(singleChoiceQuestion);
        doNothing().when(answerService).editSelectionCount(answer1);

        // Act
        participateService.participateProcess(singleChoiceResponse);

        // Assert
        // Make sure the answer count and selection count have been updated
        assertEquals(1, singleChoiceQuestion.getAnswerCount(), "Answer count should be updated to 1");
        assertEquals(1, answer1.getSelectionCount(), "Selection count should be updated to 1");

        // Verify that the methods were called on the mock services
        verify(questionService).editAnswerCount(singleChoiceQuestion);
        verify(answerService).editSelectionCount(answer1);
    }



    @Test
    void shouldThrowExceptionWhenSingleChoiceResponseHasMultipleAnswers() {
        // Arrange
        ParticipateDTO.Response invalidResponse = new ParticipateDTO.Response(1L, null, List.of(new ParticipateDTO.Answer(1L), new ParticipateDTO.Answer(2L)));
        when(questionService.findQuestionById(invalidResponse.questionId())).thenReturn(singleChoiceQuestion);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> participateService.participateProcess(invalidResponse));
        assertEquals("Single-choice question cannot have multiple answers.", thrown.getMessage());
    }

    @Test
    void shouldHandleMultiChoiceResponseSuccessfully() {


        // Assuming multiChoiceResponse.answers() contains two responses
        when(questionService.findQuestionById(multiChoiceResponse.questionId())).thenReturn(multiChoiceQuestion);

        // Mock each answer based on the response IDs
        for (ParticipateDTO.Answer response : multiChoiceResponse.answers()) {
            if (response.answerId() == 1L) {
                when(answerService.findAnswerById(1L)).thenReturn(answer1);
            } else if (response.answerId() == 2L) {
                when(answerService.findAnswerById(2L)).thenReturn(answer2);
            }
        }

        // Mock the void methods
        doNothing().when(questionService).editAnswerCount(multiChoiceQuestion);
        doNothing().when(answerService).editSelectionCount(answer1);
        doNothing().when(answerService).editSelectionCount(answer2);

        // Act
        participateService.participateProcess(multiChoiceResponse);

        // Assert
        assertEquals(2, multiChoiceQuestion.getAnswerCount());  // Two answers should be counted for the question
        assertEquals(1, answer1.getSelectionCount());  // Answer 1 should have selection count of 1
        assertEquals(1, answer2.getSelectionCount());  // Answer 2 should have selection count of 1

        // Verify the methods were called on the mocks
        verify(questionService).editAnswerCount(multiChoiceQuestion);
        verify(answerService).editSelectionCount(answer1);
        verify(answerService).editSelectionCount(answer2);
    }



    @Test
    void shouldHandleSingleChoiceWhenNoAnswerIdProvided() {
        // Arrange
        singleChoiceResponse = new ParticipateDTO.Response(1L, null, null); // No answerId provided
        when(questionService.findQuestionById(singleChoiceResponse.questionId())).thenReturn(singleChoiceQuestion);

        // Act
        participateService.participateProcess(singleChoiceResponse);

        // Assert
        assertEquals(0, singleChoiceQuestion.getAnswerCount()); // No answer selected
        verify(questionService, never()).editAnswerCount(singleChoiceQuestion);
    }
}
