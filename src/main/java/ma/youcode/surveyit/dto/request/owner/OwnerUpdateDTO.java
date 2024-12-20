package ma.youcode.surveyit.dto.request.owner;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public record OwnerUpdateDTO
        (
                @NotEmpty
                String name
        )

        implements Serializable {
}
