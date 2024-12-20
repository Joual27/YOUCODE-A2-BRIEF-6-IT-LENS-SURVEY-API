package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.question.QuestionCreateDTO;
import ma.youcode.surveyit.dto.request.question.QuestionUpdateDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionResponseDTO toResponseDTO(Question question);

    Question toQuestion(QuestionCreateDTO dto);
    Question toQuestion(QuestionUpdateDTO dto);


}
