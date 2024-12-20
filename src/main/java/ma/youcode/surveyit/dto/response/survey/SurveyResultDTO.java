package ma.youcode.surveyit.dto.response.survey;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record SurveyResultDTO(String surveyTitle, List<Chapter> chapters) implements Serializable {


    public record Chapter(String title, @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Subchapter> subchapters) {}
    public record Subchapter(String title, @JsonInclude(JsonInclude.Include.NON_EMPTY) List<Question> questions , @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Subchapter> subchapters) { }
    public record Question(String question , Map<String, Integer> answers , int totalAnswers){}

}
