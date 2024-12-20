package ma.youcode.surveyit.dto.response.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        int status,
        String message,
        LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Map<String, String>errors) implements Serializable {

//    public ErrorResponseDTO(int status, String message, LocalDateTime time) {
//        this(status, message, time, Optional.empty());
//    }
//
//    public ErrorResponseDTO(int status, String message, LocalDateTime time , Map<String , String> errors) {
//        this(status, message, time, Optional.of(errors));
//    }

}
