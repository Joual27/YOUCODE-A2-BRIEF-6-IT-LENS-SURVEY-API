package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.auth.OwnerRegistrationDTO;
import ma.youcode.surveyit.entity.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    Owner toOwner(OwnerRegistrationDTO dto);
}
