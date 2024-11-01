package org.youcode.ITLens.survey.core.entity.mappers;

import org.mapstruct.Mapper;
import org.youcode.ITLens.common.interfaces.BaseMapper;
import org.youcode.ITLens.owner.core.entities.mappers.OwnerEntityToNestedOwnerDTOMapper;
import org.youcode.ITLens.survey.core.entity.DTOs.SurveyResponseDTO;
import org.youcode.ITLens.survey.core.entity.Survey;

@Mapper(componentModel = "spring" , uses = OwnerEntityToNestedOwnerDTOMapper.class)
public interface SurveyEntityToSurveyResponseDTOMapper extends BaseMapper<Survey , SurveyResponseDTO>{
}
