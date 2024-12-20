package ma.youcode.surveyit.dto.response.answer;

import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.question.QuestionEmbeddedDTO;
import ma.youcode.surveyit.enums.QuestionType;

import java.io.Serializable;


public record AnswerResponseDTO(
        Long id,
        String text,
        int selectionCount,
        QuestionEmbeddedDTO question
) implements Serializable { }
