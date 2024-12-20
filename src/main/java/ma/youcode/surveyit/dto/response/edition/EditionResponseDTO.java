package ma.youcode.surveyit.dto.response.edition;

import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyCustomDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyEmbeddedDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public record EditionResponseDTO(
                          Long id,
                          LocalDateTime creationDate,
                          LocalDateTime startDate,
                          SurveyCustomDTO survey,
                          List<ChapterEmbeddedDTO> chapters
) implements Serializable {
}
