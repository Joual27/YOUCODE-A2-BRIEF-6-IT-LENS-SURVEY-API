package ma.youcode.surveyit.dto.response.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.youcode.surveyit.dto.response.question.QuestionEmbeddedDTO;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChapterCustomDTO(
                            Long id,
                            String title,
                            List<ChapterCustomDTO> subchapters

) implements Serializable {
}
