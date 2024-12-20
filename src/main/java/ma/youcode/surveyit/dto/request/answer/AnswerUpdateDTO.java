package ma.youcode.surveyit.dto.request.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Question;

import java.io.Serializable;

public record AnswerUpdateDTO
        (
                @NotEmpty
                String text,
                @NotNull
                int selectionCount,
                @Exists(entity = Question.class , message = "Question not found.")
                Long questionId
        )

        implements Serializable {
}
