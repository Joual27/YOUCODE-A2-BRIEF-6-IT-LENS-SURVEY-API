package org.youcode.ITLens.owner.core.entities.DTOs;

import org.youcode.ITLens.survey.core.entity.DTOs.NestedSurveyDTO;

import java.util.List;

public record OwnerResponseDTO(Long id , String name , List<NestedSurveyDTO> surveys){
}
