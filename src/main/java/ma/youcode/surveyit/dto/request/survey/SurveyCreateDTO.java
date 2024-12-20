package ma.youcode.surveyit.dto.request.survey;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ma.youcode.surveyit.annotation.interfaces.Unique;
import ma.youcode.surveyit.entity.Owner;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.entity.Survey;

import java.io.Serializable;

public record SurveyCreateDTO(
        @NotEmpty @Unique(field = "title" , entity = Survey.class) String title ,
        @NotEmpty String description,
        @NotNull @Exists(entity = Owner.class , message = "Owner not found.") Long ownerId
) implements Serializable { }
