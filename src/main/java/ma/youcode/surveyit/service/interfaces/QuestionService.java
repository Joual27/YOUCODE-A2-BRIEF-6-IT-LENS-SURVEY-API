package ma.youcode.surveyit.service.interfaces;

import ma.youcode.surveyit.dto.request.question.QuestionCreateDTO;
import ma.youcode.surveyit.dto.request.question.QuestionUpdateDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.entity.Question;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    Page<QuestionResponseDTO> getAllQuestions(int page , int size);
    QuestionResponseDTO getQuestion(Long id);
    QuestionResponseDTO createQuestion(QuestionCreateDTO dto , Long subchapterId);
    QuestionResponseDTO editQuestion(QuestionUpdateDTO dto , Long id);
    void deleteQuestion(Long id);
    Question findQuestionById(Long id);
    void editAnswerCount(Question question);

}
