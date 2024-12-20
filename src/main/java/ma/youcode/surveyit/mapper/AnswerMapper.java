package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.answer.AnswerCreateDTO;
import ma.youcode.surveyit.dto.request.answer.AnswerUpdateDTO;
import ma.youcode.surveyit.dto.response.answer.AnswerResponseDTO;
import ma.youcode.surveyit.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerResponseDTO toResponseDTO(Answer answer);

    Answer toAnswer(AnswerCreateDTO dto);
    Answer toAnswer(AnswerUpdateDTO dto);


}
