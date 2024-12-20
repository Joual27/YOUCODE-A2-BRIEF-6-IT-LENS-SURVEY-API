package ma.youcode.surveyit.dto.request.survey;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SurveyUpdateDTO
        (
                @NotEmpty
                String title,
                @NotEmpty
                String description,
                @NotNull
                Long ownerId
        )

        implements Serializable {
}
