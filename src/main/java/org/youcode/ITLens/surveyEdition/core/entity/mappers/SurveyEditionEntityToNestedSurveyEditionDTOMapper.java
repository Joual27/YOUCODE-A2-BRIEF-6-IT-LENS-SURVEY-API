package org.youcode.ITLens.surveyEdition.core.entity.mappers;

import org.mapstruct.Mapper;
import org.youcode.ITLens.common.interfaces.BaseMapper;
import org.youcode.ITLens.surveyEdition.core.entity.DTOs.NestedSurveyEditionDTO;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

@Mapper(componentModel = "spring" , uses = SurveyEditionEntityToNestedSurveyEditionDTOMapper.class)
public interface SurveyEditionEntityToNestedSurveyEditionDTOMapper extends BaseMapper<SurveyEdition , NestedSurveyEditionDTO> {
}
