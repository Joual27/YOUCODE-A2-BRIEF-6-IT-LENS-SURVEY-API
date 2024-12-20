package ma.youcode.surveyit.dto.request.edition;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import ma.youcode.surveyit.entity.Survey;
import ma.youcode.surveyit.annotation.interfaces.Exists;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;

public record EditionCreateDTO(
        @NotNull LocalDateTime creationDate ,
        @NotNull LocalDateTime startDate,
        @NotNull int year,
        @NotNull @Exists(entity = Survey.class , message = "Survey not found.") Long surveyId
) implements Serializable {

    @AssertTrue(message = "Year must be valid")
    public boolean isValidYear(){
        return year >= Year.now().getValue();
    }

    @AssertTrue(message = "Start date must be valid")
    public boolean isStartDateValid(){
        return startDate.isAfter(creationDate);
    }
}

