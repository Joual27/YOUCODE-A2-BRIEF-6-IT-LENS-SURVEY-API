package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.edition.EditionCreateDTO;
import ma.youcode.surveyit.dto.request.edition.EditionUpdateDTO;
import ma.youcode.surveyit.dto.response.edition.EditionResponseDTO;
import ma.youcode.surveyit.entity.Edition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = SurveyMapper.class)
public interface EditionMapper {

    EditionResponseDTO toResponseDTO(Edition edition);
//    EditionEmbeddedDTO toEmbeddedDTO(Edition edition);

    @Mapping(source = "surveyId" , target = "survey.id")
    Edition toEdition(EditionCreateDTO dto);
    @Mapping(source = "surveyId" , target = "survey.id")
    Edition toEdition(EditionUpdateDTO dto);


}
