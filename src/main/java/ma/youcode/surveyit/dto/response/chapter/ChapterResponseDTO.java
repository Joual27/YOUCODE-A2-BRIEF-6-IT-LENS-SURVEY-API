package ma.youcode.surveyit.dto.response.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.youcode.surveyit.dto.response.edition.EditionCustomDTO;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChapterResponseDTO(
        Long id,
        String title,
        EditionCustomDTO edition,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<ChapterEmbeddedDTO> subchapters

) implements Serializable {
}
