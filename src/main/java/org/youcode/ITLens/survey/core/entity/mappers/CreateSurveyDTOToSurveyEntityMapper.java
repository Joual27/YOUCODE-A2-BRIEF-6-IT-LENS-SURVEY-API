package org.youcode.ITLens.survey.core.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.youcode.ITLens.common.interfaces.BaseMapper;
import org.youcode.ITLens.survey.core.entity.DTOs.CreateSurveyDTO;
import org.youcode.ITLens.survey.core.entity.Survey;

@Mapper(componentModel = "spring")
public interface CreateSurveyDTOToSurveyEntityMapper extends BaseMapper<Survey , CreateSurveyDTO> {

    @Mapping(target = "owner.id" , source = "ownerId")
    Survey toEntity(CreateSurveyDTO dto);
}
