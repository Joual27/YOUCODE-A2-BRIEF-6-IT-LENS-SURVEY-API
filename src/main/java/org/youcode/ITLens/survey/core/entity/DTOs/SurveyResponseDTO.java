package org.youcode.ITLens.survey.core.entity.DTOs;

import org.youcode.ITLens.owner.core.entities.DTOs.NestedOwnerDTO;
import org.youcode.ITLens.owner.core.entities.Owner;
import org.youcode.ITLens.surveyEdition.core.entity.DTOs.NestedSurveyEditionDTO;

import java.util.List;

public record SurveyResponseDTO(Long id , String title , String description , NestedOwnerDTO owner, List<NestedSurveyEditionDTO> editions) {
}
