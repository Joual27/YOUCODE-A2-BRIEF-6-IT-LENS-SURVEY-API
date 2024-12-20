package ma.youcode.surveyit.dto.response.edition;

import java.io.Serializable;
import java.time.LocalDateTime;

public record EditionCustomDTO(
        Long id,
        LocalDateTime creationDate,
        LocalDateTime startDate
) implements Serializable {
}
