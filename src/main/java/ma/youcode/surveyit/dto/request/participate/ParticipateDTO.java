package ma.youcode.surveyit.dto.request.participate;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;


public record ParticipateDTO() implements Serializable {
    public record Responses(List<Response> responses) {}

    public record Response(Long questionId,
                           @JsonInclude(JsonInclude.Include.NON_NULL)
                           Long answerId,
                           @JsonInclude(JsonInclude.Include.NON_NULL)
                           List<Answer> answers) {
    }

    public record Answer(Long answerId) {
    }
}
