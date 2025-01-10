package ma.youcode.surveyit.dto.request.auth;

import ma.youcode.surveyit.annotation.interfaces.Unique;
import ma.youcode.surveyit.entity.Owner;

public record OwnerRegistrationDTO(String username , String password , @Unique(entity = Owner.class , field = "name") String name) {
}
