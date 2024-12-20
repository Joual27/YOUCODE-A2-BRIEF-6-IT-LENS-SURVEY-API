package ma.youcode.surveyit.dto.response.edition;

import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyEmbeddedDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record EditionEmbeddedDTO(
        Long id,
        LocalDateTime creationDate,
        LocalDateTime startDate,
        List<ChapterEmbeddedDTO> chapters
) implements Serializable {
}
