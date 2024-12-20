package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.owner.OwnerCreateDTO;
import ma.youcode.surveyit.dto.request.owner.OwnerUpdateDTO;
import ma.youcode.surveyit.dto.response.owner.OwnerEmbeddedDTO;
import ma.youcode.surveyit.dto.response.owner.OwnerResponseDTO;
import ma.youcode.surveyit.entity.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerResponseDTO toResponseDTO(Owner owner);
    OwnerEmbeddedDTO toEmbeddedDTO(Owner owner);

    Owner toOwner(OwnerCreateDTO dto);
    Owner toOwner(OwnerUpdateDTO dto);


}
