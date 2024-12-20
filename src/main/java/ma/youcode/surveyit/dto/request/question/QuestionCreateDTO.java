package ma.youcode.surveyit.dto.request.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.enums.QuestionType;

import java.io.Serializable;

public record QuestionCreateDTO(
        @NotEmpty
        String text,
        @NotNull(message = "Question type cannot be null")
        QuestionType type,
        @NotNull
        int answerCount,
        @Exists(entity = Chapter.class , message = "Subchapter not found.")
        Long subchapterId
) implements Serializable { }
