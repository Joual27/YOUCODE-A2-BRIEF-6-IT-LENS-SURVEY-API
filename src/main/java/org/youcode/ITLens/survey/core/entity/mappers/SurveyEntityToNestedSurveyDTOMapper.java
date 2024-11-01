package org.youcode.ITLens.survey.core.entity.mappers;

import org.mapstruct.Mapper;
import org.youcode.ITLens.common.interfaces.BaseMapper;
import org.youcode.ITLens.survey.core.entity.DTOs.NestedSurveyDTO;
import org.youcode.ITLens.survey.core.entity.Survey;

@Mapper(componentModel = "spring")
public interface SurveyEntityToNestedSurveyDTOMapper extends BaseMapper<Survey , NestedSurveyDTO> {
}
