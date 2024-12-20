package ma.youcode.surveyit.dto.response.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyEmbeddedDTO;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.enums.QuestionType;

import java.io.Serializable;
import java.time.LocalDateTime;

public record QuestionEmbeddedDTO(
        Long id,
        String text,
        QuestionType type,
        int answerCount
) implements Serializable {
}
