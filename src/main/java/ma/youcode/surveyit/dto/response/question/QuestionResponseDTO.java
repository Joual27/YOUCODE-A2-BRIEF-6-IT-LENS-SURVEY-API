package ma.youcode.surveyit.dto.response.question;

import ma.youcode.surveyit.dto.response.answer.AnswerEmbeddedDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterCustomDTO;
import ma.youcode.surveyit.enums.QuestionType;

import java.io.Serializable;
import java.util.List;


public record QuestionResponseDTO(
        Long id,
        String text,
        QuestionType type,
        int answerCount,
        ChapterCustomDTO subchapter,
        List<AnswerEmbeddedDTO> answers
) implements Serializable { }
