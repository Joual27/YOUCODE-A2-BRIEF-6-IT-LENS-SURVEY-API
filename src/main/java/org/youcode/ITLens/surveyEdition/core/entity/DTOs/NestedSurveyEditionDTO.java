package org.youcode.ITLens.surveyEdition.core.entity.DTOs;

import org.youcode.ITLens.survey.core.entity.DTOs.NestedSurveyDTO;

import java.time.LocalDate;

public record NestedSurveyEditionDTO(LocalDate startDate , int year , NestedSurveyDTO survey) {
}
