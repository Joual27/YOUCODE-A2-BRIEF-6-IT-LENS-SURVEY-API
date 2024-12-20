package ma.youcode.surveyit.dto.request.chapter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ChapterCreateDTO(
        @NotEmpty
        String title
) implements Serializable { }
