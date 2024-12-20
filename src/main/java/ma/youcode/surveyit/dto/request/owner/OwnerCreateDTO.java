package ma.youcode.surveyit.dto.request.owner;

import jakarta.validation.constraints.NotEmpty;
import ma.youcode.surveyit.annotation.interfaces.Unique;
import ma.youcode.surveyit.entity.Owner;

import java.io.Serializable;

public record OwnerCreateDTO(
        @NotEmpty
        @Unique(entity = Owner.class, field = "name")
        String name
) implements Serializable {
}
